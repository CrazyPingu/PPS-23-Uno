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
È stata implementata una metodologia di sviluppo che minimizzasse la complessità affinché il codice possa essere
il più chiaro e leggibile possibile, sopratutto per questo componente fondamentale per questo progetto.

Ogni metodo necessario si basa sulle funzionalità offerte da `ArrayBuffer` e viene implementato 
all'interno del trait come mostrato successivamente:


```scala 3
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
ereditare tutte le sue funzionalità, proprio come la classe `Player`.

Il trait `BotPlayer` presenta due metodi fondamentali per il gioco:
- `chooseCardToUse` che ritorna la carta scelta dal BotPlayer per essere giocata, se possibile e valida.
- `chooseColor` che automatizza e ritorna il colore scelto dal BotPlayer in caso di necessità.

```scala 3

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

Il metodo `chooseColor` è stato implementato in modo da selezionare il colore più frequente tra le carte in mano
così da ritornarlo quando il controller lo richiede, automatizzando così la scelta.
Questo metodo è generale per tutti i bot implementabili e quindi condiviso all'interno di questa classe.

Il metodo `chooseCardToUse` viene invece modellato in maniera separata e diversa per ogni tipologia di 
bot, in quanto la scelta della carta viene influenzata in
base alla strategia di gioco che il bot deve seguire.

A seguire l'implementazione di `BotPlayerImpl`:

```scala 3
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

```scala 3
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

```scala 3
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

```scala 3
def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
  (selectedCard, centerCard) match
  case (s, c) if s.color == Color.Black || c.color == Color.Black        => true
  case (s, c) if s.color == c.color                                      => true
  case (s: SimpleCard, c: SimpleCard) if s.num == c.num                  => true
  case (s, c) if s.getClass == c.getClass && !s.isInstanceOf[SimpleCard] => true
  case _                                                                 => false
```

### Tutorial (GUI)
È stata sviluppata una GUI molto semplice per il tutorial del gioco.

Il tutorial è stato implementato in modo da guidare l'utente e mostrare tutte le possibili interazioni
all'interno del gioco tramite una serie di immagini e testi.

Per sviluppare le varie schermate del tutorial, è stata sviluppata una classe generica
`GeneralTutorialSlide` che contiene un'immagine, un titolo e una descrizione.

```scala 3
class GeneralTutorialSlide(val image: Image, val title: String, val description: String)
```

Successivamente, è stata implementata una factory che generi le varie schermate del tutorial quando necessario

```scala 3
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

## Componenti Sviluppati da Nicola Montanari e Samuele De Tuglie

### GameLoop

Il `GameLoop` è il componente principale del gioco; si occupa di gestire il flusso di gioco e le interazioni tra 
i vari componenti. Questo include la gestione dei turni, la gestione delle carte in mano ai giocatori
e alle interazioni che avvengono tra i giocatori.

Il suo metodo principale è `nextTurn` che si occupa di gestire il turno successivo, controllando il tipo di giocatore
che deve giocare e gestendo le azioni di gioco.

```scala 3
  private def nextTurn(): Unit =
      if !isRunning then return
      Future:
        currentTurn = (currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size
        gameGui.updateTurnArrow(currentTurn)
        turnOrder(currentTurn) match
          case bot: BotPlayer =>
            gameGui.allowPlayerAction(false)
            Thread.sleep((1500 + Random.nextInt(1500)).toLong)
            bot.chooseCardToUse(lastPlayedCard) match
              case Some(card) => chooseCard(card, bot)
              case None       => drawCard(bot)
          case _ => gameGui.allowPlayerAction(true)
```

