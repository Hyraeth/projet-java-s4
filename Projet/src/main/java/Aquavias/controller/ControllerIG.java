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
/**
 * Controleur de l'interface graphique du plateau et du menu
 */
public class ControllerIG {
    /**
     * Interface graphique du plateau
     */
    private VueIG vue;
    /**
     * Interface graphique du menu
     */
    private Fenetre fenetre;
    /**
     * Niveau 
     */
    private Niveau model;
    /**
     * Affichage terminal pour le debuggage
     */
    private VueTerm vt = new VueTerm();
    /**
     * Timer pour le mode de jeu à temps réduit
     */
    private Timer timer;
    /**
     * Indicateur s'il faut afficher les info de debugage
     */
    private boolean debug = false;

    /**
     * Construteur du controller
     */
    public ControllerIG() {
        debug = false;
    }

    /**
     * Change le niveau dans le controlleur
     * @param n niveau
     */
    public void setNiveau(Niveau n) {
        this.model = n;
        if (n.getType() == 2)
            updateLoop();
    }

    /**
     * Change le menu du controller
     */
    public void setFenetre(Fenetre f) {
        this.fenetre = f;
    }

    /**
     * Désative/réactive l'interactivité de la fenetre
     * @param b true pour activer le menu
     */
    public void setEnabledFenetre(boolean b) {
        fenetre.setEnabled(b);
    }

    /**
     * Lance le niveau j de la liste de niveau prédéfinis et affiche l'interface graphique associé.
     * @param j int
     */
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

    /**
     * Lance un timer qui actualise l'interface graphique du plateau toute les seconde et vérifie si le jeu est fini
     */
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
                }
            }
        }, 0, 1000);
    }

    /**
     * Change l'interface graphique du plateau
     * @param v
     */
    public void setVue(VueIG v) {
        vue = v;
    }

    /**
     * Résout un niveau par backtracking
     * @deprecated
     */
    public void solve() {
        model.gagnable(0, 0);
    }

    /**
     * Charge un niveau aléatoire facile
     */
    public void niveauAleaF() {       // lance un niveau facile
        int int1 = (int)(Math.random() * 2) + 3;
        int int2 = (int)(Math.random() * 2) + 3;
        Niveau n = Generation.init(int1,int2,500);
        File f = new File("assets/lvls/niveau.json");
        try {
            n.save(f, "niveaux_off");
        } catch (IOException e) {
            e.printStackTrace();
        }
        n.setLvlType("niveauAleaF");
        this.setNiveau(n);
        fenetre.setEnabled(false);
        VueIG v = new VueIG(this, n);
        this.setVue(v);
    }

    /**
     * Charge un niveau aléatoire normal
     */
    public void niveauAleaN() {       // lance un niveau normal
        int int1 = (int)(Math.random() * 2) + 4;
        int int2 = (int)(Math.random() * 2) + 4;
        Niveau n = Generation.init(int1,int2,10);
        File f = new File("assets/lvls/niveau.json");
        try {
            n.save(f, "niveaux_off");
        } catch (IOException e) {
            e.printStackTrace();
        }
        n.setLvlType("niveauAleaN");
        this.setNiveau(n);
        fenetre.setEnabled(false);
        VueIG v = new VueIG(this, n);
        this.setVue(v);
    }

    /**
     * Charge un niveau aléatoire difficile
     */
    public void niveauAleaD() {       // lance un niveau dificile
        int int1 = (int)(Math.random() * 2) + 6;
        int int2 = (int)(Math.random() * 2) + 6;
        Niveau n = Generation.init(int1,int2,5);
        File f = new File("assets/lvls/niveau.json");
        try {
            n.save(f, "niveaux_off");
        } catch (IOException e) {
            e.printStackTrace();
        }
        n.setLvlType("niveauAleaD");
        this.setNiveau(n);
        fenetre.setEnabled(false);
        VueIG v = new VueIG(this, n);
        this.setVue(v);
    }

    /**
     * Tourne le tuyau à la position i,j du plateau et met à jour l'interface graphique associée
     * @param i int
     * @param j int
     */
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
        }
        
        if(f) {
            if(timer != null) {
                timer.cancel();
                timer.purge();
            }
            model.quit();
            vue.displayWinScreen();
        }
    }

    /**
     * @return longueur du plateau
     */
    public int getLongueur() {
        return model.getLongueur();
    }

    /**
     * @return largeuru du plateau
     */
    public int getLargeur() {
        return model.getLargeur();
    }

    /**
     * Annule la derniere action (la derniere pipe tournée)
     */
    public void undo() {
        model.undo();
        vt.afficheNiv(model, debug);
        vue.update();
    }

    /**
     * Ferme la vue du plateau et réactive le menu
     */
    public void quit() {
        if(timer != null) {
            timer.cancel();
            timer.purge();
        }
        fenetre.setEnabled(true);
        model.quit();
    }

    /**
     * Active ou désactive le mode debug
     */
    public void debug() {
        if(debug) debug = false;
        else debug = true;
    }
}
