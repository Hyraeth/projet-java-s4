package Aquavias.model;

public class Menu{

  //Constructeur
  public Menu(){

  }

  //Affiche la barre de menu
  public void affiche(){
    System.out.println(
    Color.WHITE_BOLD_BRIGHT+"\n              MENU         \n"+Color.RESET
    +  "      _____________________\n\n"
    +  "            JOUER(j)\n"
    +  "            CREER(c)\n"
    +  "           QUITTER(q) \n");
  }
}
