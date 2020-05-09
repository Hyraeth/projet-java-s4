package Aquavias.vue.GUI;

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
  private int max = 27;  // faudra faire du json

  public Fenetre(){
    this.setTitle("Aquavias");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    contenu.setLayout(new FlowLayout());

    menuAccueil();
  }

  // ACCUEIL

  public void menuAccueil() {     // lance l'accueil
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    pan.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 3;
    gc.weighty = 4;

    JLabel labelAquavias = new JLabel("Aquavias", JLabel.CENTER);
    labelAquavias.setPreferredSize(new Dimension(300,100));
    Font font = new Font("Arial",Font.BOLD,50);
    labelAquavias.setFont(font);
    gc.gridx = 2;
    gc.gridy = 1;
    gc.gridwidth = 1;
    pan.add(labelAquavias, gc);

    JButton boutonJouer = new JButton("Jouer");
    boutonJouer.addActionListener( (event) -> jouer());
    boutonJouer.setBackground(Color.YELLOW);
    boutonJouer.setPreferredSize(new Dimension(150,50));
    gc.gridy = 2;
    pan.add(boutonJouer, gc);

    JButton boutonAide = new JButton("Aide");
    boutonAide.addActionListener( (event) -> aide());
    boutonAide.setPreferredSize(new Dimension(150,50));
    boutonAide.setBackground(Color.RED);
    gc.gridy = 3;
    pan.add(boutonAide, gc);

    JButton boutonQuitter = new JButton("Quitter");
    boutonQuitter.addActionListener((e)-> {
			this.dispose();
	  });
    boutonQuitter.setPreferredSize(new Dimension(150,50));
    boutonQuitter.setBackground(Color.BLACK);
    boutonQuitter.setForeground(Color.WHITE);
    gc.gridy = 4;
    pan.add(boutonQuitter, gc);

    this.setContentPane(pan);
    this.setVisible(true);
  }


  public void jouer() {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    pan.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 5;
    gc.weighty = 7;

    JButton bontonMenu = new JButton("Retour");
    bontonMenu.addActionListener( (event) -> menuAccueil());
    bontonMenu.setBackground(Color.BLACK);
    bontonMenu.setForeground(Color.WHITE);
    gc.gridx = 0; gc.gridy = 0;
    pan.add(bontonMenu, gc);

    JButton boutonJouer = new JButton("Niveaux normaux");
    boutonJouer.addActionListener( (event) -> niveaux("normaux"));
    boutonJouer.setBackground(Color.YELLOW);
    boutonJouer.setPreferredSize(new Dimension(300,50));
    gc.gridx = 2; gc.gridy = 1;
    pan.add(boutonJouer, gc);


    JLabel labelChoix = new JLabel("Niveau aléatoire:");
    Font font = new Font("Arial",Font.BOLD,20);
    labelChoix.setFont(font);
    gc.gridx = 2; gc.gridy = 2;
    pan.add(labelChoix, gc);

    JButton boutonAleaF = new JButton("Facile");
    boutonAleaF.addActionListener( (event) -> niveauAleaF());
    boutonAleaF.setBackground(Color.GREEN);
    boutonAleaF.setPreferredSize(new Dimension(150,50));
    gc.gridx = 1; gc.gridy = 3;
    pan.add(boutonAleaF, gc);

    JButton boutonAleaN = new JButton("Normale");
    boutonAleaN.addActionListener( (event) -> niveauAleaN());
    boutonAleaN.setBackground(Color.BLUE);
    boutonAleaN.setPreferredSize(new Dimension(150,50));
    gc.gridx = 2; gc.gridy = 3;
    pan.add(boutonAleaN, gc);

    JButton boutonAleaD = new JButton("Difficile");
    boutonAleaD.addActionListener( (event) -> niveauAleaD());
    boutonAleaD.setBackground(Color.RED);
    boutonAleaD.setPreferredSize(new Dimension(150,50));
    gc.gridx = 3; gc.gridy = 3;
    pan.add(boutonAleaD, gc);

    JPanel vide = new JPanel();
    gc.gridx = 4; gc.gridy = 6;
    pan.add(vide, gc);

  }





  // PARTIE SELECTION DE NIVEAU

  public void niveaux(String niv) {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    JPanel panCenter = new JPanel();            // partie au centre (les niveaux)
    panCenter.setLayout(new GridLayout(3,3,40,40));

    // ATTENTION !!! quand on aura plusieurs niveaux et fichier json il faudra changer ca pour avoir liste niveaux normaux ET liste niveaux perso

    if (niv.equals("perso")) {
      panCenter.add(new JLabel("Il n y a pas encore de niveaux personalisés, attendez une prochaine MAJ.", JLabel.CENTER));
    }

    else {
      ArrayList<JButton> listeNiveaux = new ArrayList<JButton>();
      for(int i = 1; i<4;i++) {
        listeNiveaux.add(new JButton("Niveau "+i));
      }
      for (int i=debut; i<9; i++) {
        int j = i;
        if (i < listeNiveaux.size()+1) {
          if (listeNiveaux.get(j-1) != null) {
            listeNiveaux.get(i-1).addActionListener( (event) -> lancerNiv(j) );
            listeNiveaux.get(i-1).setBackground(Color.GREEN);
            listeNiveaux.get(i-1).setForeground(Color.WHITE);
            panCenter.add(listeNiveaux.get(i-1));
          }
        } else {
          panCenter.add(new JPanel());
        }
      }
    }
    panCenter.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
    panCenter.setVisible(true);


    JPanel panSouth = new JPanel();                               // Jpanel du sud avec les boutons suivant et precedent
    JButton boutonPrecedent = new JButton("Précedent");           // qui vont changer la valeur de début pour afficher les niveaux au centre
    if (debut >= 9) boutonPrecedent.addActionListener( (event) -> precedent(niv) );
    panSouth.add(boutonPrecedent);
    JButton boutonSuivant = new JButton("Suivant");
    if (debut <= max) boutonSuivant.addActionListener( (event) -> suivant(niv) );
    panSouth.add(boutonSuivant);
    panSouth.setVisible(true);


    JPanel panNorth = new JPanel();                               // Jpanel du nord avec le boutons retour, le label et le bouton pour passer de
    JButton boutonRetour = new JButton("Retour");                 // niveau personalisés à niveau normaux ou l'inverse
    boutonRetour.addActionListener( (event) -> menuAccueil());
    boutonRetour.setBackground(Color.BLACK);
    boutonRetour.setForeground(Color.WHITE);
    panNorth.add(boutonRetour);
    if (niv.equals("normaux")) {
      JLabel label = new JLabel("Selection de niveaux normaux");
      panNorth.add(label);

      JButton boutonPerso = new JButton("Niveaux personalisés");
      boutonPerso.addActionListener( (event) -> niveaux("perso"));
      panNorth.add(boutonPerso);
    }
    else {
      JLabel label = new JLabel("Selection de niveaux personalisés");
      panNorth.add(label);

      JButton boutonNormaux = new JButton("Niveaux normaux");
      boutonNormaux.addActionListener( (event) -> niveaux("normaux"));
      panNorth.add(boutonNormaux);
    }
    panNorth.setVisible(true);


    pan.setLayout(new BorderLayout());
    pan.add(panCenter, BorderLayout.CENTER);
    pan.add(panNorth, BorderLayout.NORTH);
    pan.add(panSouth, BorderLayout.SOUTH);
    pan.add(new JLabel(""), BorderLayout.EAST);
    pan.add(new JLabel(""), BorderLayout.WEST);
    pan.setVisible(true);
  }

  public void suivant(String niv) {   // avancer dans le tableau de niveaux
    if (debut<max-9) debut +=9;
    niveaux(niv);
  }

  public void precedent(String niv) {  // reculer dans le tableau de niveaux
    if (debut>9) debut -= 9;
    niveaux(niv);
  }


  public void lancerNiv(int i) {
    EventQueue.invokeLater(() -> {
        File f = new File("assets/lvls/niveau.json");
        Niveau m = new Niveau();
        try {
            m.load(f, "niveaux_off", i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ControllerIG c = new ControllerIG(m);
        VueIG gui = new VueIG(c, m);
        c.setVue(gui);
    });
  }

  public void niveauAleaF() {
    int int1 = (int)(Math.random() * 2);
    int int2 = (int)(Math.random() * 2);
    String str1 = Integer.toString(int1+3);
    String str2 = Integer.toString(int2+3);
    String[] a = {str1,str2,"500"};
    Generation.main(a);
  }

  public void niveauAleaN() {
    int int1 = (int)(Math.random() * 2);
    int int2 = (int)(Math.random() * 2);
    String str1 = Integer.toString(int1+4);
    String str2 = Integer.toString(int2+4);
    String[] a = {str1,str2,"10"};
    Generation.main(a);
  }

  public void niveauAleaD() {
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

    /*JButton boutonRetour = new JButton("Retour");
    boutonRetour.addActionListener( (event) -> menuAccueil());
    pan.add(boutonRetour);

    pan.add(new JLabel("Regles"));                      // il faut trouver les regles sur internet et les mettre ici.....
    */

    pan.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 5;
    gc.weighty = 7;

    JButton bontonMenu = new JButton("Retour");
    bontonMenu.addActionListener( (event) -> menuAccueil());
    bontonMenu.setBackground(Color.BLACK);
    bontonMenu.setForeground(Color.WHITE);
    gc.gridx = 0; gc.gridy = 0;
    pan.add(bontonMenu, gc);

    JLabel titre = new JLabel("REGLES");
    Font font = new Font("Arial",Font.BOLD,20);
    titre.setFont(font);
    gc.gridx = 1; gc.gridy = 0;
    pan.add(titre, gc);

    JLabel regleun = new JLabel("Il faut faire arriver l'eau jusqu'au tuyau rouge.");
    gc.gridx = 1; gc.gridy = 1;
    pan.add(regleun, gc);

    JLabel regledeux = new JLabel("vous pouvez choisir entre des niveaux aléatoires ou déjà créés.");
    gc.gridx = 1; gc.gridy = 2;
    pan.add(regledeux, gc);

    JPanel vide = new JPanel();
    gc.gridx = 3; gc.gridy = 4;
    pan.add(vide, gc);


    this.setContentPane(pan);
    this.setVisible(true);
  }


}
