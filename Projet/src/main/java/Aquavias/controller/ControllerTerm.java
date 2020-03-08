package Aquavias.controller;

import Aquavias.vue.GUI.VueTerm;

public class ControllerTerm{

    private VueTerm vue;
    private Niveau niv;

    public ControllerTerm(Niveau n) {
        niv = n;
    }

    public void setVue(VueTerm v) {
        vue = v;
    }


}