package Aquavias.model;

public class Tuyau {
    //Entre/sortie/rien
    private char type;
    //liste des entrées possibles
    private boolean[] connections;
    //savoir si un tuyau est rempli
    private boolean rempli;
    //affiche un char associé à un tuyau
    private char[] afficheTerm;
    //pour faciliter l'interface graphique
    private String fileName;

    public Tuyau(int i, char t) {
        //chaque i correspond à un tuyau diffrent. fill indique si le tuyau est rempli dès sa création
        switch (i) {
            //Entree sortie
            case 0:
                connections = new boolean[]{true, false, false, false};
                rempli = false;
                type = t;
                afficheTerm = new char[]{'╨','╞','╥','╡'};
                if(t=='E') fileName = t+"0"+true;
                else fileName = t+"0"+false;
                break;
            //Tuyau en L
            case 1:
                connections = new boolean[]{true, true, false, false};
                rempli = false;
                type = t;
                afficheTerm = new char[]{'╚','╔', '╗', '╝'};
                fileName = t+"1"+false;
                break;
            //Tuyau droit
            case 2:
                connections = new boolean[]{true, false, true, false};
                rempli = false;
                type = t;
                afficheTerm = new char[]{'║','═','║','═'};
                fileName = t+"2"+false;
                break;
            //Tuyau en T
            case 3:
                connections = new boolean[]{true, true, true, false};
                rempli = false;
                type = t;
                afficheTerm = new char[]{'╠', '╦', '╣', '╩'};
                fileName = t+"3"+false;
                break;
            //Tuyaux nulls
            default:
                connections = new boolean[]{true, true, true, true};
                rempli = false;
                type = 'S';
                afficheTerm = new char[]{'╬', '╬', '╬', '╬'};
                fileName = t+"4"+false;
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

    public int getRotation() {
        for (int i = 0; i < connections.length; i++) {
            if(connections[i]) return i;
        }
        return 0;
    }

    public void afficher() {
        System.out.println(afficheTerm[0]);
    }

    public String toString() {
        return fileName;
    }

    //
    public boolean isRacine() {
        if(rempli && fileName.equals("E0true") && type=='E') return true;
        return false;
    }

    public boolean isArrivee() {
        if(type=='S') return true;
        return false;
    }
}