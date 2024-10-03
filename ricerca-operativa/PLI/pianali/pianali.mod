/*
*/

# DATI
param nA;               # Numero automobili
set A := 1..nA;         # Insieme automobili
param l {A};            # Lunghezze delle automobili [m]
param su {A} binary;    # su[a] = 1 sse a deve stare sopra
param maxInf;           # Massimo allungamento complessivo dei pianali inferiori [m]
param nC;               # Numero di camion disponibili (arbitrario)
set C := 1..nC;         # Insieme dei camion disponibili
set MR;                 # Insieme delle parti di un camion (motrice e rimorchio)
set L;                  # Insieme dei livelli dei pianali di un camion (sup e inf)
set P := MR cross L;    # Insieme dei pianali di un camion (prodotto cartesiano di MR e L)
param lP {P};           # Lunghezza di ciascun pianale [m]
param al {P};           # Allungamento consentito per ciascun pianale [m]

# VARIABILI
var x {C,P,A} binary;   # x[c,p,a] = 1 sse metto l'automobile a sul pianale p del camion c
var all {C,P} >= 0;     # Allungamento dei pianali di ciascun camion
# (AUSILIARIE)
var y {C} binary;       # y[c] = 1 sse utilizzo il camion c        

# VINCOLI
/*
l'allungamento totale dei pianali (inferiori) non deve superare il limite [m]
Non serve che specifichi che i pianali sono inferiori, tanto quelli sup non si allungano.
*/
subject to AllungamentoInf {c in C}: sum {(p1,p2) in P} all[c,p1,p2] <= maxInf;

# L'allungamento di ciascun pianale deve sottostare alla relativa soglia [m]
subject to AllungamentoPianale {c in C, (p1,p2) in P}: all[c,p1,p2] <= al[p1,p2];

# Non bisogna eccedere la capacità di nessun pianale di nessun camion (variabile in base ad all) [m]
subject to Capacity {c in C, (p1,p2) in P}: 
    sum {a in A} l[a] * x[c,p1,p2,a] <= lP[p1,p2] * y[c] + all[c,p1,p2];

# Ogni macchina dev'essere caricata su un solo camion (per ogni macchina un pianale di un camion)
subject to Assegnamento {a in A}: sum {c in C, (p1,p2) in P} x[c,p1,p2,a] = 1;

# Automobili che non possono stare nel pianale inferiore
subject to Superiore: 
    sum {c in C, (p1,p2) in P, a in A: p2 = 1 and su[a] = 1} x[c,p1,p2,a] = 0;

# OBIETTIVO
# Minimizzare il numero di camion effettivamente utilizzati
minimize z: sum {c in C} y[c];

##########################
data;

param nC := 2;     # a caso

set MR := Motrice Rimorchio;
set L := Inferiore Superiore;

param nA := 16;
param maxInf := 1.5;
param:  l   su  :=
 1    3.3    0
 2    3.8    1
 3    3.8    1
 4    3.4    0
 5    4.0    0
 6    3.8    0
 7    3.8    0
 8    3.9    0
 9    4.0    0
10    4.0    0
11    3.5    0
12    3.6    1
13    3.7    1
14    3.5    0
15    4.0    0
16    3.5    0;

param lP :  Inferiore   Superiore   :=
Motrice          7.0    7.1
Rimorchio        7.0    7.2;

param al :  Inferiore   Superiore  :=
Motrice           1.0   0.0
Rimorchio         1.0   0.0;

end;
