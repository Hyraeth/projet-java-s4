public class Niveau{
  public int nb;
  //Constructeur
  public Niveau(int n){
    nb = n;
  }

  public void LanceNiv(int x){
    System.out.println("Voir dans le fichier JSON le niveau numero "+ x);
    System.out.println("Disponible dans une prochaîne mise à jour...");
  }

  //Affiche la liste de tous les niveaux
  public void affiche(){ //n le nombre de niveau dans le jeu

    System.out.println(Color.BLUE_BRIGHT
    +"\n             AQUAVIAS         \n"
    +  "     ________________________"
    +Color.RESET);
    for(int i=0; i<nb; i++){

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
}
