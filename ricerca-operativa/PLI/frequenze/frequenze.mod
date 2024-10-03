/*
dai temi d'esame
*/

# DATI
param N;            # Numero di frequenze
set F := 1..N;      # Insieme delle frequenze
param freq {F};     # Valori delle frequenze
param K;            # Soglia

# VARIABILI
var x {F} binary;   # x[i] = 1 se includo la frequenza i

# VINCOLI
/*
Un vicolo per ogni coppia di frequenze interferenti, ossia:
guardo ogni coppia (mi basta metà matrice (i < j) perché
tanto |freq[j] - freq[i]| = |freq[i] - freq[j]|)
e guardo se la sua differenza è inferiore alla soglia K
NB: sto guardando solo le coppie in cui j > i, quindi so che
la differenza freq[j] - freq[i] è positiva!
Questo perchè le frequenze sono disposte in ordine crescente, quindi
se j > i => freq[j] > freq[i]
*/
subject to Interferenze {i in F, j in F: i < j and freq[j] - freq[i] < K}:
    x[i] + x[j] <= 1;

# OBIETTIVO
# Scegliere il massimo numero di frequenze.
maximize z : sum {i in F} x[i];

##########################
data;

param N := 22;

param K := 5;

param freq :=
1    101
2    103
3    105
4    107
5    109
6    110
7    112
8    114
9    116
10   118
11   121
12   124
13   125
14   128
15   129
16   132
17   133
18   134
19   135
20   136
21   138
22   140;


end;
