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

    //return un boolean de taille 2 avec dans la première case, si l'eau a atteint l'arrivé,
    //et dans la deuxième, si il n'y a pas de fuite.
    public static boolean[] remplir(Pipe[][] pip, int x, int y, int prec) {
        boolean[] res = new boolean[2];
        if (Pipe[x][y].connections[prec]==true){  //Si il est connecté au precedent.
            pip[x][y].remplir();
            if (pip[x][y] instanceof PipeArrivee) res[0] = true;
        }
        res[1] = fuite();
        boolean[] a,b,c,d;
        boolean[] possible = possible(pip, x, y, prec);
        if (possible[0]) a=remplir(pip, x-1, y, 2);
        if (possible[1]) a=remplir(pip, x, y+1, 2);
        if (possible[2]) a=remplir(pip, x+1, y, 2);
        if (possible[3]) a=remplir(pip, x, y-1, 2);
        res[0]=(res[0] || a[0] || b[0] || c[0] || d[0]);
        res[1]=(res[0] && a[0] && b[0] && c[0] && d[0]);
    }

    //return les possibilité d'acces aux cases suivantes.
    public static boolean[] possible (Pipe[][] pip, int i, int j, int prec) {
        boolean[] b = new boolean[4];
        if (i-1>=0             //Si la case est dans le plateau
            && pip[i][j].connections[0]==true   //Si il est connecté au suivant
            && pip[i-1][j]!=null                 //Si le suivant n'est pas null
            && !pip[i-1][j].rempli) {          //Si le suivant n'est pas deja rempli
                b[0] = true;
        }
        if (j+1<pip[0].length
            && pip[i][j].connections[1]==true
            && pip[i][j+1]!=null
            && !pip[i][j+1].rempli){
                b[1] = true;
        }
        if (i+1<pip.length
            && pip[i][j].connections[2]==true
            && pip[i+1][j]!=null
            && !pip[i+1][j].rempli){
                b[2] = true;
        }
        if (j-1>=0
            && pip[i][j].connections[3]==true
            && pip[i][j-1]!=null
            && !pip[i][j-1].rempli){
                b[3] = true;
        }
        return b;
    }
    public static boolean fuite(Pipe[][] pip, int x, int y) {
        
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
            System.out.print(Color.BLUE_BRIGHT );
            System.out.print(afficheTerm[index+getRotation()]);
            System.out.print(Color.RESET);
        }else{
            System.out.print(afficheTerm[index+getRotation()]);
        }
    }
    public String toString() {
        return fileName;
    }
} 

class PipeDepart extends Pipe{

    public PipeDepart() {
        this.moveable = false;
        this.connections = new boolean[]{false, true, false, false};
        this.rempli = true;
        this.fileName = "Depart";
        this.rotation = 1;
        this.index = 0;
    }

}

class PipeArrivee extends Pipe {

    public PipeArrivee() {
        this.moveable = false;
        this.connections = new boolean[]{false, false, false, true};
        this.rempli = false;
        this.fileName = "Arrivee";
        this.rotation = 3;
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