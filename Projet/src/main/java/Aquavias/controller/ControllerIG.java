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
    private Timer timer;
    public boolean debug;

    public ControllerIG(Niveau n) {
        this.model = n;
        if(n.getType() == 2) updateLoop();
        debug = false;
	    //String[] arg=new String[0];
	    //Test.main(arg);
    }

    public void updateLoop() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                //System.out.println("hello");
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
        
        if(model.finis()) {
            if(timer != null) {
                timer.cancel();
                timer.purge();
            }
            model.quit();
            vue.displayWinScreen();
            System.out.print("fin");
        }
    }

    public int getLongueur() {
        return model.getLongueur();
    }

    public int getLargeur() {
        return model.getLargeur();
    }

    public void undo() {
        model.undo();
        vt.afficheNiv(model);
        vue.update();
    }

    public void quit() {
        if(timer != null) {
            timer.cancel();
        timer.purge();
        }
        
        model.quit();
        System.out.print("fin");
    }

    public void debug() {
        if(debug) debug = false;
        else debug = true;
    }
}
