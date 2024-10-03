/*
Formuliamo il problema così:
Per ogni cifra e per ogni potenza di 10 (tra quelle "consentite") assegno una variabile 
binaria che lega cifra e potenza di 10.
Se x[i,j] = 1, allora la cifra i (V[i]) va moltiplicata per 10^j.
Chiaramente ad ogni cifra spetta una sola potenza, e questa dev'essere "coerente", ossia:
solo se x[i,j] = 1, allora posso mettere x[i-1, i+j] = 1.
Per esempio: solo se 3 ha potenza 0, posso mettere 4 (che si trova prima, quindi i-1)
con potenza 1. Avrò quindi 4*10^1 + 3*10^0 = 43

Soluzione trovata:
9 + 8 + 7 + 654 + 321 = 999

*/

# DATI
param N;                            # Numero di posizioni (ciascuna corrispondente ad una cifra)
set C := 1..N;                      # Insieme delle posizioni (ciascuna corrispondente ad una cifra)
param V {C};                        # Vettore delle cifre date
param soglia;                       # Numero da non superare con la somma
param MaxP := log(soglia)/log(10);  # Massimo esponente entro la soglia (nel nostro caso 2, se fosse 3 avrei 10^3, troppo)
set Potenze := 0..MaxP;             # Vettore delle potenze di 10 entro la soglia (1, 10, 100 nel nostro caso)

# VARIABILI
var x {C, Potenze} binary;          # Vedi commento in alto

# VINCOLI
/*
La somma deve stare nella soglia. 
(vedi commento in alto x chiarezza)
*/
subject to Totale:
    sum {i in C, j in Potenze} V[i] * 10^j * x[i, j] <= soglia;

/*
Ogni cifra deve avere una sola potenza di 10 associata.
*/
subject to UnaSolaPotenzaPerCifra {i in C} :
    sum {j in Potenze} x[i,j] = 1;

/*
Ogni potenza dev'essere coerente con la posizione
(vedi commento in alto)
Posso concentrarmi sul "cosa c'è a destra della cifra".
Esempio: a destra delle centinaia devono per forza esserci
le decine, a destra delle decine ci sono le unità.
Al contrario, non so se a sinistra delle decine ci sono 
le centinaia (dello stesso numero) o le unità (di un altro numero)

x[i+1, j-1] >= x[i,j]
Se x[i,j] = 0, allora il vincolo è "off": a primo membro
ci può stare sia 0 che 1 (la variabile binaria è libera).
Se x[i,j] = 1, allora il vincolo è "on": a primo membro
deve per forza esserci un 1.

Se per una certa cifra i, x[i,j] = 1, allora la cifra 
alla sua destra (i+1) dev'esserci una potenza di 10 che sia
minore (j-1). 
È un po' un trip perché x non simboleggia la potenza, ma solo
una variabile di assegnamento; la vera potenza è j!

Il vincolo si riferisce alle j >= 1 e alle i < N perché
vogliamo vincolare solo le cifre che non sono unità (le unità
possono avere qualsiasi cosa alla loro destra) e non vogliamo 
vincolare l'ultima cifra a destra (perché alla sua destra non
c'è niente).
Per quest'ultima cifra ci sarà un vincolo apposta che la 
obbliga ad essere un'unità. absolute unit

*/
subject to PotenzeCoerenti {i in C, j in Potenze : j >= 1 and i < N} :
    x[i+1, j-1] >= x[i,j];

subject to UltimaCifra : x[N, 0] = 1;

# OBIETTIVO
maximize z : sum {i in C, j in Potenze} V[i] * 10^j * x[i, j];

##########################
data;

param N := 9;
param soglia := 1000;
param V :=
1   9
2   8
3   7
4   6
5   5
6   4
7   3
8   2
9   1;


end;