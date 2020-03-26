package Aquavias.controller;

import java.util.Timer;
import java.util.TimerTask;

import Aquavias.model.Niveau;
import Aquavias.vue.GUI.VueIG;
import Aquavias.vue.GUI.VueTerm;


public class ControllerIG {
    private VueIG vue;
    private Niveau model;
    private VueTerm vt = new VueTerm();

    public ControllerIG(Niveau n) {
        this.model = n;
        if(n.getType() == 2) updateLoop();
	    //String[] arg=new String[0];
	    //Test.main(arg);
    }

    public void updateLoop() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("hello");
                model.countdown();
                if(vue != null) vue.update();
            }
        }, 0, 1000);
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

    public int getLongueur() {
        return model.getLongueur();
    }

    public int getLargeur() {
        return model.getLargeur();
    }

    public void undo() {
        model.undo();
        //tests pour voir si l'ig fonctionne correctement
        vt.afficheNiv(model);
        //model.flow();
        vue.update();
    }
}
