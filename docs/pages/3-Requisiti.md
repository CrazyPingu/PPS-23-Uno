# Requisiti e specifiche di progetto

## Business

L'obbiettivo del progetto è quello di creare una versione digitale del gioco di carte "Uno"
fornendo all'utente un'esperienza di gioco divertente e coinvolgente, offrendo diverse opzioni di gioco
contro dei bot a cui è possibile assegnare diverse difficoltà, grazie a un'interfaccia utente intuitiva
per migliorare ulteriormente l'esperienza di gioco.

## Modello di dominio

Il gioco "Uno" è un gioco di carte in cui i giocatori competono per essere i primi a esaurire tutte le carte nella propria mano. 
Il gioco si svolge in turni, durante i quali i giocatori giocano una carta dalla propria mano che corrisponde per colore o valore 
all'ultima carta giocata sul tavolo. Oltre alle carte numeriche, ci sono carte speciali che possono cambiare il colore, invertire 
il turno, saltare il turno del prossimo giocatore, o costringere gli avversari a pescare carte aggiuntive. Il primo giocatore a 
esaurire tutte le carte vince la partita. Se un giocatore rimane con una sola carta, deve dichiarare "Uno" per evitare penalità.

- **Carta:** Entità rappresentante una carta del mazzo di "Uno", con attributi come colore, valore, e tipo speciale (es. +2, +4, cambio di colore).
- **Mazzo:** Collezione di carte, contenente tutte le carte disponibili per la partita.
- **Giocatore:** Entità rappresentante un partecipante alla partita, che può essere un giocatore umano o un bot.
- **Mano:** Collezione di carte attualmente in possesso di un giocatore.
- **Partita:** Entità che rappresenta una singola sessione di gioco, contenente informazioni su giocatori, stato del gioco, e carte giocate.

## Funzionali

### Utente

- Gli utenti dovranno interagire con il sistema tramite un'interfaccia grafica (GUI).
- Gli utenti possono interagire col sistema effettuando diverse azioni:
  - Accedere al tutorial per apprendere le regole del gioco.
  - Modificare le regole personalizzate del gioco:
    - Impostare il numero di carte iniziali di ogni giocatore.
    - Impostare la difficoltà dei bot.
    - Impostare il livello di Handicap rispetto ai bot.
  - Visualizzare tutti gli achievement disponibili.
  - Individuare gli achievement sbloccati o da sbloccare.
  - Resettare gli achievement di gioco.
  - Iniziare una nuova partita selezionando l'opzione dal menù principale.
    - Durante una partita, è possibile effettuare le seguenti azioni:
      - Giocare una carta dalla propria mano durante il proprio turno.
      - Pescare una carta dal mazzo.
      - Dichiarare "UNO" quando si rimane con una sola carta in mano.
- Gli utenti possono visualizzare i diversi fattori che rappresentano lo stato attuale della partita:
    - Numero di carte in mano per ogni giocatore.
    - Il giocatore di turno.
    - La carta giocata più recentemente.
    - L'ordine dei turni.
    - Lo status del pulsante UNO.
    - Lo stato finale della partita.


### Di sistema

Il sistema software deve essere in grado di gestire correttamente l'intero game loop del gioco,
determinando lo stato della partita,
l'ordine dei turni e l'applicazione e il rispetto delle regole di gioco ad ogni mossa sia 
da parte del giocatore che da 
parte dei bot.
Inoltre è necessario che il sistema sia in grado di gestire la presenza di bot,
determinando la loro strategia di gioco in base
alle impostazioni di gioco e allo stato attuale della partita.
Mantenere lo stato corrente di ogni mano del giocatore, aggiornando le mani dei giocatori,
gestendo il mazzo e lo stato 
dell'ultima carta giocata in modo da garantire l'integrità della partita ad ogni turno.

## Non funzionali
L'interfaccia deve essere intuitiva e facilmente navigabile,
con chiare indicazioni su come eseguire le azioni di gioco. 
Il codice deve essere strutturato in modo modulare per facilitare 
l'aggiunta di nuove funzionalità, cambio di regole o aggiornamenti.

## Di implementazione

- Il gioco sarà implementato usando Scala 3.x
- Utilizzo di JDK 21

## Opzionali
 - Ulteriori regole personalizzate vincolate
   - Livello di Difficoltà dei Bot
   - Numero di carte iniziali per ogni giocatore

---

[Indice](../index.md) | [Capitolo Precedente](./2-Processo.md) | [Capitolo Successivo](./4-Design-architetturale.md)