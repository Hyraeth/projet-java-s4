# Projet de programmation : AQUAVIAS

## Architecture du programme

Notre programme est divisé en 3 parties : la partie logique du programme (le model), la partie graphique (la vue) et un controller qui gère la communication entre la vue et le model.

### Le model

La partie logique du code qui s'occupe de toutes les manipulations du plateau (rotation des tuyaux, chargement de niveau, faire couler l'eau, tester si le niveau est fini, ...) se trouve dans la classe Niveau. Chaque niveau est en fait une matrice de Pipe.
La classe Pipe sert de bloc de construction pour notre programme. Chaque Pipe est un tableau de boolean de taille 4. Celui-ci indique les côtés du tuyau qui peuvent être connectés. Il possède également d'autres attributs qui servent d'information sur le tuyau (comme par exemple s'il peut être tourné, s'il est rempli, etc).
La classe Generation sert à générer des niveaux aléatoires.

Lanceur est la classe que l'on appelle pour lancer le jeu.
Jeu est la classe qui concerne le menu principal.
MenuNiveau est la classe qui concerne les sous-niveaux. On y trouve également la fonction "jouer", qui permet de lancer une partie.

### La vue

La classe fenêtre est le menu de notre interface graphique. C'est ce qui nous affiche la liste des niveaux disponibles et qui nous permet de générer des niveaux aléatoirement. Lorsque l'on clique sur un bouton, on appelle une fonction du controller qui va s'occcuper d'initialiser un niveau et d'ouvrir une interface graphique adaptée (VueIG).
VueIG sert à afficher une fenêtre avec un plateau de jeu et ouvre une fenêtre lorsque le joueur gagne ou perd. Le plateau affiché est un tableau de JPanelPipe. Cette classe sert à afficher les images qui correspondent aux tuyaux du niveau.
VueTerm est l'affichage du jeu dans le terminal. C'est une classe qui sert de debuggage.

### Le controller

Le controller s'occupe de lancer un niveau et avertit la vue lorsqu'il y a des changement à faire. Il sert aussi à dire au model lorsqu'il y a quelque chose à faire. Par exemple si le joueur clique sur un tuyau, le controller dit au model de tourner ce tuyau et une fois qu'il est tourner, dire à la vue de se mettre à jour afin d'afficher le tuyau bien tourner.

## Arbre des fichiers

```BASH
Projet
 ┣ src
 ┃ ┣ main
 ┃ ┃ ┗ java
 ┃ ┃ ┃ ┗ Aquavias
 ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┗ ControllerIG.java
 ┃ ┃ ┃ ┃ ┣ model
 ┃ ┃ ┃ ┃ ┃ ┣ Color.java
 ┃ ┃ ┃ ┃ ┃ ┣ Generation.java
 ┃ ┃ ┃ ┃ ┃ ┣ Jeu.java
 ┃ ┃ ┃ ┃ ┃ ┣ Lanceur.java
 ┃ ┃ ┃ ┃ ┃ ┣ MenuNiveau.java
 ┃ ┃ ┃ ┃ ┃ ┣ Niveau.java
 ┃ ┃ ┃ ┃ ┃ ┣ Pipe.java
 ┃ ┃ ┃ ┃ ┃ ┣ PipeFactory.java
 ┃ ┃ ┃ ┃ ┃ ┣ Readme.md
 ┃ ┃ ┃ ┃ ┃ ┗ Score.java
 ┃ ┃ ┃ ┃ ┗ vue
 ┃ ┃ ┃ ┃ ┃ ┣ GUI
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ Fenetre.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ JPanelPipe.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ScoreIG.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ VueIG.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ VueTerm.java
 ┃ ┃ ┃ ┃ ┃ ┗ App.java
```

## Modélisation du jeu

Il y a 3 classes principales pour le modèle. 
La classe Tuyau qui sert de bloc de construction pour les niveaux.
La classe Niveau qui représente un niveau du jeu.
La classe Generation qui génère des niveaux aléatoires.

### Classe Tuyau

Cette classe sera modélisée par un tableau de boolean de taille 4. Chaque indice du tableau correspond à une coordonnée (voir schéma). De plus, chaque tuyau possède un boolean rempli qui indique s'il contient de l'eau ou pas.

En tout, il y a 6 tuyaux :

