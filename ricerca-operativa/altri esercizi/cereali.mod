/*
Devo capire, per ogni tipo di cereale, quanto produrre su ciascun terreno, 
considerando ovviamente lo spazio occupato e il volume di 
acqua consumato, in modo da massimizzare il profitto.
*/

# DATI
param nC;           # Numero di tipi di cereali
param nT;           # Numero di terreni
set C := 1..nC;     # Insieme dei tipi di cereali
set T := 1..nT;     # Insieme dei terreni
param a {T, C};     # Consumo di superficie per ogni tipo di cereale su ciascun terreno [acri/q]
param p {C};        # Profitto per tipo di cereale [€/q]
param w {C};        # Consumo di acqua per tipo di cereale [m^3/q]
param s {T};        # Area disponibile per terreno [acri]
param W;            # Volume di acqua disponibile [m^3]


# VARIABILI
var x {T,C} >= 0;     # Produzione di ciascun cereale su ciascun terreno [q]


# VINCOLI

/*
Massima area disponibile su ogni terreno [acri].
Stiamo scrivendo due vincoli in uno usando
{i in T} => un vincolo per ogni terreno.
*/
subject to Area {i in T}: sum {j in C} a[i,j] * x[i,j] <= s[i];

/*
Massimo consumo di acqua [m^3].
*/
subject to Acqua: sum {i in T, j in C} w[j] * x[i,j] <= W;

# OBIETTIVO
# Massimizzare il profitto totale [€]
maximize z: sum {i in T, j in C} p[j] * x[i,j];

##########################
data;

param nC := 6;
param nT := 2;

param a : 	1       2       3       4       5       6 :=
1	       0.02    0.03    0.02    0.016   0.05    0.04
2	       0.02    0.034   0.024   0.02    0.06    0.034;

param :  p             w    :=
1	     48		     0.120
2	     62		     0.160
3	     28		     0.100
4	     36		     0.140
5	    122		     0.215
6	     94		     0.180;

param s :=
1	200
2	400;

param W := 400000;

end;