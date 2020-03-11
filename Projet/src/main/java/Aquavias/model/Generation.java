package Aquavias.model;

import Aquavias.vue.GUI.VueTerm;

public class Generation {
  private static int[][] tab;
  public int[][] getTab(){return tab;}

  public Generation(int[][] a) {
    tab = a;
  }

  public static Generation generer(int hau, int lon) {
    int a = (int)(Math.random() * hau);
    int b = (int)(Math.random() * hau);
    int[][] t = new int[hau][lon];
    Generation g = new Generation(t);
    for (int i = 0; i<hau; i++) {
      for (int j = 0; j<lon; j++) {
        g.tab[i][j] = 9;
      }
    }
    g.tab[a][0] = 5;   // 5 = tuyau depart
    g.tab[b][lon-1] = 6;  // 6 = tuyau arrivé
    g.cree(a,1);

    return g;
  }

  public boolean cree(int x, int y){
    if (this.finis(x,y)) {
      this.tab[x][y]=1;
      return true;
    }
    int n = (int)(Math.random() * 4);
    boolean[] possible = this.disponible(x,y);
    while (!tableauDeFalse(possible)){
      while (possible[n]==false && !tableauDeFalse(possible)) {
        n = (int)(Math.random() * 4);
      }
      this.tab[x][y]=n;
      boolean b = false;
      switch(n) {
        case 0: b=this.cree(x-1,y);break;   //haut
        case 1: b=this.cree(x,y+1);break;
        case 2: b=this.cree(x+1,y);break;
        case 3: b=this.cree(x,y-1);break;   //gauche
      }
      possible[n]=false;
      if (b) return true;
    }
    this.tab[x][y]=9;
    return false;
  }



  public boolean[] disponible(int i, int j) {
    boolean[] b = new boolean[4];
    if (i-1>=0 && this.tab[i-1][j] == 9) b[0] = true;            //verifie si la case est dans le plateau
    if (j+1<this.tab[0].length && this.tab[i][j+1] == 9) b[1] = true;   //et si la case n'a pas deja été visitée.
    if (i+1<this.tab.length && this.tab[i+1][j] == 9) b[2] = true;
    if (j-1>=0 && this.tab[i][j-1] == 9) b[3] = true;
    return b;
  }

  public boolean tableauDeFalse (boolean[] b) {
    for (int i=0; i<b.length; i++) {
      if (b[i]==true) return false;
    }
    return true;
  }

  public boolean finis(int x, int y) {
    if (y+1<this.tab[0].length) return (this.tab[x][y+1]==6);
    else return false;
  }

  public static void main(String[] args) {
    VueTerm vt = new VueTerm();
    Niveau n = init(9,9);
    vt.afficheNiv(n);
  }

  public static Niveau init(int i, int j) {
    Generation g1 = generer(i,j);
    //affiche(g1.tab);
    Niveau n1 = new Niveau(i,j);
    n1.setNiveau(const1(g1.tab));
    return n1;

  }

  public static Pipe[][] const1(int[][] t) {
    Pipe[][] pip = new Pipe[t.length][t[0].length];
    int i=0;
    while(t[i][0] != 5) {  //tant que ce n'est pas le depart
      i++;
    }
    pip[i][0] = new PipeDepart();
    const2(pip,t,i,1,3);

    for (int k=0; k<t.length; k++) {
      for (int j=0; j<t[0].length; j++) {
        if (pip[k][j] == null) {
          pip[k][j] = pipeAlea();
        }
      }
    }
    return pip;
  }
  public static void const2(Pipe[][] pip, int[][] t, int x, int y, int prec) {
    if (t[x][y]==6) {
      pip[x][y] = new PipeArrivee();
      return;
    }
    if((t[x][y]+prec)%2 == 0) {  //pour savoir si on met un L ou un I.
      pip[x][y]=new PipeI(true);
    } else {
      pip[x][y]=new PipeL(true);
      if (tab[x][y] == (prec+1)%4){
        prec = (prec+3)%4;
      } else {
        prec = (prec+1)%4;
      }
    }
    switch (prec) {
      case 0 : const2(pip,t,x+1,y,prec);break;
      case 1 : const2(pip,t,x,y-1,prec);break;
      case 2 : const2(pip,t,x-1,y,prec);break;
      case 3 : const2(pip,t,x,y+1,prec);break;
    }
  }

  //return un tuyau aleatoire:
  public static Pipe pipeAlea() {
    int n = (int)(Math.random() * 6);
    switch(n) {
      case 0:
        return new PipeL(true);
      case 1:
        return new PipeI(true);
      case 2:
        return new PipeT(true);
      case 3:
        return null;
      case 4:
        return null;
      default:
        return new PipeX();
    }
  }

}
