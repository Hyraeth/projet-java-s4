package Aquavias.vue.GUI;

import java.util.*;

import Aquavias.model.MenuNiveau;
import Aquavias.model.Pipe;
import Aquavias.model.Niveau;
import Aquavias.model.Color;
import Aquavias.model.Score;

public class VueTerm{
  MenuNiveau mn = new MenuNiveau(25,13);

  public VueTerm(){

  }

  public void afficheStart(){
    //Message de bienvenue
    System.out.println(Color.BLUE_BOLD_BRIGHT+"\n     \033[3m ~BIENVENUE DANS AQUAVIAS~ \033[0m \n"+Color.RESET);
  }

  //Affiche la barre de menu
  public void afficheMenu(){
    System.out.println(
    Color.WHITE_BOLD_BRIGHT+"\n              MENU         \n"+Color.RESET
    +  "      _____________________\n\n"
    +  "            JOUER(j)\n"
    +  "            CREER(c)\n"
    +  "           QUITTER(q) \n");
  }

  //Affiche la liste de tous les niveaux normaux
  public void afficheNorm(){

    System.out.println(Color.BLUE_BRIGHT
    +"\n             AQUAVIAS         \n"
    +  "     ________________________"
    +Color.RESET);
    for(int i=0; i<mn.getNorm(); i++){ //Norm le nombre de niveaux normaux

      if(i%5 == 0){ //Saut de ligne chaque 5 niveaux
        System.out.println();
        System.out.print("       ");
      }
      if(i == 0) System.out.print(Color.WHITE_BOLD+" "+(1)+" "+Color.RESET);
      //Il faudra vérifier si le niveau a déjà été complété pour afficher les niveaux avec différentes couleurs
      else System.out.print(" "+(i+1)+" ");
      if(i<10) System.out.print(" ");//pour rendre l'affichage plus beau
    }
    System.out.println("\n\nRetour(r)  Quitter(q)\n");
    //Exemple de couleurs qui seront utilisées
    System.out.println(
    Color.YELLOW_BOLD+"  Niveau déjà complété. \n"+Color.RESET
    +Color.WHITE+"  Niveau pas encore complété. \n"+Color.RESET
    +Color.WHITE_BOLD+"  Niveau à compléter. \n"+Color.RESET);

  }

  //Affiche la liste de tous les niveaux personnalisés
  public void affichePers(){

    System.out.println(
    Color.WHITE_BOLD_BRIGHT
    +"\n \033[3m ~BIENVENUE DANS LES NIVEAUX PERSONALISES~ \033[0m \n"
    +Color.RESET
    +Color.BLUE_BRIGHT
    +"\n             AQUAVIAS         \n"
    +  "     ________________________"
    +Color.RESET);
    for(int i=0; i<mn.getPers(); i++){ //pers le nombre de niveaux personnalisés

      if(i%5 == 0){ //Saut de ligne chaque 5 niveaux
        System.out.println();
        System.out.print("       ");
      }
      if(i == 0) System.out.print(Color.WHITE_BOLD+" "+(1)+" "+Color.RESET);
      //Il faudra vérifier si le niveau a déjà été complété pour afficher les niveaux avec différentes couleurs
      else System.out.print(" "+(i+1)+" ");
      if(i<10) System.out.print(" ");//pour rendre l'affichage plus beau
    }
    System.out.println("\n\nRetour(r)  Quitter(q)\n");
    //Exemple de couleurs qui seront utilisées
    System.out.println(
    Color.YELLOW_BOLD+"  Niveau déjà complété. \n"+Color.RESET
    +Color.WHITE+"  Niveau pas encore complété. \n"+Color.RESET
    +Color.WHITE_BOLD+"  Niveau à compléter. \n"+Color.RESET);
  }

  // affiche le plateau dans le terminal
  public void afficheNiv(Niveau n) {
      if (n.finis()) System.out.println("TUE AS GUANIER !!!");
      for (int i = 0; i < n.getLargeur(); i++) {
          for (int j = 0; j < n.getLongueur(i); j++) {
              if (n.getPipe(i,j) != null)
                  affichePipe(n.getPipe(i,j));
              else
                  System.out.print(" ");
          }
          System.out.println();
      }
  }

  // affiche le plateau dans le terminal avec des séparation.
  public void afficheAvecCase(Niveau n) {
      for (int i = 0; i < n.getLargeur(); i++) {
          for (int j = 0; j < n.getLongueur(i); j++) {
              if (j == 0)
                  System.out.print("|");
              if (n.getPipe(i,j) != null)
                  affichePipe(n.getPipe(i,j));
              else
                  System.out.print(" ");
              System.out.print("|");
          }
          System.out.println();
          for (int j = 0; j < 2 * n.getLongueur(i) + 1; j++) {
              System.out.print("-");
          }
          System.out.println();
      }
  }

  public void afficheNiv(){
    //Affichage de la fenêtre
      System.out.println(
       "       Sélection de niveau \n"
      +"     ________________________\n\n"
      +"        Niveau normaux (n)\n"
      +"     Niveau personnalisés (p)\n");
      System.out.println("Retour(r) Quitter(q)");

  }



  // scoreComparaison contient les "catégorie" de score possibles : le premier elem contient
  // le minimum pour avoir une étoile, le deuxieme pour 2 étoiles, et le 3éme pour avoir les
  // 3 étoiles (score max). Si on a moins que 1 une étoile, on affiche une tête de mort.
  public static void afficheScore(int[] scoreComparaison, int score, int lvl) {

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

    if (str.equals("R") || str.equals("r")) Score.recommencer(lvl);
    else if ((str.equals("S") && score > scoreComparaison[0]+5) || (str.equals("s") && score > scoreComparaison[0]+5)) Score.suivant(lvl);
      else if (str.equals("B") || str.equals("b")) Score.retour();
        else {
          System.out.println("Vous avez rentré une entrée incorrect, réessayez.");
          afficheScore(scoreComparaison, score,lvl);
          System.exit(0);
        }
  }

  public static void afficheGen(int[][] tab) {
    for (int i=0; i<tab.length; i++) {
      for (int j=0; j<tab[0].length; j++) {
        System.out.print(tab[i][j] + "    ");
      }
      System.out.println();
    }
  }


  public void affichePipe(Pipe p) {
      if(p.isRempli()){
          System.out.print(Color.BLUE_BRIGHT );
          System.out.print(p.getTerm(p.getIndexTerm()+p.getRotation()));
          System.out.print(Color.RESET);
      }else{
          System.out.print(p.getTerm(p.getIndexTerm()+p.getRotation()));
      }
  }

}
