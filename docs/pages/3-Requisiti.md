# Requisiti e specifiche di progetto

## Business

- Creare una versione digitale del gioco di carte "Uno" per fornire un'esperienza di gioco divertente e coinvolgente.
- Offrire diverse opzioni di gioco contro bot, con diverse difficoltà.
- Fornire un'interfaccia utente intuitiva per migliorare l'esperienza di gioco.

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

- Iniziare una nuova partita selezionando l'opzione dal menù principale.
- Giocare una carta dalla propria mano durante il proprio turno.
- Accedere al tutorial per apprendere le regole del gioco.
- Impostare i bot con diversi livelli di handicap prima di iniziare la partita.
- Modificare le regole custom vincolate (es. numero di carte iniziali, difficoltà dei bot).

### Di sistema

- Gestire il game loop, determinando l'ordine dei turni e applicando le regole di gioco ad ogni mossa.
- Gestire il comportamento dei bot, determinando la loro strategia di gioco in base agli handicap assegnati.
- Mantenere lo stato corrente del mazzo e della pila di scarto, aggiornando le mani dei giocatori ad ogni turno.

## Non funzionali

- L'interfaccia deve essere intuitiva e facilmente navigabile, con chiare indicazioni su come eseguire le azioni di gioco.
- Il codice deve essere strutturato in modo modulare per facilitare l'aggiunta di nuove funzionalità (es. nuove regole custom, skin, ecc.).

## Di implementazione

- Il gioco sarà implementato usando Scala 3.x
- Utilizzo di JDK 21

## Opzionali



---

[Indice](../index.md) | [Capitolo Precedente](./2-Processo.md) | [Capitolo Successivo](./4-Design-architetturale.md)