public class Niveau{
  public int Norm; //Nombre de niveau normaux
  public int Pers; //Nombre de niveau personnalisés

  //Constructeur
  public Niveau(int n,int p){
    Norm = n;
    Pers = p;
  }

  //Permet de lancer le niveau normal numéro x
  public void LanceNivN(int x){
    System.out.println("Voir dans le fichier JSON le niveau normal numero "+ x);
    System.out.println("Disponible dans une prochaîne mise à jour...");
  }

  //Permet de lancer le niveau personnalisé numéro x
  public void LanceNivP(int x){
    System.out.println("Voir dans le fichier JSON le niveau personnalisé numero "+ x);
    System.out.println("Disponible dans une prochaîne mise à jour...");
  }

  //Affiche la liste de tous les niveaux normaux
  public void afficheNorm(){

    System.out.println(Color.BLUE_BRIGHT
    +"\n             AQUAVIAS         \n"
    +  "     ________________________"
    +Color.RESET);
    for(int i=0; i<Norm; i++){ //Norm le nombre de niveaux normaux

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
    for(int i=0; i<Pers; i++){ //pers le nombre de niveaux personnalisés

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
