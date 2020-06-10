package Aquavias.model;

import java.awt.Point;
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Object.*;

import Aquavias.vue.GUI.VueTerm;

import org.apache.commons.io.FileUtils;
import org.json.*;


public class Niveau {
    private Pipe[][] niveau;
    private int resources;
    public void setResources(int nb) {this.resources = nb;}
    private int[] score;
    private Stack<int[]> retour = new Stack<int[]>();            // pile avec tableau de taille 2 avec les coordonées des pipes tournées
    private int type; //Si = 0 aucune type, = 1 limite de mouvement, = 2 limite de réserve
    private int lvlNumber;
    private String lvlType;


    public Niveau(int m, int n) {
        this.niveau = new Pipe[m][n];
        this.resources = 0;
        this.type = 0;
        this.score = new int[3];
        this.score[0] = 20;
        this.score[1] = 15;
        this.score[2] = 10;
        this.type = 0;
    }

    public Niveau() {
        this(0, 0);
    }

    public void load(File f, String type, int lvl) throws IOException {
        if (!f.exists())
            return;
        JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
        JSONArray lvl_liste = json.getJSONArray(type);

        JSONObject level = lvl_liste.getJSONObject(lvl);

        int largeur = level.getInt("largeur");
        int longueur = level.getInt("longueur");
        this.setSize(largeur, longueur);

        String config = level.getString("configuration");
        initConfig(config);

        this.resources = level.getInt("resources");
        this.type = level.getInt("type");

        lvlNumber = lvl;
        lvlType = type;
    }

    public static int getNumberLvl(File f, String type) throws IOException {
        if (!f.exists())
            return 0;
        JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
        JSONArray lvl_liste = json.getJSONArray(type);
        return lvl_liste.length();
    }

    public int getLvlNumber() {
        return lvlNumber;
    }

    public String getLvlType() {
        return lvlType;
    }

    public boolean countdown() {
        if(resources!=0) resources--;
        return finis();
    }

    public int getType() {
        return this.type;
    }

    public Pipe getPipe(int i, int j) {
        return niveau[i][j];
    }

    public int getresources() {
      return resources;
    }

    public void setSize(int m, int n) {
        this.niveau = new Pipe[m][n];
    }


    public int getLongueur(int i){
      return this.niveau[i].length;
    }
    public int getLongueur() {
        return getLongueur(0);
    }

    public int getLargeur() {
        return this.niveau.length;
    }

    public int[] getScore() {
      return this.score;
    }

    public void setNiveau(Pipe[][] p) {
        this.niveau = p;
    }

    public void addRetour(int i, int j) {
      int[] coordonnees = new int[2];
      coordonnees[0] = i;
      coordonnees[1] = j;
      this.retour.push(coordonnees);
    }

    public boolean finis () {
        boolean cont = true;
        switch(type){
            case 1: cont = limMouv(); //S'il y a une limite de mouvement
            case 2: cont = limRes();  //S'il y a une limite de réservoir
        }
        for (int i = 0; i<this.getLargeur(); i++) {
            if (niveau[i][this.getLongueur()-1]!=null && niveau[i][this.getLongueur()-1] instanceof PipeArrivee) {
                return cont && this.remplir() && niveau[i][this.getLongueur()-1].rempli;
            }
        }
        return false;
    }

    public boolean limMouv(){ //Return true si on a utilisé le nombre de coup limité
        if( resources >= 0) return true;
        return false;
    }

    public boolean limRes(){ //Return true si on il y a encore de l'eau dans le réservoir
        if( resources >= 0) return true;
        return false;
    }

    public void initConfig(String s) {
        PipeFactory initPipe = new PipeFactory();
        int longueur = getLongueur();
        if (s.length() % 3 == 0) {
            for (int i = 0; i < s.length(); i += 3) {
                boolean moveable = (s.charAt(i + 2) == 'T') ? true : false;
                niveau[i / (3*longueur)][(i / 3)%longueur] = initPipe.getPipe(Character.getNumericValue(s.charAt(i)), moveable);
                niveau[i / (3*longueur)][(i / 3)%longueur].rotate(Character.getNumericValue(s.charAt(i + 1)));
            }
        }
    }

