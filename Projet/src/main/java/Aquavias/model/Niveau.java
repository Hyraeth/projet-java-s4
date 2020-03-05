package Aquavias.model;

import java.io.*;

import org.apache.commons.io.FileUtils;
import org.json.*;


public class Niveau {
    private Pipe[][] niveau;
    private int coups;
    private int score;

    public void setNiveau(Pipe[][] pip) {
        niveau = pip;
    }

    public Niveau(int m, int n) {
        this.niveau = new Pipe[m][n];
        this.coups = 0;
        this.score = 0;
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

    public void initConfig(String s) {
        PipeFactory initPipe = new PipeFactory();
        int longueur = getLongueur();
        if (s.length() % 3 == 0) {
            for (int i = 0; i < s.length(); i += 3) {
                boolean moveable = (s.charAt(i + 1) == 'T') ? true : false;
                niveau[i / longueur][i % longueur] = initPipe.getPipe(Character.getNumericValue(s.charAt(i)), moveable);
                niveau[i / longueur][i % longueur].rotate(Character.getNumericValue(s.charAt(i + 1)));
            }
        }
    }

    // tourne le Pipe à la position i,j
    public void rotate(int i, int j) {
        if (coups != 0) {
            niveau[i][j].rotate();
            coups--;
        }
    }

    // Calcule l'écoulement de l'eau (rempli les tuyaux qu'il faut)
    public void flow() {

    }

    public void calculateScore() {
        score = "qqchose".length();
    }

    public boolean finish() {
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if (niveau[i][j] != null && (niveau[i][j] instanceof PipeArrivee))
                    return niveau[i][j].rempli;
            }
        }
        return false;
    }

    public void saveScore() {

    }

    // affiche le plateau dans le terminal
    public void affiche() {
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
                    s += niveau[i][j].toString();
                else
                    System.out.print(" ");
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

    public static void main(String[] args) {
        Niveau n = new Niveau();
        n.affiche();
        File f = new File("..\\assets\\lvls\\niveau.json");
        System.out.println(f.toPath());
        try {
            n.load(f, "niveaux_off", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(n);
    }
}