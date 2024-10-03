/*
*/

# DATI
param nM;           # Numero lamine madre (a piacere)
set M := 1..nM;     # Insieme lamine madre
param nC;           # Numero clienti
set C := 1..nC;     # Insieme clienti
param n {C};        # Numero lamine richieste per cliente
param l {C};        # Larghezza lamine richieste [m]
param L;            # Larghezza lamina madre

# VARIABILI
var x {M,C} integer >= 0;   # x[m,c] = numero di lamine per il cliente c che ricavo dalla lamina madre m 
var y {M} binary;           # 1 se uso la lamina m

# VINCOLI
# Devo soddisfare la domanda (numero di lamine) di ogni cliente
subject to Demand {c in C}: sum {m in M} x[m,c] = n[c];

# Non posso eccedere la larghezza della lamina madre
subject to Width {m in M}: sum {c in C} x[m,c] * l[c] - L * y[m]<= 0;

# OBIETTIVO
minimize z : sum {m in M} y[m];

##########################
data;

param nM := 6;
param nC := 9;
param L := 4;

param:  n                   l   :=
1           3             0.40
2           2             1.30
3           1             2.50
4           2             1.25
5           3             0.50
6           1             1.75
7           2             1.20
8           1             1.10
9           1             0.45;

end;
