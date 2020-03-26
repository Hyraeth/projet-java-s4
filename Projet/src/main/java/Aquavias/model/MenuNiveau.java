package Aquavias.model;

import Aquavias.vue.GUI.VueTerm;

public class MenuNiveau{
  private int Norm; //Nombre de niveau normaux
  private int Pers; //Nombre de niveau personnalisés
  private VueTerm vt = new VueTerm();

  public int getNorm(){
    return Norm;
  }
  public int getPers(){
    return Pers;
  }

  //Constructeur
  public MenuNiveau(int n,int p){
    Norm = n;
    Pers = p;
  }

  //Permet de lancer le niveau normal numéro x
  public void LanceNivN(int x){
    System.out.println("Voir dans le fichier JSON le niveau normal numero "+ x);
    System.out.println("Disponible dans une prochaîne mise à jour...");
    int i=5;
    int j=15;
    Generation g1 = Generation.generer(i,j);
    Niveau n1 = new Niveau(i,j);
    n1.setNiveau(Generation.const1(g1.getTab()));
    vt.afficheNiv(n1);
  }

  //Permet de lancer le niveau personnalisé numéro x
  public void LanceNivP(int x){
    System.out.println("Voir dans le fichier JSON le niveau personnalisé numero "+ x);
    System.out.println("Disponible dans une prochaîne mise à jour...");
  }


  public void jouer(int lvl, Niveau n) {

        int coups = 0;

        while(!n.partieTerminee()) {    // tant que la partie n'est pas terminée
          int str = 0;
          try{
            System.out.println("Choisissez un tuyau à tourner (entrez -1 pour undo)");
            str = Jeu.sc.nextInt();
          }
          catch (NumberFormatException e){ //Si ce n'est pas un nombre
            System.out.println("Ce n'est pas un nombre");
          }
          if (str == -1) {
            if (n.getRetourVide()){
              System.out.println("Vous ne pouvez pas faire un undo maintenant");
              break;
            }
            else {
              int i = n.getRetourx();
              int j = n.getRetoury();
              n.undo(i,j);
            }
          }
          if (str != -1) {
            int premier = premier(str);
            int deuxieme = deuxieme(str);
            if (n.correct(premier, deuxieme)) {        // si la valeur rentré par le scanner est correct
              n.rotate(premier, deuxieme);             // on tourne
              n.setRetour(premier, deuxieme);          // pour la fonction undo()
              coups++;
            }
            else System.out.println("Vous n'avez pas rentré une valeur correct. Réessayez.");
            vt.afficheNiv(n);                 // affiche le niveau à chaque fois qu'on tourne un tuyau
          }
        }
        vt.afficheScore(n.getScore(), coups, lvl);  // en attendant de coder json, on fait pour niveau 1
      }

      private int premier(int str) {
        return str/10;
      }

      private int deuxieme(int str) {
        return str%10;
      }
}
