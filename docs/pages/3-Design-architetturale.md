# Design architetturale

Design architetturale (architettura complessiva, 
descrizione di pattern architetturali usati, 
eventuali componenti del sistema distribuito,
scelte tecnologiche cruciali ai fini architetturali -- 
corredato da pochi ma efficaci diagrammi)

## Architettura complessiva
L'architettura complessiva è mostrata e riepilogata nel successivo diagramma UML:

![Diagramma UML](../uml/design_high_level.png)

Di seguito una breve descrizione per ogni componente illustrato:

- Card -> Elemento principale del gioco, rappresenta una carta singola.
  - SimpleCard -> Una carta semplice, che è caratterizzata da un colore e un numero.
  - SpecialCard -> Carta speciale, che ha un effetto particolare a seguito della propria giocata.

- Deck -> Modello che rappresenta una collezione di carte, semplici e speciali, con la possibilità di pescare da esso.
- Hand -> Modello che anch'esso rappresenta una collezione di carte, minore di quella del mazzo. Quest'ultimo componente è la
base di ogni giocatore e bot, in quanto rappresenta la mano di carte che ogni giocatore possiede durante una partita.

- Player -> Componente che rappresenta un giocatore all'interno del gioco, che contiene una mano di carte gestibili durante una partita.
- BotPlayer -> Unità fondamentale che rappresenta un bot generico, anch'esso presenta una mano gestibile.
  - EasyBotPlayer -> Implementazione di un bot con difficoltà facile, che come regola base gioca sempre la prima carta valida che trova.
  - HardBotPlayer -> Implementazione di un bot con difficoltà difficile, che come regola base gioca sempre una carta speciale se possibile, altrimenti gioca la prima carta valida che trova.

- GameLoop -> Componente model che si occupa interamente di gestire e modellare la logica del gioco e il rispetto
delle regole.

- GameController -> Intermediario controller tra la GUI e il GameLoop, si occupa di gestire e sincronizzare gli eventi,
le azioni e le interazioni con l'utente che avvengono nel tempo.

- Gui -> Interfaccia grafica in grado di mostrare le numerose interfacce
presenti all'interno del programma quando viene richiesto.



## Pattern architetturali
Il pattern architetturale utilizzato per la creazione di questo progetto è MVC (Model-View-Controller).

Questo pattern è stato scelto in quanto permette di separare
la logica di business (Model) dalla interfaccia (View)
e dalla gestione degli eventi (Controller).
Questa separazione permette di rendere il codice più modulare e mantenibile,
in quanto ogni componente ha un compito ben definito e indipendente dagli altri.

All'interno del progetto, i package sono stati distribuiti proprio seguendo questo pattern architetturale:
- Controller
  - Contiene gli elementi che si occupano 
di gestire gli eventi del gioco e le azioni dei bot e dell'utente
- Model
  - Contiene i componenti principali necessari per la gestione del gioco
- View
  - Contiene il necessario a formare quelle che saranno le interfacce grafiche del gioco

## Scelte tecnologiche
Per la realizzazione di questo progetto, non risulta essere necessario l'impiego di alcuna
tecnologia o framework particolare, in quanto si ritiene che il progetto si possa realizzare grazie
all'utilizzo di Scala 3.x.
L'unica tecnologia particolare da 
utilizzare per il completamento del progetto è quella per l'implementazione di un'interfaccia grafica.

---

[Indice](../index.md) | [Capitolo Precedente](./2-Requisiti.md) | [Capitolo Successivo](./4-Design-dettaglio.md)