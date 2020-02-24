import java.util.*;
import java.io.*;


public class Jeu{

  Scanner sc;

  //Constructeur du jeu
  public Jeu(){

  }

  public void creer(){
    Creation c = new Creation();
    while(true){
      c.affiche();
      String rep = sc.next();
      if(rep.equals("r")) return;
      else if(rep.equals("q")) System.exit(0);
    }
  }
  public void jouer(){
    Niveau n = new Niveau(25);
    while(true){
      n.affiche();
      String rep = sc.next();
      int x;
      if(rep.equals("r")) return;
      else if(rep.equals("q")) System.exit(0);
      else{
        try{
          x = Integer.parseInt(rep);
          if (x<1 || x>n.nb) throw new IndexOutOfBoundsException();
          else n.LanceNiv(x);
        }
        catch (NumberFormatException e){
          System.out.println("Ce n'est pas un nombres");
        }
        catch (IndexOutOfBoundsException r) {
          System.out.println("Ce nombre n'est pas compris entre 1 et " + n.nb);
        }
      }
    }
  }

  //Lanceur du Jeu
  public void start(){
    System.out.println(Color.BLUE_BOLD_BRIGHT+"\n     \033[3m ~BIENVENUE DANS AQUAVIAS~ \033[0m \n"+Color.RESET);


    while(true){
      Menu m = new Menu();
      m.affiche();
      sc = new Scanner(System.in);
      switch (sc.next()){
        case "j": jouer(); break;//Afficher les niveaux
        case "c": creer(); break;//Afficher Creation
        case "q": System.exit(0);//Quitter
        default : System.out.println("\nVeuillez entrer j,c ou q .\n");
      }
    }

  }



}
