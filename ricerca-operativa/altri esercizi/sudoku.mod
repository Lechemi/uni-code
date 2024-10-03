/*
È un problema solo di soddisfacimento vincoli.
*/

# DATI
param N;                    # Numero righe/colonne (è un quadrato)
set R := 1..N;              # Righe
set C := 1..N;              # Colonne
set D := 1..9;              # Cifre (da 1 a 9)
set Mr := 1..3;             # Macrorighe
set Mc := 1..3;             # Macrocolonne

# VARIABILI
var x {R, C, D} binary;     # x[i,j,k] = 1 sse alla posizione i,j scrivo la cifra k

# VINCOLI
# Self explanatory
subject to UnaCifraPerCasella {i in R, j in C} :
    sum {k in D} x[i,j,k] = 1;

# Su ogni riga la cifra deve comparire solo una volta
# Vedila così: "data la riga e la cifra..."
subject to CifraRiga {i in R, k in D} : 
    sum {j in C} x[i,j,k] = 1;

# Su ogni colonna la cifra deve comparire solo una volta
# Vedila così: "data la colonna e la cifra..."
subject to CifraColonna {j in C, k in D} : 
    sum {i in R} x[i,j,k] = 1;

/*
In ogni quadrato 3x3 la cifra deve comparire solo una volta
Il difficile è dire quali sono le coppie i,j che appartengono
allo stesso quadrato 3x3.
Una volta espresse queste, è facile dire che, per ogni k,
la somma su tutte le i,j dello stesso quadrato di x[i,j,k]
deve fare 1.

Possiamo dividere il sudoku in macrorighe e macrocolonne 
di larghezza 3, in modo che la combinazione di queste e
quelle dia un quadrato 3x3.
intuitivamente:
subject to Quadrato {k in D, macroriga mr, macrocolonna mc} :
    sum {riga in mr, colonna in mc} x[riga,colonna,k] = 1;

In questo vincolo sto considerando un sudoku 9x9
*/
subject to Quadrato {k in D, mr in Mr, mc in Mc} :
    sum {i in R, j in C : (i >= 3*mr-2 and i <= 3*mr) and (j >= 3*mc-2 and j <= 3*mc)} x[i,j,k] = 1;


# OBIETTIVO
# Nessun obiettivo

##########################
data;

param N := 9;



end;