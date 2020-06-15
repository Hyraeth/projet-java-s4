package Aquavias.controller;

import java.util.Timer;
import java.util.TimerTask;

import Aquavias.model.Generation;
import Aquavias.model.Niveau;
import Aquavias.vue.GUI.Fenetre;
import Aquavias.vue.GUI.VueIG;
import Aquavias.vue.GUI.VueTerm;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ControllerIG {
    private VueIG vue;
    private Fenetre fenetre;
    private Niveau model;
    private VueTerm vt = new VueTerm();
    private Timer timer;
    private boolean debug = false;

    public ControllerIG() {
        debug = false;
    }

    public void setNiveau(Niveau n) {
        this.model = n;
        if (n.getType() == 2)
            updateLoop();
    }

    public void setFenetre(Fenetre f) {
        this.fenetre = f;
    }

    public void setEnabledFenetre(boolean b) {
        fenetre.setEnabled(b);
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
            fenetre.setEnabled(false);
            VueIG gui = new VueIG(this, m);
            this.setVue(gui);
        });
    }

    public void updateLoop() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                boolean b = model.countdown();
                if (vue != null)
                    vue.update();
                if (model.getresources() == 0 && !b) {
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                    }
                    model.quit();
                    vue.displayLoseScreen();
                    System.out.print("fin");
                }
            }
        }, 0, 1000);
    }

    public void setVue(VueIG v) {
        vue = v;
    }

    public void solve() {
        model.gagnable(0, 0);
    }

    public void niveauAleaF() {       // lance un niveau facile
        int int1 = (int)(Math.random() * 2) + 3;
        int int2 = (int)(Math.random() * 2) + 3;
        Niveau n = Generation.init(int1,int2,500);
        n.setLvlType("niveauAleaF");
        this.setNiveau(n);
        fenetre.setEnabled(false);
        VueIG v = new VueIG(this, n);
        this.setVue(v);
    }

    public void niveauAleaN() {       // lance un niveau normal
        int int1 = (int)(Math.random() * 2) + 4;
        int int2 = (int)(Math.random() * 2) + 4;
        Niveau n = Generation.init(int1,int2,10);
        n.setLvlType("niveauAleaN");
        this.setNiveau(n);
        fenetre.setEnabled(false);
        VueIG v = new VueIG(this, n);
        this.setVue(v);
    }

    public void niveauAleaD() {       // lance un niveau dificile
        int int1 = (int)(Math.random() * 2) + 6;
        int int2 = (int)(Math.random() * 2) + 6;
        Niveau n = Generation.init(int1,int2,5);
        n.setLvlType("niveauAleaD");
        this.setNiveau(n);
        fenetre.setEnabled(false);
        VueIG v = new VueIG(this, n);
        this.setVue(v);
    }

    public void rotate(int i, int j) {
        model.rotate(i,j);
        //tests pour voir si l'ig fonctionne correctement
        if(debug) {
            System.out.println(model.getPipe(i, j));
        }
        vt.afficheNiv(model, debug);

        vue.update();
        boolean f = model.finis();
        if(model.getresources() == 0 && !f) {
            if(timer != null) {
                timer.cancel();
                timer.purge();
            }
            model.quit();
            vue.displayLoseScreen();
            System.out.println("fin");
        }
        
        if(f) {
            if(timer != null) {
                timer.cancel();
                timer.purge();
            }
            model.quit();
            vue.displayWinScreen();
            System.out.println("fin");
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
        fenetre.setEnabled(true);
        model.quit();
        System.out.println("fin");
    }

    public void debug() {
        if(debug) debug = false;
        else debug = true;
    }
}
