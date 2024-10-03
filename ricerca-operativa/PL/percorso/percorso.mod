/*
Il problema si può formulare come un problema di controllo di flusso con variabili non intere.
Vedila così: le variabili indicano la percentuale di persone che passano da ogni strada.

Come vedi nella soluzione, le x ottime sono 0 oppure 1, anche se non sono 
dichiarate binarie. Ha senso se ci pensi: se ragioni in termini di "numero di 
persone che passa dalla strada x", è ovvio che tutte le persone vorranno fare il 
percorso di rischio minore, quindi tutte le persone passeranno dalle stesse strade ed
eviteranno le stesse strade.

Vedi file analisi.sen per capire la risposta alla domanda dell'esercizio.
Nella tabella relativa alle variabili x, possiamo vedere il loro coefficiente nella funzione obiettivo 
(Obj coef), che corrisponde col rischio. Infatti ogni variabile si riferisce ad una strada, ed entra in z
moltiplicata per il rischio di quella strada.
L'analisi ci dice il range entro cui tale coefficiente può variare senza che cambi la soluzione ottima.
Esempio: per quanto riguarda x[2], il suo coefficiente in z è 1 (infatti il rischio della strada 2 è 1), e
può scendere fino a -Inf o salire fino a 4 senza che la soluzione ottima cambi.
Quindi la strada 2 potrebbe tollerare un aumento del rischio fino a 4, ossia un aumento del 300%.
Invece il rischio della strada 23, pari a 4, può al massimo diminuire fino a 2, ma non aumentare! Quindi
è il meno robuso, insieme al rischio della strada 19, che è 2 e deve rimanere tale.
*/

# DATI
param nN;               # Numero di nodi
set N := 1..nN;         # Insieme di nodi
param nS;               # Numero di strade
set S := 1..nS;         # Insieme delle strade
param r {S};            # Rischio di ciascuna strada
param from {S};         # Nodo di inizio di ciascuna strada
param to {S};           # Nodo di fine di ciascuna strada

# VARIABILI
var x {S} >= 0, <= 1;   # x[s] = percentuale di persone che passano dalla strada s

# VINCOLI
/*
Flusso nel primo e nell'ultimo nodo.
La percentuale di persone che parte da una delle strade che ha from = 0 dev'essere 100%.
La percentuale di persone che arriva in una delle strade che ha to = 15 dev'essere 100%.
*/
subject to Start : sum {s in S: from[s] = 0} x[s] = 1;
subject to Goal : sum {s in S: to[s] = 15} x[s] = 1;

/*
Controllo del flusso.
Per ogni nodo "interno", la percentuale di persone che ci entra dev'essere uguale alla percentuale di persone
che ci esce.
Si capisce bene se ti immagini x[s] come la quantità di persone che passa sulla strada s
*/
subject to Flux {n in N: n != 0 and n != 15} : 
    sum {s in S: to[s] = n} x[s] = sum {s in S: from[s] = n} x[s];

# OBIETTIVO
minimize z : sum {s in S} x[s] * r[s];

##########################
data;

param nN := 16;
param nS := 23;

param r :=
1   2      
2   1     
3   2      
4   2      
5   3      
6   2      
7   2      
8   3      
9   2      
10   4      
11   3      
12   2     
13   2     
14   2     
15   1     
16   5     
17   3     
18   1     
19   2     
20   2     
21   3     
22   3     
23   4;   

param from :=
1   0
2   0
3   1    
4   2
5   2
6   3
7   3
8   4
9   4
10  4
11  5
12  6
13  7
14  8
15  8
16  9
17  10
18  11
19  11
20  12
21  13
22  13
23  14;

param to :=
1   1
2   3
3   2    
4   3
5   4
6   5
7   6
8   5
9   9
10  10
11  7
12  7
13  8
14  9
15  11
16  13
17  13
18  12
19  14
20  13
21  14
22  15
23  15;

end;
