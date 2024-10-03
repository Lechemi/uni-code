/*
*/

# DATI
param nP;           # Numero pazienti
set P := 1..nP;     # Insieme pazienti
param d {P};        # Durata di ogni intervento [min]
param nS;           # Numero di sale operatorie
set S := 1..nS;     # Insieme delle sale
param t {S};        # Tempo per cui ciascuna sala sarà disponibile [min]

# VARIABILI
var x {P,S} binary; # se x[p,s] = 1, allora opero il paziente p nella sala s

# VINCOLI
# Vincolo sulla durata totale degli interventi in ciascuna sala [min]
subject to InterventiPerSala {s in S}:
    sum {p in P} x[p,s] * d[p] <= t[s];

# Ciascun intervento può essere al max in una sala
subject to SalePerIntervento {p in P}:
    sum {s in S} x[p,s] <= 1;

# OBIETTIVO
# Massimizzare il numero di interventi svolti
maximize z: sum {p in P, s in S} x[p,s]; 

##########################
data;

param nP := 10;
param d :=
1		120
2		60
3		75
4		80
5		130
6		110
7		90
8		150
9		140
10		180;

param nS := 3;

param t :=
1   360
2   360
3   420;

end;
