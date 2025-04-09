# Tests et Validation

## Types de tests

1. **Tests unitaires** :

   - Vérifient le comportement des classes et méthodes isolées.
   - Utilisent Mockito pour simuler les dépendances.
2. **Tests d'intégration** :

   - Vérifient l'interaction entre les différentes couches de l'application.
   - Utilisent une base de données en mémoire ou un conteneur Docker pour les tests.
3. **Tests end-to-end** :

   - Vérifient le fonctionnement global de l'application via des appels API.

## Frameworks utilisés

- **JUnit 5** : Pour l'exécution des tests.
- **AssertJ** : Pour des assertions lisibles et puissantes.
- **Mockito** : Pour le mock des dépendances.
- **Spring Boot Test** : Pour les tests d'intégration.

## Descriptions des classes de tests

### **Service**

#### **PersonneManagerUnitTest**

- **Type** : Test unitaire.
- **Description** : Vérifie les fonctionnalités de `PersonneManager` en utilisant JUnit 5 et Mockito pour simuler les dépendances.
- **Tests principaux** :
  - Ajout d'une personne (valide et invalide).
  - Recherche d'une personne (existante et inexistante).
  - Liste des personnes.

#### **PersonneManagerAssertJUnitTest**

- **Type** : Test unitaire.
- **Description** : Vérifie les fonctionnalités de la classe `PersonneManager` en utilisant AssertJ pour des assertions lisibles et Mockito pour simuler les dépendances.
- **Tests principaux** :
  - Ajout d'une personne (valide et invalide).
  - Recherche d'une personne (existante et inexistante).
  - Liste des personnes.

#### **PersonneManagerSpringUnitTest**

- **Type** : Test d'intégration léger.
- **Description** : Vérifie les fonctionnalités de `PersonneManager` avec un contexte Spring minimal, en utilisant des beans Mockito pour simuler les dépendances.
- **Tests principaux** :
  - Ajout d'une personne.
  - Recherche d'une personne.
  - Liste des personnes.

### **Repository**
#### **PersonneDaoIntegrationTest**

- **Type** : Test d'intégration.
- **Description** : Vérifie les interactions avec la base de données via le repository `PersonneDao`.
- **Annotations utilisées** :
  - `@DataJpaTest` : Configure un environnement de test JPA minimal, avec une base de données en mémoire par défaut.
  - `@Autowired` : Injecte automatiquement les dépendances nécessaires, comme le repository `PersonneDao`.
  - `@Sql` : Permet d'exécuter des scripts SQL avant ou après les tests pour préparer les données.
- **Tests principaux** :
  - **Recherche de personnes par nom** : Utilise des scripts SQL pour insérer des données, puis vérifie que les résultats correspondent.
  - **Ajout d'une personne** : Vérifie que l'entité est correctement persistée dans la base de données.
  - **Liste des personnes** : Vérifie que toutes les entités sont récupérées correctement.

### **Controller**

#### **PersonneRestControllerUnitTest**

- **Type** : Test unitaire.
- **Description** : Vérifie les fonctionnalités de `PersonneRestController` en simulant les dépendances avec Mockito.
- **Annotations utilisées** :
  - `@ExtendWith(MockitoExtension.class)` : Permet d'intégrer le framework Mockito avec JUnit 5 pour la gestion des mocks.
  - `@InjectMocks` : Injecte automatiquement les dépendances simulées dans l'objet testé (`PersonneRestController`).
  - `@Mock` : Crée des mocks pour les dépendances de la classe testée, ici `PersonneManager`.
- **Tests principaux** :
  - **Recherche d'une personne existante** : Simule une réponse valide de `PersonneManager` et vérifie que la réponse HTTP est correcte.
  - **Recherche d'une personne inexistante** : Simule une exception `EntityNotFoundException` et vérifie que l'exception est correctement gérée.

#### **PersonneRestControllerSpringUnitTest**

- **Type** : Test d'intégration léger.
- **Description** : Vérifie les fonctionnalités de `PersonneRestController` avec un contexte Spring minimal, en simulant les dépendances avec Mockito.
- **Annotations utilisées** :
  - `@SpringBootTest(classes = {PersonneRestController.class})` : Charge un contexte Spring minimal contenant uniquement la classe spécifiée (`PersonneRestController`).
  - `@MockitoBean` : Fournit des mocks gérés par Spring pour les dépendances de la classe testée, ici `PersonneManager`.
  - `@Autowired` : Injecte automatiquement le contrôleur `PersonneRestController` dans le test.
- **Tests principaux** :
  - **Recherche d'une personne inexistante** : Simule une exception `EntityNotFoundException` levée par `PersonneManager` et vérifie que l'exception est correctement propagée.

#### **PersonneRestControllerIntegrationTest**

- **Type** : Test d'intégration.
- **Description** : Vérifie les endpoints REST de `PersonneRestController` en utilisant MockMvc pour simuler des requêtes HTTP.
- **Annotations utilisées** :
  - `@SpringBootTest` : Charge le contexte complet de l'application Spring pour les tests d'intégration.
  - `@AutoConfigureMockMvc` : Configure automatiquement un bean `MockMvc` pour simuler des requêtes HTTP sans démarrer un serveur réel.
  - `@Autowired` : Injecte automatiquement le bean `MockMvc` dans la classe de test.
