package Aquavias.vue.GUI;

import java.io.*;
import java.lang.String.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.*;
import java.io.File;

import Aquavias.controller.ControllerIG;
import Aquavias.model.Niveau;
import Aquavias.model.Generation;
import Aquavias.vue.GUI.VueIG;


public class Fenetre extends JFrame{

  private JPanel contenu = (JPanel) getContentPane();
  private int debut = 1;
  private int max = nblvl();
  private ControllerIG c;

  public Fenetre(){
    c = new ControllerIG();
    this.setTitle("Aquavias");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    contenu.setLayout(new FlowLayout());

    menuAccueil();
  }

  // ACCUEIL

  public void menuAccueil() {        // lance l'accueil
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    pan.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    gc.weightx = 3;
    gc.weighty = 4;
    JLabel labelAquavias = titre();
    gc.gridwidth = 1;
    pan.add(labelAquavias, gc);
    JButton boutonJouer = boutonJouer();
    gc.gridy = 2;
    pan.add(boutonJouer, gc);
    JButton boutonAide = boutonAide();
    gc.gridy = 3;
    pan.add(boutonAide, gc);
    JButton boutonQuitter = boutonQuitter();
    gc.gridy = 4;
    pan.add(boutonQuitter, gc);

    this.setContentPane(pan);
    this.setVisible(true);
  }


  public void jouer() {               // lance le menu jouer depuis lequel on selectionne un niveau
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    pan.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    gc.weightx = 5;
    gc.weighty = 7;
    JButton bontonMenu = boutonRetour();
    gc.gridx = 0; gc.gridy = 0;
    pan.add(bontonMenu, gc);
    JButton boutonJouer = lancerJeu();
    gc.gridx = 2; gc.gridy = 1;
    pan.add(boutonJouer, gc);
    JLabel labelChoix = labelChoix();
    gc.gridx = 2; gc.gridy = 2;
    pan.add(labelChoix, gc);
    JButton boutonAleaF = boutonAleaFacile();
    gc.gridx = 1; gc.gridy = 3;
    pan.add(boutonAleaF, gc);
    JButton boutonAleaN = boutonAleaNormal();
    gc.gridx = 2; gc.gridy = 3;
    pan.add(boutonAleaN, gc);
    JButton boutonAleaD = boutonAleaDifficile();
    gc.gridx = 3; gc.gridy = 3;
    pan.add(boutonAleaD, gc);
    JPanel vide = new JPanel();
    gc.gridx = 4; gc.gridy = 6;
    pan.add(vide, gc);
  }


  // PARTIE SELECTION DE NIVEAU

  public void niveaux() {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    JPanel panCenter = centre();           // affichage des boutons de chaque niveau
    JPanel panSouth = panSouth();          // pour naviguer entre les différentes pages
    JPanel panNorth = panNorth();          // bouton retour + titre

    pan.setLayout(new BorderLayout());
    pan.add(panCenter, BorderLayout.CENTER);
    pan.add(panNorth, BorderLayout.NORTH);
    pan.add(panSouth, BorderLayout.SOUTH);
    pan.add(new JLabel(""), BorderLayout.EAST);
    pan.add(new JLabel(""), BorderLayout.WEST);
    pan.setVisible(true);
  }


  // SOUS FONCTIONS DE NIVEAU

  JPanel centre() {
    JPanel panCenter = new JPanel();            // partie au centre (les niveaux)
    panCenter.setLayout(new GridLayout(3,3,40,40));

    ArrayList<JButton> listeNiveaux = new ArrayList<JButton>();     // on regroupe tous les niveaux dans une ArrayList pour les afficher
    for(int i = 1; i < max; i++) {                                  // on fait ça pour pouvoir ajouter un ActionListener à chaque bouton
      listeNiveaux.add(new JButton("Niveau "+i));
    }

    for (int i=debut; i<9; i++) {       // car on veut afficher 9 niveaux max par page
      int j = i;
      if (i < listeNiveaux.size()+1) {
        if (listeNiveaux.get(j-1) != null) {
          listeNiveaux.get(i-1).addActionListener( (event) -> c.lancerNiv(j) );
          listeNiveaux.get(i-1).setBackground(Color.GREEN);
          listeNiveaux.get(i-1).setForeground(Color.WHITE);
          panCenter.add(listeNiveaux.get(i-1));
        }
      } else {
        panCenter.add(new JPanel());
      }
    }
    panCenter.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
    panCenter.setVisible(true);
    return panCenter;
  }

  JPanel panSouth() {
    JPanel panSouth = new JPanel();                               // Jpanel du sud avec les boutons suivant et precedent
    JButton boutonPrecedent = new JButton("Précedent");           // qui vont changer la valeur de début pour afficher les niveaux au centre
    if (debut >= 9) boutonPrecedent.addActionListener( (event) -> precedent() );
    panSouth.add(boutonPrecedent);
    JButton boutonSuivant = new JButton("Suivant");
    if (debut <= max) boutonSuivant.addActionListener( (event) -> suivant() );
    panSouth.add(boutonSuivant);
    panSouth.setVisible(true);
    return panSouth;
  }

  JPanel panNorth() {
    JPanel panNorth = new JPanel();                               // Jpanel du nord avec le bouton retour et le titre
    JButton boutonRetour = new JButton("Retour");
    boutonRetour.addActionListener( (event) -> menuAccueil());
    boutonRetour.setBackground(Color.BLACK);
    boutonRetour.setForeground(Color.WHITE);
    panNorth.add(boutonRetour);
    JLabel label = new JLabel("Selection de niveaux");
    panNorth.add(label);
    panNorth.setVisible(true);
    return panNorth;
  }

