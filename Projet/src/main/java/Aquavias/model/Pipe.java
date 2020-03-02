package Aquavias.model;

public abstract class Pipe {
    //Si la pièce peut etre tournée
    protected boolean moveable;
    //liste des entrées possibles
    protected boolean[] connections;
    //savoir si un Pipe est rempli
    protected boolean rempli;
    //affiche un char associé à un Pipe
    protected static char[] afficheTerm = new char[]{'╨','╞','╥','╡','╚','╔', '╗', '╝','║','═','║','═','╠', '╦', '╣', '╩'};
    //pour faciliter l'interface graphique
    protected String fileName;

    protected int rotation;

    protected int index;

    public void rotate() {
        for (int i = 0; i < connections.length; i++) {
            //On décale le tableau vers la droite
            connections[i] = connections[(i-1)%4];
            afficheTerm[i] = afficheTerm[(i-1)%4];
            rotation++;
            rotation %= 4;
        }
    }

    public void rotate(int i) {
        if(moveable) {
            for (int j = 0; j < i; j++) {
                this.rotate();
            }
        }
    }

    public void remplir() {
        rempli = true;
    }

    public void vide() {
        rempli = false;
    }

    public boolean connected(Pipe t) {
        for (int i = 0; i < connections.length; i++) {
            //On regarde si les connections opposé sont true
            if(this.connections[i] && t.connections[(i+2)%4]) return true;
        }
        //On retourne false si aucune connections n'est possible
        return false;
    }

    public int getRotation() {
        return rotation;
    }

    public void affiche() {
        System.out.print(afficheTerm[index+getRotation()]);
    }
    public String toString() {
        return fileName;
    }
} 

class PipeDepart extends Pipe{

    public PipeDepart() {
        this.moveable = false;
        this.connections = new boolean[]{true, false, false, false};
        this.rempli = true;
        this.fileName = "Depart";
        this.rotation = 0;
        this.index = 0;
    }

}

class PipeArrivee extends Pipe {

    public PipeArrivee() {
        this.moveable = false;
        this.connections = new boolean[]{true, false, false, false};
        this.rempli = false;
        this.fileName = "Arrivee";
        this.rotation = 0;
        this.index = 0;
    }

}

class PipeL extends Pipe {

    public PipeL(boolean move) {
        this.moveable = move;
        this.connections = new boolean[]{true, true, false, false};
        this.rempli = false;
        this.fileName = "PipeL";
        this.rotation = 0;
        this.index = 4;
    }

}

class PipeI extends Pipe {

    public PipeI(boolean move) {
        this.moveable = move;
        this.connections = new boolean[]{true, false, true, false};
        this.rempli = false;
        this.fileName = "PipeI";
        this.rotation = 0;
        this.index = 8;
    }
}

class PipeT extends Pipe {

    public PipeT(boolean move) {
        this.moveable = move;
        this.connections = new boolean[]{true, true, true, false};
        this.rempli = false;
        this.fileName = "PipeT";
        this.rotation = 0;
        this.index = 12;
    }
}

class PipeX extends Pipe {
    
    public PipeX() {
        this.moveable = false;
        this.connections = new boolean[]{true, true, true, true};
        this.rempli = false;
        this.fileName = "PipeX";
    }

    public void affiche() {
        System.out.print("╬");
    }
}