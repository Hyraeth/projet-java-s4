package Aquavias.model;

import java.util.Scanner;

public class Score {

  private static void recommencer(int lvl) {     // recommencer le niveau
    MenuNiveau n = new MenuNiveau(25,13);
    if (lvl<1 || lvl>n.getNorm()) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
    else n.LanceNivN(lvl); //Lance le niveau numéro x
  }

  private static void suivant(int lvl) {         // niveau suivant
    MenuNiveau n = new MenuNiveau(25,13);
    if (lvl+1<1 || lvl+1>n.getNorm()) throw new IndexOutOfBoundsException(); //Vérifie si le niveau existe
    else n.LanceNivN(lvl+1); //Lance le niveau numéro x
  }

  private static void retour() {          // retourner à la liste des niveaux
    return;
  }


}
