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

    protected int indexTerm;

    protected int indexGUI;

    public String getFilename() {
        return fileName;
    }

    public void rotate() {
        boolean last = connections[3];
        for( int i =2; i > 0 ; i-- )
            connections[i+1] = connections[i];
        connections[0] = last;
        rotation = (rotation+1)%4;
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
        if(rempli){
            System.out.print(""+Color.BLUE_BRIGHT + afficheTerm[indexTerm+getRotation()] + Color.RESET);
        }else{
            System.out.print(afficheTerm[indexTerm+getRotation()]);
        }
    }
    public String toString() {
        return "moveable :"+moveable+"; rempli :"+rempli+"; rotation :"+rotation+"; indexTerm :"+indexTerm+"; indexGui :"+indexGUI;
    }

    public static void main(String[] args) {
        PipeDepart pd = new PipeDepart();
        PipeArrivee pa = new PipeArrivee();
        PipeI pi = new PipeI(true);
        PipeL pl = new PipeL(true);
        PipeT pt = new PipeT(true);
        PipeX px = new PipeX();
        pd.affiche();
        pa.affiche();
        pi.affiche();
        pl.affiche();
        pt.affiche();
        px.affiche();
        pd.rotate(); pd.affiche();
    }

	public boolean isRempli() {
		return rempli;
	}

	public int getIndexGui() {
		return this.indexGUI;
    }
    
    public boolean getRempli() {
        return rempli;
    }
} 

class PipeDepart extends Pipe{

    public PipeDepart() {
        this.moveable = false;
        this.connections = new boolean[]{false, true, false, false};
        this.rempli = true;
        this.fileName = "Depart"+rempli;
        this.rotation = 1;
        this.indexTerm = 0;
        this.indexGUI = 0;
    }

}

class PipeArrivee extends Pipe {

    public PipeArrivee() {
        this.moveable = false;
        this.connections = new boolean[]{false, false, false, true};
        this.rempli = false;
        this.fileName = "Arrivee"+rempli;
        this.rotation = 3;
        this.indexTerm = 0;
        this.indexGUI = 4;
    }

}

class PipeL extends Pipe {

    public PipeL(boolean move) {
        this.moveable = move;
        this.connections = new boolean[]{true, true, false, false};
        this.rempli = false;
        this.fileName = "PipeL"+rempli;
        this.rotation = 0;
        this.indexTerm = 4;
        this.indexGUI = 8;
    }

}

class PipeI extends Pipe {

    public PipeI(boolean move) {
        this.moveable = move;
        this.connections = new boolean[]{true, false, true, false};
        this.rempli = false;
        this.fileName = "PipeI"+rempli;
        this.rotation = 0;
        this.indexTerm = 8;
        this.indexGUI = 12;
    }
}

class PipeT extends Pipe {

    public PipeT(boolean move) {
        this.moveable = move;
        this.connections = new boolean[]{true, true, true, false};
        this.rempli = false;
        this.fileName = "PipeT"+rempli;
        this.rotation = 0;
        this.indexTerm = 12;
        this.indexGUI = 16;
    }
}

class PipeX extends Pipe {
    
    public PipeX() {
        this.moveable = false;
        this.connections = new boolean[]{true, true, true, true};
        this.rempli = false;
        this.fileName = "PipeX"+rempli;
        this.indexGUI = 20;
    }

    public void affiche() {
        System.out.print("╬");
    }
}