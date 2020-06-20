package Aquavias.model;

import java.util.Scanner;

public class Score {

/**
     * fonction pour recommencer un niveau
     * @param lvl
     */
  public static void recommencer(int lvl) { 
    MenuNiveau n = new MenuNiveau(25,13);
    if (lvl<1 || lvl>n.getNorm()) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
    else n.LanceNivN(lvl); //Lance le niveau numéro x
  }

/**
     * fonction pour lancer le niveau suivant
     * @param lvl
     */
  public static void suivant(int lvl) {     
    MenuNiveau n = new MenuNiveau(25,13);
    if (lvl+1<1 || lvl+1>n.getNorm()) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
    else n.LanceNivN(lvl+1); //Lance le niveau numéro x
  }

/**
     * fonction pour retourner à la liste des niveaux
     */
  public static void retour() {
    return;
  }


}