- **Tests principaux** :
  - **Recherche de personnes** : Utilise `MockMvc` pour effectuer une requête GET sur l'endpoint `/personnes` et vérifie que la réponse contient le nombre attendu de personnes.
  - **Ajout d'une personne (valide)** : Simule une requête POST avec un payload JSON valide et vérifie que la réponse HTTP est `201 Created`.
  - **Ajout d'une personne (invalide)** : Simule une requête POST avec un payload JSON invalide et vérifie que la réponse HTTP est `400 Bad Request`.

#### **PersonneRestControllerIntegrationContainerMariadbTest**

- **Type** : Test d'intégration.
- **Description** : Vérifie les endpoints REST de `PersonneRestController` en utilisant un conteneur Docker MariaDB pour la base de données.
- **Annotations utilisées** :
  - `@SpringBootTest` : Charge le contexte complet de l'application Spring pour exécuter les tests d'intégration.
  - `@AutoConfigureMockMvc` : Configure automatiquement un bean `MockMvc` pour simuler des requêtes HTTP sans démarrer un serveur réel.
  - `@ContextConfiguration` : Permet de spécifier une configuration de contexte personnalisée. Ici, elle utilise `TestcontainersMariaDBConfig` pour initialiser un conteneur MariaDB via Testcontainers.
  - `@Autowired` : Injecte automatiquement le bean `MockMvc` dans la classe de test.

- **Approche des tests** :
  1. **Utilisation de Testcontainers** :
    - Un conteneur MariaDB est configuré via la classe `TestcontainersMariaDBConfig`. Cela permet d'exécuter les tests avec une base de données réelle dans un environnement isolé.
    - Les propriétés de connexion (URL, utilisateur, mot de passe) sont injectées dans le contexte Spring.

  2. **Simulation des requêtes HTTP** :
    - `MockMvc` est utilisé pour simuler des appels HTTP (GET, POST) vers les endpoints REST de `PersonneRestController`.
    - Les réponses sont validées en vérifiant le statut HTTP (`200 OK`, `400 Bad Request`, etc.) et le contenu JSON attendu.

  3. **Scénarios de test principaux** :
    - **Recherche de personnes** : Vérifie que l'endpoint `/personnes` retourne le nombre attendu de personnes.
    - **Ajout d'une personne (valide)** : Simule une requête POST avec un payload JSON valide et vérifie que la réponse est `201 Created`.
    - **Ajout d'une personne (invalide)** : Simule une requête POST avec un payload JSON invalide et vérifie que la réponse est `400 Bad Request`.


#### **PersonneRestControllerIntegrationContainerPostgresTest**

- **Type** : Test d'intégration.
- **Description** : Vérifie les endpoints REST de `PersonneRestController` en utilisant un conteneur Docker PostgreSQL pour la base de données.
- **Annotations utilisées** :
  - `@SpringBootTest` : Charge le contexte complet de l'application Spring pour exécuter les tests d'intégration.
  - `@AutoConfigureMockMvc` : Configure automatiquement un bean `MockMvc` pour simuler des requêtes HTTP sans démarrer un serveur réel.
  - `@ContextConfiguration` : Permet de spécifier une configuration de contexte personnalisée, ici `TestcontainersPostgresConfig`, pour initialiser un conteneur PostgreSQL via Testcontainers.
  - `@Autowired` : Injecte automatiquement le bean `MockMvc` dans la classe de test.
- **Tests principaux** :
  - **Recherche de personnes** : Utilise `MockMvc` pour effectuer une requête GET sur l'endpoint `/personnes` et vérifie que la réponse contient le nombre attendu de personnes.
  - **Ajout d'une personne (valide)** : Simule une requête POST avec un payload JSON valide et vérifie que la réponse HTTP est `201 Created`.
  - **Ajout d'une personne (invalide)** : Simule une requête POST avec un payload JSON invalide et vérifie que la réponse HTTP est `400 Bad Request`.

#### **PersonneRestControllerEndToEndTest**

- **Type** : Test end-to-end.
- **Description** : Vérifie le fonctionnement global des endpoints REST exposés par `PersonneRestController` en simulant des appels HTTP.

- **Annotations utilisées** :
  - `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)` : Charge le contexte complet de l'application Spring et démarre un serveur HTTP sur un port aléatoire pour les tests.
  - `@LocalServerPort` : Injecte le port aléatoire utilisé par le serveur HTTP dans le test.
  - `@Autowired` : Injecte automatiquement le bean `TestRestTemplate` pour effectuer des appels HTTP.

- **Approche des tests** :
  1. **Utilisation de `TestRestTemplate`** :
     - Permet d'effectuer des appels HTTP réels (GET, POST, etc.) vers les endpoints REST exposés par l'application.
     - Les réponses sont validées en vérifiant le statut HTTP (`200 OK`, `404 Not Found`, etc.) et le contenu JSON attendu.

  2. **Scénarios de test principaux** :
     - **Recherche d'une personne existante** : Vérifie que l'endpoint retourne les informations correctes pour une personne existante.
     - **Recherche d'une personne inexistante** : Vérifie que l'endpoint retourne une réponse `404 Not Found` pour une personne non trouvée.
     - **Appels à des URL incorrectes** : Vérifie que les requêtes mal formées ou les URL incorrectes retournent une réponse `400 Bad Request`.