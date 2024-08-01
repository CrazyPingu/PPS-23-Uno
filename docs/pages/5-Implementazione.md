# Implementazione

Implementazione (per ogni studente, una sotto-sezione descrittiva 
di cosa fatto/co-fatto e con chi, e descrizione di aspetti implementativi importanti 
non già presenti nel design, come ad esempio relativamente 
all'uso di meccanismi avanzati di Scala)

Il seguente contenuto è da dividere in sottosezioni:


## Montanari Nicola

### Hand

Lo sviluppo della classe `Hand` è stato fondamentale per la gestione delle carte in gioco.

Per la sua implementazione è stato definito un nuovo `trait` che estende `ArrayBuffer[Card]`
così da garantire e gestire i metodi necessari per la gestione degli elementi al suo interno.

Questo componente è strutturato per garantire le funzionalità fondamentali per la gestione delle carte in gioco.
E' stata implementata una metodologia di sviluppo che minimizzasse la complessità affinche il codice possa essere
il più chiaro e leggibile possibile, sopratutto per questo componente fondamentale per questo progetto.

Ogni metodo necessario si basa sulle funzionalità offerte da `ArrayBuffer` e viene implementato 
all'interno del trait come mostrato successivamente:


```scala
trait Hand extends ArrayBuffer[Card]:

  def addCard(card: Card): Unit = this += card

  def removeCard(card: Card): Boolean =
    if this.contains(card) then
      this -= card
      true
    else false

  def getCardCount: Int = this.size

  def hasCard(card: Card): Boolean = this.contains(card)

  def clearHand(): Unit = this.clear
  
```

### Player

Per suddividere i giocatori umani dai bot, è stata definita una classe `Player` che estende `Hand`.
Il controller del gioco gestirà in maniera diretta il giocatore umano differentemente da come gestirà i bot.

L'implementazione del Player non è altro che un oggetto che estende `Hand`, in quanto presenta già tutte
le funzionalità necessarie per gestire i comportamenti di un giocatore all'interno di una partita.

```scala
object Player extends Hand
```
### BOTS
INSERIRE QUI SCHEMA UML

### BotPlayer e BotPlayerImpl

Per gestire i bot all'interno del gioco, è stata definito un trait generale `BotPlayer` che estende anch'esso `Hand` così da
ereditare tutte le sue funzionalità, proprio come l'object `Player`.

Il trait `BotPlayer` presenta due metodi fondamentali per il gioco:
- `chooseCardToUse` che ritorna la carta scelta dal BotPlayer per essere giocata, se possibile e valida.
- `chooseColor` che automatizza e ritorna il colore scelto dal BotPlayer in caso di necessità.

```scala

trait BotPlayer extends Hand:
  def chooseCardToUse(card: Card): Option[Card]
  def chooseColor(): Color
  
 ```

Per implementare i bot, il trait `BotPlayer` è stato esteso in una classe astratta `BotPlayerImpl` 
che implementa i metodi precedenti, oltre
che a definire un metodo `isCompatible` che verifica la compatibilità tra due carte, 
così da renderlo disponibile ai propri figli.

Il metodo `isCompatible` è stato definito poi esternamente e contiene la logica per la verifica.
Questo metodo ritorna un booleano che indica se la carta selezionata è compatibile con la carta al centro del tavolo.

Il medoto `chooseColor` è stato implementato in modo da selezionare il colore più frequente tra le carte in mano
così da ritornarlo quando il controller lo richiede, automatizzando così la scelta.
Questo metodo è generale per tutti i bot implementabili e quindi condiviso all'interno di questa classe.

Il metodo `chooseCardToUse` viene invece modellato in maniera separata e diversa per ogni tipologia di 
bot, in quanto la scelta della carta viene infuenzata in
base alla strategia di gioco che il bot deve seguire.

A seguire l'implementazione di `BotPlayerImpl`:

```scala
abstract class BotPlayerImpl extends BotPlayer:

protected def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
Compatibility.isCompatible(selectedCard, centerCard)

def chooseColor(): Color =
val colorCounts = mutable.Map[Color, Int]().withDefaultValue(0)
    for card <- this do colorCounts(card.color) += 1
    if colorCounts.isEmpty then return Random.shuffle(Color.values.filterNot(_ == Color.Black).toList).head
    val sortedColors = colorCounts.toSeq.sortBy(-_(1))
    val mostFrequent = sortedColors.head(0)
    if mostFrequent == Color.Black then Random.shuffle(Color.values.filterNot(_ == Color.Black)).toList.head
    else mostFrequent
 ```
### EasyBotPlayerImpl
`EasyBotPlayer` è un `BotPlayerImpl` che gioca in maniera casuale, senza particolari strategie o logiche.

In questa implementazione, è stato necessario solo la ridefinizione del
metodo `chooseCardToUse` in modo da poter effettuare una scelta casuale tra le carte in mano.

Per implementare questa scelta, il metodo `chooseCardToUse` ricerca le carte compatibili con quella al centro del tavolo, 
se ne esistono, ne seleziona una casuale e la ritorna sfruttando `Some`, altrimenti ritorna `None`.

```scala
class EasyBotPlayerImpl extends BotPlayerImpl:
  override def chooseCardToUse(centerCard: Card): Option[Card] =
    val compatibleCards = this.filter(
      c => isCompatible(c, centerCard)
    )
    if compatibleCards.nonEmpty then
      val card = compatibleCards.head
      Some(card)
  else None
```
### HardBotPlayerImpl
`HardBotPlayer` è un `BotPlayerImpl` che gioca in maniera più strategica e intelligente, cercando di massimizzare le
proprie possibilità di vittoria.

In questa implementazione, è stato necessario solo la ridefinizione del
metodo `chooseCardToUse`, con lo scopo di selezionare le SpecialCard prima delle altre, 
in modo da danneggiare gli avversari e ottenere un vantaggio.

Per implementare questa scelta, il metodo `chooseCardToUse` ricerca le carte compatibili con quella 
al centro del tavolo,
se ne esistono, ricerca all'interno di questa lista se sono presenti delle carte speciali.
Nel caso di un riscontro positivo, viene scelta e ritornata la prima carta speciale trovata.
Altrimenti, se non sono presenti carte speciali nelle carte compatibili,
ne seleziona una casuale e la ritorna sfruttando `Some`.
Nel caso invece che non ci siano carte compatibili, ritorna `None`.

```scala
class HardBotPlayerImpl extends BotPlayerImpl:
  override def chooseCardToUse(centerCard: Card): Option[Card] =
    val compatibleCards = this.filter(
      c => isCompatible(c, centerCard)
    )
    if compatibleCards.nonEmpty then
      val specialCards = compatibleCards.filter(
        c => c.isInstanceOf[SpecialCard]
      )
      if specialCards.nonEmpty then
        val card = specialCards.head
        Some(card)
    else
      val card = compatibleCards.head
      Some(card)
  else None
```

### Compatibility
Con lo scopo di rispettare regolamentare la compatibilità delle carte in gioco,
è stato sviluppato un metodo
`isCompatible` che verifica se una carta è compatibile con una determinata carta 
(quella attualmente in gioco al centro del tavolo).

Questa funzione è stata volutamente implementata esternamente e in maniera più semplice e chiara possibile.
Questa funzione regola la compatibilità all'interno dell'intero flusso di gioco.
Nel caso futuro in cui si volessero cambiare le regole di gioco, basta effettuare delle modifiche a questo
metodo per variare la compatibilità tra le carte in gioco in maniera facile e veloce.

La verifica avviene in base a tutti i criteri possibili:
- Tipo di carta (SimpleCard o SpecialCard)
- Colore (Compreso anche il colore `BLACK`)
- Numero (se presente)

Durante la verifica vengono comparati i vari criteri.
Appena viene rilevato un criterio compatibile, la funzione ritorna `true`.
Nel momento in cui si finiscono i criteri da controllare, il metodo ritorna `false`.

```scala
def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
(selectedCard, centerCard) match
case (s, c) if s.color == Color.Black || c.color == Color.Black        => true
case (s, c) if s.color == c.color                                      => true
case (s: SimpleCard, c: SimpleCard) if s.num == c.num                  => true
case (s, c) if s.getClass == c.getClass && !s.isInstanceOf[SimpleCard] => true
case _                                                                 => false
```

### Tutorial (GUI)
E' stata sviluppata una GUI molto semplice per il tutorial del gioco.

Il tutorial è stato implementato in modo da guidare l'utente e mostrare tutte le possibili interazioni
all'interno del gioco tramite una serie di immagini e testi.

Per sviluppare le varie schermate del tutorial, è stata sviluppata una classe generica
`GeneralTutorialSlide` che contiene un'immagine, un titolo e una descrizione.

```scala
class GeneralTutorialSlide(val image: Image, val title: String, val description: String)
```

Successivamente, è stata implementata una factory che generi le varie schermate del tutorial quando necessario

```scala
class TutorialSlideFactory:
    def createCardSlide(): GeneralTutorialSlide =
        new GeneralTutorialSlide(
        ImageHandler.chooseCard,
        "How to choose a Card",
        "Click a card in your hand to play it when it's your turn"
        )
    
    def createDeckSlide(): GeneralTutorialSlide =
        new GeneralTutorialSlide(
        ImageHandler.drawCard,
        "How to Draw a Card",
        "Draw a card from the deck if you can’t play any of your current cards"
        )
    
    def createCompatibilitySlide(): GeneralTutorialSlide =
        new GeneralTutorialSlide(
        ImageHandler.cardCompatibility,
        "Card Compatibility",
        "Play cards that match the color or number of the top card on the discard pile"
        )
    
    def createUnoSlide(): GeneralTutorialSlide =
        new GeneralTutorialSlide(
        ImageHandler.UnoPress,
        "How to call UNO",
        "Announce \"UNO\" when you have only one card left to avoid a penalty"
        )
    
    def createWinLoseSlide(): GeneralTutorialSlide =
        new GeneralTutorialSlide(
        ImageHandler.tutorialWin,
        "How to Win a Game",
        "Be the first to play all your cards to win the game"
        )
    
    def createSpecialCardSlide(): GeneralTutorialSlide =
        new GeneralTutorialSlide(
        ImageHandler.specialCards,
        "Special Cards",
        "Use special cards like Skip, Wild, Reverse, Draw +2 or +4 to alter the game and challenge your opponents"
        )
```

La precedente Factory viene poi sfruttata nella classe principale della gui, che procederà a generare ogni singola
slide e inserirla poi in un Array, così da poterle visualizzare e gestire in sequenza.
La scelta di questa struttura è stata fatta per garantire una maggiore scalabilità e facilità di gestione delle varie slide,
in un futuro in cui si volessero aggiungere nuove schermate dopo l'aggiunta di nuove funzionalità.


```scala
Insert code here
```

- Interfaccia Grafica
  - Tutorial

### Componenti Sviluppati insieme a Samuele De Tuglie

- GameLoop
- GameController

## Samuele De Tuglie

- Card
  - SimpleCard
  - SpecialCard
    - DrawCard
    - SkipCard
    - ReverseCard
    - ChangeColor
  - Deck

- PageController
  - Main Menu
  - Game Board
    - Cells
  - ChangeColorGui
  - Frame


## Pablo Sebastian Vargas Grateron

- Settings
  - SettingsController

- Achievements
  - AchievementController

Gui di entrambe


---

[Indice](../index.md) | [Capitolo Precedente](./4-Design-dettaglio.md) | [Capitolo Successivo](./6-Testing.md)