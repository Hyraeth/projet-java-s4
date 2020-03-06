package Aquavias.model;

public class Verif {
    public Pipe[][] remplir(Pipe[][] pip) {
        int n=0;
        for (int i = 0; i<pip.length; i++) {
            for (int j = 0; j<pip[0].length; j++) {
                pip[i][j].vide();
            }
            if (pip[i][0] instanceof PipeDepart) {
                n=i;
                pip[i][0].remplir();
            }
        }
        if (pip[n][0] instanceof PipeDepart) {
            //remplir(pip,n,1,3);
        }
        return pip;
    }
}