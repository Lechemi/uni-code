/*
Disclaimer: è un problema molto difficile

TESTO:
Un magazzino automatico è fatto da una matrice rettangolare di celle 
di uguale dimensione, ciascuna delle quali contiene un box con dei 
materiali. Una delle posizioni nella matrice è denominata "origine" 
e non contiene materiali bensì funge da interfaccia con l'esterno. 
È la posizione da cui devono essere prelevati i box che vengono immessi 
nel magazzino e a cui devono essere portati i box che devono uscire dal 
magazzino. Un carrello può muoversi in linea retta da qualsiasi 
posizione a qualsiasi altra, prelevare un box o consegnare un box. 
Il carrello ha capacità pari a 2 box. Sono dati un insieme di ordini 
di prelievo e un insieme di ordini di consegna. I primi richiedono 
l'estrazione di un box dal magazzino, i secondi l'inserimento di un 
box nel magazzino. Ciascun ordine può essere soddisfatto accedendo 
a una data posizione.
Le posizioni associate agli ordini sono tutte diverse tra loro. 
Gli ordini possono essere soddisfatti in qualsiasi sequenza ed il 
carrello può soddisfare sia ordini di consegna che di prelievo tra 
due visite consecutive all'origine. Tuttavia, se nello stesso viaggio 
il carrello esegue sia consegne che prelievi, le consegne devono essere 
eseguite prima e i prelievi dopo. Si vuole pianificare in modo ottimale 
i movimenti del carrello in modo da minimizzare la distanza 
complessivamente percorsa per soddisfare tutti gli ordini.

Con "viaggio" si intende una sequenza Origine -> ... -> Origine
Cioè: parto dall'origine, poi vado in giro nel magazzino (obv non
passando dall'origine) e poi torno all'origine.

Si può vedere la sequenza dei movimenti come una serie di viaggi,
che ovviamente hanno lunghezza limitata perché il robot non può 
portare in giro infiniti pacchi.

NB: Un prelievo si considera "eseguito" dal momento in cui prendo
il pacco dal magazzino. Quello che dice il problema è che non si può
fare, durante uno stesso viaggio: Prelievo -> ... -> Consegna
Ma si può fare: Consegna -> ... -> Prelievo

In sostanza, tutti i siti di prelievo/consegna, insieme all'origine, 
costituiscono i nodi di un grafo.
Devo solo capire come collegare questi nodi (rispettando ovviamente i
vari vincoli), formando diversi viaggi.
Si tratta quindi di un problema di scelta degli archi.
Ogni arco ha una lunghezza (calcolabile) e devo minimizzare la somma
delle lunghezze degli archi che ho scelto.

Nota che non potrò definire in che ordine i viaggi verranno percorsi,
ma non mi interessa! 
Mi interessa però che un viaggio sia sensato.

*/

# DATI
param nC;               # Numero colonne
param nR;               # Numero righe
param o;                # Posizione dell'origine
param dimh;             # Dimensione orizzontale [m]
param dimv;             # Dimensione verticale [m]
param nP;               # Numero prelievi
param nD;               # Numero consegne (delivery)
set P := 1..nP;         # Insieme prelievi
set D := 1..nD;         # Insieme consegne
param sitoP {P};        # Sito di ogni prelievo (da dove devo prendere il pacco per portarlo all'origine)
param sitoD {D};        # Sito di ogni consegna (dove devo mettere il pacco quando arriva all'origine)

# Dati ausiliari (per tradurre il numero di sito in una posizione riga-colonna)
# Esempio: il sito 22 viene tradotto in: riga 2, colonna 9
param rowP {p in P} := (sitoP[p]-1 div nC) + 1;         # Riga di ogni sito di prelievo
param rowD {d in D} := (sitoD[d]-1 div nC) + 1;         # Riga di ogni sito di consegna
param colP {p in P} := (sitoP[p]-1 mod nC) + 1;         # Colonna di ogni sito di prelievo
param colD {d in D} := (sitoD[d]-1 mod nC) + 1;         # Colonna di ogni sito di consegna
# Origine
param row0 := (o-1 div nC) + 1;
param col0 := (o-1 mod nC) + 1;

