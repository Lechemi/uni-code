/*
Si vogliono massimizzare i profitti.

È sempre un mix produttivo ottimale, ma 
siamo liberi di decidere quanta sostanza di un certo tipo mettere in 
ciascun tipo di benzina, siamo solo vincolati da limiti
superiori ed inferiori.
Ovviamente poi ci sono anche i vincoli sulle quantità 
effettivamente disponibili di sostanze.
*/

# DATI
set Benzine;
set Sostanze;
param b {Sostanze};             # Quantità disponibili per ogni sostanza [barili/d]
param p {Sostanze};             # Prezzo di acquisto di ogni sostanza [$/barile]
param c {Benzine};              # Prezzo di vendita di ogni benzina [$/barile]
param fmin {Sostanze,Benzine};  # Frazione minima di ogni sostanza in ogni benzina
param fmax {Sostanze,Benzine};  # Frazione massima di ogni sostanza in ogni benzina


# VARIABILI
/*
Quantità di sostanza i nella benzina j [barili/d].
Nota che associo direttamente qua gli indici per ciascuna 
entry della matrice variabile.
*/
var x {i in Sostanze, j in Benzine} >= 0;

/*
Variabili ausiliarie, introdotte per comodità.
Nota che le definisco poi coi vincoli, in modo
da togliere gradi di libertà.
*/
var totB {Benzine};      # Produzione totale di ogni benzina [barili/d]  
var totS {Sostanze};     # Consumo totale di ogni sostanza [barili/g]

# VINCOLI
subject to TotB {j in Benzine} : totB[j] = sum {i in Sostanze} x[i,j];
subject to TotS {i in Sostanze} : totS[i] = sum {j in Benzine} x[i,j];

/*
Massimo consumo di sostanze [barili/d].
Sono 4 vincoli in 1 (un vincolo per sostanza).
*/
subject to Consumi {i in Sostanze}: totS[i] <= b[i];

/*
Frazione minima per ogni sostanza in ogni benzina [adim].
Sono 12 vincoli in 1 (un vincolo per ogni combinazione sostanza-benzina).
Quel (sum {k in Sostanze} x[k,j]) non è altro che la quantità
di benzina di tipo j prodotta.
*/
subject to FrazioneMinima {i in Sostanze, j in Benzine} :
    x[i,j] >= fmin[i,j] * totB[j];

# Frazione massima, analogo a sopra.
subject to FrazioneMassima {i in Sostanze, j in Benzine} :
    x[i,j] <= fmax[i,j] * totB[j];


# OBIETTIVO
# Profitto [$/d] = ricavi - costi (le parentesi non sono necessarie)
maximize z: (sum {j in Benzine} c[j]*totB[j]) - (sum {i in Sostanze} p[i] * totS[i]);

##########################
data;

set Benzine := Super Normale Verde;
set Sostanze := A, B, C, D;

param :     b           p   := 
A			3000		3
B			2000		6
C			4000		4
D			1000		5;

param c :=
Super		5.5
Normale		4.5
Verde		3.5;

param fmin :   Super  Normale  Verde :=
A	            0.0	    0.0	    0.0
B	            0.4	    0.1	    0.0
C	            0.0	    0.0	    0.0
D	            0.0	    0.0	    0.0;

param fmax :   Super  Normale  Verde :=
A	            0.3	    0.5	    0.7
B	            1.0	    1.0	    1.0
C	            0.5	    1.0	    1.0
D	            1.0	    1.0	    1.0;

end;