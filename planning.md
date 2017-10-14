# ESB-SOA Team 3

# TravEL Expenses GReat Again! - Planning 

### Team composition
* Carlier Maxime
* Chevalier Mathias
* Eroglu Yasin
* Gning Khadim

### Web services consommés
* Flights : Team 8 [Github](https://github.com/HCScorp/soa)
* Hotels : Team 5 [Github](https://github.com/iliasnaamane/microservices-uns)
* Cars : Team 1 [Github](https://github.com/scipio3000/polytech-soa/)


### Stack Techno
* Enterprise Service Bus: [Apache Service Mix](http://servicemix.apache.org/) (7.0.1)
* Message Broker: [Apache ActiveMQ](http://activemq.apache.org/)
* Routing: [Apache Camel](http://camel.apache.org/) (2.19.2)


## Features

* Employé:
  * Demande un vol d’un aéroport A vers un aéroport B
  * Demander un hôtel à une localisation souhaitée
  * Demander une location de voiture à la localisation souhaitée
  * Envoyer la prévision de ses frais de transports
  * Envoyer factures pour les dépenses 
  * Justifier le dépassement de frais

* Manager :
  * Récupérer les prévisions de frais de déplacement des employés pour un déplacement professionnel pour approbation
  * Approuver ou refuser les demandes de remboursement des employés selon le montant de leur demande (automatique si les prix n'excède pas le seuil fixé par le tableau des dépenses autorisées)
  * Récupérer compte rendu résumant les frais de voyages (avec justificatifs)

* Système :
  * Demander automatiquement une justification à l’employé lors d’un dépassement de frais
  * Récupérer et valider les factures pour le remboursement des frais
  * Déclencher le processus de remboursement si la demande a été acceptée
  * Déclencher le processus d’archivage lorsque le voyage est complétée



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



