# NYX_Project

Element missing : 
- [] information in the file wrote : price buy, price cell, feasible or not, percentage of success
- [X] error of decreasing element quantity even if it's not feasible
- [] javadoc and documentation
- [] design cohérent entre les multiples interfaces
- [X] write in text file when app is closed
- [] check input when we add (inventory, chaine)
- [X] clean code (class Usine)  
- [X] display notification on the confirmation controller
- [] afficher la faisabilité de manière dynamique sur la page d'accueil en afficher que les chaines de production à partir desquelles on peut produire
- [] afficher la faisabilité sur la page d'accueil au fur et à mesure qu'on incrémente la quantité souhaitée

# Gestionnaire de production

## 1 Présentation générale

### 1.1 Objectif

Ce projet concerne une application dédiée à la gestion de la production d’une usine. L’application devra permettre de connaître les stocks d’une usine, de planifier les achats et surtout de programmer l’activité des différentes
chaînes de production.

### 1.2 Développement

L’application sera développée de manière incrémentale. La première étape constitue le coeur de l’application,
en particulier la structure de données. Les étapes suivantes concernent l’ajout et l’amélioration de fonctionnalités.
L’application est une application de bureau (par opposition à une application web). Les données seront stockées
sous forme de fichiers et chargés/sauvegardés à la demande de l’utilisateur.

### 1.3 Client

Les utilisateurs de l’application sont les responsables de production. Leur tâche est de décider de la production
de l’usine en fonction des commandes et des opportunités de vente. L’outil a pour but de les aider à choisir la
meilleure solution de production hebdomadaire. En particulier, il doit leur permettre de tester des programmes
de production et de déterminer l’efficience et l’efficacité de ces programmes.
A plus long terme (hors du cadre de ce projet), l’application pourra intégrer des algorithmes d’optimisation.
2 Principes généraux : 
- L’usine utilise des matières premières et les transforme dans ses chaînes de production pour obtenir des produits. Certains de ces produits peuvent être également utilisés en entrée de certains chaînes de production afin
d’obtenir des produits plus complexes.
- Les données nécessaire à l’application sont présentées sous forme de fichiers .csv. Dans un premier temps ces
fichiers contiendront uniquement les informations essentielles. Ces fichiers seront ultérieurement enrichis pour
répondre aux besoins de nouvelles fonctionnalités.
Il est tout à fait possible d’ajouter de nouveaux fichiers si nécessaire au cours du développement de l’application
tout comme de renommer/modifier les fichiers existants, voire d’en modifier le format. Vous êtes également libre
de modifier l’organisation des fichiers.

### 2.1 Matières premières et produits

Les matières premières et les produits sont dénommés ci-après élément et décrits dans un fichier commun
elements.csv. Les informations initialement utilisées pour chaque élément sont :
— un code unique
— un nom
— une quantité (stock)
— une unité de mesure du stock (litres, unités, kilogrammes. . .)

### 2.2 Chaînes de production

Les chaînes de productions sont décrites dans un fichier chaines.csv. Chaque chaîne prend une certaine
quantités d’éléments en entrée et produit un (ou plusieurs) élément (possiblement différents) en sortie.
Le fonctionnement productif d’une chaîne est décrit par son niveau d’activation. Lorsque celui ci est de 0, la
chaîne ne produit rien, lorsqu’il est de 1 elle consomme et produit ce qui est donnée dans sa description. Lorsque
le niveau d’activation est de n (non nul), elle consomme et produit n fois plus d’éléments.
Le niveau d’activation est la variable que l’opérateur doit pouvoir modifier pour tester les différentes options
de production.
La chaîne de production est caractérisée par :
— un code unique
— un nom
— liste des éléments en entrée et leurs quantités
— liste des éléments en sortie et leurs quantités

### 2.3 Achats/Ventes/Demande

