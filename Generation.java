import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Generation {


  // FONCTIONS POUR L'ALGORITHME

  private String[][] algorithme(int[] taille) {

    int largeur = taille[0];
    int longueur = taille[1];
    Tuyau[][] res = new Tuyau[taille[0]][taile[1];

    res[0][0] = new Tuyau(0,0,D) ; //tuyau de depart
    res[largeur][longueur] = new Tuyau(0,0,A); //tuyau d'arrivé

    int[] point = new int[2];
    point[0] = 0;
    point[1] = 1;  // on est en [0,0] parce que le tuyau de depart va tout droit et nous fait descendre d'une case



    // on définit le circuit avec la longueur et la largeur, puis le point de départ et d'arrivé.
    // tant que le point d'arrivé n'est pas atteint, on continue.
    // on est sur un point, on regarde le nombre de points disponibles autour de ce point.
    // on choisit un point au hasard parmis ces points, puis on continue comme ça jusqu'à ce qu'on arrive
    // sur le point d'arrivé.
    // Il faut faire attention de pas tomber sur un cul-de-sac

    while(point.disponible(res)) {

      int[][] casesDispo = point.casesDisponibles(res);

      Random rand = new Random();
      int nombreAleatoire = rand.nextInt(casesDispo.length + 1);   // choisi une cases aleatoire parmis celles disponibles

      // si le tuyau suivant va dans le meme sens : on ajouter un tuyau droit
      // sinon on ajoute un tuyau qui tourne


    }

    // une fois que tous les tuyaux qui menent au tuyau final sont ajoutés, il faut ajouté des tuyaux de maniere nombreAleatoire
    // sur les cases libres, puis tourner aleatoirement tous les tuyaux du circuit
  }



  private boolean disponible(int[][] circuit) {     // on renvoi true si il y a une case disponible autour de la case this
    if (circuit[this[0]+1][this[1]] == null || circuit[this[0]-1][this[1]] == null
      || circuit[this[0]][this[1]+1] == null || circuit[this[0]][this[1]-1] == null) return true;
    else return false;
  }

  private int[][] casesDisponibles(int[][] circuit) {   // renvoi les cases disponibles autour de la case this
    int[][] res = new int[4][2];
    if (circuit[this[0]+1][this[1]] == null) res[0] = circuit[this[0]+1][this[1]];
    if (circuit[this[0]-1][this[1]] == null) res[1] = circuit[this[0]-1][this[1]];
    if (circuit[this[0]][this[1]+1] == null) res[2] = circuit[this[0]][this[1]+1];
    if (circuit[this[0]][this[1]-1] == null) res[3] = circuit[this[0]][this[1]-1];

    // dans la suite, on créer un nouveau tableau en enlevant les cases inutiles
    int k = 0;
    for (int i=0; i<4; i++) {
      if (res[i][0] != 0 || res[i][1] != 0) k++;
    }

    int[][] res2 = new int[k][2];
    for (int i=0; i<k; i++) {
      for (int j=0; j<4; j++) {
        if (res[j][0] != 0 || res[j][1] != 0) {
          res2[i] = res[j];
          break;
      }
    }

    return res2;
  }




  // FONCTIONS POUR LE SCORE

  private int[] score(int[][] modele) {
    int[]res = new int[3];
    res[0] = nbCoupsMini(modele);
    res[0] = nbCoupsMini(modele) + 5;
    res[0] = nbCoupsMini(modele) + 10;
    return res;
  }


  private int nbCoupsMini(int[][] modele) {   // calcul le nombre de coup mini qu'il faut pour finir le niveau

  }




  // CREATION DU FICHIER JSON

  private JSONObject creation(int[] taille, int coups) {
    JSONObject obj = new JSONObject();
    obj.put("taille", taille);
    obj.put("configuration", algorithme(taille));
    obj.put("score", score(taille));
    obj.put("coups", coups);

    try(FileWriter file = new FileWriter("myJSON.json"))
    {
      file.write(obj.toString());
      file.flush();
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }

    System.out.println(obj);
  }






  public static void main(String[] args) {

  }


}