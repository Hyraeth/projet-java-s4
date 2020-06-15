package Aquavias.model;

import Aquavias.vue.GUI.VueTerm;

public class MenuNiveau{
    
    /**
     * Représente soit le nombre de niveau normaux
     */
  private int Norm;
      
    /**
     * Représente soit le nombre de niveau personnalisés
     */
  private int Pers;
      
    /**
     * Représente soit la vue
     */
  private VueTerm vt = new VueTerm();

    /**
     * Renvoie le nombre de niveau normaux
     */
  public int getNorm(){
    return Norm;
  }
  
     /**
     * Renvoie le nombre de niveau personnalisés
     */
  public int getPers(){
    return Pers;
  }

     /**
     * Constructeur du menu de niveau
     * @param n int
     * @param p int
     */
  public MenuNiveau(int n,int p){
    Norm = n;
    Pers = p;
  }

     /**
     * Fonction qui permet de lancer le niveau normal numéro x
     * @param x int
     */
  public void LanceNivN(int x){
    System.out.println("Voir dans le fichier JSON le niveau normal numero "+ x);
    System.out.println("Disponible dans une prochaîne mise à jour...");
    int i=5;
    int j=15;
    Generation g1 = Generation.generer(i,j,300);
    Niveau n1 = new Niveau(i,j);
    n1.setNiveau(Generation.const1(g1.getTab()));
    vt.afficheNiv(n1, true);
  }

     /**
     * Fonction qui permet de lancer le niveau personalisé numéro x
     * @param x int
     */
  public void LanceNivP(int x){
    System.out.println("Voir dans le fichier JSON le niveau personnalisé numero "+ x);
    System.out.println("Disponible dans une prochaîne mise à jour...");
  }

     /**
     * Fonction qui permet de jouer un niveau
     * @param x int
     * @param n Niveau
     */
  public void jouer(int lvl, Niveau n) {

        int coups = 0;

        while(!n.partieTerminee()) {    // tant que la partie n'est pas terminée
          int str = 0;
          try{
            System.out.println("Choisissez un tuyau à tourner (entrez -1 pour undo, -2 pour quitter)");
            str = Jeu.sc.nextInt();
          }
          catch (NumberFormatException e){ //Si ce n'est pas un nombre
            System.out.println("Ce n'est pas un nombre");
          }
          if (str == -1) n.undo();
          if (str == -2) {
            coups = 0;
            n.quit();
            break;
          }
          else {
            int premier = premier(str);
            int deuxieme = deuxieme(str);
            if (n.correct(premier, deuxieme)) {        // si la valeur rentré par le scanner est correct
              n.rotate(premier, deuxieme);             // on tourne
              n.addRetour(premier, deuxieme);          // pour la fonction undo()
              coups++;
            }
            else System.out.println("Vous n'avez pas rentré une valeur correct. Réessayez.");
            vt.afficheNiv(n, true);                 // affiche le niveau à chaque fois qu'on tourne un tuyau
          }
        }
        VueTerm.afficheScore(n.getScore(), coups, lvl);
      }

     /**
     * Fonction qui permet de retourner la valeur de l'argument/10 pour tourner ensuite un tuyau, utilisé dans la classe MenuNiveau fonction: jouer()
     * @param str int
     */
      private int premier(int str) {
        return str/10;
      }

    /**
     * Fonction qui permet de retourner la valeur de l'argument%10 pour tourner ensuite un tuyau, utilisé dans la classe MenuNiveau fonction: jouer()
     * @param str int
     */
      private int deuxieme(int str) {
        return str%10;
      }
}