- Un tuyau avec une seule connection qui sert de départ d'eau et un autre tuyau qui sert de recepteur d'eau.
  
  Représentation dans l'interface terminale : ╨ ╞ ╥ ╡

  Représentation dans l'interface graphique :
  [Tuyau départ](https://gaufre.informatique.univ-paris-diderot.fr/ydidel28/projet-java-s4/blob/master/assets/img/00false.png)
  [Tuyau arriver](https://gaufre.informatique.univ-paris-diderot.fr/ydidel28/projet-java-s4/blob/master/assets/img/10false.png)

- Un tuyau avec 2 connections "suivie".
  
  Représentation dans l'interface terminale : ╚ ╔ ╗ ╝

  Représentation dans l'interface graphique :
  [Tuyau en L](https://gaufre.informatique.univ-paris-diderot.fr/ydidel28/projet-java-s4/blob/master/assets/img/20false.png)

- Un tuyau avec 2 connections opposées.
  
  Représentation dans l'interface terminale : ║ ═

  Représentation dans l'interface graphique :
  [Tuyau en I](https://gaufre.informatique.univ-paris-diderot.fr/ydidel28/projet-java-s4/blob/master/assets/img/30false.png)

- Un tuyau avec 3 connections.
  
  Représentation dans l'interface terminale : ╠ ╦ ╣ ╩

  Représentation dans l'interface graphique :
  [Tuyau en T](https://gaufre.informatique.univ-paris-diderot.fr/ydidel28/projet-java-s4/blob/master/assets/img/40false.png)
  
- Un tuyau avec 4 connections.
  
  Représentation dans l'interface terminale : '╬'

  Représentation dans l'interface graphique :
  [Tuyau en X](https://gaufre.informatique.univ-paris-diderot.fr/ydidel28/projet-java-s4/blob/master/assets/img/50false.png)

Voici par exemple comment un tuyau à deux connexions opposées serait modélisé.
`[TRUE, FALSE, TRUE, FALSE]`
Cela nous facilitera la rotation (une rotation de 90° vers la droite est un right-shift d'un cran), et la modélisation de l'écoulement de l'eau (deux tuyaux adjacents sont connectés si leur indices opposés sont tous les deux `TRUE`).

Schéma des différents tuyaux :
![Schéma des tuyaux](Schéma_tuyaux.PNG)

Il s'agit des tuyaux les plus simples que nous allons implementer au départ. Si le projet avance correctement, on ajoutera d'autres types de tuyaux.

### Classe Niveau

Cette classe sera modélisée par une matrice de tuyaux.

Exemple d'affichage d'un niveau dans l'interface terminale :

```bash
╔╝║═╚╔╝═╝
║═╗╝║═╔╚╝
╞═╔╔═╝╗╝╡
╝╚╗║╗║╗╔║
║╝╚╝═║╗═║
╝╚═╔╚╝║╔╗
```

### Classe Generation

Cette classe sert à générer des niveaux aléatoires. Pour plus d'information, aller à la section Algorithmes.

## Structures de données

### Stockage des niveaux de jeu

Pour pouvoir stocker les niveaux du jeu on a décidé qu'il serait mieux d'utiliser un outil familier.
Au départ, on avait hésiter à utiiser des fichiers XML pour stocker les données des niveaux, mais le format JSON est plus adapté à nos besoins. Ce sera donc le format que l'on utilisera pour stocker les niveaux.

Le fichier JSON sera structuré de la manière suivante :

```JSON
{
//Niveaux préconçus
"niveaux_off" :
    [
        {
            //La taille du niveau
            "largeur" : 3,
            "longueur" : 4,
            //Placement des tuyaux dans la grille. Chaque groupes de 3 charactères représentent un tuyau. Le premier char le type de tuyau, le deuxieme sa rotation, et le dernier s'il peut etre tourné.
            "configuration" : "00F23T31T21T20T40T43T10F21T30T22T22T",
            //Resources pour jouer aux jeux(temps ou nombre de coups)
            "resources" : 500,
            //Mode de jeu: (2)temps, (1)nombre de coups, (0)resources infinies
            "type" : 1
        },
        {
            ...
        }
    ]
}
```

À chaque fois qu'un niveau est généré, il sera immédiatement sauvegardé.

### Structures de données dans la classe Pipe

Les attributs de la classe sont décrit dans la javadoc.
Les points importants sont que pour l'affichage d'un plateau dans le terminal, il y a un tableau de char static qui contient tous les caractères qui représentent les tuyaux. De plus, il y a un attribut qui sert d'indicateur pour chaque tuyau qui lui dit où se trouve son caractère (cela va déprendre du type de tuyau, et de sa rotation actuelle). Il y a également un indice qui sert à afficher la bonne image dans l'interface graphique.

### Structures de données dans la classe Niveau

Les attributs de la classes sont décrits dans la javadoc.
Un niveau est principalement une matrice de Pipe.
Il y a un attribut qui sert à stocker les positions des tuyaux qui ont été tournés par le joueur qui est un Stack.

### Structures de données de la classe JPanelPipe

Cette classe sert à afficher un tuyau dans l'interface graphique. Il y a un tableau de tableau de BuffedredImage static. Cela sert à ne charger qu'une seule fois les images, et ensuite choisir quelle image afficher selon l'indice (un attribut) du tuyau à afficher.

## Algorithmes

### Algorithme de coulage d'eau

Cet algorithme permet de remplir d'eau les cases qui sont reliées au départ. Il sert également à savoir si il y a des fuites (Il renvoie true si il n'y a pas de fuite).
Tout d'abord, l'algorithme commence par remplir d'eau la case si elle est reliée à la case précédente. Ensuite, il va tester s'il a des fuites. Enfin, il va appeler l'algorithme sur les cases adjacentes connectées.

### Algorithme de génération de niveaux

L'algorithme de génération de niveaux va commencer par créer une matrice d'entiers qui vont nous permettre d'associer un pipe à une case, et d'avoir à la fin un niveau fonctionnel.
La matrice d'entiers est en réalité une matice de tableaux d'entiers, qui donne pour une case, les connexions du pipe.
L'algorithme commence au pipe de départ. Sont but est de rejoindre le pipe d'arrivée en créant un chemin aléatoire. Mais selon la difficulté du niveau, l'algorithme a la possibilité de poser des pipes T, qui vont créer un autre chemin aléatoire. L'objectif de l'algorithme sera alors modifié, puisque le nouveau chemin devra rejoindre un chemin déjà existant.