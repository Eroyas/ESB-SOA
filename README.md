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
##### Hotel alternatif -> Resource
Le service de reservation d'hôtel est bien adapté pour être un service Ressource dans la messure ou on expose que des ressources que le client va venir consulter.  
###### Les fonctionnalités attendues : 
* Sélectionner une ville
* Sélectionner un hotel
* Sélectionner une date d’arrivée
* Sélectionner une date de départ
* (Sélectionner le type de chambre)
* (Sélectionner le trie par prix)

###### Les interfaces présentes :
* public Response getHotels() :
    * Path : get sur le path @Path("/hotels")
    * Utilisation : http://localhost:8080/tta-car-and-hotel/hotels
    * Description : liste tout les hôtels présent en France.
* public Response getHotelsByName(@PathParam("name") String name) :
    * Path : get sur le path @Path("name/{name}")
    * Utilisation : http://localhost:8080/tta-car-and-hotel/hotels/name/Ibis
    * Description : liste tout les hôtels d'une enseigne précise présent en France, comme par exemple dans le cas d'utilisation ci-dessus, tout les Ibis de France.
* public Response getHotelsByCity(@PathParam("city") String city) :
    * Path : get sur le path @Path("city/{city}")
    * Utilisation : http://localhost:8080/tta-car-and-hotel/hotels/city/Paris
    * Description : liste tout les hôtels présent dans une ville donné, comme par exemple dans le cas d'utilisation ci-dessus, tout les hôtels de Paris.
* public Response getHotelReservation(@PathParam("city") String city,
                                    @PathParam("name") String name,
                                    @PathParam("arrival") String arrival,
                                    @PathParam("departure") String departure) :
    * Path : get sur le path @Path("{city}/{name}/{arrival}/{departure}")
    * Utilisation : http://localhost:8080/tta-car-and-hotel/hotels/Paris/Ibis/01-10-2017/03-10-2017
    * Description : liste un hôtel d'une enseigne précise et présent dans une ville donné en indiquant la date de l'arrivée et la date de départ du client. Comme par exemple dans le cas d'utilisation ci-dessus, l'hôtel Ibis de Paris pour les dates 01-10-2017 et 03-10-2017. 
    * Remarque : pour l'instant il y a de la redondance dans les noms des hôtels par vile car nous avons mis en place une base de donnée de rapide et facile d'utilisation pour tester nos services. Donc la requête retourne pour l'instant plusieurs hôtels. Par la suite nous auront un nom unique d'hôtel par ville comme par exemple un « Ibis Paris Nord » ou « Ibis Paris sud » et etc.

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
