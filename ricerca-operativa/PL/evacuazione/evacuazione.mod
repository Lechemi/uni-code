/*
tempo minimo: 85.27341635
costo corrispondente al tempo minimo: 1084930

costo minimo: 1020000
tempo corrispondente al costo minimo: da 180 ore in su (quindi tengo 180)

sono entrambe soluzioni paretiane perché nessuna domina l'altra

Se il tempo scende a 179, cambia la base e il costo minimo 
sale a 1020011.111. Anche questa soluzione è paretiana: non è dominata
da nessuna delle due sopra.

Tutte le soluzioni relative ad un tempo compreso tra 180 e 85.27341635 
sono paretiane! Sopra 180, il costo non cambia (1020000) ma il tempo peggiora, quindi 
sono tutte soluzioni dominate dalla soluzione relativa a t = 180 e c = 1020000.
Sotto 85.27341635, non ci sono soluzioni ammissibili (85.27341635 è il tempo minimo).
Tutto ciò che c'è in mezzo è pareto-ottimo: il tempo scende e il costo sale; nessuna soluzione
domina l'altra.


1) Il minimo costo con tempo non superiore a 120 ore è 1034595.238 euro. (ho semplicemente messo 
120 come parametro nel vincolo Parametric).

2) il minimo tempo con costo non superiore a 1 250 000 Euro è 85.27341635
(non serve neanche calcolarlo: guarda il costo nella soluzione trovata minimizzando solo il tempo)

3) se t = 120 e c = 1250000, allora sono certamente fuori dalla regione paretiana (basta pensare che 
per t = 120, si potrebbero spendere solo 1034595.238 euro). Ma questa soluzione, anche se paretiana, migliora
solo il costo e non il tempo, che rimane a 120. 
c = 1.04683e+06
t = 100.49583

4) la soluzione si può desumere senza fare alcun calcolo dall’analisi parametrica. 
Infatti la soluzione ottima è il punto di discontinuità della regione paretiana che separa 
i due segmenti adiacenti che hanno coefficiente angolare l’uno superiore e l’altro inferiore 
rispetto al peso indicato dal testo (500 euro/ora)

*/

# DATI
param nS;           # Numero strutture ricettive
param nR;           # Numero rifugi temporanei
set S := 1..nS;     # Insieme strutture ricettive
set R := 1..nR;     # Insieme rifugi temporanei
param c {S};        # Capienza di ciascuna struttura ricettiva [persone]
param n {R};        # Numero di persone in ogni rifugio temporaneo [persone]
param cs {R,S};     # Costo spostamento da ogni rifugio temporaneo ad ogni struttura ricettiva [euro/persona]
param ts {R,S};     # Tempo spostamento da ogni rifugio temporaneo ad ogni struttura ricettiva [ore/persona]
param maxC;         # Massima capacità per ogni collegamento [persone]

# VARIABILI
var x {R,S} >= 0;        # Numero di persone su ciascun collegamento [persone]
# (AUSILIARIE)
var tempo;                  # tempo totale di spostamento [ore]
var costo;                  # costo di spostamento [euro]
var alfa >= 0, <= 1;

# VINCOLI
# Massima capacità per ogni collegamento [persone]
subject to Capacity {r in R, s in S} :
    x[r,s] <= maxC;

# Tutti i rifugi temporanei devono essere svuotati [persone]
subject to Empty {r in R} :
    sum {s in S} x[r,s] = n[r];

# Massima capienza di ciascuna struttura ricettiva [persone]
subject to Capienza {s in S} :
    sum {r in R} x[r,s] <= c[s];

# Definizione del tempo minimo di spostamento [ore]
subject to TempoMax {r in R, s in S} : tempo >= x[r,s] * ts[r,s];
# Definizione dei costi di spostamento [euro]
subject to DefCosti : costo = sum {r in R, s in S} x[r,s] * cs[r,s];

# Vincolo sul tempo massimo per analisi parametrica
subject to Parametric: tempo <= 100.49583;

#subject to TerzoPuntoT: tempo = 120 * alfa;
#subject to TerzoPuntoC: costo = 1250000 * alfa;

# OBIETTIVO
/*
Abbiamo 2 obiettivi in conflitto tra loro.

Il tempo totale di spostamento equivale al tempo necessario per lo spostamento che 
ci mette più tempo. Si tratta quindi di una funzione min-max.
*/
minimize z : costo;

##########################
data;

param nS := 6;
param nR := 3;
param maxC := 400;

param c :=
1     500
2     650
3     550
4     550
5     550
6     400;

param n :=
1      850
2     1250
3      900;

param cs : 1    2   3   4   5   6   :=
1         250  350  300  380  310  340
2         280  420  450  390  375  350
3         360  410  420  400  380  290;

param ts :  1    2    3    4    5    6  :=
1          0.20 0.35 0.40 0.25 0.25 0.30 
2          0.35 0.50 0.45 0.35 0.45 0.40 
3          0.25 0.35 0.45 0.40 0.30 0.40;

end;
