package Aquavias.controller;

import Aquavias.model.Niveau;
import Aquavias.vue.GUI.VueIG;

public class controllerIG {
    private VueIG vue;
    private Niveau model;

    public void setVue(VueIG v) {
        vue = v;
    }

    public void rotate(int i, int j) {
        model.rotate(i,j);
        vue.update();
        if(model.finish()) vue.displayWinScreen();
    }

    public void undo() {

    }
}