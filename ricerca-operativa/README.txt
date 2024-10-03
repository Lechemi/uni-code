comando per far partire il tool di risoluzione:
glpsol --model prova.mod

dove "prova.mod" è il problema da risolvere

se vuoi anche avere il file con la soluzione di base:
glpsol --model prova.mod -o fileSoluzione.out

se vuoi anche avere il file con l'analisi di sensitività:
glpsol --model prova.mod --ranges analisi.sen

have fun


modello:

/*

*/

# DATI

# VARIABILI

# VINCOLI

# OBIETTIVO

##########################
data;


end;

PL
- rete logistica
- bilanciamento containers

PLI
- mediane
- pianali
- rover
