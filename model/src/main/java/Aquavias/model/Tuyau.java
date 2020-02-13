package Aquavias.model;

public class Tuyau {
    //liste des entrées possibles
    private boolean[] connections = new boolean[4];
    //savoir si un tuyau est rempli
    private boolean rempli;
    //affiche un char associé à un tuyau
    private char[] afficheTerm = new char[4];
    //pour faciliter l'interface gaphique
    private String fileName;

    public Tuyau(int i, boolean fill) {
        //chaque i correspond à un tuyau diffrent. fill indique si le tuyau est rempli dès sa création
        switch (i) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
            
                break;
            default:
                //Niveaux null
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