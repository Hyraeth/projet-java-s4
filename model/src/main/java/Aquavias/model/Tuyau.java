package Aquavias.model;

public class Tuyau {
    //Entre/sortie/rien
    private String type;
    //liste des entrées possibles
    private boolean[] connections;
    //savoir si un tuyau est rempli
    private boolean rempli;
    //affiche un char associé à un tuyau
    private char[] afficheTerm;
    //pour faciliter l'interface graphique
    private String fileName;

    public Tuyau(int i, boolean fill, String t) {
        //chaque i correspond à un tuyau diffrent. fill indique si le tuyau est rempli dès sa création
        switch (i) {
            //Entree sortie
            case 0:
                connections = new boolean[]{true, false, false, false};
                rempli = fill;
                type = t;
                afficheTerm = new char[]{'╨','╞','╥','╡'};
                fileName = t+"0"+fill;
                break;
            //Tuyau en L
            case 1:
                connections = new boolean[]{true, true, false, false};
                rempli = fill;
                type = t;
                afficheTerm = new char[]{'╚','╔', '╗', '╝'};
                fileName = t+"1"+fill;
                break;
            //Tuyau droit
            case 2:
                connections = new boolean[]{true, false, true, false};
                rempli = fill;
                type = t;
                afficheTerm = new char[]{'║','═','║','═'};
                fileName = t+"2"+fill;
                break;
            //Tuyau en T
            case 3:
                connections = new boolean[]{true, true, true, false};
                rempli = fill;
                type = t;
                afficheTerm = new char[]{'╠', '╦', '╣', '╩'};
                fileName = t+"3"+fill;
                break;
            //Tuyaux nulls
            default:
                break;
        }
    }

    public void rotate() {
        for (int i = 0; i < connections.length; i++) {
            //On décale le tableau vers la droite
            connections[i] = connections[(i-1)%4];
            afficheTerm[i] = afficheTerm[(i-1)%4];
        }
    }

    public void rotate(int i) {
        for (int j = 0; j < i; j++) {
            this.rotate();
        }
    }

    public void remplir() {
        rempli = true;
    }

    public boolean connected(Tuyau t) {
        for (int i = 0; i < connections.length; i++) {
            //On regarde si les connections opposé sont true
            if(this.connections[i] && t.connections[(i+2)%4]) return true;
        }
        //On retourne false si aucune connections n'est possible
        return false;
    }

    public String toString() {
        return afficheTerm[0]+"";
    }

    //
    public boolean isRacine() {
        if(rempli && fileName.equals("racine")) return true;
        return false;
    }
}