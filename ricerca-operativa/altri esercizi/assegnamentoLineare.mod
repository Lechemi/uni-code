/*
Esercizio un po' nascosto nella pagina del Righini
(vedi videolezioni per scoprire dove)
*/

# DATI
param n;                # Numero persone e mansioni
set P := 1..n;          # Persone
set M := 1..n;          # Mansioni
param costo {P, M};     # Costo per ogni persona di ogni mansione

# VARIABILI
/*
In realtà i problemi di assegnamento lineare non necessitano
di variabili binarie, basta vincolare 0 ≤ x ≤ 1.
Ossia, il rilassamento continuo del problema ha la stessa
soluzione ottima di quello originale intero.
[Non abbiamo giustificato questa cosa]
*/
var x {P, M} binary;    # 1 -> i svolge j; 0 -> i non svolge j;

# VINCOLI
# Ogni persona deve svolgere esattamente una mansiona
subject to UnaMansionePerPersona {i in P} : sum {j in M} x[i,j] = 1;
# Ogni mansione dev'essere svolta da esattamente una persona
subject to UnaPersonaPerMansione {j in M} : sum {i in P} x[i,j] = 1;

# OBIETTIVO
minimize z : sum {i in P, j in M} costo[i,j] * x[i,j]; 

##########################
data;

# Non ho voglia/tempo di sistemare sti dati lol
param costo :=
1 35
2 72
3 48
4 26
5 62
6 37
7 59
8 60
9 62
24 62 25 42 37 62 26 73 26 37 37 76 98 84 54 95 42 68 52 62
57 81 34 25 64 14 14 56 94 83 15 89 26 15 37
3 47 52 26 47 73 50 45 6 74 73 12 38 95 60
361263 24 247484 15 517611 21 892144 53 246154 13 253861 50 515451 48 823361 72 363738 50 882526 84
10 2;


end;