  int nblvl() {                  // retourne le nombre de niveaux stockés dans le fichier Json
    try {
      return Niveau.getNumberLvl(new File("assets/lvls/niveau.json"), "niveaux_off");
    }
    catch(Exception e) {
      return 4;  // si on arrive pas à acceder au nombre de niveau, on affiche que les 4 premiers
    }
  }

  public void suivant() {   // avancer dans le tableau de niveaux
    if (debut < max-9) debut += 9;
    niveaux();
  }

  public void precedent() {  // reculer dans le tableau de niveaux
    if (debut > 9) debut -= 9;
    niveaux();
  }

  public void niveauAleaF() {       // lance un niveau facile
    int int1 = (int)(Math.random() * 2);
    int int2 = (int)(Math.random() * 2);
    String str1 = Integer.toString(int1+3);
    String str2 = Integer.toString(int2+3);
    String[] a = {str1,str2,"500"};
    Generation.main(a);
  }

  public void niveauAleaN() {       // lance un niveau normal
    int int1 = (int)(Math.random() * 2);
    int int2 = (int)(Math.random() * 2);
    String str1 = Integer.toString(int1+4);
    String str2 = Integer.toString(int2+4);
    String[] a = {str1,str2,"10"};
    Generation.main(a);
  }

  public void niveauAleaD() {       // lance un niveau dificile
    int int1 = (int)(Math.random() * 2);
    int int2 = (int)(Math.random() * 2);
    String str1 = Integer.toString(int1+6);
    String str2 = Integer.toString(int2+6);
    String[] a = {str1,str2,"5"};
    Generation.main(a);
  }


  public void aide() {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    pan.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    gc.weightx = 5;
    gc.weighty = 7;
    JButton bontonMenu = boutonRetour();
    gc.gridx = 0; gc.gridy = 0;
    pan.add(bontonMenu, gc);
    JLabel titre = reglesTitre();
    gc.gridx = 1; gc.gridy = 0;
    pan.add(titre, gc);
    JLabel regleun = new JLabel("Il faut faire arriver l'eau jusqu'au tuyau rouge.");
    gc.gridx = 1; gc.gridy = 1;
    pan.add(regleun, gc);
    JPanel vide = new JPanel();
    gc.gridx = 3; gc.gridy = 4;
    pan.add(vide, gc);

    this.setContentPane(pan);
    this.setVisible(true);
  }



  // BOUTONS ET LABELS

  JLabel titre() {
    JLabel labelAquavias = new JLabel("Aquavias", JLabel.CENTER);
    labelAquavias.setPreferredSize(new Dimension(300,100));
    Font font = new Font("Arial",Font.BOLD,50);
    labelAquavias.setFont(font);
    return labelAquavias;
  }

  JButton boutonJouer() {
    JButton boutonJouer = new JButton("Jouer");
    boutonJouer.addActionListener( (event) -> jouer());
    boutonJouer.setBackground(Color.YELLOW);
    boutonJouer.setPreferredSize(new Dimension(150,50));
    return boutonJouer;
  }

  JButton boutonAide() {
    JButton boutonAide = new JButton("Aide");
    boutonAide.addActionListener( (event) -> aide());
    boutonAide.setPreferredSize(new Dimension(150,50));
    boutonAide.setBackground(Color.RED);
    return boutonAide;
  }

  JButton boutonQuitter() {
    JButton boutonQuitter = new JButton("Quitter");
    boutonQuitter.addActionListener((e)-> {
			this.dispose();
	  });
    boutonQuitter.setPreferredSize(new Dimension(150,50));
    boutonQuitter.setBackground(Color.BLACK);
    boutonQuitter.setForeground(Color.WHITE);
    return boutonQuitter;
  }

  JButton lancerJeu() {
    JButton boutonJouer = new JButton("Niveaux normaux");
    boutonJouer.addActionListener( (event) -> niveaux());
    boutonJouer.setBackground(Color.YELLOW);
    boutonJouer.setPreferredSize(new Dimension(300,50));
    return boutonJouer;
  }

  JLabel labelChoix() {
    JLabel labelChoix = new JLabel("Niveau aléatoire:");
    Font font = new Font("Arial",Font.BOLD,20);
    labelChoix.setFont(font);
    return labelChoix;
  }

  JButton boutonAleaFacile() {
    JButton boutonAleaF = new JButton("Facile");
    boutonAleaF.addActionListener( (event) -> niveauAleaF());
    boutonAleaF.setBackground(Color.GREEN);
    boutonAleaF.setPreferredSize(new Dimension(150,50));
    return boutonAleaF;
  }

  JButton boutonAleaNormal() {
    JButton boutonAleaN = new JButton("Normal");
    boutonAleaN.addActionListener( (event) -> niveauAleaN());
    boutonAleaN.setBackground(Color.BLUE);
    boutonAleaN.setPreferredSize(new Dimension(150,50));
    return boutonAleaN;
  }

  JButton boutonAleaDifficile() {
    JButton boutonAleaD = new JButton("Difficile");
    boutonAleaD.addActionListener( (event) -> niveauAleaD());
    boutonAleaD.setBackground(Color.RED);
    boutonAleaD.setPreferredSize(new Dimension(150,50));
    return boutonAleaD;
  }

  JButton boutonRetour() {
    JButton boutonRetour = new JButton("Retour");
    boutonRetour.addActionListener( (event) -> menuAccueil());
    boutonRetour.setBackground(Color.BLACK);
    boutonRetour.setForeground(Color.WHITE);
    return boutonRetour;
  }

  JLabel reglesTitre() {
    JLabel titre = new JLabel("REGLES");
    Font font = new Font("Arial",Font.BOLD,20);
    titre.setFont(font);
    return titre;
  }

}
