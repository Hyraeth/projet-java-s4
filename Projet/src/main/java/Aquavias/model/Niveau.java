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

/**
 * Représente un plateau de jeu, un Niveau
 */
public class Niveau {
    /**
     * Représente le plateau
     */
    private Pipe[][] niveau;
    /**
     * Représente soit le temps soit le nombre de coup restant
     */
    private int resources;

    private int[] score;
    /**
     * pile avec tableau de taille 2 avec les coordonées des pipes tournées
     */
    private Stack<int[]> retour = new Stack<int[]>();
    /**
     * 0 aucune type, 1 limite de mouvement, 2 limite de réserve
     */
    private int type; 
    /**
     * Indique le numéro du niveau dans le fichier json
     */
    private int lvlNumber;
    /**
     * Indique s'il s'agit d'un niveau du fichier ou un niveau généré.
     */
    private String lvlType;


    /**
     * Crée un niveau de la taille spécifiée
     * @param m largeur
     * @param n longueur
     */
    public Niveau(int m, int n) {
        this.niveau = new Pipe[m][n];
        this.resources = 0;
        this.type = 0;

        this.type = 0;
    }

    /**
     * Crée un niveau vide de taille 0,0.
     */
    public Niveau() {
        this(0, 0);
    }

    /**
     * Charge le niveau lvl depuis le fichier f
     * @param f le fichier
     * @param type string indiquant le type de niveau 
     * @param lvl int indiquant le niveau 
     * @throws IOException si le fichier n'existe pas
     */
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

    /**
     * Sauvegarde un niveau dans le fichier f
     * @param f le fichier
     * @param type string indiquant le type de niveau 
     * @throws IOException si le fichier n'existe pas
     */
    public void save(File f, String type) throws IOException {
        if (!f.exists())
            return;
        JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
        JSONArray lvl_liste = json.getJSONArray(type);

        JSONObject jo = new JSONObject();
        jo.put("largeur", this.getLargeur()); 
        jo.put("longueur", this.getLongueur());
        jo.put("configuration", this.getConfig());
        jo.put("resources", this.resources);
        jo.put("type", this.type);

        lvl_liste.put(jo);
        json.put(type, lvl_liste);

        FileWriter fw = new FileWriter (f);
		fw.write (json.toString (2));
		fw.close ();
    }

    /**
     * Donne le nombre de niveau d'un certain type dans le fichier f
     * @param f le fichier
     * @param type le type de niveau
     * @return le nombre de niveau
     * @throws IOException si le fichier n'existe pas
     */
    public static int getNumberLvl(File f, String type) throws IOException {
        if (!f.exists())
            return 0;
        JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
        JSONArray lvl_liste = json.getJSONArray(type);
        return lvl_liste.length();
    }

    /**
     * Donne le numéro du niveau actuel
     * @return le numéro du niveua actuel
     */
    public int getLvlNumber() {
        return lvlNumber;
    }

    /**
     * Renvoie le type du niveau
     */
    public String getLvlType() {
        return lvlType;
    }

    /**
     * Change le type du niveau
     */
    public void setLvlType(String s) {
        this.lvlType = s;
    }

    /**
     * Décrémente le nombre de resources et renvoie true si le niveua est finis
     */
    public boolean countdown() {
        if(resources!=0 && !remplir()) resources--;
        return remplir();
    }

    /**
     * Renvoie le type de règle du niveau
     */
    public int getType() {
        return this.type;
    }

    /**
     * Change le type de règle du niveau
     */
    public void setType(int t) {
        this.type = t;
    }

    /**
     * Renvoie le tuyau à la position i,j du niveau
     * @return un tuyau
     */
    public Pipe getPipe(int i, int j) {
        return niveau[i][j];
    }

    /**
     * Renvoie le nombre de resouces restantes dans le niveau
     */
    public int getresources() {
      return resources;
    }

    /**
     * Change le nombre de resouces restantes dans le niveau
     */
    public void setResources(int r) {
        this.resources = r;
    }

