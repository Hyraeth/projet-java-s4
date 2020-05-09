package Aquavias.model;

import java.util.ArrayList;

import Aquavias.controller.ControllerIG;
import Aquavias.vue.GUI.VueIG;
import Aquavias.vue.GUI.VueTerm;

public class Generation {
    private int[][][] tab;
    public int[][][] getTab(){return tab;}

    public Generation(int[][][] a) {
        tab = a;
    }

    public static Generation generer(int hau, int lon, int facilité) {
        int a = (int)(Math.random() * hau);
        int b = (int)(Math.random() * hau);
        int[][][] t = new int[hau][lon][3];
        Generation g = new Generation(t);
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

    public boolean cree(int x, int y, int facilité){
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
            this.tab[x][y][0]=n;
            boolean b = false;
            switch(n) {
                case 0: b=this.cree(x-1,y,facilité);break;   //haut
                case 1: b=this.cree(x,y+1,facilité);break;
                case 2: b=this.cree(x+1,y,facilité);break;
                case 3: b=this.cree(x,y-1,facilité);break;   //gauche
            }
            System.out.println("dgdsfg");
            possible[n]=false;
            if (b) {
                /*if (tuyau3 == 0 && this.tab[x][y][1]==9) {
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
                                if (this.tab[x][y][2]!=9) this.tab[x][y][2]=9;
                                else this.tab[x][y][1]=9;    //si ca n'a pas marcher et si c'est un T
                            }
                            else return true;
                        }
                    }
                }*/
                return true;
            } 
        }
        this.tab[x][y][0]=9;
        this.tab[x][y][1]=9;
        this.tab[x][y][2]=9;
        return false;
    }

