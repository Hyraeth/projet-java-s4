# Projet Aquavias

## Règles du Jeu

### Jeu

La règle principale est de réussir à relier la source à la ville en formant une série finie de "tuyau" ou de "pont".

Par contre, il ne faut pas qu'un tuyau reliant ces deux points menne à une issue vide.

Il y a 3 variantes dans le jeu:

- La première variante est que la source et les mouvements sont illimités. Il n'y a donc aucune contrainte pour ces niveaux

- La deuxième variante est que la source est illimitée mais les mouvements sont limités.

- La troisième variante est le contraire, c'est-à-dire que la source est limitée mais les mouvements illimités.
  (Pour cette variante on peut implémenter un compte à rebour qui se lance si on rencontre un tuyau qui a une sortie[True] et qui ne rencontre pas de tuyau ou qui rencontre un tuyau qui a une entrée[False])

### Menu

Un niveau est accessible si et seulement si le niveau précédent a déjà été terminé. Une ecception existe pour le tout premier niveau.

Un niveau déjà fait se démarque des autres par un affichage d'une couleur différente.

## Ce que vous auriez aimé ajouter

Dans l’ensemble notre projet est plutôt satisfaisant de notre point de vue car il donne un ensemble qui ne fonctionne pas mal. Bien évidemment il est vrai qu’il n’est pas parfait et qu’on aurait voulu ajouter plus de contenu.
Tout d’abord on voulait au début ajouter une catégorie de création pour que nous puissions créer des niveaux dits «personnalisés» mais on a finalement décidé de créer une fonction de génération de niveaux car cela nous semblait plus intéressant.

## La répartition du travail

La répartition du travail a été plutôt simple. On se mettait d’accord sur le travail à fournir pour chaque sprint. Chacun prenait donc les tâches qui l’intéressaient puis on répartissait le reste selon les possibilités de chacun et le niveau de chacun. Lorsqu’une personne avait du mal pour sa partie on venait naturellement à s’entraider.

## Les difficultés rencontrées

En général nous nous sommes bien débrouillés par contre nous avons rencontré chacun de notre côté plusieurs difficultés marquantes.
Tout d’abord la génération de niveaux aléatoire. Cette fonction ne marchait pas à chaque fois car une seule petite erreur difficile à percevoir pouvait ruiner toute la fonction. 
Ensuite il y a l’affichage de l’interface graphique, rendre un contenu joli à voir. L’interface graphique a été notre plus grand défaut.
Finalement le plus difficile était la communication surtout pendant la période de confinement. Car chacun a son point de vue de structure de codage donc il est parfois difficile de comprendre le code des autres même avec une explication.
