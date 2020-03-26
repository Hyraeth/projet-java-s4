package Aquavias.vue;

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
    gc.insets = new Insets(3,6,10,10);

    gc.weightx = 1;
    gc.weighty = 5;

    JButton boutonJouer = new JButton("Jouer");
    boutonJouer.addActionListener( (event) -> jouer());
    gc.gridx = 1; gc.gridy = 1;
    pan.add(boutonJouer, gc);

    JButton boutonCreer = new JButton("Créer");
    boutonCreer.addActionListener( (event) -> creer());
    gc.gridx = 1; gc.gridy = 2;
    pan.add(boutonCreer, gc);

    JButton boutonAide = new JButton("Aide");
    boutonAide.addActionListener( (event) -> aide());
    gc.gridx = 1; gc.gridy = 3;
    pan.add(boutonAide, gc);

    JButton boutonQuitter = new JButton("Quitter");
    boutonQuitter.addActionListener((e)-> {
			this.dispose();
	  });
    gc.gridx = 1; gc.gridy = 4;
    pan.add(boutonQuitter, gc);

    this.setContentPane(pan);
    this.setVisible(true);
  }

  public void jouer() {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    JButton boutonJouer = new JButton("Niveaux normaux");
    boutonJouer.addActionListener( (event) -> niveaux("normaux"));
    pan.add(boutonJouer);

    JButton boutonCreer = new JButton("Niveaux personalisés");
    boutonCreer.addActionListener( (event) -> niveaux("perso"));
    pan.add(boutonCreer);
  }





  // PARTIE SELECTION DE NIVEAU

  public void niveaux(String niv) {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    JPanel panCenter = new JPanel();            // partie au centre (les niveaux)
    panCenter.setLayout(new GridLayout(3,3));

    // ATTENTION !!! quand on aura plusieurs niveaux et fichier json il faudra changer ca pour avoir liste niveaux normaux ET liste niveaux perso

    ArrayList<JButton> listeNiveaux = new ArrayList<JButton>();
    for(int i = 1; i<28;i++) {
      listeNiveaux.add(new JButton("Niveau "+i));
    }
    for (int i=debut; i<debut+9; i++) {
      int j = i;
      listeNiveaux.get(i-1).addActionListener( (event) -> lancerNiv(j) );
      panCenter.add(listeNiveaux.get(i-1));
    }
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




  // Partie création de niveaux (à implementer plus tard)
  public void creer() {

  }

  public void aide() {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    JButton boutonRetour = new JButton("Retour");
    boutonRetour.addActionListener( (event) -> menuAccueil());
    pan.add(boutonRetour);

    pan.add(new JLabel("Regles"));                      // il faut trouver les regles sur internet et les mettre ici.....

    this.setContentPane(pan);
    this.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {}

}
