package Aquavias.vue.GUI;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.event.*;

import org.w3c.dom.events.MouseEvent;

import Aquavias.controller.ControllerIG;
import Aquavias.model.Niveau;

public class VueIG {
    private ControllerIG controller;
    private Niveau model;

    private JFrame jframe;

    private JPanel actionBar;
    private JButton undoButton;
    private JLabel resources;

    private JPanel zonePlateau;
    private JPanelPipe[][] Pipes;

    public VueIG(ControllerIG c, Niveau m) {
        GridBagConstraints gbc = new GridBagConstraints();
        controller = c;
        model = m;
        m.remplir();

        jframe = new JFrame();
        jframe.setLayout(new GridBagLayout());

        JPanelPipe.loadImg();
        zonePlateau = new JPanel();
        zonePlateau.setPreferredSize(new Dimension(m.getLongueur()*200,m.getLargeur()*200));
        initPlateau(m,c);

        actionBar = new JPanel();
        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener((e)-> {
          controller.quit();
    			jframe.dispose();
    	  });
        actionBar.add(boutonQuitter);
        undoButton = new JButton("Undo");
        undoButton.addActionListener((event) -> c.undo());
        resources = new JLabel(m.getresources()+"");
        actionBar.add(undoButton);
        actionBar.add(resources);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.1;
        gbc.weightx = 1;
        gbc.gridy = 0;
        jframe.add(actionBar,gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.9;
        gbc.gridy = 1;
        jframe.add(zonePlateau,gbc);
        jframe.pack();
        //jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setTitle("Aquavias");
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initPlateau(Niveau model, ControllerIG controller) {
        zonePlateau.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Pipes = new JPanelPipe[model.getLargeur()][model.getLongueur()];
        for (int i = 0; i < Pipes.length; i++) {
            for (int j = 0; j < Pipes[i].length; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                Pipes[i][j] = new JPanelPipe(model.getPipe(i, j).getIndexGui(), model.getPipe(i, j), model.getPipe(i, j).getRempli(), controller, i, j, model.getPipe(i,j).isMoveable());
                //System.out.println(model.getPipe(i, j).getIndexGui()+" "+model.getPipe(i, j).getRotation()+" "+model.getPipe(i, j).getRempli());
                Pipes[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                if(model.getPipe(i,j).isMoveable()) Pipes[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                zonePlateau.add(Pipes[i][j], gbc);
            }
        }
    }

    public void update() {
        for (int i = 0; i < Pipes.length; i++) {
            for (int j = 0; j < Pipes[i].length; j++) {
                Pipes[i][j].remplir(model.getPipe(i, j).getRempli());
                Pipes[i][j].repaint();
            }
        }
        resources.setText(model.getresources()+"");
    }

    public void displayWinScreen() {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            File f = new File("assets/lvls/niveau.json");
            Niveau m = new Niveau();
            try {
                m.load(f, "niveaux_off", 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerIG c = new ControllerIG(m);
            VueIG gui = new VueIG(c, m);
            c.setVue(gui);
        });

    }
}
