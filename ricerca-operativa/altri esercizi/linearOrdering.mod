/*
NOTA: QUESTO PROBLEMA NON L'HO CAPITO DEL TUTTO E LA SOLUZIONE MI SEMBRA
SBAGLIATA, QUINDI L'HO LASCIATO INDIETRO.

la soluzione ottima dovrebbe essere 662
con nodi in quest'ordine: 3 1 7 4 5 6 2
*/

# DATI
# Nota: dichiaro il grafo come matrice dei pesi
param N;                    # Numero di nodi
set Nodi := 1..N;           # Nodi
param c {Nodi, Nodi};    # Peso di ciascun arco

# VARIABILI
/*
Come facciamo ad esprimere un ordinamento? Ossia, come facciamo
a dire che vogliamo seguire il percorso (esempio a caso)
1 4 6 3 2 7 5?
Usando solo variabili è più difficile di quel che sembra.
Possiamo farlo solo con una matrice di variabili binarie:
x[i,j] = 1 sse i precede j nell'ordinamento scelto
*/
var x {Nodi, Nodi} binary;

# VINCOLI

# OBIETTIVO
/*
Minimizzo la somma dei pesi degli archi che ho scelto. 
that's it
*/
minimize z : sum {i in Nodi, j in Nodi} c[i,j] * x[i,j];

##########################
data;






end;