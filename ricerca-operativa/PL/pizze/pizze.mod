/*
Non è finito del tutto perché è troppo facile
*/

# DATI
param nA;           # Numero amici
set A := 1..nA;     # Insieme amici
param nP;           # Numero pizze
set P := 1..nP;     # Insieme pizze
param g {A,P};      # Valori di gradimento

# VARIABILI
var x {A,P} >= 0, <= 1;        # x[a,p] = frazione della pizza p che l'amico a mangia
# (AUSILIARIE)
var c {A};          # Grado di contentezza di ciascun amico
var w;              # Grado di contentezza minimo

# VINCOLI
/*
Le pizze devono essere mangiate tutte completamente.
*/
subject to PensaAiBambiniInAfrica {p in P} :
    sum {a in A} x[a,p] = 1;

/*
Definizione del grado di contentezza
Immagino sia uguale alla somma su ogni p di: 
quanto mangia la pizza p * quanto gli piace la pizza p
*/
subject to Defc {a in A} : c[a] = sum {p in P} x[a,p] * g[a,p];

/*
Vincolo caso b
ciascuno mangia l’equivalente di una pizza e mezzo
*/
subject to BConstraint {a in A} : sum {p in P} x[a,p] = nP/nA;


# OBIETTIVO (caso a)
/*
1) massimizzare il grado di contentezza del più scontento

maximize z1 : w;
subject to Defw {a in A} : w <= c[a];
*/


/*
2) massimizzare il grado di contentezza del più contento
Non serve neanche calcolare la soluzione, basta dare tutte e tre le pizze ad uno solo
dei due amici, così che il suo grado di content. sia 1.
*/

/*
2) massimizzare il grado di contentezza totale

maximize z3 : sum {a in A} c[a];
*/



# OBIETTIVO (caso b)
/*
1) massimizzare il grado di contentezza del più scontento

maximize z1 : w;
subject to Defw {a in A} : w <= c[a];
*/

/*
2) massimizzare il grado di contentezza del più contento

Per farlo proverei a risolvere il problema prima SOLO per l'amico 1, sempre col limite
della pizza e mezzo, ma avendo a disposizione tutte e tre le pizze e mi 
segno il valore raggiunto. Poi stessa cosa per l'amico 2 e vedo qual è più alto. 
*/

/*
2) massimizzare il grado di contentezza totale
*/
maximize z3 : sum {a in A} c[a];

##########################
data;

param nA := 2;
param nP := 3;

param g :   1       2       3  :=
1         0.3     0.5     0.2
2         0.7     0.2     0.1;

end;