# Distanze per ciascun tipo di spostamento
# Esempio: dOP[i] = distanza dell'arco i (di tipo OP)
# NB: dOP = dPO, non mi servono entrambe (stesso per D)
param dOP {p in P} := sqrt((dimh * (col0 - colP[p]))^2 + (dimv * (row0 - rowP[p]))^2);
param dOD {d in D} := sqrt((dimh * (col0 - colD[d]))^2 + (dimv * (row0 - rowD[d]))^2);
param dPP {i in P, j in P} := sqrt((dimh * (colP[i] - colP[j]))^2 + (dimv * (rowP[i] - rowP[j]))^2);
param dDP {d in D, p in P} := sqrt((dimh * (colD[d] - colP[p]))^2 + (dimv * (rowD[d] - rowP[p]))^2);
param dDD {i in D, j in D} := sqrt((dimh * (colD[i] - colD[j]))^2 + (dimv * (rowD[i] - rowD[j]))^2);






# VARIABILI
# Ho una variabile per ogni tipo di arco
# Nota che non ho xPD, perchè non posso andare da un sito di ritiro quello di una consegna
# per xPP e xDD creo la variabile solo per gli indici non uguali
var xOP {P} binary;     # x[i] = 1 se, ad un certo punto, vado dall'origine ad un sito di ritiro
var xOD {D} binary;
var xPO {P} binary;
var xDO {D} binary;
var xPP {p1 in P, p2 in P: p1 != p2} binary;   # x[i,j] = 1 se, ad un certo punto, vado dal sito di ritiro i al sito di ritiro j
var xDP {D,P} binary;
var xDD {d1 in D, d2 in D: d1 != d2} binary;






# VINCOLI
/*
Ogni prelievo dev'essere soddisfatto, ossia:
dev'esserci esattamente un arco entrante e uno uscente
per ogni sito di prelievo.

Tutti i possibili archi entranti sono:
- quelli dall'origine: xOP
- quelli da un altro sito di prelievo (p2): xPP
- quelli da un sito di consegna (d): xDP

Stesso discorso (leggerm. diverso) per gli uscenti
*/
subject to EntrantiP {p in P} : 
    xOP[p] + sum {p2 in P: p2 != p} xPP[p2,p] + sum {d in D} xDP[d,p] = 1;
subject to UscentiP {p in P} : 
    xPO[p] + sum {p2 in P: p2 != p} xPP[p,p2] = 1;

# Stesso discorso per delivery
subject to EntrantiD {d in D} :
    xOD[d] + sum {d2 in D: d2 != d} xDD[d2,d] = 1;
subject to UscentiD {d in D} : 
    xDO[d] + sum {p in P} xDP[d,p] + sum {d2 in D: d2 != d} xDD[d,d2] = 1;

/*
Vincoli per la capacità del carrello, max 2.
In sostanza devo proibire 3 prelievi di fila o 3 consegne di fila.
Questo costringe automaticamente il carrello a 
tornare all'origine.
*/
subject to No3P {p1 in P, p2 in P, p3 in P: p1 < p2 and p2 < p3} :
    xPP[p1, p2] + xPP[p2, p3] + xPP[p1, p3] +
    xPP[p2, p1] + xPP[p3, p2] + xPP[p3, p1] <= 1;

subject to No3D {d1 in D, d2 in D, d3 in D: d1 < d2 and d2 < d3} :
    xDD[d1, d2] + xDD[d2, d3] + xDD[d1, d3] +
    xDD[d2, d1] + xDD[d3, d2] + xDD[d3, d1] <= 1;






# OBIETTIVO
/*
Minimizzare la distanza totale percorsa.
Per ogni tipo di arco, faccio una somma su tutti quelli
che ho scelto, e li moltiplico per le relative distanze.
*/
minimize z: sum {p in P} ((xOP[p] + xPO[p]) * dOP[p]) + 
            sum {d in D} ((xOD[d] + xDO[d]) * dOD[d]) + 
            sum {i in P, j in P: i != j} (xPP[i,j] * dPP[i,j]) + 
            sum {i in D, j in D: i != j} (xDD[i,j] * dDD[i,j]) + 
            sum {d in D, p in P} (xDP[d,p] * dDP[d,p]); 

##########################
data;

param nC := 13;
param nR := 5;
param o := 33;
param dimh := 1;
param dimv := 0.6;
param nD := 9;
param nP := 7;

param sitoD := 
1   24
2   39
3   12
4   60
5   48
6   49
7   42
8   19
9   5;

param sitoP := 
1   38
2   26
3   11
4   9
5   63
6   18
7   55;

end;
