public class Jeu{

  //Constructeur du jeu
  public Jeu(){

  }

  //Lanceur du Jeu
  public void start(){
    System.out.println("\n     \033[3m ~BIENVENU DANS AQUAVIAS~ \033[0m \n");

    Menu m = new Menu();
    m.afficherMenu();
  }



}
