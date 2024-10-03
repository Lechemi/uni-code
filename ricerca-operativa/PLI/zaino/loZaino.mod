/*
Esercizio un po' nascosto nella pagina del Righini
(vedi videolezioni per scoprire dove)
*/

# DATI
param n;                # Numero di oggetti
set O := 1..n;          # Insieme di oggetti
param val {O};          # Valore di ogni oggetto [€]
param vol {O};          # Volume di ogni oggetto [l]
param l;                # Capacità dello zaino [l]

# VARIABILI
var x {O} binary;       # Variabile binaria (1 -> dentro; 0 -> fuori)

# VINCOLI
# La somma dei volumi degli oggetti scelti non deve eccedere la capacità
subject to Capacity : sum {i in O} vol[i] * x[i] <= l;

# OBIETTIVO
# Massimizzare il valore degli oggetti contenuti nello zaino
maximize z : sum {i in O} val[i] * x[i];

##########################
data;

param n := 10;

param l := 100;

param :   vol       val     :=
1           8       4 
2           9       6
3           13      40
4           24      15 
5           28      20 
6           36      20 
7           41      21 
8           57      38 
9           68      46
10          70      56;

end;