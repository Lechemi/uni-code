/*
devo capire:
a) in che direzione instradare i dati per ogni coppia origine-destinazione
La dimensione (e quindi il costo) di ogni arco viene da sè, 
una volta stabilita la direzione di instradamento.

x[i,j] = 1 sse i instrada in senso orario i dati per j, 0 altrimenti

*/

# DATI
param N;                    # Numero di nodi
set Nodi := 1..N;           # Nodi (calcolatori) della rete
param t {Nodi, Nodi};       # Matrice (riga = origine; colonna = destinazione) del traffico massimo previsto tra ogni coppia di calcolatori [MB/sec]

# VARIABILI
var x {Nodi, Nodi} binary;  # x[i,j] = 0 sse i instrada in senso orario i dati per j, 1 altrimenti

# Ausiliarie
var q;                      # Capacità massima da installare [MB/sec]

# VINCOLI
/*
Dobbiamo definire q:
q >= traffico su arco i
siccome è una rete circolare, abbiamo un arco per nodo, quindi
posso usare {i in Nodi} per scandire gli archi
ogni arco i va dal nodo i al nodo i+1 (modulo N, perché
l'ultimo arco va dal nodo 10 al nodo 1)

Come esprimo il traffico sull'arco i?
traffico su arco i = (traffico orario da i a i+1) + (traffico antiorario da i+1 a i)
Da qua è difficile spiegare a parole, guarda videolezione 
Comunque bisogna considerare i tre possibili
casi di dove si possono trovare mittente e destinatario rispetto
al nodo (e quindi all'arco) i.
Prima nel caso del senso orario, poi in quello antiorario.
È un po' incasinato perché ci sono, alla fine, non pochi casi diversi.

*/
subject to MinMax1 {i in Nodi: i < N} :
    q >= sum {j in Nodi, k in Nodi : (j <= i and k>= i+1) 
    or (k >= i+1 and j>k) 
    or (j <= i and k<j)} t[j,k] * x[j,k] +
    sum {j in Nodi, k in Nodi : (j <= i and k>= i+1) 
    or (k >= i+1 and j>k) 
    or (j <= i and k<j)} t[k,j] * (1-x[k,j]);

subject to MinMaxSpeciale : 
    q >= sum {j in Nodi, k in Nodi : (k<j)} t[j,k] * x[j,k] +
         sum {j in Nodi, k in Nodi : (k<j)} t[k,j] * (1-x[k,j]); 


# OBIETTIVO
/*
Dobbiamo minimizzare la capacità massima che ci serve installare
*/
minimize z : q;

##########################
data;

param N := 10;

param t : 1   2   3   4   5   6   7   8   9  10 :=
1         9   7   7   8   7   9   9   6   6  10
2         7   7   7   7   7   8   5   7  10   9
3         7   5   8   8   8  10   6  10   9  10
4         7  10   7  10   9   8   5  10   7   9
5        10   5   6  10   5   8   7   9   8   7
6         6   7   8   7   8  10   9   5   9   7
7         9   5   8   9   7  10   8   9  10   7
8         6   5   9   5   6   8  10   6   9   8
9         7   5   5   8   8   8  10   7   9   7
10        8   5   5   7   8   9   7   6   5   8;

end;