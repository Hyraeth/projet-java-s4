package Aquavias.model;

import Aquavias.vue.GUI.VueTerm;
import java.util.*;
import java.io.*;


public class Jeu{
  private VueTerm vt = new VueTerm();
  static Scanner sc;

  //Constructeur du jeu
  public Jeu(){

  }

  //Lancé si le joueur veut creer
  public void creer(){
    /*Creation c = new Creation();
    while(true){
      //Affiche la fenêtre de création de niveau
      c.affiche();
      String rep = sc.next();
      if(rep.equals("r")) return; //Retour
      else if(rep.equals("q")) System.exit(0); //Quitter
      else System.out.println("Je n'ai pas compris.");
    }*/
  }

  //Lancé si le joueur veut jouer
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

  //Lanceur de niveau (normal ou personnalisé)
  public void lanceNiv(String s){
    while(true){
      MenuNiveau n = new MenuNiveau(25,13);
      if(s.equals("n")){ //Si il faut lancer un niveau normal
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
      }else{ //Si il faut lancer un niveau personnalisé
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

  //Lanceur du Jeu
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
