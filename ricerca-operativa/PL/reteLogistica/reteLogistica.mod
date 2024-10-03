/*

*/

# DATI
param n;            # Numero di nodi
set N := 1..n;      # Insieme dei nodi
param d {N,N};      # Distanze tra ogni nodo [km]
param ecc {N};      # Eccesso [unità]
param dif {N};      # Difetto [unità]
param c;            # Costo unitario di trasporto [Euro/(container*km)]
param cap;          # Capacità di ogni tratta [unità]

# VARIABILI
var x {N,N} >= 0;   # x[a,b] = container trasportati sull'arco (a,b) [unità]

# VINCOLI
# Non posso eccedere la capacità
subject to Capacity {i in N, j in N}:
    x[i,j] <= cap;

/*
I container in eccesso + quelli che arrivano da fuori devono essere uguali a quelli
in difetto + quelli che escono. 
Con questo vincolo esprimo sia la conservazione del flusso che la necessità di 
bilanciare i container! :)
*/
subject to FluxConservation {i in N}:
    ecc[i] + sum {j in N : j != i} x[j,i] = dif[i] + sum {j in N : j != i} x[i,j];

# OBIETTIVO
# Minimizzare il costo
minimize z: sum {i in N, j in N} x[i,j] * d[i,j] * c;

##########################
data;

param n := 10;
param c := 0.80;
param cap := 50;

param d:     1    2    3    4    5    6    7    8    9   10 :=
 1            0  250  130  600  660  720  700  840  850  670
 2          250    0  240  550  620  700  800  750  640  580
 3          130  240    0  580  650  710  650  660  640  700
 4          600  550  580    0  240  150  240  260  250  220
 5          660  620  650  240    0  190  310  300  280  180
 6          720  700  710  150  190    0  320  310  210  260
 7          700  800  650  240  310  320    0  130  140  180
 8          840  750  660  260  300  310  130    0   90  110
 9          850  640  640  250  280  210  140   90    0  120
10          670  580  700  220  180  260  180  110  120    0;

param: ecc        dif :=
 1      60         0
 2      75         0
 3       0        95
 4       0        80
 5      15         0
 6      90         0
 7       0        15
 8       0        55
 9       0        75
10      80         0;

end;
