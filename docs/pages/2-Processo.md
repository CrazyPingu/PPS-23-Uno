# Processo di sviluppo

Il processo di sviluppo del progetto è stato guidato da principi di agilità e collaborazione, seguendo la metodologia _Scrum_ e 
utilizzando strumenti e tecniche come _Test Driven Development (TDD)_, _Continuous Integration (CI)_, _Build Automation_ e _Quality Assurance_.

## Scrum

Scrum è un framework di gestione dei progetti e sviluppo del software che si basa su principi di agilità e collaborazione (Metodologia _Agile_). 

La metodologia si sviluppa attraverso iterazioni chiamate sprint. In ciascuno di essi, si introducono nuove funzionalità o si apportano miglioramenti al sistema esistente.

Ogni sprint è composto dalle seguenti attività:

- **Daily Scrum:** Durante il Daily Scrum, ciascun partecipante fornisce un aggiornamento su quanto è stato completato, ciò che si intende realizzare nella giornata e le eventuali problematiche o impedimenti incontrati.
- **Sprint Planning:** Viene definito un obiettivo chiaro per lo sprint e si sviluppa un piano dettagliato su come raggiungere tale obiettivo.

Al termine di ogni _sprint_ si esegue una revisione, che consente al team di adattare il lavoro in base ai feedback e ai cambiamenti delle esigenze. La revisione dello sprint è composta dalle seguenti attività:

- **Product Backlog review:** Durante questa revisione, il team di sviluppo e il Product Owner esaminano e riflettono sul contenuto del Product Backlog per assicurarsi che esso continui a rispondere alle esigenze del progetto.

- **Sprint Review:** Questo incontro ha lo scopo di dimostrare i risultati ottenuti, raccogliere feedback e discutere eventuali modifiche necessarie.

- **Sprint Retrospective:** Durante questa attività, il team riflette sulle esperienze dello sprint appena concluso, analizzando le pratiche adottate e i risultati ottenuti. L’obiettivo principale è identificare le aree di miglioramento e discutere modifiche ai processi e alle metodologie di lavoro.

### Suddivisone del lavoro

Nel nostro progetto si sono presentati tre ruoli principali:

- Product Owner: il cui compito é di verificare l'adeguatezza del sistema che si sta sviluppando, ruolo svolto da Pablo Sebastian Vargas Grateron.
- Scrum Master: il cui ruolo e quello di mediare tra il Product Owner e il Development Team, ruolo svolto da Nicola Montanari.
- Development Team: team che si occupa dello sviluppo del progetto e del testing, composto da:
  - Samuele De Tuglie
  - Pablo Sebastian Vargas Grateron
  - Nicola Montanari

## Test Driven Development (TDD)

Per questo progetto abbiamo scelto di seguire il metodo di sviluppo Test Driven Development (TDD). I benefici del TDD includono una maggiore qualità del codice grazie a una progettazione più modulare e meno dipendente. I test automatizzati forniscono un feedback immediato, aiutano a identificare e correggere rapidamente gli errori e fungono da documentazione vivente del sistema. Inoltre, permettono di apportare modifiche al codice con maggiore fiducia, sapendo che eventuali regressioni verranno rilevate rapidamente.

Il sviluppo TDD si divide in tre fasi:

1. **Prima fase:** Creazione dei test che falliscono.
2. **Seconda fase:** Scrivere il codice minimo per far passare il test.
3. **Terza fase:** Riffattorizzare il codice per migliorare la struttura del codice.

## Continuous Integration (CI)

Per il Continuous Integration abbiamo utilizzato _GitHub Actions_, implementando un _workflow_ che esegue il testing del codice ad ogni _push_ e _pull request_ per assicurare l'integrità e la correttezza del codice.

## Build Automation

Per automatizzare il testing e la release del progetto, abbiamo scelto di utilizzare _Simple Build Tool_ (sbt) grazie alla sua integrazione nativa con Scala e alla sua efficacia nella gestione delle dipendenze.

## Quality Assurance

Per garantire la coerenza e l'uniformità nel codice, è stato implementato un file di configurazione _scalafmt_. Questo file si occupa di applicare automaticamente le regole di formattazione stabilite, assicurando che il codice sorgente rispetti uno standard coerente e leggibile in tutte le parti del progetto.

---

[Indice](../index.md) | [Capitolo Precedente](./1-Introduzione.md) | [Capitolo Successivo](./3-Requisiti.md)