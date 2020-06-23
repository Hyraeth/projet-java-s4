package Aquavias.model;

import java.util.ArrayList;

import Aquavias.controller.ControllerIG;
import Aquavias.vue.GUI.VueIG;
import Aquavias.vue.GUI.VueTerm;

/**
 * Permet de créer un niveau aléatoirement
 */
public class Generation {
    /**
     * Représente les connexions du tuyau
     */
    private int[][][] tab;
    public int[][][] getTab(){return tab;}

    /**
     * Initialise une Generation
     */
    public Generation(int[][][] a) {
        tab = a;
    }

    /**
     * Crée une Generation aléatoirement
     * @param hau dimention en hauteur du niveau souhaité
     * @param lon dimention en longueur du niveau souhaité
     * @param facilité difficultée du niveau (plus la valeur est basse, plus le niveau est difficile)
     */
    public static Generation generer(int hau, int lon, int facilité) {
        /**
         * hauteur du niveau de depart
         */
        int a = (int)(Math.random() * hau);
        /**
         * hauteur du niveau d'arrivé
         */
        int b = (int)(Math.random() * hau);
        int[][][] t = new int[hau][lon][3];
        Generation g = new Generation(t);
        /**
         * initialise les valeur du tableau a 9
         */
        for (int i = 0; i<hau; i++) {
            for (int j = 0; j<lon; j++) {
                g.tab[i][j][0] = 9;
                g.tab[i][j][1] = 9;
                g.tab[i][j][2] = 9;
            }
        }
        g.tab[a][0][0] = 5;   // 5 = tuyau depart
        g.tab[b][lon-1][0] = 6;  // 6 = tuyau arrivé
        boolean c = g.cree(a,1,facilité);
        while(!c) {
            for (int i = 0; i<hau; i++) {
                for (int j = 0; j<lon; j++) {
                    g.tab[i][j][0] = 9;
                    g.tab[i][j][1] = 9;
                    g.tab[i][j][2] = 9;
                }
            }
            c = g.cree(a,1,facilité);
        }

        return g;
    }

    /**
     * Crée un chemin de connection jusqu'a l'arriver
     * @param x position actuel
     * @param y position actuel
     * @param facilité facilité du niveau
     * @return true si il a été rempli correctement, false sinon
     */
    public boolean cree(int x, int y, int facilité){
        /**
         * verifie si on est arrivé
         */
        if (this.finis(x,y)) {
            this.tab[x][y][0]=1;
            return true;
        }
        int n = (int)(Math.random() * 4);
        int tuyau3 = (int)(Math.random() * facilité);
        boolean[] possible = this.disponible(x,y);
        while (!tableauDeFalse(possible)){
            while (possible[n]==false) {
                n = (int)(Math.random() * 4);
            }
            possible[n]=false;
            this.tab[x][y][0]=n;
            boolean b = false;
            switch(n) {
                case 0: b=this.cree(x-1,y,facilité);break;   //haut
                case 1: b=this.cree(x,y+1,facilité);break;
                case 2: b=this.cree(x+1,y,facilité);break;
                case 3: b=this.cree(x,y-1,facilité);break;   //gauche
            }
            /**
             * si le chemin jusqu'a l'arriver est créé, possibilité de créé un autre chemin pour pouvoir poser des Pipe T
             */
            if (b) {
                possible = this.disponible(x,y);
                if (tuyau3 == 0 && this.tab[x][y][1]==9 && facilité < 100) {
                    int aleat = (int)(Math.random() * 4);
                    for (int i = 0; i<4; i++) {
                        n = (aleat + i)%4;
                        if (possible[n]) {
                            possible[n] = false;
                            if (this.tab[x][y][1] == 9) this.tab[x][y][1] = n;
                            else this.tab[x][y][2] = n;

                            boolean c = false;
                            switch(n) {
                                case 0: c=this.creeT(x-1,y,2,facilité);break;   //haut
                                case 1: c=this.creeT(x,y+1,3,facilité);break;   //droite
                                case 2: c=this.creeT(x+1,y,0,facilité);break;   //bas
                                case 3: c=this.creeT(x,y-1,1,facilité);break;   //gauche
                            }
                            if (!c) {
                                if (this.tab[x][y][2]!=9 && this.tab[x][y][2]==n) this.tab[x][y][2]=9;
                                else this.tab[x][y][1]=9;    //si ca n'a pas marcher et si c'est un T
                            }
                        }
                    }
                }
                return true;
            } 
        }
        this.tab[x][y][0]=9;
        this.tab[x][y][1]=9;
        this.tab[x][y][2]=9;
        return false;
    }

