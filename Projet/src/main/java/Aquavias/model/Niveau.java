package Aquavias.model;

import java.io.*;

import org.apache.commons.io.FileUtils;
import org.json.*;


public class Niveau {
    private Pipe[][] niveau;
    private int coups;
    private int[] score;

    public void setNiveau(Pipe[][] pip) {
        niveau = pip;
    }

    public Niveau(int m, int n) {
        this.niveau = new Pipe[m][n];
        this.coups = 0;
        this.score = new int[3];
        this.score[0] = 20;
        this.score[1] = 15;
        this.score[2] = 10;
    }

    public Niveau() {
        this(0, 0);
    }

    public void load(File f, String type, int lvl) throws IOException {
        if (!f.exists())
            return;
        System.out.println("file found");
        JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
        JSONArray lvl_liste = json.getJSONArray(type);

        JSONObject level = lvl_liste.getJSONObject(lvl);

        int largeur = level.getInt("largeur");
        int longueur = level.getInt("longueur");
        this.setSize(largeur, longueur);

        String config = level.getString("configuration");
        System.out.println(config);
        initConfig(config);

        this.coups = level.getInt("coups");
        //this.score = level.getInt("score");
    }

    public Pipe getPipe(int i, int j) {
        return niveau[i][j];
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

    public int[] getScore() {
      return this.score;
    }

    public void initConfig(String s) {
        PipeFactory initPipe = new PipeFactory();
        int longueur = getLongueur();
        System.out.println(longueur);
        if (s.length() % 3 == 0) {
            for (int i = 0; i < s.length(); i += 3) {
                boolean moveable = (s.charAt(i + 2) == 'T') ? true : false;
                niveau[i / (3*longueur)][(i / 3)%longueur] = initPipe.getPipe(Character.getNumericValue(s.charAt(i)), moveable);
                niveau[i / (3*longueur)][(i / 3)%longueur].rotate(Character.getNumericValue(s.charAt(i + 1)));
            }
        }
    }

    // tourne le Pipe à la position i,j
    public void rotate(int i, int j) {
        if (coups != 0 && niveau[i][j].moveable) {
            if(niveau[i][j] != null) niveau[i][j].rotate();
            coups--;
        }
    }*/

    // Calcule l'écoulement de l'eau (rempli les tuyaux qu'il faut)
    public void flow() {

    }

    /*public void calculateScore() {
        score = "qqchose".length();
    } */

    public boolean finish() {
        for (int i = 0; i < niveau.length; i++) {
            if (/*niveau[i][niveau[0].length-1] != null && */(niveau[i][niveau[0].length-1] instanceof PipeArrivee))
                return niveau[i][niveau[0].length-1].rempli;

        }
        return false;
    }

    public void saveScore() {

    }

    // affiche le plateau dans le terminal
    public void affiche() {

        System.out.print("           Niveau 1     coups: " + coups + "\n\n  ");  // on changera le 1 par le niveau du fichier json après
        for (int x = 0; x< niveau[0].length; x++) {
          System.out.print(x);
        }
        System.out.println();
        System.out.println();

        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if (niveau[i][j] != null)
                    niveau[i][j].affiche();
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
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

    // affiche le plateau dans le terminal avec des séparation.
    public void afficheAvecCase() {
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if (j == 0)
                    System.out.print("|");
                if (niveau[i][j] != null)
                    niveau[i][j].affiche();
                else
                    System.out.print(" ");
                System.out.print("|");
            }
            System.out.println();
            for (int j = 0; j < 2 * niveau[i].length + 1; j++) {
                System.out.print("-");
            }
            System.out.println();
        }
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


    public void rotate(int premier, int deuxieme) {
      niveau[premier][deuxieme].rotate(1);
    }


    public boolean partieTerminee() {
      if (coups > score[0]+5) return true;  // si nb de coups depassé
      if (arrivee()) return true;
      return false;
    }

    public boolean arrivee() { // return true si eau arrivee à destination
      // à coder
      return false;  // pour tester en attendant
    }

    public static void main(String[] args) {
        Niveau n = new Niveau();
        n.affiche();
        File f = new File("assets\\lvls\\niveau.json");
        System.out.println(f.toPath());
        try {
            n.load(f, "niveaux_off", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        n.affiche();
        System.out.println(n.niveau[1][1]);
        n.rotate(1, 1);
        System.out.println();
        n.affiche();
    }
}