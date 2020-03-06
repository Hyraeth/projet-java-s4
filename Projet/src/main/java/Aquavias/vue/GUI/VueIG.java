package Aquavias.vue.GUI;

import java.awt.*;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import Aquavias.controller.ControllerIG;
import Aquavias.model.Niveau;

public class VueIG {
    private ControllerIG controller;
    private Niveau model;

    private JFrame jframe;

    private JPanel zonePlateau;
    private JPanelPipe[][] Pipes;

    public VueIG(ControllerIG c, Niveau m) {
        controller = c;
        model = m;

        jframe = new JFrame();

        zonePlateau = new JPanel(new GridLayout(model.getLargeur(), model.getLongueur()));

        initPlateau();

        jframe.add(zonePlateau);
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setTitle("Aquavias");
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initPlateau() {
        zonePlateau = new JPanel(new GridLayout(model.getLargeur(), model.getLongueur()));
        Pipes = new JPanelPipe[model.getLargeur()][model.getLongueur()];
        for (int i = 0; i < Pipes.length; i++) {
            for (int j = 0; j < Pipes[i].length; j++) {
                Pipes[i][j] = new JPanelPipe(model.getPipe(i, j).getFilename());
                Pipes[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                Pipes[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                /*
                Pipes[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        Pipes[i][j].rotateImg();
                        updateWater();
                    }
                });*/
                zonePlateau.add(Pipes[i][j]);
            }
        }
    }

    public void update() {

    }

    public void displayWinScreen() {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            File f = new File("assets\\lvls\\niveau.json");
            Niveau m = new Niveau();
            try {
                m.load(f, "niveaux_off", 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerIG c = new ControllerIG();
            VueIG gui = new VueIG(c, m);
            c.setVue(gui);
        });
        
    }
}