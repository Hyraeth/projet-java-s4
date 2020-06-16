package Aquavias.model;

import Aquavias.vue.GUI.VueTerm;
import java.util.*;
import java.io.*;

/**
 * Représente le lien entre le menu du jeu et le plateau de jeu
 */

public class Jeu{

  /**
   * Représente la vue du Terminal, pour lancer les affichages
   */
  private VueTerm vt = new VueTerm();

  /** 
   * Scanner de la classe pour interagir avec le terminal
   */
  static Scanner sc;

  /**
   * Constructeur de la classe Jeu
   */
  public Jeu(){

  }

  /**
   * Lancé si le joueur veut creer
   */
  public void creer(){
    
  }

  /**
   * Lance le Jeu
   * Lance un niveau Normal si le joueur entre "n"
   * Lance un niveau personnalisé si le joueur entre "p"
   * Revient au menu si le joueur entre "r"
   * Quitte le jeu si le joueur entre "q"
   */ 
  public void jouer(){

    while(true){
      //Affichage de la fenêtre
      vt.afficheNiv();
      switch (sc.next()){
        case "n": lanceNiv("n"); break;//Afficher les niveaux normaux
        case "p": lanceNiv("p"); break;//Afficher les niveaux personnalisés
        case "r": return;//Revenir au menu
        case "q": System.exit(0);//Quitter
        default : System.out.println("\nVeuillez entrer n,p,r ou q .\n");
      }
    }
  }

  /**
   * Lance un niveau normal ou personnalisé
   * @param s String
   */
  public void lanceNiv(String s){
    while(true){
      MenuNiveau n = new MenuNiveau(25,13);
      if(s.equals("n")){ //S'il faut lancer un niveau normal
        vt.afficheNorm(); //Affiche la liste des niveaux normaux
        int x;
        String rep = sc.next();
        if(rep.equals("r")) return; //Retour
        else if(rep.equals("q")) System.exit(0); //Quitter
        else{
          try{
            x = Integer.parseInt(rep);
            if (x<1 || x>n.getNorm()) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
            else n.LanceNivN(x); //Lance le niveau numéro x
          }
          catch (NumberFormatException e){ //Si ce n'est pas un nombre
            System.out.println("Ce n'est pas un nombres");
          }
          catch (IndexOutOfBoundsException r){ //Si ce n'est pas accepté
            System.out.println("Ce nombre n'est pas compris entre 1 et " + n.getNorm());
          }
        }
      }else{ //S'il faut lancer un niveau personnalisé
        vt.affichePers(); //Affiche la liste des niveaux personnalisés
        int x;
        String rep = sc.next();
        if(rep.equals("r")) return; //Retour
        else if(rep.equals("q")) System.exit(0); //Quitter
        else{
          try{
            x = Integer.parseInt(rep);
            if (x<1 || x>n.getPers()) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
            else n.LanceNivP(x); //Lance le niveau numéro x
          }
          catch (NumberFormatException e){ //Si ce n'est pas un nombre
            System.out.println("Ce n'est pas un nombres");
          }
          catch (IndexOutOfBoundsException r){ //Si ce n'est pas accepté
            System.out.println("Ce nombre n'est pas compris entre 1 et " + n.getPers());
          }
        }
      }
    }
  }

  /**
   * Fonction appelé par la classe Lanceur pour lancer le jeu
   */
  public void start(){
    //Message de bienvenue
    vt.afficheStart();

    while(true){
      vt.afficheMenu(); //Affiche le Menu
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
