/*
*/

# DATI
param nT;               # Numero di tipi di oggetto
set T := 1..nT;         # Insieme dei tipi di oggetto
param q {T};            # Quantità di oggetti per ogni tipo
param v {T};            # Volume di ogni tipo di oggetto
param c;                # Capacità dei containers
param nC;               # Numero totale di containers (a piacere)
set C := 1..nC;         # Insieme dei containers

# VARIABILI
var x {C} binary;           # x[c] = 1 se uso il container c
var y {T,C} integer >= 0;   # Numero di oggetti di tipo t che metto nel container c

# VINCOLI

# Non devo eccedere la capacità dei container 
subject to Capacity {i in C}:
    sum {t in T} y[t,i] * v[t] <= c * x[i];

# Tutti gli oggetti devono essere caricati nei containers
subject to Tutti {t in T}:
    sum {i in C} y[t,i] = q[t];

# OBIETTIVO
minimize z : sum {i in C} x[i];

##########################
data;

param nT := 10;
param nC := 10;

param :     q               v     :=
1          68               30
2          90               25
3          10              200 
4          48               40
5          28              105
6          70              150
7          56               18
8          10              250
9          45               54
10          12               67;

param c := 5000;
# I containers alternativi ahnno capacità 3000 o 4000.

end;
