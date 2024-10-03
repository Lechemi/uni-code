/*
Ad ogni contratto corrisponde un'ambulanza, quindi 
*/

# DATI
set O := 0..23;         # Insieme ore del giorno
param nC;               # Numero contratti
set C := 1..nC;         # Insieme contratti
param f {O};            # Fabbisogno di ambulanze [unità]
param d {C};            # Durata di ogni contratto [ore]
param s {C};            # Costo di ogni contratto [euro/unità]
param K;                # Seconda domanda

# VARIABILI
var x {C,O} integer >= 0;  # se x[c,o] = n, allora noleggio n ambulanze col contratto c a partire dall'ora o  [unità]
# (AUSILIARIE)
var q {O} >= 0;            # Numero di ambulanze presenti all'ora o [unità]
# Lascio sta variabile nel continuo che tanto per com'è definita rimane intera

# VINCOLI
/*
Definizione di q [unità]
((o-h) mod 24) <= d[c] è per dire: sarebbe ancora valido il contratto c se l'ho inizato alle h?
*/
subject to DefQ {o in O}: q[o] = sum {c in C, h in O: ((o-h) mod 24) <= d[c]} x[c,h];

# Soddisfacimento del fabbisogno [unità]
subject to Fabbisogno {o in O}: q[o] >= f[o];

# OBIETTIVO
# Minimizzare i costi [euro]
minimize z: sum {c in C, o in O} x[c,o] * s[c];

/*
Seconda domanda, che tradotta vuol dire: se per ogni contratto ci fosse un numero 
massimo K di ambulanze che posso noleggiare, come cambierebbe la soluzione?

Se K >= 7, i costi rimangono minimi, a 6900 euro
Se K = 6, i costi aumentano, arrivando a 6950
*/
subject to Kappa {c in C}: sum {o in O} x[c,o] <= K;

##########################
data;

# Per analisi parametrica (seconda domanda)
param K := 6;

param nC := 4;
param f :=
0                    8
1                    8
2                    8
3                    7
4                    6
5                    6
6                    8
7                    9
8                   14
9                   16
10                   10
11                    9
12                    9
13                    8
14                    7
15                   10
16                   12
17                   17
18                   16
19                   14
20                   10
21                    8
22                    7
23                    7;

param :    d      s   :=
1         24      500
2         12      350
3          8      250
4          6      200;

end;
