# Gestion des Personnes - Projet Spring Boot

Ce projet est une application Spring Boot qui permet de gérer des entités "Personne" via une API REST. Il utilise Spring Data JPA pour la gestion des données et inclut des tests unitaires, d'intégration et end-to-end pour garantir son bon fonctionnement.

## Fonctionnalités principales

- **API REST** : Gestion des entités "Personne" (ajout, recherche, liste).
- **Spring Data JPA** : Interaction avec la base de données pour la persistance des données.
- **Tests** : Suite complète de tests pour valider les fonctionnalités.

## Documentation

- [Application Spring Boot](docs/application.md) : Détails sur la configuration et les fonctionnalités de l'application.
- [Tests et Validation](docs/tests.md) : Explications sur les différents types de tests implémentés.

## Structure du projet

- **`src/main/java`** : Contient le code source de l'application.
- **`src/test/java`** : Contient les tests unitaires, d'intégration et end-to-end.
- **`docs/`** : Contient la documentation détaillée.

## Prérequis

Pour exécuter ce projet, assurez-vous d'avoir les éléments suivants installés et configurés :

- **Java 21** : Le projet utilise Java 21 comme version minimale.
- **Maven** : Pour la gestion des dépendances et la compilation du projet.
- **Base de données** :
  - **MariaDB** : Utilisée pour la persistance des données en production.
  - **H2** : Base de données en mémoire utilisée pour les tests.
- **Docker** (optionnel) : Requis si vous utilisez Testcontainers pour les tests d'intégration.

## Dépendances principales

Le projet utilise les dépendances suivantes :

- **Spring Boot** :
  - `spring-boot-starter-data-jpa` : Pour la gestion des données avec JPA.
  - `spring-boot-starter-web` : Pour créer l'API REST.
  - `spring-boot-starter-validation` : Pour la validation des données.
- **Base de données** :
  - `h2` : Base de données en mémoire.
  - `postgresql` : Driver pour PostgreSQL pour les tests.
  - `mariadb-java-client` : Driver pour MariaDB pour les tests.
- **Mapper** :
  - `mapstruct` : Permet d'automatiser les mappings requestDto → entity et entiry → responseDto.
- **Tests** :
  - `spring-boot-starter-test` : Pour les tests unitaires et d'intégration.
  - `testcontainers` : Pour les tests d'intégration avec des bases de données Dockerisées (postgre + mariadb).
- **Documentation API** :
  - `springdoc-openapi-starter-webmvc-ui` : Pour générer la documentation OpenAPI/Swagger.

Assurez-vous que ces outils et configurations sont en place avant de lancer l'application.

## Lancer l'application

1. Configurez la base de données dans le fichier `application.properties`.
2. Compilez et lancez l'application avec Maven :
   ```bash
   mvn spring-boot:run
   ```