    /**
     * Crée un chemin de connection qui essaye de rejoindre un chemin (fonctionne un peu pres pareil que la fonction cree)
     * @param x position actuel
     * @param y position actuel
     * @param prec position precedente (0=haut, 1=droite, 2=bas, 3=gauche)
     * @param facilité facilité du niveau
     * @return true si il a été rempli correctement, false sinon
     */
    public boolean creeT(int x, int y, int prec, int facilité) {
        if (this.tab[x][y][0]!=9) {
            if (tab[x][y][1]==9 || tab[x][y][1]==prec) this.tab[x][y][1]=prec;
            else this.tab[x][y][2]=prec;
            return true;
        }
        int n = (int)(Math.random() * 4);
        int tuyau3 = (int)(Math.random() * facilité+4);
        boolean[] possible = this.disponibleT(x,y,prec);
        while (!tableauDeFalse(possible)){
            n = (int)(Math.random() * 4);
            while (possible[n]==false) {
                n = (n+1)%4;
            }
            this.tab[x][y][0]=n;
            boolean b = false;
            switch(n) {
                case 0: b=this.creeT(x-1,y,2,facilité);break;   //haut
                case 1: b=this.creeT(x,y+1,3,facilité);break;
                case 2: b=this.creeT(x+1,y,0,facilité);break;
                case 3: b=this.creeT(x,y-1,1,facilité);break;   //gauche
            }
            possible[n]=false;
            if (b && facilité < 100) {
                possible = this.disponible(x,y);
                if (tuyau3 == 0 && this.tab[x][y][1]==9) {
                    int aleat = (int)(Math.random() * 4);
                    for (int i = 0; i<4; i++) {
                        n = (aleat + i)%4;
                        if (possible[n]) {
                            possible[n] = false;
                            tab[x][y][1] = n;

                            boolean c = false;
                            switch(n) {
                                case 0: c=this.creeT(x-1,y,2,facilité);break;   //haut
                                case 1: c=this.creeT(x,y+1,3,facilité);break;   //droite
                                case 2: c=this.creeT(x+1,y,0,facilité);break;   //bas
                                case 3: c=this.creeT(x,y-1,1,facilité);break;   //gauche
                            }
                            if (!c) {
                                tab[x][y][1]=9;
                                tab[x][y][2]=9;    //si ca n'a pas marcher et si c'est un X
                            }
                        }
                    }
                }
                return true;
            }
        }
        this.tab[x][y][0]=9;
        this.tab[x][y][1]=9;
        this.tab[x][y][2]=9;
        return false;
    }


    /**
     * Cherche a trouver les dirrection possible depuis une case pour la fonction creeT
     * @param i position actuel
     * @param j position actuel
     * @param prec direction du precedent
     * @return un tableau de boolean, t[x]=true si la dirrection x est possible
     */
    public boolean[] disponibleT(int i, int j, int prec) {
        boolean[] b = new boolean[4];
        if (i-1>=0 && prec != 0 && this.tab[i-1][j][0] != 5 && this.tab[i-1][j][0] != 6) b[0] = true;            //verifie si la case est dans le plateau
        if (j+1<this.tab[0].length && prec != 1 && this.tab[i][j+1][0] != 5 && this.tab[i][j+1][0] != 6) b[1] = true;
        if (i+1<this.tab.length && prec != 2 && this.tab[i+1][j][0] != 5 && this.tab[i+1][j][0] != 6) b[2] = true;
        if (j-1>=0 && prec != 3 && this.tab[i][j-1][0] != 5 && this.tab[i][j-1][0] != 6) b[3] = true;
        return b;
    } 

