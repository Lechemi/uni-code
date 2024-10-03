/*
*/

# DATI
param nH;           # Numero ore
set H := 0..nH;     # Insieme ore
param t {H};        # Tasso di proteina Y [mg/cc per grammo di sostanza assunta]
param l {H};        # Livello minimo di proteina da garantire [mg/cc]
param M;            # Vedi vincolo Binary

# VARIABILI
var q {H} >= 0;         # Quantità di sostanza da assumere ad ogni ora [g]
# (AUSILIARIE)
var s {H} >= 0;         # Quantità di proteina Y presente in corpo ad ogni ora [mg/cc]
var x {H} binary;       # Indica se all'ora h è stato assunto (1) o meno (0) il farmaco

# VINCOLI
# Legame tra x e q (M è un numero abbastanza grande da essere ≥ a qualsiasi q)
subject to Binary {h in H}: q[h] <= M * x[h];

/*
Definizione di s[h] [mg/cc]
*/
subject to DefS {h in H}: s[h] = sum {o in H: o <= h} q[o] * t[h - o];

/*
Devo coprire il fabbisogno di proteina Y [mg/cc]
*/
subject to Fabbisogno {h in H}: s[h] >= l[h];

# OBIETTIVO
/*
1) minimizzare la quantità di sostanza complessiva da assumere durante la giornata [g]
minimize z1 : sum {h in H} q[h];
*/


/*
2) minimizzare il numero di volte in cui il paziente deve assumere la sostanza
Risultato: ovviamente, senza vincoli sulla quantità di sostanza che il paziente può prendere,
possono essere somministrate dosi altissime.

minimize z2 : sum {h in H} x[h];
*/

/*
3) minimizzare la quantità di sostanza, come al punto (1), posto che essa può 
essere assunta solo tre volte al giorno

minimize z3 : sum {h in H} q[h];
subject to ThreeTimes: sum {h in H} x[h] <= 3;
*/

/*
4) minimizzare il numero di volte in cui il paziente assume la sostanza, 
come al punto (2), posto che in ogni fascia oraria non può assumerne più di 
una data quantità, pari a 25 grammi
*/
minimize z4 : sum {h in H} x[h];
subject to Grams {h in H}: q[h] <= 25;

##########################
data;

param nH := 16;
param M := 999;

param t :=
0              1.5
1              3.0
2              4.0
3              2.5
4              1.9
5              1.4
6              1.0
7              0.7
8              0.5
9              0.3
10              0.2
11              0.1
12              0.0
13              0.0
14              0.0
15              0.0
16              0.0;

param l :=
0       5.0
1       1.0
2       0.0
3        0.0
4        0.0
5        0.0
6        4.0
7       15.0
8       12.0
9        5.0
10        4.0
11        3.0
12       25.0
13       30.0
14       25.0
15       15.0
16       5.0;

end;
