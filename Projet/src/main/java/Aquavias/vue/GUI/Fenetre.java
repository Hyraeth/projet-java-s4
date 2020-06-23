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

    /**
     * Représente le contenu de la fenetre
     */
  private JPanel contenu = (JPanel) getContentPane();
  
  /**
     * Représente le premier niveau affiché dans la partie des niveaux, utilisé pour faire défiler les niveaux
     */
  private int debut = 1;
  
  /**
     * Représente le controller
     */
  private ControllerIG c;


    /**
     * Initialise la fenêtre en affichant le menu d'accueil
     */
  public Fenetre(){
    c = new ControllerIG();
    c.setFenetre(this);
    this.setTitle("Aquavias");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    contenu.setLayout(new FlowLayout());

    menuAccueil();
  }

  // ACCUEIL
  
  /**
     * affiche le menu d'accueil avec les différents boutons et labels
     */

  public void menuAccueil() {       
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


/**
     * affiche le menu "jouer" avec les différents boutons et labels utilisé dans la classe fenetre, fonction: menuAccueil()
     */

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

/**
     * affiche le menu de sélection de niveau avec les différents boutons et labels utilisé dans la classe fenetre, fonction: jouer()
     */
     
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

/**
     * fonction qui gére la partie centrale, c'est à dire les boutons pour selectionner un niveau utilisé dans la classe fenetre, fonction: niveaux()
     * @return le panel central
     */
     
  JPanel centre() {
    boolean[] b = lvlfinis();
    JPanel panCenter = new JPanel();            // partie au centre (les niveaux)
    panCenter.setLayout(new GridLayout(3,3,40,40));

    ArrayList<JButton> listeNiveaux = new ArrayList<JButton>();     // on regroupe tous les niveaux dans une ArrayList pour les afficher
    for(int i = 1; i < nblvl(); i++) {                                  // on fait ça pour pouvoir ajouter un ActionListener à chaque bouton
      listeNiveaux.add(new JButton("Niveau "+i));
    }

    for (int i=debut; i<9+debut; i++) {       // car on veut afficher 9 niveaux max par page
      int j = i;
      if (i < listeNiveaux.size()+1) {
        if (listeNiveaux.get(j-1) != null) {
          listeNiveaux.get(i-1).addActionListener( (event) -> c.lancerNiv(j) );
          listeNiveaux.get(i-1).setBackground(Color.GREEN);
          if(b[i]) listeNiveaux.get(i-1).setBackground(Color.BLUE);
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
  
  /**
     * fonction qui gére la partie du bas, c'est à dire les boutons pour faire défiler la selection de niveau utilisé dans la classe fenetre, fonction: niveaux()
     * @return le panel du bas
     */
  JPanel panSouth() {
    JPanel panSouth = new JPanel();                               // Jpanel du sud avec les boutons suivant et precedent
    JButton boutonPrecedent = new JButton("Précedent");           // qui vont changer la valeur de début pour afficher les niveaux au centre
    if (debut >= 9) boutonPrecedent.addActionListener( (event) -> precedent() );
    panSouth.add(boutonPrecedent);
    JButton boutonSuivant = new JButton("Suivant");
    if (debut <= nblvl()) boutonSuivant.addActionListener( (event) -> suivant() );
    panSouth.add(boutonSuivant);
    panSouth.setVisible(true);
    return panSouth;
  }

  /**
     * fonction qui gére la partie du haut, c'est à dire le bouton retour et le label utilisé dans la classe fenetre, fonction: niveaux()
     * @return le panel du haut
     */
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

  /**
     * fonction connaître le nombre de niveaux dans le fichier Json utilisé dans la classe fenetre
     * @return le nombre de niveaux stockés dans le fichier Json
     */
  int nblvl() {                  
    try {
      return Niveau.getNumberLvl(new File("assets/lvls/niveau.json"), "niveaux_off");
    }
    catch(Exception e) {
      return 4;  // si on arrive pas à acceder au nombre de niveau, on affiche que les 4 premiers
    }
  }

   /**
     * fonction connaître les niveaux finis
     * @return un tableau de boolean qui vaut true à la position i si le niveau i est fini.
     */
  boolean[] lvlfinis() {
    try {
      return Niveau.getLvlFinished(new File("assets/lvls/niveau.json"), "niveaux_off");
    }
    catch(Exception e) {
      return null;  // si on arrive pas à acceder au nombre de niveau, on affiche que les 4 premiers
    }
    
  }

  /**
     * fonction pour avancer dans la selection de niveau utilisé dans la classe fenetre, fonction: panSouth()
     */
  public void suivant() {   // avancer dans le tableau de niveaux
    if (debut < nblvl()-9) debut += 9;
    niveaux();
  }

  /**
     * fonction pour reculer dans la selection de niveau utilisé dans la classe fenetre, fonction: panSouth()
     */
  public void precedent() {  // reculer dans le tableau de niveaux
    if (debut > 9) debut -= 9;
    niveaux();
  }

  /**
     * fonction pour afficher l'aide utilisé dans la classe fenetre, fonction: boutonAide()
     */
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

 /**
     * fonction pour afficher le titre "Aquavias" utilisé dans la classe fenetre, fonction: menuAccueil()
     * @return le label labelAquavias
     */
  JLabel titre() {
    JLabel labelAquavias = new JLabel("Aquavias", JLabel.CENTER);
    labelAquavias.setPreferredSize(new Dimension(300,100));
    Font font = new Font("Arial",Font.BOLD,50);
    labelAquavias.setFont(font);
    return labelAquavias;
  }

 /**
     * fonction pour créer le bouton jouer qui redirige vers la page "jouer" utilisé dans la classe fenetre, fonction: menuAccueil()
     * @return le bouton boutonJouer
     */
  JButton boutonJouer() {
    JButton boutonJouer = new JButton("Jouer");
    boutonJouer.addActionListener( (event) -> jouer());
    boutonJouer.setBackground(Color.YELLOW);
    boutonJouer.setPreferredSize(new Dimension(150,50));
    return boutonJouer;
  }

 /**
     * fonction pour créer le bouton aide qui redirige vers la page "aide" utilisé dans la classe fenetre, fonction: menuAccueil()
     * @return le bouton boutonAide
     */
  JButton boutonAide() {
    JButton boutonAide = new JButton("Aide");
    boutonAide.addActionListener( (event) -> aide());
    boutonAide.setPreferredSize(new Dimension(150,50));
    boutonAide.setBackground(Color.RED);
    return boutonAide;
  }

 /**
     * fonction pour créer le bouton jouer qui permet de quitter le jeu utilisé dans la classe fenetre, fonction: menuAccueil()
     * @return le bouton boutonQuitter
     */
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

 /**
     * fonction pour créer le bouton qui redirige vers la page de selection de niveau utilisé dans la classe fenetre, fonction: jouer()
     * @return le bouton boutonJouer
     */
  JButton lancerJeu() {
    JButton boutonJouer = new JButton("Niveaux sauvegardés");
    boutonJouer.addActionListener( (event) -> niveaux());
    boutonJouer.setBackground(Color.YELLOW);
    boutonJouer.setPreferredSize(new Dimension(300,50));
    return boutonJouer;
  }

 /**
     * fonction pour créer le label "niveau aleatoire" utilisé dans la classe fenetre, fonction: jouer()
     * @return le label labelChoix
     */
  JLabel labelChoix() {
    JLabel labelChoix = new JLabel("Niveau aléatoire:");
    Font font = new Font("Arial",Font.BOLD,20);
    labelChoix.setFont(font);
    return labelChoix;
  }

 /**
     * fonction pour créer le bouton permettant de jouer à un niveau aléatoire facile utilisé dans la classe fenetre, fonction: jouer()
     * @return le bouton boutonAleaF
     */
  JButton boutonAleaFacile() {
    JButton boutonAleaF = new JButton("Facile");
    boutonAleaF.addActionListener( (event) -> c.niveauAleaF());
    boutonAleaF.setBackground(Color.GREEN);
    boutonAleaF.setPreferredSize(new Dimension(150,50));
    return boutonAleaF;
  }

/**
     * fonction pour créer le bouton permettant de jouer à un niveau aléatoire normal utilisé dans la classe fenetre, fonction: jouer()
     * @return le bouton boutonAleaN
     */
  JButton boutonAleaNormal() {
    JButton boutonAleaN = new JButton("Normal");
    boutonAleaN.addActionListener( (event) -> c.niveauAleaN());
    boutonAleaN.setBackground(Color.BLUE);
    boutonAleaN.setPreferredSize(new Dimension(150,50));
    return boutonAleaN;
  }

/**
     * fonction pour créer le bouton permettant de jouer à un niveau aléatoire difficile utilisé dans la classe fenetre, fonction: jouer()
     * @return le bouton boutonAleaD
     */
  JButton boutonAleaDifficile() {
    JButton boutonAleaD = new JButton("Difficile");
    boutonAleaD.addActionListener( (event) -> c.niveauAleaD());
    boutonAleaD.setBackground(Color.RED);
    boutonAleaD.setPreferredSize(new Dimension(150,50));
    return boutonAleaD;
  }

/**
     * fonction pour créer le bouton permettant de retourner à l'accueil utilisé dans la classe fenetre, fonction: jouer()
     * @return le bouton boutonRetour
     */
  JButton boutonRetour() {
    JButton boutonRetour = new JButton("Retour");
    boutonRetour.addActionListener( (event) -> menuAccueil());
    boutonRetour.setBackground(Color.BLACK);
    boutonRetour.setForeground(Color.WHITE);
    return boutonRetour;
  }
  
/**
     * fonction pour créer le label "regles" utilisé dans la classe fenetre, fonction: aide()
     * @return le label titre
     */
  JLabel reglesTitre() {
    JLabel titre = new JLabel("REGLES");
    Font font = new Font("Arial",Font.BOLD,20);
    titre.setFont(font);
    return titre;
  }

}
