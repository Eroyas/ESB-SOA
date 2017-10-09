# ESB-SOA Team 3

# TravEL Expenses GReat Again! - Planning 

### Team composition
   * Carlier Maxime
   * Chevalier Mathias
   * Eroglu Yasin
   * Gning Khadim

### Web services utilisés
   * Flights : Team 8
   * Hotels : Team 2
   * Cars : Team 1


### Stack Technologique
    * Enterprise Service Bus: [Apache Service Mix](http://servicemix.apache.org/) (7.0.1)
    * Message Broker: [Apache ActiveMQ](http://activemq.apache.org/)
    * Routing: [Apache Camel](http://camel.apache.org/) (2.19.2)


## Fonctionnalités

Employé:
Demande un vol d’un aéroport A vers un aéroport B
Demander un hôtel à une localisation souhaitée
Demander une location de voiture à la localisation souhaitée
Envoyer la prévision de ses frais de transport
Envoyer factures pour les dépenses 
Justifier le dépassement si le manager le demande car le prix excède légèrement le seuil autorisé pour un déplacement professionnel

Manager :
Récupérer les prévisions de frais de déplacement des employés pour un déplacement professionnel
(Approuver la prévision)
Approuver ou refuser les demandes de remboursement des employés selon le montant de leur demande (automatique si les prix n'excède pas le prix fixé par le tableau des dépenses autorisés pour une activitée)
Récupérer compte rendu résumant les frais de voyages (avec justif)
Système :
Si refusé pour cause de frais supérieurs au seuil, demander une justification à l’employé
Si la demande est approuvée, récupérer les factures pour le remboursement 
Déclencher le processus de remboursement si la demande a été accepté
Déclencher le processus d’archivage
Déclencher le processus de notification si le remboursement est effectué



## Planning

### Week 41

* Récupérer le vol le moins cher entre 2 aéroports
* Récupérer l'hôtel le moins cher à la localisation souhaitée
* Récupérer la location la moins chère à la localisation souhaitée

### Week 42

* Permettre à un employés de soumettre un plan de voyage avec :
  * Le vol le moins cher possible
  * L’hôtel le moins cher possible
  * La location la moins chère possible

* Permettre à un manageur de valider ou refuser un plan de voyage
  * Approbation manuelle du vol par le manager

* Permettre à un employé de soumettre manuellement (saisir) une dépense

### Week 43 (MVP Review)

* Approuver les frais de vie d’un voyage par rapport à un seuil fixe automatiquement
* Archiver l’ensemble des transactions liées au voyage
* Procéder au remboursement des frais dès lors que les dépenses sont approuvées

### Week 44

* Calcul du seuil pour les frais de vie depuis les données gouvernementales
* Justification pour un employé d’un dépassement sur les frais de vie
* Le manager peut valider ou non la justification du dépassement de frais
* Si la justification n’est pas validée, le remboursement maximal (jusqu’au seuil) est accordé

### Week 45 (final delivery)

* Joindre une facture avec la soumission d’une dépense
* Vérification et saisie automatique avec OCR des factures jointes