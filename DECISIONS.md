This file is optional, if you find it useful to document your decisions.

Our discussion will be based on the code, not on this file.

## Content
- Assumptions made
- Trade-offs
- Open questions
- What I would change in production

#Assumptions made
- ISIN come chiave universale per mappare la posizione titoli tra swarm e sistemi esterni
- La visualizzazione sarà quella dell'ultimo report disponibile per il calcolo del delta attuale
- La nota inserita per la validazione manuale debba essere vista da tutto il team senza limitazioni di visibilità
- In prima battuta non ho inserito filtri o paginazioni sulla tabella dando per scontato che il set sia ristretto

# Trade-offs
- Mybatis: ho scelto mybatis per avere la massima libertà di scrittura di query complesse, come nel caso di recupero del report. 
Questo porta a dover scrivere anche query semplici (vedi select da singola tabella) però è scalabile nel momento in cui volessimo aggiungere parametri di filtro o altro

- Database h2: per il tipo di esercizio da svolgere avere un semplice db in memory o su file era concesso ed è coerente con la scelta di mybatis

- Liquibase: nel caso specifico per l'inizzializzazione della base dati, ma in futuro anche per modifiche in modo da avere una storicità a livello di strutture che altrimenti verrebbe persa (a meno di utilizzo di tool ad hoc per questa necessità es. SQLVersion)

# Open questions

- Quali dati si ha la necessità di avere in pagina?
- Qual'è il volume dei dati? Appunto come detto prima la query del report può essere pagina come ottimizzazione
- Necessario introdurre un sistema di autorizzazione per permettere solo ad alcuni utenti di poter validare manualmente?
- Come devono essere gestite differenti valute? 

#  What I would change in production

- Implemenrare autenticazione. Concesso non averla in prima battuta ma indispensabile
- Sostituzione del DB in memory con una base dati più solida
- Miglioramento del logging
- Implementare un metodo di acquisizione dei dati automatico
- Storicizzazione della dashboard per poter verificare la situazione in periodi temporali diversi
- Rivedere la UI perché sia in linea con il resto del prodotto. Vogliamo avere un'interfaccia standard
- Filtri e ricerca
- Export in PDF/Excel


