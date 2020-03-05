package Aquavias.vue.GUI;

import java.io.File;
import java.io.IOException;

import javax.swing.*;

import Aquavias.controller.controllerIG;
import Aquavias.model.Niveau;

public class VueIG {
    private controllerIG controller;
    private Niveau model;

    private JFrame jframe;

    private JPanel zonePlateau;
    private JPanel[][] Pipes;

    public VueIG(controllerIG c, Niveau m) {
        controller = c;
        model = m;

        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setTitle("Aquavias");
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jframe.add(zonePlateau);
    }

    public void initPlateau() {
        Pipes = new JPanel[model.getLargeur()][model.getLongueur()];
        for (int i = 0; i < Pipes.length; i++) {
            for (int j = 0; j < Pipes[i].length; j++) {
                Pipes[i][j] = new JPanel();
            }
        }
    }

    public void update() {

    }

    public void displayWinScreen() {

    }

    public static void main(String[] args) {
        File f = new File("assets/lvl/niveau.json");
        Niveau m = new Niveau();
        try {
            m.load(f, "niveaux_off", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controllerIG c = new controllerIG();
        VueIG gui = new VueIG(c, m);
        c.setVue(gui);
    }
}