    public boolean creeT(int x, int y, int prec, int facilité) {
        if (this.tab[x][y][0]!=9) {
            if (tab[x][y][1]==9) this.tab[x][y][1]=prec;
            else this.tab[x][y][2]=prec;
            return true;
        }
        int n = (int)(Math.random() * 4);
        int tuyau3 = (int)(Math.random() * facilité+4);
        boolean[] possible = this.disponibleT(x,y,prec);
        System.out.println("dfhgf");
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
            if (b) {
                /*if (tuyau3 == 0 && this.tab[x][y][1]==9) {
                    int aleat = (int)(Math.random() * 4);
                    for (int i = 0; i<4; i++) {
                        n = (aleat + i)%4;
                        if (possible[n]) {
                            possible[n] = false;
                            tab[x][y][1] = n;

                            boolean c = false;
                            switch(n) {
                                case 0: c=this.creeT(x-1,y,2);break;   //haut
                                case 1: c=this.creeT(x,y+1,3);break;   //droite
                                case 2: c=this.creeT(x+1,y,0);break;   //bas
                                case 3: c=this.creeT(x,y-1,1);break;   //gauche
                            }
                            if (!c) {
                                tab[x][y][1]=9;
                                tab[x][y][2]=9;    //si ca n'a pas marcher et si c'est un X
                            }
                            else return true;
                        }
                    }
                }*/
                return true;
            }
        }
        if (this.tab[x][y][2]!=9) this.tab[x][y][2]=9;
        else this.tab[x][y][1]=9;
        return false;
    }



    public boolean[] disponibleT(int i, int j, int prec) {
        boolean[] b = new boolean[4];
        if (i-1>=0 && prec != 0) b[0] = true;            //verifie si la case est dans le plateau
        if (j+1<this.tab[0].length && prec != 1) b[1] = true;
        if (i+1<this.tab.length && prec != 2) b[2] = true;
        if (j-1>=0 && prec != 3) b[3] = true;
        return b;
    } 


    public boolean[] disponible(int i, int j) {
        boolean[] b = new boolean[4];
        if (i-1>=0 && this.tab[i-1][j][0] == 9) b[0] = true;            //verifie si la case est dans le plateau
        else b[0] = false;
        if (j+1<this.tab[0].length && this.tab[i][j+1][0] == 9) b[1] = true;   //et si la case n'a pas deja été visitée.
        else b[1] = false;
        if (i+1<this.tab.length && this.tab[i+1][j][0] == 9) b[2] = true;
        else b[2] = false;
        if (j-1>=0 && this.tab[i][j-1][0] == 9) b[3] = true;
        else b[3] = false;
        return b;
    }

    public boolean tableauDeFalse (boolean[] b) {
        for (int i=0; i<b.length; i++) {
            if (b[i]==true) return false;
        }
        return true;
    }

    public boolean finis(int x, int y) {
        if (y+1<this.tab[0].length) return (this.tab[x][y+1][0]==6);
        else return false;
    }

    public static void main(String[] args) {
        VueTerm vt = new VueTerm();
        Niveau n = init(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]));
        vt.afficheNiv(n, true);
        ControllerIG c = new ControllerIG(n);
        VueIG v = new VueIG(c, n);
        c.setVue(v);
    }

    public static Niveau init(int i, int j, int facilité) {
        Generation g1 = generer(i,j,facilité);
        g1.affiche();
        Niveau n1 = new Niveau(i,j);
        n1.setNiveau(const1(g1.tab));
        n1.setResources(1);
        return n1;

    }

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










    public static Pipe[][] const1(int[][][] t) {
        Pipe[][] pip = new Pipe[t.length][t[0].length];
        int i=0;
        while(t[i][0][0] != 5) {  //tant que ce n'est pas le depart
            i++;
        }
        pip[i][0] = new PipeDepart();
        const2(pip,t,i,1,3,new boolean[t.length][t[0].length]);

        for (int k=0; k<t.length; k++) {
            for (int j=0; j<t[0].length; j++) {
                if (pip[k][j] == null) {
                    pip[k][j] = pipeAlea();
                }
            }
        }
        return pip;
    }
    public static void const2(Pipe[][] pip, int[][][] t, int x, int y, int prec, boolean[][] vis) {
        //System.out.println(vis[x][y]);
        if (t[x][y][0]==6) {
            pip[x][y] = new PipeArrivee();
            return;
        }
        if (vis[x][y] || prec==t[x][y][0] || prec==t[x][y][1] || prec==t[x][y][2]) {
            return;
        } else {
            vis[x][y]=true;
        }
        int suiv;

        if (t[x][y][1] == 9) {   
            if((t[x][y][0]+prec)%2 == 0) {  //pour savoir si on met un L ou un I.
                pip[x][y]=new PipeI(true);
                suiv = (prec+2)%4;
            } else {
                pip[x][y]=new PipeL(true);
                if (t[x][y][0] == (prec+1)%4){
                    suiv = (prec+1)%4;
                    prec = (prec+3)%4;
                } else {
                    suiv = (prec+3)%4;
                    prec = (prec+1)%4;
                }
            }
            switch (prec) {
                case 0 : const2(pip,t,x+1,y,prec,vis);break;
                case 1 : const2(pip,t,x,y-1,prec,vis);break;
                case 2 : const2(pip,t,x-1,y,prec,vis);break;
                case 3 : const2(pip,t,x,y+1,prec,vis);break;
            }
        }

        //cas tuyau en T ou tuyau en X
        else {
            if (t[x][y][2]!=9) {
                pip[x][y]=new PipeX();
                switch (t[x][y][0]) {
                    case 0 : const2(pip,t,x-1,y,2,vis);break;
                    case 1 : const2(pip,t,x,y+1,3,vis);break;
                    case 2 : const2(pip,t,x+1,y,0,vis);break;
                    case 3 : const2(pip,t,x,y-1,1,vis);break;
                }
                switch (t[x][y][1]) {
                    case 0 : const2(pip,t,x-1,y,2,vis);break;
                    case 1 : const2(pip,t,x,y+1,3,vis);break;
                    case 2 : const2(pip,t,x+1,y,0,vis);break;
                    case 3 : const2(pip,t,x,y-1,1,vis);break;
                }
                switch (t[x][y][2]) {
                    case 0 : const2(pip,t,x-1,y,2,vis);break;
                    case 1 : const2(pip,t,x,y+1,3,vis);break;
                    case 2 : const2(pip,t,x+1,y,0,vis);break;
                    case 3 : const2(pip,t,x,y-1,1,vis);break;
                }
            } else {
                pip[x][y]=new PipeT(true);
                switch (t[x][y][0]) {
                    case 0 : const2(pip,t,x-1,y,2,vis);break;
                    case 1 : const2(pip,t,x,y+1,3,vis);break;
                    case 2 : const2(pip,t,x+1,y,0,vis);break;
                    case 3 : const2(pip,t,x,y-1,1,vis);break;
                }
                switch (t[x][y][1]) {
                    case 0 : const2(pip,t,x-1,y,2,vis);break;
                    case 1 : const2(pip,t,x,y+1,3,vis);break;
                    case 2 : const2(pip,t,x+1,y,0,vis);break;
                    case 3 : const2(pip,t,x,y-1,1,vis);break;
                }
            }
        }
        
    }

    //return un tuyau aleatoire:
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








    /*public class position{
        public ArrayList<Integer> Suivant = new ArrayList<>();

        public position () {}
    }*/

}
