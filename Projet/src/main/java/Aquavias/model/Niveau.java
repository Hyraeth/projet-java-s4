package Aquavias.model;

import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.json.*;

public class Niveau {
    private Pipe[][] niveau;
    private int coups;
    private int score;

    public void setNiveau(Pipe[][] pip) {niveau=pip;}

    public Niveau(int m, int n) {
        this.niveau = new Pipe[m][n];
        this.coups = 0;
        this.score = 0;
    }

    public Niveau() {
        this(0,0);
    }

    public void load (File f, String type, int lvl) throws IOException {
		if (!f.exists()) return;
		JSONObject json = new JSONObject (FileUtils.readFileToString (f, "utf-8"));
		JSONArray lvl_liste = json.getJSONArray (type);

        JSONObject level = lvl_liste.getJSONObject(lvl);

        int largeur = level.getInt("largeur");
        int longueur = level.getInt("longueur");
        this.setSize(largeur, longueur);

        String config = level.getString("config");
        initConfig(config);

        this.coups = level.getInt("coups");
        this.score = level.getInt("score");
    }

    public void setSize(int m, int n) {
        this.niveau = new Pipe[m][n];
    }

    public int getLongueur() {
        return this.niveau[0].length;
    }

    public int getLargeur() {
        return this.niveau.length;
    }

    public void initConfig (String s) {
        PipeFactory initPipe = new PipeFactory();
        int longueur = getLongueur();
        int n;
        if(s.length()%3==0) {
            for (int i = 0; i < s.length(); i+=3) {
                n = (int)(Math.random() * 7);
                niveau[i/longueur][i%longueur] = initPipe.getPipe(n, false);
                niveau[i/longueur][i%longueur].rotate(Character.getNumericValue(s.charAt(i+1)));
            }
        }
    }

    public void remplir() {
        int k=0;
        for (int i = 0; i<this.getLargeur(); i++) {
            for (int j = 0; j<this.getLongueur(); j++) {
                if (niveau[i][j] != null) niveau[i][j].vide();
            }
            if (niveau[i][0] instanceof PipeDepart) {
                k=i;
            }
        }
        if (niveau[k][0] instanceof PipeDepart) {
            this.remplir(k,1,3);
        }
    }
    //return un boolean de taille 2 avec dans la première case, si l'eau a atteint l'arrivé,
    //et dans la deuxième, si il n'y a pas de fuite.
    public boolean remplir(int x, int y, int prec) {
        if (niveau[x][y].connections[prec]==true){  //Si il est connecté au precedent.
            niveau[x][y].remplir();
        } else return false;
        boolean fuite = this.fuite(x,y);   //est vrai si il a une fuite.
        boolean a = false,b = false,c = false,d = false;
        boolean[] possible = this.possible(x, y, prec);
        if (possible[0]) a = this.remplir(x-1, y, 2);
        if (possible[1]) b = this.remplir(x, y+1, 3);
        if (possible[2]) c = this.remplir(x+1, y, 0);
        if (possible[3]) d = this.remplir(x, y-1, 1);
        return (!fuite && a && b && c && d);
    }

    //return les possibilité d'acces aux cases suivantes.
    public boolean[] possible (int i, int j, int prec) {
        boolean[] b = new boolean[4];
        if (i-1>=0             //Si la case est dans le plateau
            && niveau[i][j].connections[0]==true   //Si il est connecté au suivant
            && niveau[i-1][j]!=null                 //Si le suivant n'est pas null
            && !niveau[i-1][j].rempli) {          //Si le suivant n'est pas deja rempli
                b[0] = true;
        }
        if (j+1<niveau[0].length
            && niveau[i][j].connections[1]==true
            && niveau[i][j+1]!=null
            && !niveau[i][j+1].rempli){
                b[1] = true;
        }
        if (i+1<niveau.length
            && niveau[i][j].connections[2]==true
            && niveau[i+1][j]!=null
            && !niveau[i+1][j].rempli){
                b[2] = true;
        }
        if (j-1>=0
            && niveau[i][j].connections[3]==true
            && niveau[i][j-1]!=null
            && !niveau[i][j-1].rempli){
                b[3] = true;
        }
        return b;
    }
    //return true si il y a une fuite 
    public boolean fuite(int i, int j) {
        if (niveau[i][j].connections[0]==true   //Si il est connecté au suivant
            && (i-1<0                        //Si la case n'est pas dans le plateau
                || niveau[i-1][j]==null                 //Si le suivant est null
                || !niveau[i-1][j].connect(2))){          //Si le suivant n'est pas connecté a lui
            return true;
        }
        if (niveau[i][j].connections[1]==true
            && (j+1>=niveau[0].length
                || niveau[i][j+1]==null
                || !niveau[i][j+1].connect(3))){
            return true;
        }
        if (niveau[i][j].connections[2]==true
            && (i+1>=niveau.length
                || niveau[i+1][j]==null
                || !niveau[i+1][j].connect(0))){
            return true;
        }
        if (niveau[i][j].connections[3]==true
            && (j-1<0
                || niveau[i][j-1]==null
                || !niveau[i][j-1].connect(1))){
            return true;
        }return false;
    }

    //tourne le Pipe à la position i,j
    public void rotate(int i, int j) {
        niveau[i][j].rotate();
    }

    //Calcule l'écoulement de l'eau (rempli les tuyaux qu'il faut)
    public void flow() {

    }

    //affiche le plateau dans le terminal
    public void affiche() {
        this.remplir();
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if(niveau[i][j] != null) niveau[i][j].affiche();
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
    //affiche le plateau dans le terminal avec des séparation.
    public void afficheAvecCase() {
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if (j==0) System.out.print("|");
                if(niveau[i][j] != null) niveau[i][j].affiche();
                else System.out.print(" ");
                System.out.print("|");
            }
            System.out.println();
            for (int j = 0; j < 2*niveau[i].length+1; j++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }
}