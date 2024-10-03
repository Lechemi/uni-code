/*
Nel testo non è specificato, ma l'obiettivo è decidere quante patate comprare da ciascun
fornitore in modo da massimizzare la resa, tenendo conto di quanto quel fornitore
rende nella produzione di ciascun prodotto.
*/

# DATI
param nF;           # numero di fornitori
param nP;           # numero di prodotti
set F := 1..nF;     # indici dei fornitori (da 1 al numero di fornitori)
set P := 1..nP;     # indici dei prodotti
param profitti {F}; # profitto ricavato da ciascun fornitore [€/quintale]
param limite {P};   # limite di peso di patate acquistate per tipologia di prodotto [ton]
param rese {F, P};  # resa dello specifico fornitore per lo specifico prodotto [%]

# VARIABILI
var x {F} >= 0;     # quantità acquistate da ciascun fornitore [quintale]

# VINCOLI

/*
Questo vincolo impone che la produzione di ciascun tipo di prodotto rientri nel
limite di tonnellate specificato nei dati. 
Moltiplico limite[j] per 10 perché questo è espresso in tonnellate, mentre
a primo membro abbiamo x[i] che è in quintali.
Nota che poi effettivamente si hanno 3 vincoli (uno per prodotto [j in P]). 
*/
subject to Produzione {j in P}: sum {i in F} x[i]*rese[i,j]/100 <= limite[j]*10;

# OBIETTIVO 

/*
L'obiettivo è massimizzare il profitto totale (z [€]), uguale a:
il profitto che mi dà un quintale di patate acquistate dal fornitore [i], per la quantità
di patate (quintali) che ho acquistato da lui. Ovviamente esteso su tutti i fornitori.
*/
maximize z : sum {i in F} profitti[i]*x[i];

###############
data;

param nF := 2;
param nP := 3;     
param profitti := 
1   2
2   3;

param limite := 
1   6
2   4
3   8;

param rese: 1   2   3 :=
1           20  20  30
2           30  10  30;

end;