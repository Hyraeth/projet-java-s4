package Aquavias.model;

public class Tuyau {
    private boolean[] connections = new boolean[4];
    private boolean rempli;
    private char[] afficheTerm;
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
            System.out.println("Build failed");
                break;
        }
    }

    public void rotate() {
        for (int i = 0; i < connections.length; i++) {
            //On décale le tableau vers la droite
            connections[i] = connections[(i-1)%4];
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
        return false;
    }
}