Sostanzialmente, una volta che si assicura che il gioco sia in corso, controllando la variabile `isRunning`,
il metodo procede a calcolare il prossimo turno, mostrando sul tavolo di gioco la freccia che indica il giocatore corrente.
Successivamente, controlla il tipo di giocatore che deve giocare:
- Se è un bot, il gioco disabilita le azioni del giocatore e attende un tempo casuale (compreso tra 1,5 e 3 sec) prima di far giocare il bot.
Nel caso in cui il bot abbia scelto una carta da giocare, il bot la gioca, altrimenti pesca una carta.
- Se è un giocatore umano, il gioco abilita le azioni del giocatore.

## Samuele De Tuglie

### Card

Lo sviluppo della classe `Card` é stato fondamentale per il funzionamento del gioco.

Di carte ne esistono di due tipologie: `SimpleCard` e `SpecialCard`. Siccome sia le carte semplici che le carte speciali
sono caratterizzate da un colore, si é deciso di creare un trait `Card` formato da un campo `color` di tipo `Color`.

```scala 3
trait Card:

  def color: Color

  def image: Image
```

I colori disponibili sono stati definiti in un enum `Color` che contiene i colori base del gioco.

```scala 3
enum Color(val rgb: Int):
  case Red extends Color(0xff0000)
  case Green extends Color(0x00ff00)
  case Blue extends Color(0x0000ff)
  case Yellow extends Color(0xffff00)
  case Black extends Color(0x000000)
```

#### SimpleCard
Le `SimpleCard` sono le carte normali del gioco, caratterizzate da un numero e da un colore.

```scala 3

trait SimpleCard extends Card:
  
  def num: CardNumber

object SimpleCard:
  def apply(num: CardNumber, color: Color): SimpleCard =
    SimpleCardImpl(num, color, loadCardImage(num.value.toString, color))
```

Per indicare il numero della carta, si é deciso di creare un enum `CardNumber` che contiene i numeri delle carte.

```scala 3
enum CardNumber(val value: Int):
  case Zero extends CardNumber(0)
  case One extends CardNumber(1)
  case Two extends CardNumber(2)
  case Three extends CardNumber(3)
  case Four extends CardNumber(4)
  case Five extends CardNumber(5)
  case Six extends CardNumber(6)
  case Seven extends CardNumber(7)
  case Eight extends CardNumber(8)
  case Nine extends CardNumber(9)
```

#### SpecialCard

Le `SpecialCard` sono le carte speciali del gioco, caratterizzate da un colore, da un'immagine e da un metodo `execute`
che verrà chiamato quando la carta verrà giocata.


```scala 3

abstract class SpecialCard(val color: Color, val image: Image) extends Card:
  def execute(): Unit

object SpecialCard:
  var gameLoop: GameLoop = _
  
  case class ChangeColor(override val color: Color = Color.Black) extends SpecialCard(color, loadCardImage("Wild", color)):
    override def toString: String = "ChangeColor " + color.toString
    override def execute(): Unit = gameLoop.showChangeColor()
  
  case class ReverseCard(override val color: Color) extends SpecialCard(color, loadCardImage("Reverse", color)):
    override def toString: String = "Reverse " + color.toString
    override def execute(): Unit = gameLoop.reverseTurnOrder()


  case class SkipCard(override val color: Color, numberToSkip: Int = 1) extends SpecialCard(color, loadCardImage("Skip", color)):
    override def toString: String = "Skip " + color.toString
    override def execute(): Unit = gameLoop.skipNextTurn(numberToSkip)


  case class WildDrawFourCard(override val color: Color = Color.Black) extends SpecialCard(color, loadCardImage("Draw4", color)):
    override def toString: String = "Draw 4" + " " + color.toString
    override def execute(): Unit = gameLoop.nextDrawCard(4)

  case class DrawTwoCard(override val color: Color) extends SpecialCard(color, loadCardImage("Draw2", color)):
    override def toString: String = "Draw 2" + " " + color.toString
    override def execute(): Unit = gameLoop.nextDrawCard(2)
```

