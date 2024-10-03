/*
C'è un solo prodotto!
Ciò che cambia è che abbiamo più periodi, e possiamo
immagazzinare il prodotto per venderlo nei mesi successivi.
Nota anche che non si tratta di massimizzare i profitti (di cui non si parla), 
ma di minimizzare i costi.
*/

# DATI
set Mesi := 1..4;
param d {Mesi};     # Domanda [ton]
param c {Mesi};     # Capacità produttiva [ton]
param p {Mesi};     # Prezzo ingredienti [€/ton]
param s {Mesi};     # Costo unitario di stoccaggio [€/ton]
param y0;           # Stock iniziale in magazzino [ton] (dato mancante)

# VARIABILI
var x {i in Mesi} >= 0, <= c[i];  # Produzione mensile [ton]
var y {Mesi} >= 0;                # Stock di gelato alla fine del mese [ton]
/*
NB: la condizione di non negatività sulle y racchiude il fatto che 
si riesca a soddisfare la domanda mensile! 
*/

# VINCOLI
/*
Conservazione del flusso: ciò che "entra" in magazzino
è il gelato prodotto nel mese + lo stock già presente a inizio mese;
ciò che "esce" è il gelato richiesto (domanda) + lo stock a fine mese.
*/
subject to FlowConservationConstraints {i in Mesi: i > 1} :
    y[i-1] + x[i] = d[i] + y[i];
# Stesso vincolo ma per il primo mese
subject to FlowConservationConstraints1 :
    y0 + x[1] = d[1] + y[1];


# OBIETTIVO
/*
Minimizzare i costi [€], che sono dati, nel mese i, da:
costo della produzione in i per produzione in i + 
costo dello stoccaggio in i per gelato stoccato in i.
*/
minimize z : sum {i in Mesi} (p[i] * x[i] + s[i] * y[i]);

##########################
data;

param y0 := 0;

param :     d       c       p       s   :=
1		    200		400		34		2000
2		    300		500		36		3000
3		    500		300		32		2000
4	        400		500		38		3000;

end;