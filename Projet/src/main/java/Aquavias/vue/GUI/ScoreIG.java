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

import java.awt.Insets;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.event.*;

import org.w3c.dom.events.MouseEvent;

import Aquavias.controller.ControllerIG;
import Aquavias.model.Niveau;

import java.util.*;


public class ScoreIG extends JFrame{

  private JPanel contenu = (JPanel) getContentPane();

  public ScoreIG(int[] scoreComparaison, int score, int lvl){
    this.setTitle("Aquavias");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    contenu.setLayout(new FlowLayout());

    score(scoreComparaison, score, lvl);
    this.setVisible(true);
  }

  public void score(int[] scoreComparaison, int score, int lvl) {
    JPanel pan = (JPanel) this.getContentPane();
    pan.removeAll();
    pan.revalidate();
    pan.repaint();

    JPanel panCenter = new JPanel();
    if (score >= scoreComparaison[2] && score < scoreComparaison[1]) {
      ImageIcon icon = new ImageIcon("assets/img/trois.png");
      JLabel img = new JLabel(icon);
      panCenter.add(img);
    } else if (score >= scoreComparaison[1] && score < scoreComparaison[0]) {
          ImageIcon icon = new ImageIcon("assets/img/deux.png");
          JLabel img = new JLabel(icon);
          panCenter.add(img);
        }
        else if (score >= scoreComparaison[0] && score < scoreComparaison[0]+5) {
            ImageIcon icon = new ImageIcon("assets/img/un.png");
            JLabel img = new JLabel(icon);
            panCenter.add(img);
          }
          else {
            ImageIcon icon = new ImageIcon("assets/img/ratee.png");
            JLabel img = new JLabel(icon);
            panCenter.add(img);
          }
    panCenter.setVisible(true);

    JPanel panNorth = new JPanel();
    JButton boutonMenu = new JButton("Menu");
    boutonMenu.addActionListener( (event) -> menuAccueil());
    panNorth.add(boutonMenu);
    JLabel label = new JLabel("Score de fin de niveau");
    panNorth.add(label);
    panNorth.setVisible(true);


    JPanel panSouth = new JPanel();
    JButton boutonRetour = new JButton("Retour");
    boutonRetour.addActionListener( (event) -> retour() );
    panSouth.add(boutonRetour);
    JButton boutonRecommencer = new JButton("Recommencer");
    boutonRecommencer.addActionListener( (event) -> lancerNiv(lvl) );
    panSouth.add(boutonRecommencer);
    if(score > scoreComparaison[0]+5) {
      JButton boutonSuivant = new JButton("Suivant");
      boutonSuivant.addActionListener( (event) -> lancerNiv(lvl+1) );
      panSouth.add(boutonSuivant);
    }
    panSouth.setVisible(true);


    pan.setLayout(new BorderLayout());
    pan.add(panCenter, BorderLayout.CENTER);
    pan.add(panNorth, BorderLayout.NORTH);
    pan.add(panSouth, BorderLayout.SOUTH);
    pan.add(new JLabel(""), BorderLayout.EAST);
    pan.add(new JLabel(""), BorderLayout.WEST);
    pan.setVisible(true);
  }

  public void lancerNiv(int lvl) {
    EventQueue.invokeLater(() -> {
        File f = new File("assets/lvls/niveau.json");
        Niveau m = new Niveau();
        try {
            m.load(f, "niveaux_off", lvl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ControllerIG c = new ControllerIG(m);
        VueIG gui = new VueIG(c, m);
        c.setVue(gui);
    });
  }

  public void retour() {

  }

  public void menuAccueil() {

  }

}
