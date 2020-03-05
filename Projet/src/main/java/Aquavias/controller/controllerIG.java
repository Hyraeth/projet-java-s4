package Aquavias.controller;

import Aquavias.model.Niveau;
import Aquavias.vue.GUI.VueIG;

public class controllerIG {
    private VueIG vue;
    private Niveau model;

    public void rotate(int i, int j) {
        model.rotate(i,j);
        vue.update();
        if(model.finish()) vue.displayWinScreen();
    }
}