/*
Classico problema di scheduling.
*/

# DATI
param N;                # Numero di ordini
set O := 1..N;          # Ordini
param t {O};            # Tempi di lavorazione (processing time)
param s {O};            # Scadenze (due date)

# VARIABILI
var x {O,O} binary;     # x[i,j] = 1 se l'ordine i viene processato sta prima di j 

# Ausiliarie
var c {O};              # Tempo di completamento (vedi vincolo)
var tard {O} >= 0;      # Tardiness = ritardo rispetto alla due date
var tmax >= 0;          # Massima tardiness

# VINCOLI
/*
Dobbiamo imporre che non ci siano cicli, tipo:
x[i,j] = 1 (i viene prima di j)
x[j,k] = 1 (j viene prima di k)
x[k,i] = 1 (k viene prima di i)
Ovvero: i -> j -> k -> i -> j -> k -> ...
Come visto nel problema "linearOrdering", basta evitare
cicli di ordine 3 e sono automaticamente evitati tutti
*/
subject to NoLoop {i in O, j in O, k in O: i != j and j != k and k != i} :
    x[i,j] + x[j,k] + x[k,i] <= 2;

# i non può precedere i
subject to Diagonal {i in O}:
    x[i,i] = 0;

# O j precede i, oppure i precede j. Non può essere entrambe.
subject to Preced {i in O, j in O: i != j} :
    x[i,j] + x[j,i] = 1;

/*
Devo fissare il tempo di completamento
c[i] = tempo di attesa prima che inizi i + processing time di i
tempo di attesa: sum {j in O} t[j] * x[i,j]
processing time: t[i]
[la variabile c non serve di per sè, è solo per rendere
tutto più pulito]
*/
subject to CompletionTime {i in O} :
    c[i] = sum {j in O} x[i,j] * t[j] + t[i];

/*
Definizione della tardiness per ogni ordine.
tard[i] = tempo di completamento di i - due date di i
Nota che se rientro nella scadenza, il risultato è negativo,
ossia sono in anticipo, ma siccome non ci interessano gli anticipi,
abbiamo anche imposto, alla dichiarazione di tard, che questa
dev'essere non-negativa. 
In questo modo tardiness esprime solo i ritardi.
Si spiega anche il "≥" di tard[i] >= c[i] - s[i]
Se mettessi uguale, il vincolo di non-negatività non potrebbe
lavorare (o almeno i due vincoli sarebbero in conflitto).
Non devi preccouparti che tard diventi enorme (essendo che
ci sono solo vincoli di ≥), perché poi nella funzione obiettivo
viene minimizzata!
*/
subject to Tardiness {i in O} :
    tard[i] >= c[i] - s[i];

# OBIETTIVO
/*
Obiettivo (a): minimizzare somma tardiness
Nota che calcoliamo solo i ritardi, non sottraiamo gli anticipi.
*/
#minimize z1: sum {i in O} tard[i];

/*
Obiettivo (b): minimizzare la tardiness massima (minmax)
*/
minimize z2: tmax;
subject to Minmax {i in O}: tmax >= tard[i];

##########################
data;

param N := 10;

param:  t       s   :=
1       24      50
2       12      50
3       30      90
4       15      70
5       18      50
6       7       80
7       8       100
8       15      90
9       14      120
10      22      150;

end;
