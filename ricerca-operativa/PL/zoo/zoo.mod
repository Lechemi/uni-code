/*
Si vuole inoltre studiare la convenienza dell'eventuale acquisto di un altro prodotto, 
composto esclusivamente da carne, latte, frutta e verdura.
Non conviene: per quelle sostanze abbiamo un surplus, come indicano le variabili di slack.
Infatti il loro coefficiente di costo ridotto (prezzo ombra) è 0, potremmo darle via gratis.
Anche inserendo il quinto prodotto nel modello, la soluzione ottima è quella che non lo usa per niente.

L'acquisto ulteriore di un orso è convieniente e per verificarlo basta aggiungerlo
nei dati e si vede dai su.
*/

# DATI
param nA;           # Numero di specie di animali
set A := 1..nA;     # Insieme delle specie di animali
param nS;           # Numero sostanze nutritive
set S := 1..nS;     # Insieme delle sostanze nutritive
param nP;           # Numero di prodotti
set P := 1..nP;     # Insieme dei prodotti
param q {A};        # Quantità di esemplari per specie
param comp {P,S};   # Composizione percentuale in sostanze dei prodotti [%]
param fab {A,S};    # Fabbisogno di sostanze per ogni specie [kg/giorno]
param c {P};        # Costo di ciascun prodotto [euro/kg]

# VARIABILI
var x {P} >= 0;          # Quantità di ogni prodotto da comprare [kg/giorno]
# (AUSILIARIE)
var fabGlobale {S};     # Quantità di sostanza necessaria in totale [kg/giorno]
# var y {A} integer;              # Quantità di esemplari extra che posso mantenere

# VINCOLI
# Definizione del fabbisogno totale (somma dei fabbisogni di ogni specie) [kg/giorno]
subject to FabGlobale {s in S} : fabGlobale[s] = sum {a in A} fab[a,s]*(q[a]);

# Punto 3
# subject to FabGlobale {s in S} : fabGlobale[s] = sum {a in A} fab[a,s]*(q[a]+y[a]);


# Devo soddisfare il fabbisogno globale per ogni sostanza [kg/giorno].

subject to Fabbisogno {s in S} : 
    sum {p in P} x[p] * comp[p,s]/100 >= fabGlobale[s];



/*
# Punto 2
subject to Fabbisogno {s in S: s != 8} : 
    sum {p in P} x[p] * comp[p,s]/100 >= fabGlobale[s];

subject to Extra {a in A: a != 5 and a != 7} : y[a] = 0;
subject to Elefanti : y[5] <= 5;
subject to Giraffe : y[7] <= 8;
*/

# OBIETTIVO
/*
Voglio minimizzare la spesa giornaliera [euro/giorno] pur soddisfacendo il fabbisogno di ogni esemplare.
*/
minimize z1 : sum {p in P} x[p]*c[p];

/*
Sappiamo che la spesa minima è di 1527.866667 euro/giorno.
Si vuole sapere se accettare il dono di un sultano che vorrebbe regalare allo zoo 
fino a 5 elefanti e fino a 8 giraffe e l'acqua necessaria a mantenerli. 
Quanti elefanti e quante giraffe potrebbe accettare lo zoo senza aumentare i propri costi?

Se si vuole massimizzare la somma di esemplari di elefante e giraffa, allora si può
ospitare al massimo 1 elefante e 8 giraffe, senza che i costi aumentino.

maximize z2 : sum {a in A} y[a];
subject to Spesa : sum {p in P} x[p]*c[p] = 1527.866667;
*/

##########################
data;

param nA := 20;
param nS := 8;
param nP := 5;

param q :=
1   1 
2   2 
3   1 
4   1 
5   1 
6   8 
7   1 
8   1 
9   3 
10  2 
11  2 
12  1 
13  2 
14  8 
15  1 
16  6 
17  1 
18  4 
19  4 
20  2;

param comp (tr) :    1       2       3       4      5   :=
1               80       5       0      25          50
2               0       5       0       0           5
3               0      25      30       5           10
4               5      40      25      10           35
5               5       0       0       0           0
6               0       0       0      15           0
7               0       0      25       0           0
8               10      25      20      45          0;

param fab : 1   2   3   4   5   6   7   8   :=
1   0.0  0.0  0.0  2.0  0.2  0.5  1.0  3.0
2   1.0  1.0  3.0  0.3  0.2  0.2  0.0  2.0
3   0.1  0.5  0.1  1.0  0.1  0.3  0.0  4.0
4   0.5  0.5  1.0  0.5  0.1  0.5  0.5  5.0
5   0.0  0.5  5.0  9.0  0.5  1.0  1.0  9.0
6   0.0  0.0  0.0  0.0  0.0  0.0  0.1  0.1
7   0.0  0.0  0.2  3.0  0.0  0.0  0.0  4.0
8   0.0  0.0  8.0  6.0  0.5  1.0  0.0 20.0
9   0.0  1.0  1.0  1.0  0.0  0.0  0.2  0.5
10   5.0  0.0  0.0  0.0  1.0  0.5  0.0  5.0
11   0.0  1.0  0.0  5.0  0.0  0.0  0.0  3.0
12   0.0  0.0  0.0  0.0  0.0  0.0  3.0  0.0
13   5.0  0.5  3.0  1.0  0.0  0.0  0.0 10.0
14   0.0  0.0  0.0  0.0  0.0  0.0  0.2  0.5
15   1.0  0.0  0.0 12.0  0.0  2.0  0.0 15.0
16   0.5  0.0  0.0  0.1  0.0  0.0  0.0  0.1
17   0.0  0.2  1.0  9.0  0.2  0.5  0.0  6.0
18   0.0  0.0  0.0  0.5  0.1  0.0  0.5  1.0
19   0.0  0.2  0.5  3.0  0.0  0.0  1.0  1.0
20   0.0  0.0  0.0  5.0  0.5  0.2  0.5  5.0;

param c :=
1   5
2   2
3   3   
4   4
5   0.5;

end;
