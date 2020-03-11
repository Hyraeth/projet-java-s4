package Aquavias.controller;

import Aquavias.model.Niveau;
import Aquavias.vue.GUI.VueIG;
import Aquavias.vue.GUI.VueTerm;
import Aquavias.vue.GUI.Test;


public class ControllerIG {
    private VueIG vue;
    private Niveau model;
    private VueTerm vt = new VueTerm();

    public ControllerIG(Niveau n) {
        this.model = n;
	String[] arg=new String[0];
	Test.main(arg);
    }

    public void setVue(VueIG v) {
        vue = v;
    }

    public void rotate(int i, int j) {
        model.rotate(i,j);
        //tests pour voir si l'ig fonctionne correctement
        System.out.println(model.getPipe(i, j));
        vt.afficheNiv(model);
        //model.flow();
        vue.update();
	
        //if(model.finish()) vue.displayWinScreen();
    }

    public void undo() {

    }
}
