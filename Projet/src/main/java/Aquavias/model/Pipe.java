package Aquavias.model;

import Aquavias.vue.GUI.VueTerm;
/**
 * Représente un tuyau
 */
public abstract class Pipe {
    /**
     * Boolean qui indique si le tuyau peut être tourner.
     */
    protected boolean moveable;
    /**
     * Représente les connexions du tuyau
     */
    protected boolean[] connections;
    /**
     * Boolean qui indique si le tuyau est rempli
     */
    protected boolean rempli;
    /**
     * Tableau de char qui représentent chacun un tuyaux sous forme textuel.
     */
    protected static char[] afficheTerm = new char[]{'╨','╞','╥','╡','╚','╔', '╗', '╝','║','═','║','═','╠', '╦', '╣', '╩','╬'};
    /**
     * Indique quel fichier image choisir pour afficher le tuyau correctement dans l'interface graphique.
     */
    protected String fileName;
    /**
     * Indique quelle est la rotation actuelle du tuyau (i.e combien de fois le tuyau a été tourner depuis sa position initiale)
     */
    protected int rotation;
    /**
     * Donne la position dans le tableau de char où se trouve le char associé au tuyau.
     */
    protected int indexTerm;
    /**
     * Donne la position dans le tableau de Bufferedimage où se trouve l'image associée au tuyau (voir classe JPanelPipe)
     */
    protected int indexGUI;

    /**
     * Recupère le nom du  image correspondant au tuyau.
     * @return Un string représentant le nom du fichier.
     */
    public String getFilename() {
        return fileName;
    }

    /**
     * Récupère la position du charactère correspondant au tuyau
     * @return Un int correspondant à la position.
     */
    public int getIndexTerm(){
      return indexTerm;
    }

    /**
     * Récupère le charactère à la position i du tableau
     * @param i la position où se trouve le charactère à récupérer
     * @return Un charactère  qui représente le tuyau.
     */
    public char getTerm(int i){
      return afficheTerm[i];
    }

    /**
     * Tourne le tuyau de 90° vers la droite.
     * Il s'agit d'un décalage vers la droite du tableau de boolean des connections du tuyau.
     */
    public void rotate() {
        boolean last = connections[3];
        for( int i =2; i >= 0 ; i-- )
            connections[i+1] = connections[i];
        connections[0] = last;
        rotation = (rotation+1)%4;
    }

    /**
     * Tourne le tuyau i fois vers la droite.
     * @param i nombre de fois qu'il faut tourner le tuyau.
     */
    public void rotate(int i) {
        if(moveable) {
            for (int j = 0; j < i; j++) {
                this.rotate();
            }
        }
    }

    /**
     * Rempli un tuyau
     */
    public void remplir() {
        rempli = true;
    }

    /**
     * Vide un tuyau
     */
    public void vide() {
        rempli = false;
    }

    /**
     * Indique s'il y a une connexion possible à la position i du tableau de connexions.
     * @param i position à vérifiée
     * @return un boolean indiquant si la position i est une connexion
     */
    public boolean connect(int i) {
        return this.connections[i];
    }

    /**
     * Récupère le nombre de rotation du tuyau (modulo 4).
     * @return un int représentant le nombre de rotation depuis la position initiale
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Indique si un tuyau peut être tourner.
     * @return true si le tuyau peut etre trouner.
     */
    public boolean isMoveable() {
      return moveable;
    }

    /**
     * Affiche le tuyau sur le terminal avec des couleurs. (bleu si le tuyau est rempli blanc sinon)
     */
    public void affiche() {
        if(rempli){
            System.out.print(Color.BLUE_BRIGHT );
            System.out.print(afficheTerm[indexTerm+getRotation()]);
            System.out.print(Color.RESET);
        }else{
            System.out.print(afficheTerm[indexTerm+getRotation()]);
        }
    }

    /**
     * Return un string donnant des informations sur le tuyau.
     */
    public String toString() {
        return "moveable :"+moveable+"; rempli :"+rempli+"; rotation :"+rotation+"; indexTerm :"+indexTerm+"; indexGui :"+indexGUI;
    }

    /**
     * return la config pour un tuyau pour sauvegarder dans un fichier
     */
    public String config() {
        return this.moveable ? this.indexGUI/4+""+this.rotation+"T" : this.indexGUI/4+""+this.rotation+"F";
    }

    public static void main(String[] args) {
        VueTerm vt = new VueTerm();
        PipeDepart pd = new PipeDepart();
        PipeArrivee pa = new PipeArrivee();
        PipeI pi = new PipeI(true);
        PipeL pl = new PipeL(true);
        PipeT pt = new PipeT(true);
        PipeX px = new PipeX();
        vt.affichePipe(pd);
        vt.affichePipe(pa);
        vt.affichePipe(pi);
        vt.affichePipe(pl);
        vt.affichePipe(pt);
        vt.affichePipe(px);
        pd.rotate();
        vt.affichePipe(pd);
    }

    /**
     * Indique si le tuyau est rempli
     * @return true si c'est le cas
     */
	public boolean isRempli() {
		return rempli;
	}

    /**
     * Indique la position de l'image dans le tabbleau de bufferedImage
     * @return la position de l'image associée au tuyau
     */
	public int getIndexGui() {
		return this.indexGUI;
    }
    
} 

/**
 * Représente le tuyau de départ.
 */
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

/**
 * Représente le tuyau d'arrivé.
 */
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

/**
 * Représente un tuyau de la forme L.
 */
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

/**
 * Représente un tuyau de la forme I.
 */
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

/**
 * Représente un tuyau de la forme T.
 */
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

/**
 * Représente un tuyau de la forme +.
 */
class PipeX extends Pipe {

    public PipeX() {
        this.moveable = false;
        this.connections = new boolean[]{true, true, true, true};
        this.rempli = false;
        this.fileName = "PipeX"+rempli;
        this.rotation = 0;
        this.indexTerm = 16;
        this.indexGUI = 20;
    }
}
