package Aquavias.vue.GUI;

import java.awt.*;

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
        m.remplir();
        jframe = new JFrame();

        zonePlateau = new JPanel(new GridLayout(model.getLargeur(), model.getLongueur()));

        initPlateau(m,c);

        jframe.add(zonePlateau);
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setTitle("Aquavias");
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initPlateau(Niveau model, ControllerIG controller) {
        zonePlateau = new JPanel(new GridLayout(model.getLargeur(), model.getLongueur()));
        Pipes = new JPanelPipe[model.getLargeur()][model.getLongueur()];
        for (int i = 0; i < Pipes.length; i++) {
            for (int j = 0; j < Pipes[i].length; j++) {
                Pipes[i][j] = new JPanelPipe(model.getPipe(i, j).getIndexGui(), model.getPipe(i, j).getRotation(), model.getPipe(i, j).getRempli(), controller, i, j, model.getPipe(i,j).isMoveable());
                //System.out.println(model.getPipe(i, j).getIndexGui()+" "+model.getPipe(i, j).getRotation()+" "+model.getPipe(i, j).getRempli());
                Pipes[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                Pipes[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                zonePlateau.add(Pipes[i][j]);
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
            ControllerIG c = new ControllerIG(m);
            VueIG gui = new VueIG(c, m);
            c.setVue(gui);
        });
        
    }
}