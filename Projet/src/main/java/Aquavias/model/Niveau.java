package Aquavias.model;

import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.json.*;

public class Niveau {
    private Pipe[][] niveau;
    private int coups;
    private int[] score;

    public void setNiveau(Pipe[][] pip) {niveau=pip;}

    public Niveau(int m, int n) {
        this.niveau = new Pipe[m][n];
        this.coups = 0;
        this.score = new int[3];
        this.score[0] = 20;
        this.score[1] = 15;
        this.score[2] = 10;
    }

    public Niveau() {
        this(0,0);
    }
    /*
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
    } */

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

    //Calcule l'écoulement de l'eau (rempli les tuyaux qu'il faut)
    public void flow() {

    }

    //affiche le plateau dans le terminal
    public void affiche() {
        System.out.print("           Niveau 1\n\n  ");  // on changera le 1 par le niveau du fichier json après
        for (int x = 0; x< niveau[0].length; x++) {
          System.out.print(x);
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < niveau.length; i++) {
            System.out.print(i + " ");
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


    public void jouer() {

      while(!partieTerminee()) {    // tant que la partie n'est pas terminée

        int str = 0;

        try{
          System.out.println("Choisissez un tuyau à tourner");
          str = Jeu.sc.nextInt();
        }
        catch (NumberFormatException e){ //Si ce n'est pas un nombre
          System.out.println("Ce n'est pas un nombre");
        }

        if (correct(str)) {        // si la valeur rentré par le scanner est correct
          rotate(str);             // on tourne
          coups++;
        }
        else System.out.println("Vous n'avez pas rentré une valeur correct. Réessayez.");
        affiche();                 // affiche le niveau à chaque fois qu'on tourne un tuyau
      }

      Score.afficher(this.score, this.coups, 1);  // en attendant de coder json, on fait pour niveau 1
    }


    public boolean correct(int tuyau) {        // renvoie true si les coordonnées sont bonnes
      int premier = tuyau/10;
      int deuxieme = tuyau%10;
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


    public void rotate(int tuyau) {
      int premier = tuyau/10;
      int deuxieme = tuyau%10;
      System.out.println("la piece tourne.......");
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
}