Qui sono state definite le carte speciali `ChangeColor`, `ReverseCard`, `SkipCard`, `WildDrawFourCard` e `DrawTwoCard`.
Di base la carta `ChangeColor` e la carta `WildDrawFourCard` sono di colore nero, tuttavia una volta giocate
il giocatore può scegliere il colore che preferisce.

### Deck

Il `Deck` é una collezione di carte non ordinata, inizialmente composto da tutte le carte del gioco.

```scala 3
class Deck extends ArrayBuffer[Card]:

  for color <- Color.values if color != Color.Black do
    this += SimpleCard(CardNumber.Zero, color)
    for _ <- 0 to 1 do
      this += SkipCard(color)
      this += ReverseCard(color)
      this += DrawTwoCard(color)
    for number <- 0 to 17 do this += SimpleCard(CardNumber.values(number / 2), color)
  for _ <- 0 to 3 do
    this += ChangeColor()
    this += WildDrawFourCard()

  this.shuffle()

  private def shuffle(): Unit =
    val shuffledList = Random.shuffle(this)
    this.clear()
    this.addAll(shuffledList)
  
  def draw(): Card = this.remove(0)
```

Il `Deck` é composto da tutte le carte del gioco, inizialmente mescolate.
Il metodo `draw` permette di pescare una carta dal mazzo.

### Page Controller

Il `PageController` é il controller principale dell'ambiente grafico, e si occupa
di gestire lo scambio tra le varie schermate del gioco.

```scala 3
case class PageController private (player: Player, gameGui: GameGui, gameLoop: GameLoop):

  private val achievementGui: AchievementGui = AchievementGui(this)
  frame.add(Mainmenu(this), CardLayoutId.MainMenu)
  frame.add(WinScreen(this), CardLayoutId.Win)
  frame.add(LoseScreen(this), CardLayoutId.Lose)
  frame.add(gameGui, CardLayoutId.Game)
  frame.add(ChooseColor(gameLoop), CardLayoutId.ChangeColor)
  frame.add(SettingsGui(this), CardLayoutId.Settings)
  frame.add(achievementGui, CardLayoutId.Achievement)
  frame.add(TutorialGui(this), CardLayoutId.Tutorial)

  def showMainMenu(): Unit =
    frame.show(CardLayoutId.MainMenu)

  def showGame(newGame: Boolean = true): Unit =
    if newGame then gameLoop.start()
    frame.show(CardLayoutId.Game)

  def showChangeColor(): Unit =
    frame.show(CardLayoutId.ChangeColor)

  def showTutorial(): Unit =
    frame.show(CardLayoutId.Tutorial)

  def showAchievements(): Unit =
    achievementGui.updateGui()
    frame.show(CardLayoutId.Achievement)

  def showSettings(): Unit =
    frame.show(CardLayoutId.Settings)

  def showWin(): Unit =
    gameLoop.stop()
    frame.show(CardLayoutId.Win)

  def showLose(): Unit =
    gameLoop.stop()
    frame.show(CardLayoutId.Lose)

  def closeWindow(): Unit =
    frame.dispose()

object PageController:

  private val frame: Frame = Frame()

  def apply(player: Player, gameGui: GameGui, gameLoop: GameLoop): PageController =
    new PageController(player, gameGui, gameLoop)
  ```

### Frame
L'intera interfaccia grafica si basa sul `Frame`, un'implementazione di `JFrame` che permette di gestire
le varie schermate del gioco aggiungendo la possibilità di scambiare tra le varie schermate in modo semplice e veloce
attraverso l'utilizzo di un `CardLayout`.

