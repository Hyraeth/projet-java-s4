public class Niveau{

  //Constructeur
  public Niveau(){

  }



  //Affiche la liste de tous les niveaux
  public void afficherNiveau(int n){ //n le nombre de niveau dans le jeu

    System.out.println(Color.BLUE_BRIGHT
    +"\n             AQUAVIAS         \n"
    +  "     ________________________"
    +Color.RESET);

    for(int i=0; i<n; i++){

      if(i%5 == 0){ //Saut de ligne chaque 5 niveaux
        System.out.println();
        System.out.print("       ");
      }
      //Il faudra vérifier si le niveau a déjà été complété pour afficher les niveaux avec différentes couleurs
      System.out.print(" "+(i+1)+" ");
      if(i<10) System.out.print(" ");
    }
    System.out.println("\n");
    //Exemple de couleurs qui seront utilisées
    System.out.println(
    Color.WHITE_BOLD+"  Niveau déjà complété \n"+Color.RESET
    +Color.WHITE+"  Niveau pas encore complété \n"+Color.RESET
    +Color.YELLOW_BOLD+"  Niveau complété parfaitement \n"+Color.RESET);
  }
}