    public boolean remplir() {
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
            niveau[k][0].remplir();
            return this.remplir(k,1,3);
        } else return false;
    }
    //rempli d'eau et renvoie true si il n'y a pas de fuite.
    public boolean remplir(int x, int y, int prec) {
        if (niveau[x][y].connections[prec]){  //Si il est connecté au precedent.
            niveau[x][y].remplir();
        } else return false;
        boolean fuite = this.fuite(x,y);   //est vrai si il a une fuite.
        boolean a = true,b = true,c = true,d = true;
        boolean[] possible = this.possible(x, y, prec);
        if (possible[0]) a = this.remplir(x-1, y, 2);
        if (possible[1]) b = this.remplir(x, y+1, 3);
        if (possible[2]) c = this.remplir(x+1, y, 0);
        if (possible[3]) d = this.remplir(x, y-1, 1);
        return (!fuite && a && b && c && d);
    }

    //return les possibilités d'accès aux cases suivantes.
    public boolean[] possible (int i, int j, int prec) {
        boolean[] b = new boolean[4];
        if (i-1>=0             //Si la case est dans le plateau
            && niveau[i][j].connections[0]==true   //S'il est connecté au suivant
            && niveau[i-1][j]!=null                //Si le suivant n'est pas null
            && !niveau[i-1][j].rempli) {           //Si le suivant n'est pas déjà rempli
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
        if (niveau[i][j].connections[0]==true        //S'il est connecté au suivant
            && (i-1<0                                //Si la case n'est pas dans le plateau
                || niveau[i-1][j]==null              //Si le suivant est null
                || !niveau[i-1][j].connect(2))){     //Si le suivant n'est pas connecté à lui
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
        if(resources != 0 && niveau[i][j] != null && niveau[i][j].moveable) {
            retour.add(new int[]{i,j});
            niveau[i][j].rotate();
        }
        if (type==1) {
            resources--;
        }
    }

    public void undo() {
      if (this.retour.isEmpty()) System.out.println("vous ne pouvez pas faire un undo");
      else {
        int[] tuile = this.retour.peek();
        int i = tuile[0];
        int j = tuile[1];
        this.retour.pop();
        if (resources != 0 && niveau[i][j].moveable) {
          if(niveau[i][j] != null) {
            for (int k=0; k<3; k++) {    // on tourne 3 fois
              niveau[i][j].rotate();
            }
            if(this.type == 1)resources++;
          }
        }
      }
    }

    public void quit() {
      this.resources = 0;
    }


    public String toString() {
        String s = "";
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if (niveau[i][j] != null)
                    s += i+" "+j+" "+niveau[i][j].toString()+"\n";
            }
        }
        return s;
    }


    public boolean correct(int premier, int deuxieme) {        // renvoie true si les coordonnées sont bonnes
      if (premier == -1 || deuxieme == -1) return false;
      if (premier >= 0  && premier < niveau.length) {
        if (deuxieme >= 0  && deuxieme < niveau[0].length) {
          if (this.niveau[premier][deuxieme] != null) {
            if (this.niveau[premier][deuxieme].isMoveable()) return true;
            else {
              System.out.println("Vous ne pouvez pas tourner ce tuyau");
              return false;
            }
          }
          else {
            System.out.println("Il n y a pas de tuyau à cette position");
            return false;
          }
        }
        else {
          System.out.println("Votre deuxieme chiffre est incorrect");
          return false;
        }
      }
      System.out.println("Votre premier chiffre est incorrect");
      return false;
    }


    public boolean partieTerminee() {
      if (resources > score[0]+5) return true;  // si nb de resources depassé
      if (finis()) return true;
      return false;
    }


    public boolean gagnable(int x, int y) {
        boolean b = false;
        int[] tab = this.caseSuivante(x, y);
        for (int i = 0; i < 4; i++) {

            if (this.finis())
                return true;

            if ((x == this.getLargeur() - 1) && (y == this.getLongueur() - 1)) {
                this.rotate(x, y);

            } else {
                b = gagnable(tab[0], tab[1]);
                if (b)
                    return true;
                this.rotate(x, y);

            }
            

        }
        return false;
    }

    //resolveur de niveau :
    public boolean resolution() {
        ArrayList<Point> list = new ArrayList<Point>();
        int i = 0;
        int j = 0;
        while ( !(niveau[i][j] instanceof PipeArrivee) ) {
            j++;
        }
        list.add(new Point(i,j));

        return true;
    }
    public boolean resolve() {
        return true;
    }

    //verifie si il appartient deja a la list
    public boolean appartient(Point p, ArrayList<Point> list) {
        for (Point point : list) {
            if ( point.equals(p) ) return true;
        }
        return false;
    }

    public int[] caseSuivante(int x, int y) {
        int[] tab = new int[2];

        if ((x == this.niveau.length-1) && (y == this.niveau[0].length-1)) {
            tab[0] = x;
            tab[1] = y;
            return tab;
        }
        if (y == this.niveau[0].length-1) {
            tab[0] = x+1;
            tab[1] = 0;
        } else {
            tab[0] = x;
            tab[1] = y+1;
        }
        return tab;
    }



    public static void main(String[] args) {
      VueTerm vt = new VueTerm();
        Niveau n = new Niveau();
        vt.afficheNiv(n, true);
        File f = new File("assets\\lvls\\niveau.json");
        System.out.println(f.toPath());
        try {
            n.load(f, "niveaux_off", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        vt.afficheNiv(n, true);
        System.out.println(n.niveau[1][1]);
        n.rotate(1, 1);
        System.out.println();
        vt.afficheNiv(n, true);
    }
}