```scala 3
class Frame extends JFrame:
  setTitle("PPS-23-UNO")
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setSize(1280, 720)
  setResizable(false)
  setLocationRelativeTo(null)
  try
    for info <- UIManager.getInstalledLookAndFeels do
      if "Nimbus" == info.getName then UIManager.setLookAndFeel(info.getClassName)
  catch case _: Exception => ()
  add(Frame.cardPanel)
  add(new JPanel(), Base)
  show(Base)
  setVisible(true)
  
  def add(panel: JPanel, layoutId: CardLayoutId): Unit = Frame.cardPanel.add(panel, layoutId.toString)
  
  def show(layoutId: CardLayoutId): Unit =
    Frame.cardLayout.show(Frame.cardPanel, layoutId.toString)
    Frame.currentLayout = layoutId
  
  def isShowing(layoutId: CardLayoutId): Boolean = Frame.currentLayout == layoutId

object Frame:
  private val cardLayout: CardLayout = new CardLayout
  private val cardPanel: JPanel = new JPanel(cardLayout)
  private var currentLayout: CardLayoutId = _
  def apply(): Frame = new Frame()
```

Come _look and feel_ é stato scelto Nimbus, in modo da assicurarsi che l'interfaccia grafica sia coerente su tutti i sistemi operativi.
Per la gestione dei pannelli si utilizza un enum `CardLayoutId` che permette di identificare in modo univoco ogni schermata.
```scala 3
enum CardLayoutId:
  case Game, MainMenu, Settings, Tutorial, Achievement, Win, Lose, ChangeColor, Base
```

### Main Menu

Il `MainMenu` é la schermata iniziale del gioco, che permette di accedere a varie funzionalità.
Queste sono:
- Iniziare una nuova partita
- Visualizzare il tutorial
- Visualizzare gli obiettivi
- Modificare le impostazioni
- Uscire dal gioco

### Game Gui
La `GameGui` é la schermata principale del gioco, che permette di visualizzare il tavolo di gioco e le carte in mano al giocatore.
La schermata é formata da una griglia di bottoni (chiamate `Cells`).

Ci sono varie tipologie di celle:
- `CardCell` : cella che contiene una carta e permette di giocarla.
- `DeckCell` : cella che contiene il mazzo di carte e permette di pescare.
- `UnoCell` : cella che permette di chiamare uno.
- `DirectionCell` : cella che contiene l'immagine che rappresenta la direzione del gioco.
- `UsedCardCell` : cella che contiene l'ultima carta giocata.

### ChangeColorGui
La `ChangeColorGui` é la schermata che permette al giocatore di scegliere il colore dell'ultima carta giocata,
solitamente una carta `ChangeColor` o `WildDrawFourCard`. Questa schermata presenta 
quattro bottoni, uno per ogni colore possibile.

### WinScreen e LoseScreen
Queste due schermate sono schermate di fine partita, che vengono visualizzate quando un giocatore vince o perde.
Data la loro somiglianza, é stato scelto di creare una classe astratta `EndGameScreen` che contiene la logica comune
tra le due schermate, e due classi `WinScreen` e `LoseScreen` che estendono `EndGameScreen` e implementano la logica specifica
per la vittoria e la sconfitta.
```scala 3
abstract class EndGameScreen(private val backgroundImage: Image, private val pageController: PageController)
    extends JPanel:

  this.setLayout(new GridBagLayout())
  private val gbc = new java.awt.GridBagConstraints()
  gbc.insets = new Insets(200, 0, 0, 0)

  private val button = new Button("Return to Main Menu", (300, 50))
  this.add(button, gbc)

  button.addActionListener(
    _ => pageController.showMainMenu()
  )

  override def paintComponent(g: java.awt.Graphics): Unit =
    super.paintComponent(g)
    g.drawImage(backgroundImage, 0, 0, this.getWidth, this.getHeight, this)

class WinScreen(private val pageController: PageController) extends EndGameScreen(winBackground, pageController)

class LoseScreen(private val pageController: PageController) extends EndGameScreen(defeatBackground, pageController)
```


## Pablo Sebastian Vargas Grateron

- Settings
  - SettingsController

- Achievements
  - AchievementController

Gui di entrambe


---

[Indice](../index.md) | [Capitolo Precedente](./4-Design-dettaglio.md) | [Capitolo Successivo](./6-Testing.md)