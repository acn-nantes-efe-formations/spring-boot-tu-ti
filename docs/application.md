# Application Spring Boot

## Fonctionnalités

- **API REST** : Permet de gérer les entités "Personne" via des endpoints HTTP.
- **Spring Data JPA** : Utilisé pour interagir avec la base de données.
- **Configuration** : Les propriétés de l'application sont définies dans `application.properties`.

## Endpoints principaux

### **GET /personnes**
- **Description** : Récupère la liste de toutes les personnes.
- **Réponse (200 OK)** :
  ```json
  [
    {
      "id": 1,
      "nom": "Dupont",
      "prenom": "Jean",
      "age": 30
    },
    {
      "id": 2,
      "nom": "Martin",
      "prenom": "Claire",
      "age": 25
    }
  ]
  ```

### **GET /personnes/{id}**
- **Description** : Récupère les informations d'une personne par son ID.
- **Paramètres** : 
  - `id` (path) : L'identifiant unique de la personne.
- **Réponse (200 OK)** :
    ```json
    {
      "id": 1,
      "nom": "Dupont",
      "prenom": "Jean",
      "age": 30
    }
    ```
- **Réponse (404 Not Found)** 


### **POST /personnes**
- **Description** : Permet d'ajouter une nouvelle personne.
- **Corps de la requête (JSON)** : 
    ```json
    {
      "nom": "Dupont",
      "prenom": "Jean",
      "age": 30
    }
    ```
- **Réponse (201 Created)** :
    ```json
    {
      "id": 1,
      "nom": "Dupont",
      "prenom": "Jean",
      "age": 30
    }
    ```
- **Réponse (400 Bad Request)** : Si les données envoyées ne respectent pas les contraintes de validation.

## ApplicationControllerAdvice

La classe `ApplicationControllerAdvice` est une classe de gestion centralisée des exceptions dans l'application. Elle utilise l'annotation `@RestControllerAdvice` pour intercepter les exceptions levées par les contrôleurs REST et fournir des réponses HTTP appropriées.

[Plus d'informations](./advice.md) 
