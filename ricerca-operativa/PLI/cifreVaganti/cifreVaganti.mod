/*
guarda il problema così:
abbiamo un insieme di posizioni (4) e un insieme di cifre
che possono occupare queste posizioni (da 1 a 9, quindi 9)

si tratta solo di mappare, tramite variabili binarie, le 
cifre sulle posizioni, rispettando i vari vincoli
non si ha funzione obiettivo
*/

# DATI
param N;                            # Numero di posizioni; 1 = unità, 4 = migliaia
set Cifre := 1..9;                  # Cifre (non c'è lo 0)
set Posizioni := 0..N-1;            # Posizioni, dove la posizione i pesa 10^i
set CifreSomma within Cifre;        # Cifre del numero somma
param somma;                        # Somma
param k;                            # Numero di cifre della somma che devono comparire nei numeri

# VARIABILI
var x {Posizioni, Cifre} binary;    # Assegnamento (x[i,j] = 1 sse nella posizione i c'è la cifra j)

# Ausiliarie
var n1;         # Numero "originale"
var n2;         # Numero con cifre spostate

# VINCOLI
/*
Vincoli per fissare variabili ausiliarie n1 e n2
*/
subject to Det_n1 : 
    n1 = sum {i in Posizioni, j in Cifre} x[i,j] * j * 10^i;

subject to Det_n2 : 
    n2 = sum {j in Cifre} x[0,j] * j * 10^3 +
    sum {j in Cifre} x[1,j] * j * 10^2 + 
    sum {j in Cifre} x[2,j] * j * 10^0 + 
    sum {j in Cifre} x[3,j] * j * 10^1;

/*
# Ogni cifra può apparire al massimo in una posizione 
aka le cifre devono essere tutte diverse
*/
subject to TutteDiverse {j in Cifre} :
    sum {i in Posizioni} x[i,j] <= 1;

/*
Per ogni posizione dev'esserci esattamenta 1 cifra "attiva"
*/
subject to Assign {i in Posizioni}: sum {j in Cifre} x[i,j] = 1;

/*
La somma dei due numeri deve fare somma.
*/
subject to Somma : n1 + n2 = somma;

/*
Esattamente k cifre della somma devono comparire nei numeri.
*/
subject to CifreSpeciali:
    sum {i in Posizioni, h in CifreSomma} 
    x[i,h] = k;

# OBIETTIVO
# Nessun obiettivo, è un esercizio di soddisfazione dei vincoli

##########################
data;

param N := 4;
param somma := 8612;
param k := 2;
set CifreSomma := 1 2 6 8;

end;