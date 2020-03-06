package Aquavias.controller;

import Aquavias.model.Niveau;
import Aquavias.vue.GUI.VueIG;

public class ControllerIG {
    private VueIG vue;
    private Niveau model;

    public void setVue(VueIG v) {
        vue = v;
    }

    public void rotate(int i, int j) {
        model.rotate(i,j);
        model.flow();
        vue.update();
        if(model.finish()) vue.displayWinScreen();
    }

    public void undo() {

    }
}