# ESB-SOA Team 3

# The Virtual Travel Agency

### Team composition
   * Carlier Maxime
   * Chevalier Mathias
   * Eroglu Yasin
   * Gning Khadim

Github repository: https://github.com/Eroyas/ESB-SOA

## Services exposés

En l'état la description des services qui suit est uniquement informative et sera nettement plus détaillée par la suite.

### Flight handler

* Afficher un panel de vol alternatif -> RPC
* Sélectionner un aéroport de départ et d’arrivée
* Sélection dans une liste statique / BDD
* Générateur fourni
* (Sélectionner aller-retour / aller simple)
* Sélectionner une date de départ
* (Sélectionner une date de retour)
* (Sélectionner le nombre de voyageur)
* (Sélectionner avec/sans escale)
* (Sélectionner le tri par prix)

### Hotel handler

* Hotel alternatif -> Resource
* Sélectionner une ville
* Sélectionner une date d’arrivée
* Sélectionner une date de départ
* (Sélectionner le nombre d’occupant)
* (Sélectionner le type de chambre)
* (Sélectionner le trie par prix)

### Car handler

* Location de véhicule alternatif -> Resource
* Sélectionner une agence de location au départ
* (Sélectionner une agence de location à l’arrivée)
* Sélectionner une durée
* (Type de voiture)

### Booking management

**Itinéraires: -> Document**
  * Soumettre un itinéraire pour validation -> Document
    * Récupérer tous les détails d’un voyage (Vol + Hôtel + location)
    * Soumettre un récapitulatif d’un voyage pour approbation à un manager
    * Valider un itinéraire de voyage

  * Envoyer un récapitulatif par e-mail -> Document
    * Soumettre un récapitulatif au client par mail (trouver une raison d’être de ce service en tant que tel)
