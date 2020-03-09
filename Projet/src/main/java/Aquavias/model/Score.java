package Aquavias.model;

import java.util.Scanner;

public class Score {

  // scoreComparaison contient les "catégorie" de score possibles : le premier elem contient
  // le minimum pour avoir une étoile, le deuxieme pour 2 étoiles, et le 3éme pour avoir les
  // 3 étoiles (score max). Si on a moins que 1 une étoile, on affiche une tête de mort.

  public static void afficher(int[] scoreComparaison, int score, int lvl) {

    if (score <= scoreComparaison[2] && score > scoreComparaison[1]) {
      System.out.println("                        ★★★");
      System.out.println("                     Excellent\n");
    } else if (score <= scoreComparaison[1] && score > scoreComparaison[0]) {
        System.out.println("                        ★★");
        System.out.println("                       Bien\n");
        }
        else if (score <= scoreComparaison[0] && score > scoreComparaison[0]+5) {
          System.out.println("                        ★");
          System.out.println("                        Ok\n");
          }
          else {
            System.out.println("                        ☠");
            System.out.println("                    Aie aie aie..\n");
          }

    if (score > scoreComparaison[0]+5) System.out.println("Recommencer (R),   Niveau suivant(S),    Retour (B)");
    else System.out.println("Recommencer (R),    Retour (B)");        // on affiche pas recommencer si le niveau n'est pas reussi

    Scanner sc = new Scanner(System.in);
    String str = sc.nextLine();

    if (str.equals("R") || str.equals("r")) recommencer(lvl);
    else if ((str.equals("S") && score > scoreComparaison[0]+5) || (str.equals("s") && score > scoreComparaison[0]+5)) suivant(lvl);
      else if (str.equals("B") || str.equals("b")) retour();
        else {
          System.out.println("Vous avez rentré une entrée incorrect, réessayez.");
          afficher(scoreComparaison, score,lvl);
          System.exit(0);
        }
  }


  private static void recommencer(int lvl) {     // recommencer le niveau
    MenuNiveau n = new MenuNiveau(25,13);
    if (lvl<1 || lvl>n.Norm) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
    else n.LanceNivN(lvl); //Lance le niveau numéro x
  }

  private static void suivant(int lvl) {         // niveau suivant
    MenuNiveau n = new MenuNiveau(25,13);
    if (lvl+1<1 || lvl+1>n.Norm) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
    else n.LanceNivN(lvl+1); //Lance le niveau numéro x
  }

  private static void retour() {          // retourner à la liste des niveaux
    return;
  }


}
