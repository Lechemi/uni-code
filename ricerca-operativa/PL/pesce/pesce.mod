/*
Mi riferisco ad un giorno solo, quindi tolgo il /giorno dalle unità di misura

RISPOSTE ALLE DOMANDE
1) Conviene vendere solo il 3

2) Per l'1, dovrebbe passare da 10 a 16, quindi 6 euro
Per il 2, dovrebbe passare da 7.5 a 12, quindi 4.5 euro

3) Solo il vincolo sul volume è soddisfatto col segno di uguale

4) Se il prezzo dei frutti scende fino a 12.5, rimane comunque conveniente
comprare solo 4 kg di quelli, e z = 50 euro;

Tra i 9.37500 euro/kg e i 12.49 euro/kg, diventa conveniente prendere 2.5 kg di 3 e 
2.5 kg di 2, e z va da 49.975 (per 12.49) a 42.1875 (per 9.37500).
Il vincolo stringente diventa il tempo.

se il prezzo scende sotto a 9.37500 (quindi anche se diventa 0), conviene solo 
comprare 5.625 kg di 2, e z vale 42.1875; il vincolo stringente è sempre il tempo.
*/

# DATI
param nP;               # Numero di pesci
set P := 1..nP;         # Insieme di pesci
param pr {P};           # Prezzo a cui il pescivendolo rivende poi ogni pesce [euro/kg]
param v {P};            # Volume occupato nella cella frigorifera [cc/kg]
param vTot;             # capacità della cella [cc]
param t {P};            # tempo di preparazione e confezionamento [min/kg]
param tTot;             # tempo disponibile [min]
param qMax {P};         # Limiti di acquisto [kg]
param prezzoFrutti;     # Prezzo dei frutti di mare [euro/kg]

# VARIABILI
var x {P} >= 0;         # Quantità acquistata per ogni tipo di pesce [kg]

# VINCOLI
# Vincolo sul volume della cella [cc]
subject to Volume: sum {p in P} x[p] * v[p] <= vTot;

# Vincolo sul tempo di preparazione e confezionamento [min]
subject to Time: sum {p in P} x[p] * t[p] <= tTot;

# Vincoli sui limiti di acquisto imposti dalla legge [kg]
subject to Law: sum {p in P} x[p]/qMax[p] <= 1;

# OBIETTIVO
# Massimizzare i ricavi dalle vendite [euro]
maximize z : (sum {p in P: p != 3} x[p] * pr[p]) + x[3] * prezzoFrutti;

##########################
data;

# Per analisi parametrica
param prezzoFrutti := 4.37499;

param nP := 3;
param vTot := 50000;
param tTot := 45;

param pr :=
1       10.00
2       7.50
3       20.00;

param v :=
1       10000
2       7500
3       12500;

param t :=
1       11
2       8
3       10;

param qMax :=
1           40
2           60
3           60;

# I limiti si riferiscono alle quantità massime per ogni 
# prodotto se il prodotto fosse l'unico acquistato.

end;
