/*
*/

# DATI
param nA;                   # Numero articoli
set A := 1..nA;             # Insieme di articoli
param P {A};                # Numero di pagine per ciascun articolo
param U {A};                # Urgenza per ogni articolo (numero di asterischi)
param nF;                   # Numero di fascicoli
set F := 1..nF;             # Insieme di fascicoli
param nP;                   # Numero di pagine per fasciolo (tutti i fascicoli hanno lo stesso numero di pagine)

# VARIABILI
var x {A, F} binary;        # x[i,j] = 1 => metto l'articolo i sul fascicolo j

# Ausiliarie
var upper;
var lower;

# VINCOLI
# Ogni articolo può essere scritto al massimo in un fascicolo 
# Nota che alcuni articoli possono rimanere fuori del tutto
subject to UnFascicoloPerArticolo {i in A} :
    sum {j in F} x[i,j] <= 1;

# Non devo eccedere il numero di pagine disponibili su ogni fascicolo
subject to LimitePagine {j in F} :
    sum {i in A} x[i,j] * P[i] <= nP;

# Non ho voglia di spiegare lol. im built different
subject to Urgenza {i in A : U[i] != 0} :
    sum {j in F : j+U[i] <= 4} x[i,j] = 1;


# OBIETTIVO
/*
Obiettivo 1: rinviare il minimo numero di articoli
ossia massimizzare gli articoli assegnati
*/
maximize z1: sum {i in A, j in F} x[i,j];

/*
Obiettivo 2: equilibrare i fascicoli
(meno importate di z1)
prima metto sto vincolo come commento e ottimizzo solo z1, una volta
fatto posso inserire il valore ottimo di z1 per vincolare z2.
Most-uniform
*/
#minimize z2: upper - lower;
#subject to maxmin {j in F} : lower <= sum {i in A} x[i,j] * P[i];
#subject to minmax {j in F} : upper >= sum {i in A} x[i,j] * P[i];
#subject to NonPeggiorareZ1 : sum {i in A, j in F} x[i,j] >= 10; # Valore ottimo di z1

##########################
data;

param nA := 12;
param:      P        U :=
1          5         2
2         11         0
3         17         2
4         10         0
5         18         3
6          8         0
7         14         0
8          9         1
9         16         1
10        12         0
11        14         0
12        13         0;

param nP := 44;
param nF := 3;

end;
