# ApplicationControllerAdvice

La classe `ApplicationControllerAdvice` est une classe de gestion centralisée des exceptions dans l'application. Elle utilise l'annotation `@RestControllerAdvice` pour intercepter les exceptions levées par les contrôleurs REST et fournir des réponses HTTP appropriées.

## Fonctionnalités principales

- **Gestion des exceptions globales** : Cette classe capture les exceptions spécifiques et renvoie des réponses HTTP avec des statuts adaptés.
- **Réduction de la duplication de code** : En centralisant la gestion des erreurs, elle évite de répéter la logique de gestion des exceptions dans chaque contrôleur.

## Méthodes et gestion des exceptions

### **methodArgumentTypeMismatchException**
- **Exception gérée** : `MethodArgumentTypeMismatchException`
- **Description** : Se déclenche lorsqu'un argument de méthode ne correspond pas au type attendu.
- **Réponse HTTP** : `400 Bad Request`

---

### **entityNotFoundException**
- **Exceptions gérées** : 
  - `NoResourceFoundException`
  - `EntityNotFoundException`
- **Description** : Se déclenche lorsqu'une ressource ou une entité demandée n'est pas trouvée.
- **Réponse HTTP** : `404 Not Found`

---

### **personneException**
- **Exceptions gérées** : 
  - `PersonneException`
  - `MethodArgumentNotValidException`
- **Description** : 
  - `PersonneException` : Exception personnalisée liée à la gestion des entités "Personne".
  - `MethodArgumentNotValidException` : Se déclenche lorsque les arguments d'une méthode ne respectent pas les contraintes de validation.
- **Réponse HTTP** : `400 Bad Request`

## Avantages

- **Centralisation** : Simplifie la gestion des erreurs en regroupant toutes les exceptions dans une seule classe.
- **Clarté** : Facilite la maintenance et améliore la lisibilité du code.
- **Personnalisation** : Permet de définir des réponses spécifiques pour chaque type d'exception.

## Annotations utilisées

- **`@RestControllerAdvice`** : Indique que cette classe fournit des conseils globaux pour les contrôleurs REST.
- **`@ExceptionHandler`** : Associe une méthode à un ou plusieurs types d'exceptions spécifiques.