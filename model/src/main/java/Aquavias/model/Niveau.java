package Aquavias.model;

public class Niveau {
    private Tuyau[][] niveau;
    private int coups;
    private int score;

    public Niveau() {

    }

    //tourne le tuyau à la position i,j
    public void rotate(int i, int j) {

    }

    //Calcule l'écoulement de l'eau
    public void flow() {

    }

    //affiche le plateau dans le terminal
    public void affiche() {
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if(niveau[i][j] != null) niveau[i][j].afficher();
                else System.out.println(" ");
            }
            System.out.println();
        }
    }

    public int[] getPosRacine() {
        int tab[] = new int[2];
        for (int i = 0; i < niveau.length; i++) {
            for (int j = 0; j < niveau[i].length; j++) {
                if(niveau[i][j].isRacine()) {
                    tab[0] = i;
                    tab[1] = j;
                }
            }
        }
        return tab;
    }
}