Chaque élément peut avoir un prix d’achat et/ou de vente. De plus l’usine peut avoir reçu un certain nombre
de commandes de certains éléments. L’ensemble de ces données est décrit dans le fichier prix.csv.
Il comporte :
— le code de l’élément concerné
— le prix d’achat (NA si achat impossible)
— le prix de vente (NA si l’élément n’est pas à la vente)
— la quantité commandée

### 2.4 Calculs

Lorsque l’opérateur a entré les niveaux d’activité de chaque chaîne de production, l’application doit fournir un
résultat permettant d’évaluer la production envisagée. Il faudra pour cela donner deux indicateurs : une indicateur
de valeur et un indicateur de commande
Indicateur de valeur Cet indicateur donne une estimation financière de la rentabilité de la production envisagée.Il est calculé comme suit :
— Les achats sont ajoutés au stock.
— Chaque chaîne de production soustrait du stocks les éléments en entrée correspondant à son niveau d’activation choisi.
— Chaque chaîne ajoute aux stocks les élément produits correspondant à son niveau d’activation.
— Les stocks sont maintenant examinés. Si un élément a un stock négatif, la production est marquée comme
impossible
— Si la production est possible, son efficacité est mesurée en ajoutant les valeurs de vente de tous les stocks
disponibles (parmi ceux qui ont un prix de vente) après production et en soustrayant le montant total des
achats à effectuer.
Indicateur de commande Cet indicateur affiche le pourcentage des commandes qui sont satisfaites.

## 3 Utilisation et ergonomie

### 3.1 Scénario d’utilisation

Nous présentons ici une exemple d’utilisation typique de l’application dans sa version initiale.
— Ouverture de l’application
— Chargement des données
— Visualisation de l’état des stocks
— Itération d’essais de production :
— saisie des niveaux d’activité des chaînes de production
— saisie des achats
— visualisation du résultat
— Lorsque l’opérateur est satisfait de son résultat, export du résultat (sous la forme de votre choix).

### 3.2 Ergonomie

Il est nécessaire de faciliter la tâche de l’utilisateur.En particulier les opérations de modification des niveaux
d’activation et des achats doivent pouvoir être réalisée en un minimum d’actions.
Il est souhaitable de minimiser le nombre d’écrans afin que la recherche d’information n’engendre pas un trop
grand nombre d’action de l’utilisateur.
La lisibilité du ou des écrans les plus utilisés est primordiale.

## 4 Fonctionnalités envisagées

Vous devrez pensez votre projet afin qu’il soit modulaire afin que toute modification impacte un minimum le
code de l’application.
Afin d’orienter les choix lors de la conception de l’application, il est souhaitable de connaître les potentielles
futures fonctionnalités. Ci-dessous se trouve une liste non exhaustive de fonctionnalités envisagées.
Sauvegarde des plans de production : sauvegarder les niveaux d’activations et les achats permetrait à l’utilisateur de tester plusieurs solutions et de les comparer.
Sessions : gestions de sessions, regroupant les infomations initiales du projet et le ou les plans de production
envisagés.
Prise en compte du temps : la production sur chaque chaîne est cadencée. Cela a un impact sur la possibilité
de réaliser effectivement certains programmes de production. Les éléments achetés ne sont également pas livrés
immédiatement.
Production sur plusieurs semaines : étude de la gestion des stocks sur plusieurs semaines pour une planification à plus long terme. Suivi de l’état des stocks.
Gestion des différents moyens de stockage : suivant leur nature (liquides, emballés. . .), les différents éléments
sont stockés différemment (en cuve, entreposés). Il faudra également prendre en compte les disponibilités de stockage.
Historique et indicateurs : mémoriser l’historique des semaines précédentes (stocks et productions) afin d’afficher des indicateurs sur les évolution de production.
Gestion des personnels qualifiés : le fonctionnement des chaînes de production repose sur la présence de personnel qualifié. Leurs effectifs constituent une contrainte supplémentaire à prendre en compte dans l’évaluation
des options de production.
Les fonctionnalités que vous aurez à intégrer seront discutées lors des RDV.