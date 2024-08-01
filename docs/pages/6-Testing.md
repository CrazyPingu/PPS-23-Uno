# Testing

## Tecnologie usate
Per quanto riguarda il testing automatizzato, sono stati effettuati principalmente unit test tramite la libreria scalatest.

## Testing Automatizzato
Gran parte del modello del dominio è stato sviluppato attraverso Test Driven Development. 
I test sono stati scritti prima dell'implementazione del codice, questo ha garantito che il codice sviluppato 
successivamente fosse corretto e funzionante.
Grazie a questa strategia, è stato possibile garantire che il codice fosse testato in modo esaustivo, garantendo una maggiore sicurezza
e allo stesso momento una minore probabilità di errori in fase di sviluppo dell'intero progetto.


## Testing non automatizzato
I test automatici della GUI non sono stati sviluppati a causa della complessità
e della natura interattiva dell'interfaccia grafica. In alternativa, è stato 
eseguito un approfondito testing manuale per garantire che tutte le funzionalità 
dell'interfaccia utente funzionino correttamente, includendo
la verifica della corretta visualizzazione degli elementi del gioco come carte, mani o mazzo,
l'interazione con i vari pulsanti e l'aggiornamento in tempo reale dello stato del gioco.

(../index.md) | [Capitolo Precedente](./5-Implementazione.md) | [Capitolo Successivo](./7-Conclusioni.md)