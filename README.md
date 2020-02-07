# Projet de programmation : AQUAVIAS

## Liste des membres du projet

- Didelot Yannis 21801118 @ydidel28

- Danyshchuk Ruslan 21803907 @ruslan

- KHIEU William 21806172 @Hyraeth

- Li Julien 21806501 @Juju

## Modélisation du jeu

Pour l'instant nous n'avons que 2 classes principale pour le modèle. La classe Tuyau qui servira de bloc de construction pour les niveaux.
Et la classe Niveau qui représente un niveau du jeu.

### Classe Tuyau

Cette classe sera modélisée par un tableau de boolean de taille 4. Chaque indice du tableau correspond à une coordonnée (voir schéma) De plus chaque tuyau possède un boolean rempli qui indique s'il contient de l'eau ou pas.

Il a a en tout au moins 4 tuyaux :

- Un tuyau avec une seule connection qui sert à la fois d'arrivée d'eau et de recepteur d'eau.
  
  Représentation dans l'interface terminale : ╥

  Représentation dans l'interface graphique : WIP

- Un tuyau avec 2 connections opposées.
  
  Représentation dans l'interface terminale : ║

  Représentation dans l'interface graphique : WIP

- Un tuyau avec 2 connections "suivie".
  
  Représentation dans l'interface terminale : ╚

  Représentation dans l'interface graphique : WIP

- Un tuyau avec 3 connections.
  
  Représentation dans l'interface terminale : ╠
  
  Représentation dans l'interface graphique : WIP

Voici par exemple comment un tuyau à 2 connections opposées serait modéliser.
`[TRUE, FALSE, TRUE, FALSE]`
Cela nous facilitera la rotation (une rotation de 90° vers la droite est un right-shift d'un cran), et la modélisation de l'écoulement de l'eau (deux tuyaux sont connectés si leur indices opposés sont tout les deux `TRUE`).

Schéma des différents tuyaux :
![Schéma des tuyaux](Schéma_tuyaux.PNG)

Il s'agit des tuyaux les plus simples que nous allons implementer au départ. Si le projets avance correctement on rajoutera d'autres type de tuyaux.

### Classe Niveau

Cette classe sera modéliser par une matrice de tuyaux.

Exemple d'affichage d'un niveau dans l'interface terminale :

```bash
 ╔╝║═╚╔╝═╝
 ║═╗╝║═╔╚╝
═╗═╔╔═╝╗╝══
 ╝╚╗║╗║╗╔║
 ║╝╚╝═║╗═║
 ╝╚═╔╚╝║╔╗
```

## Stockage des niveaux de jeu

Pour pouvoir stocker les niveaux du jeu on s'est décidé qu'il serait mieux d'utiliser un outil familier.
Au départ on avait hésiter à utiiser des fichiers XML pour stocker les données des niveaux mais le format JSON est plus adapté à nos besoin. Ce sera donc le format que l'on utilisera pour stocker les niveaux.

Le fichier JSON sera structuré de la manière suivante :

```JSON
{
//Niveaux préconçus
"niveaux_off" :
    {
        "001" : {
            //La taille du niveau
            "taille" : n m,
            //Configuration du niveau, i.e. la répartition des tuyaux
            "configuration" : ,
            "score" : ,
            "coups" ; ,
        },
        "002" : {
            ...
        }
    },
//Niveaux créés à l'aide de l'éditeur de niveau
"niveaux_cus" :
    {
       "001" : {
            "taille" : ,
            "configuration" : ,
            "score" : ,
            "coups" ; ,
        },
        "002" : {
            ...
        }
    },
}
```

La manière dont on va sauvegarder la configuration d'un niveau reste encore à voir en fonction de commment on modélise les tuyaux comme la méthode proposée ci-dessus n'est pas définitive.

On aura au moins 2 fichiers JSON. Un qui servira de "backup" lorsqu'on souhaite réinitialiser un niveau. Et un autre qui servira de sauvegarde pour le joueur.
Cela permettra de pouvoir refaire un niveau afin d'avoir un meilleur score.

## Organisation de l'interface graphique

WIP
