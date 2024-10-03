/*
lol
*/

# DATI
param nN;           # Numero di nodi
set N := 1..nN;     # Insieme dei nodi
param nA;           # Numero archi
set A := 1..nA;     # Insieme degli archi
param c {A};        # Capacità di ogni arco [ton/d]
param da {A};       # Nodo iniziale di ogni arco
param a {A};        # Nodo finale di ogni arco
param od {N};       # od[n] = 0 se il nodo n è origine, 1 se è destinazione, 2 se è intermedio

# VARIABILI
var x {A} >= 0;     # Flusso su ogni arco [ton/d]

# VINCOLI
# Non devo eccedere la capacità di ciascun arco [ton/d]
subject to Capacity {i in A}: x[i] <= c[i];

#Il flusso che parte dai nodi origine dev'essere lo stesso che arriva ai nodi destinazione [ton/d]
subject to FluxConservationOD: 
    sum {i in A: od[da[i]] = 0} x[i] = sum {j in A: od[a[j]] = 1} x[j];

# Il flusso entrante dev'essere uguale al flusso uscente per ogni nodo intermedio [ton/d]
subject to FluxConservation {n in N : od[n] = 2}:
    sum {i in A : a[i] = n} x[i] = sum {i in A : da[i] = n} x[i];

subject to Parametric : x[5] <= 

# OBIETTIVO
maximize z: sum {i in A: od[da[i]] = 0} x[i];

##########################
data;

param nN := 8;
param nA := 8;

param od :=
1   0
2   0
3   2
4   2
5   2
6   2
7   1
8   1;

param:  da       a       c   :=
1        1       3       58 
2        2       4       31 
3        3       4       40 
4        3       5       12 
5        4       6       66 
6        5       7       59 
7        6       5       25 
8        6       8       36;

end;
