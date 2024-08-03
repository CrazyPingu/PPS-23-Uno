# Introduzione

Il progetto **Uno** mira alla creazione di un gioco di carte ispirato al famoso gioco UNO.

## Scopo del Gioco

L'obiettivo di ogni giocatore è terminare tutte le carte in mano per primo,
giocandole in base al colore o al numero dell'ultima carta giocata.

## Carte Semplici

Le carte semplici sono caratterizzate da un numero e un colore.
I colori disponibili delle carte sono rosso, giallo, verde e blu.
I numeri disponibili per ogni colore vanno da 0 a 9.
Tutte le carte risultano compatibili tra di loro se condividono lo stesso colore oppure hanno lo stesso numero.

## Carte Speciali

Nel gioco sono presenti delle carte speciali che non sono caratterizzate da un numero, ma
aggiungono vari elementi di strategia e sfida, tra cui:

- **Carte Pesca Due**: obbligano il prossimo giocatore a pescare due carte.
- **Carte Pesca Quattro**: obbligano il prossimo giocatore a pescare quattro carte e permettono di cambiare il colore in gioco.
- **Carte Salta Turno**: fanno saltare il turno al prossimo giocatore.
- **Carte Cambio Colore**: permettono di cambiare il colore in gioco.
- **Carte Cambio Giro**: invertono l'ordine dei turni.

Le carte Pesca Due, Salta Turno e Cambio Giro sono caratterizzate anche loro da uno dei quattro colori disponibili,
mentre le carte Pesca Quattro e Cambio Colore non hanno un colore associato e sono utilizzabili in qualsiasi momento.

## Modalità di Gioco

Ogni giocatore inizia la partita con un numero di carte in mano predefinito e il gioco procede in senso orario.
Una volta che un giocatore gioca una carta valida, il turno passa al giocatore successivo.
Se un giocatore non possiede in mano una carta valida, è obbligato a pescare una carta dal mazzo e terminare il suo turno.
Quando un giocatore si ritrova con una sola carta in mano, deve dichiarare "UNO" per avvertire gli altri giocatori.
Se un giocatore non dichiara "UNO" prima che il suo prossimo turno inizi, sarà penalizzato e dovrà pescare due carte.
Il gioco termina quando un giocatore gioca tutte le carte in mano.
Il gioco è progettato per essere giocato da un giocatore contro tre avversari controllati dal computer.

---

[Indice](../index.md) | [Capitolo Successivo](./1-Processo.md)