    /**
     * Cherche a trouver les dirrection possible depuis une case pour la fonction cree
     * @param i position actuel
     * @param j position actuel
     * @return un tableau de boolean, t[x]=true si la dirrection x est possible
     */
    public boolean[] disponible(int i, int j) {
        boolean[] b = new boolean[4];
        if (i-1>=0 && this.tab[i-1][j][0] == 9) b[0] = true;            //verifie si la case est dans le plateau
        if (j+1<this.tab[0].length && this.tab[i][j+1][0] == 9) b[1] = true;   //et si la case n'a pas deja été visitée.
        if (i+1<this.tab.length && this.tab[i+1][j][0] == 9) b[2] = true;
        if (j-1>=0 && this.tab[i][j-1][0] == 9) b[3] = true;
        return b;
    }

    /**
     * Permet de savoir si le tableau en parametre est un tableau constitué uniquement de false
     */
    public boolean tableauDeFalse (boolean[] b) {
        for (int i=0; i<b.length; i++) {
            if (b[i]==true) return false;
        }
        return true;
    }

    /**
     * Permet de savoir si nous avons atteint l'arriver
     */
    public boolean finis(int x, int y) {
        if (y+1<this.tab[0].length) return (this.tab[x][y+1][0]==6);
        else return false;
    }