    /**
     * Modifie la taille du niveau (écrase son contenu)
     */
    public void setSize(int m, int n) {
        this.niveau = new Pipe[m][n];
    }

    /**
     * Renvoie la longueur du niveau
     * @return int
     */
    public int getLongueur() {
        return this.niveau[0].length;
    }

    /**
     * Renvoie la largeur du niveau
     * @return int
     */
    public int getLargeur() {
        return this.niveau.length;
    }

    public int[] getScore() {
      return this.score;
    }

    /**
     * Modifie le contenu du niveau
     * @param p tableau de Pipe
     */
    public void setNiveau(Pipe[][] p) {
        this.niveau = p;
    }

    /**
     * Ajoute à la pile retour les coordonées i,j
     * @param i int
     * @param j int
     */
    public void addRetour(int i, int j) {
      int[] coordonnees = new int[2];
      coordonnees[0] = i;
      coordonnees[1] = j;
      this.retour.push(coordonnees);
    }

    /**
     * Vérifie si le nivau est finis
     * @return true si le niveau est finis
     */
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

    /**
     * Return true si on a utilisé le nombre de coup limité
     */
    public boolean limMouv(){ 
        if( resources >= 0) return true;
        return false;
    }

    /**
     * Return true si on il y a encore de l'eau dans le réservoir
     */
    public boolean limRes(){ 
        if( resources >= 0) return true;
        return false;
    }

    /**
     * Initialise un niveau à partir d'un string
     * @param s string
     */
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

    /**
     * Donne un string qui représente la config du niveau
     * @return Donne un string qui représente la config du niveau
     */
    public String getConfig() {
        String s = "";
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                s+=niveau[i][j].config();
            }
        }
        return s;
    }
    /**
     * Fait couler l'eau dans le niveau
     * @return true s'il n'y a pas de fuite
     */
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

    /**
     * rempli d'eau et renvoie true si il n'y a pas de fuite.
     * @param x int
     * @param y int
     * @param prec int
     * @return true si il n'y a pas de fuite.
     */
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

    /**
     * Retourne les possibilités d'accès aux cases suivantes.
     * @param i int
     * @param j int
     * @param prec int
     * @return les possibilités d'accès aux cases suivantes.
     */
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
    /**
     * Retourne true si il y a une fuite
     * @param i int
     * @param j int
     * @return true si il y a une fuite
     */
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

    /**
     * tourne le Pipe à la position i,j
     * @param i int
     * @param j int
     */
    public void rotate(int i, int j) {
        if(resources != 0 && niveau[i][j] != null && niveau[i][j].moveable) {
            retour.add(new int[]{i,j});
            niveau[i][j].rotate();
        }
        if (type==1) {
            resources--;
        }
    }

    /**
     * Tourne vers la gauche le dernier tuyau tourné et incrémente le nombre de resource selon le type de règle
     */
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

    /**
     * Remet à 0 les recources
     */
    public void quit() {
      this.resources = 0;
    }

    /**
     * renvoie un String qui représente le niveau
     */
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

    /**
     * renvoie true si les coordonnées sont bonnes
     * @param premier int
     * @param deuxieme int
     * @return true si les coordonnées sont bonnes
     */
    public boolean correct(int premier, int deuxieme) {        
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

    /**
     * revoie true si la partie est finie
     */
    public boolean partieTerminee() {
      if (resources > score[0]+5) return true;  // si nb de resources depassé
      if (finis()) return true;
      return false;
    }

    /**
     * @deprecated complexité trop élevée
     */
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

    /**
     * resolveur de niveau
     * @return true si la niveau peut etre fini
     * @deprecated
     */
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

    /**
     * @deprecated
     */
    public boolean resolve() {
        return true;
    }

    /**
     * verifie si il appartient deja a la list
     * @deprecated
     */
    public boolean appartient(Point p, ArrayList<Point> list) {
        for (Point point : list) {
            if ( point.equals(p) ) return true;
        }
        return false;
    }

    /**
     * @deprecated utilisée dans la fonction gagnable() de la classe Niveau
     */
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
