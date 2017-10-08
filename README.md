# ESB-SOA Team 3

# The Virtual Travel Agency

### Team composition
   * Carlier Maxime
   * Chevalier Mathias
   * Eroglu Yasin
   * Gning Khadim

Github repository: https://github.com/Eroyas/ESB-SOA

## Services exposés

Le système que nous proposons est un ensemble de services indépendants les uns des autres.
Aucun service ne fait appel à un autre service à l'intérieur de lui-même.

Le scope de nos services est respectivement:
* Services de recherche de vol
* Services de recherche d'hotel
* Services de recherche de location de voiture
* Services de gestion de réservation

Ils sont décrits plus en détails ci-dessous.

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

###### Les interfaces présentes :
* public Response getHotels() :
    * Path : on fait un get sur le path @Path("/hotels")
    * Utilisation : http://localhost:9080/tta-car-and-hotel/hotels
    * Description : liste tous les hôtels présent en France.
* public Response getHotelsByName(@PathParam("name") String name) :
    * Path : on fait un get sur le path @Path("name/{name}")
    * Utilisation : http://localhost:9080/tta-car-and-hotel/hotels/name/Ibis
    * Description : liste tout les hôtels d'une enseigne précise présent en France, comme par exemple dans le cas d'utilisation ci-dessus, tous les Ibis de France. 
* public Response getHotelsByCity(@PathParam("city") String city) :
    * Path : on fait un get sur le path @Path("city/{city}")
    * Utilisation : http://localhost:9080/tta-car-and-hotel/hotels/city/Paris
    * Description : liste tout les hôtels présent dans une ville donné, comme par exemple dans le cas d'utilisation ci-dessus, tous les hôtels de Paris.
* public Response getHotelReservation(@PathParam("city") String city,
                                    @PathParam("name") String name,
                                    @PathParam("arrival") String arrival,
                                    @PathParam("departure") String departure) :
    * Path : on fait un get sur le path @Path("{city}/{name}/{arrival}/{departure}")
    * Utilisation : http://localhost:9080/tta-car-and-hotel/hotels/Paris/Ibis/01-10-2017/03-10-2017
    * Description : donne un hôtel d'une enseigne précise et qui est présent dans une ville donné en indiquant la date de l'arrivée et la date de départ du client. Comme par exemple dans le cas d'utilisation ci-dessus, l'hôtel Ibis de Paris pour les dates de 01-10-2017 et 03-10-2017. 
    * Remarque : pour l'instant il y a de la redondance dans les noms des hôtels par vile car nous avons mis en place une base de donnée de façon rapide et facile d'utilisation pour tester nos services. Donc la requête retourne pour l'instant plusieurs hôtels. Par la suite nous auront un nom unique d'hôtel par ville comme par exemple un « Ibis Paris Nord » ou « Ibis Paris sud » et etc.
    
Le retour des interfaces : données transitant en JSON.

### Car handler

* Location de véhicule alternatif -> Resource
* Sélectionner une agence de location au départ
* (Sélectionner une agence de location à l’arrivée)
* Sélectionner une durée
* (Type de voiture)

### Booking management

Le choix du paradigme "document" se justifie ici par la potentielle volatilité des informations s'y trouvant.
En effet, selon la recherche et les critères, un objet vol n'est pas défini par les mêmes attributs et il faut donc potentiellement servir un service très adaptable au niveau des données manipulées.

Les données transitant par JSON, le traitement côté back-end en est facilité, en effet, si un champ est présent en trop, il ne sera simplement pas traité contrairement à un service à contrat strict qui générerait une erreur.

Pour ce service, nous avons fait l'hypothèse d'un service de réservation à une entreprise.

**Réservations: -> Document**
  * Soumettre une réservation
    * Récupérer tous les détails d’un voyage (Vol + Hôtel + Voiture + Identité)
    * Soumettre la réservation contenant ces détails au système de réservation

  * Valider une réservation
    * Valider une réservation par ID

  * Rejeter une réservation
    * Rejeter une réservation par ID

  * Récupérer une réservation
    * Récupéere les informations et détails d'une réservation en recherchant par ID
