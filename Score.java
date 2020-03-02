import java.util.Scanner;

public class Score {

  // scoreComparaison contient les "catégorie" de score possibles : le premier elem contient
  // le minimum pour avoir une étoile, le deuxieme pour 2 étoiles, et le 3éme pour avoir les
  // 3 étoiles (score max). Si on a moins que 1 une étoile, on affiche une tête de mort.

  public void afficher(int[] scoreComparaison, int score) {

    if (score <= scoreComparaison[2] && score > scoreComparaison[1]) {
      System.out.println("                        ★★★");
      System.out.println("                     Excellent");
    } else if (score <= scoreComparaison[1] && score > scoreComparaison[0]) {
        System.out.println("                        ★★");
        System.out.println("                       Bien");
        }
        else if (score <= scoreComparaison[0] && score > scoreComparaison[0]+5) {
          System.out.println("                        ★");
          System.out.println("                        Ok");
          }
          else {
            System.out.println("                        ☠");
            System.out.println("                    Aie aie aie..");
          }

    System.out.println("Recommencer (R),   Niveau suivant(S),    Retour (B)");
    Scanner sc = new Scanner(System.in);
    String str = sc.nextLine();

    if (str.equals("R") || str.equals("r")) recommencer();
    else if (str.equals("S") || str.equals("s")) suivant();
      else if (str.equals("B") || str.equals("b")) retour();
        else {
          System.out.println("Vous avez rentré une entrée incorrect, réessayez.");
          afficher(scoreComparaison, score);
          System.exit(0);
        }
  }

 // Pour les fonctions qui suivent, il faut le reste de la partie terminale pour les coder

  private void recommencer() {     // recommencer le niveau
    System.out.println("\n\n On recommence \n\n");
  }

  private void suivant() {         // niveau suivant
    System.out.println("\n\n On fait le suivant \n\n");
  }

  private void retour() {          // retourner à la liste des niveaux
    System.out.println("\n\n On retourne en arriere \n\n");
  }


}
