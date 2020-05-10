package Aquavias.controller;

import java.util.Timer;
import java.util.TimerTask;

import Aquavias.model.Niveau;
import Aquavias.vue.GUI.VueIG;
import Aquavias.vue.GUI.VueTerm;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ControllerIG {
    private VueIG vue;
    private Niveau model;
    private VueTerm vt = new VueTerm();
    private Timer timer;
    private boolean debug = false;

    public ControllerIG() {
        debug = false;
    }

    public void setNiveau(Niveau n) {
        this.model = n;
        if(n.getType() == 2) updateLoop();
    }

    public void lancerNiv(int j) {
        EventQueue.invokeLater(() -> {
            File f = new File("assets/lvls/niveau.json");
            Niveau m = new Niveau();
            try {
                m.load(f, "niveaux_off", j);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setNiveau(m);
            VueIG gui = new VueIG(this, m);
            this.setVue(gui);
        });
    }

    public void updateLoop() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
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
        if(debug) {
            System.out.println(model.getPipe(i, j));
        }
        vt.afficheNiv(model, debug);

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
        vt.afficheNiv(model, debug);
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