    public static void main(String[] args) {
        VueTerm vt = new VueTerm();
        Niveau n = init(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]));
        vt.afficheNiv(n, true);
        ControllerIG c = new ControllerIG();
        c.setNiveau(n);
        VueIG v = new VueIG(c, n);
        c.setVue(v);
    }

    /**
     * Initialise un niveau aléatoire
     * @param i hauteur du niveau
     * @param j largeur du niveau
     * @param facilité facilité du niveau
     * @return un Niveau créé aleatoirement
     */
    public static Niveau init(int i, int j, int facilité) {
        Generation g1 = generer(i,j,facilité);
        Niveau n1 = new Niveau(i,j);

        Pipe[][] tp = const1(g1.tab);
        while(tp == null){
            g1 = generer(i,j,facilité);
            tp = const1(g1.tab);
        }
        n1.setNiveau(tp);
        n1.setType((int)(Math.random() * 2)+1);
        n1.setResources(g1.calcResource());
        return n1;

    }

    /**
     * Donne le nombre de ressource nécessaire pour résoudre un niveau
     */
    public int calcResource() {
        int n = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if(tab[i][j][0] != 9) n++;
            }
        }
        return (n-2)*4;
    }

    /**
     * permet afficher le tableau associé à un niveau dans le terminal
     */
    public void affiche() {
        for (int i=0; i<this.tab.length; i++) {
            for (int j=0; j<this.tab[0].length; j++) {
                System.out.print(this.tab[i][j][0]);
                System.out.print(this.tab[i][j][1]);
                System.out.print(this.tab[i][j][2] + " ");
            }
            System.out.println();
        }
    }



    /**
     * Crée un tableau de pipe a partir du tableau d'entier créé aléatoirement
     * @param t tableau d'entier créé aléatoirement grace au fonction precedente
     * @return un tableau de pipe qui correspond à notre niveau généré
     */
    public static Pipe[][] const1(int[][][] t) {
        Pipe[][] pip = new Pipe[t.length][t[0].length];
        int i=0;
        while(t[i][0][0] != 5) {  //tant que ce n'est pas le depart
            i++;
        }
        pip[i][0] = new PipeDepart();
        try {
            const2(pip,t,i,1,3,new boolean[t.length][t[0].length]);
        } catch (StackOverflowError e) {
            return null;
        }
        
        for (int k=0; k<t.length; k++) {
            for (int j=0; j<t[0].length; j++) {
                if (t[k][j][2] != 9) {
                    pip[k][j] = new PipeX();
                }
                else if (t[k][j][1] != 9) {
                    pip[k][j] = new PipeT(true);
                    if(t[k][j][1] == t[k][j][0]) {
                        return null;
                    }
                }
                else if (pip[k][j] == null) {
                    pip[k][j] = pipeAlea();
                }
            }
        }
        return pip;
    }

    /**
     * Permet de gérer les chemin qui parte des pipe T et des pipe X
     * @param pip notre tableau de Pipe que nous allons remplir
     * @param t tableau d'entier correspondant au connection d'une position
     * @param x position actuel
     * @param y position actuel
     * @param prec position du precedent
     * @param vis permet de savoir si la position a déjà été visitée
     */
    public static void const2(Pipe[][] pip, int[][][] t, int x, int y, int prec, boolean[][] vis) {
        if (t[x][y][0]==6) {
            pip[x][y] = new PipeArrivee();
            return;
        }
        vis[x][y] = true;
        int suiv = t[x][y][0];

        if((suiv+prec)%2 == 0) {  //pour savoir si on met un L ou un I.
            pip[x][y]=new PipeI(true);
        } else {
            pip[x][y]=new PipeL(true);
        }
        switch (suiv) {
            case 0 : const2(pip,t,x-1,y,(suiv+2)%4,vis);break;
            case 1 : const2(pip,t,x,y+1,(suiv+2)%4,vis);break;
            case 2 : const2(pip,t,x+1,y,(suiv+2)%4,vis);break;
            case 3 : const2(pip,t,x,y-1,(suiv+2)%4,vis);break;
        }
        boolean directSuivOk = false;
        if (t[x][y][1] != 9) {
            switch (t[x][y][1]) {
                case 0 : directSuivOk = ( t[x-1][y][0]!=2 && t[x-1][y][1]!=2 && t[x-1][y][2]!=2 && vis[x-1][y]==false );break;
                case 1 : directSuivOk = ( t[x][y+1][0]!=3 && t[x][y+1][1]!=3 && t[x][y+1][2]!=3 && vis[x][y+1]==false );break;
                case 2 : directSuivOk = ( t[x+1][y][0]!=0 && t[x+1][y][1]!=0 && t[x+1][y][2]!=0 && vis[x+1][y]==false );break;
                case 3 : directSuivOk = ( t[x][y-1][0]!=1 && t[x][y-1][1]!=1 && t[x][y-1][2]!=1 && vis[x][y-1]==false );break;
            }
            if (directSuivOk){
                switch (t[x][y][1]) {
                    case 0 : const2(pip,t,x-1,y,(t[x][y][1]+2)%4,vis);break;
                    case 1 : const2(pip,t,x,y+1,(t[x][y][1]+2)%4,vis);break;
                    case 2 : const2(pip,t,x+1,y,(t[x][y][1]+2)%4,vis);break;
                    case 3 : const2(pip,t,x,y-1,(t[x][y][1]+2)%4,vis);break;
                }
            }
        }
        if (t[x][y][2] != 9) {
            switch (t[x][y][2]) {
                case 0 : directSuivOk = ( t[x-1][y][0]!=2 && t[x-1][y][1]!=2 && t[x-1][y][2]!=2 && vis[x-1][y]==false );break;
                case 1 : directSuivOk = ( t[x][y+1][0]!=3 && t[x][y+1][1]!=3 && t[x][y+1][2]!=3 && vis[x][y+1]==false );break;
                case 2 : directSuivOk = ( t[x+1][y][0]!=0 && t[x+1][y][1]!=0 && t[x+1][y][2]!=0 && vis[x+1][y]==false );break;
                case 3 : directSuivOk = ( t[x][y-1][0]!=1 && t[x][y-1][1]!=1 && t[x][y-1][2]!=1 && vis[x][y-1]==false );break;
            }
            if (directSuivOk){
                switch (t[x][y][2]) {
                    case 0 : const2(pip,t,x-1,y,(t[x][y][2]+2)%4,vis);break;
                    case 1 : const2(pip,t,x,y+1,(t[x][y][2]+2)%4,vis);break;
                    case 2 : const2(pip,t,x+1,y,(t[x][y][2]+2)%4,vis);break;
                    case 3 : const2(pip,t,x,y-1,(t[x][y][2]+2)%4,vis);break;
                }
            }
        }
        
    }

    /**
     * Créé un pipe aléatoire qui va nous permetre de completer notre tableau de pipe 
     * @return un Pipe aleatoire
     */
    public static Pipe pipeAlea() {
        int n = (int)(Math.random() * 6);
        switch(n) {
            case 0:
                return new PipeL(true);
            case 1:
                return new PipeL(true);
            case 2:
                return new PipeI(true);
            case 3:
                return new PipeI(true);
            case 4:
                return new PipeT(true);
            default:
                return new PipeX();
        }
    }
}
