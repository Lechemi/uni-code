/*
*/

# DATI
param nG := 12;         # Numero giorni
param nE := 8;          # Numero elettrodomestici
set G := 1..nG;         # Giorni
set E := 1..nE;         # Elettrodomestici
param consumo {G};      # Consumo totale giornaliero [kWh]
param tempo {G, E};     # Tempo di utilizzo giornaliero per elettrodomestico [h]

# VARIABILI
var x {E} >= 0;         # Potenza assorbita da ogni elettrod. [kW]

# Ausiliarie
var e {G};              # Errore giornaliero sul contatore [kW/h]          

# VINCOLI
# Definizione della variabile ausiliaria e.
subject to Errore {i in G} : e[i] = consumo[i] - sum {j in E} x[j] * tempo[i, j];

# OBIETTIVO
/*
In base al testo, ci sono 4 possibili obiettivi.

a) Minimizzare il massimo errore in valore assoluto
La funzione scritta istintivamente sarebbe:
minimize z1 : max(abs(e[i]));
Ma così non è lineare -> linearizzazione tramite variabile 
ausiliaria maxErr e aggiunta di vincoli MinMaxPos e MinMaxNeg
(per eliminare anche il valore assoluto).
SOLUZIONE FINALE:
minimize z1 : maxErr;
# Linearizzazione di max() e di abs() 
subject to MinMaxPos {i in G} : maxErr >= e[i];
subject to MinMaxNeg {i in G} : maxErr >= -e[i];
con var maxErr >= 0 tra le variabili ausiliarie.

b) Minimizzare il valore assoluto del valor medio degli errori
minimize z2 : abs(avgErr);
Dobbiamo linearizzare abs():
SOLUZIONE FINALE:
minimize z2: avgErr;
subject to ErrMedioPos : avgErr >= + sum {i in G} e[i] / nG;
subject to ErrMedioNeg : avgErr >= - sum {i in G} e[i] / nG;
con var avgErr tra le variabili ausiliarie.

c) Minimizzare il valor medio dei valori assoluti degli errori
minimize z3 : sum {i in G} abs(e[i]) / nG;
Dobbiamo linearizzare abs() su ogni giorno, quindi abbiamo
bisogno di nG variabili ausiliarie.
SOLUZIONE FINALE:
minimize z3 : sum {i in G} absErr[i] / nG;
subject to AbsPos {i in G} : absErr[i] >= + e[i];
subject to AbsNeg {i in G} : absErr[i] >= - e[i];
con var absErr {G} tra le variabili ausiliarie.

d) Minimizzare l’errore quadratico medio
minimize z4: sum {i in G} e[i]^2 / nG;
abbiamo un elevamento alla seconda -> non è una funzione lineare
Se ci fai caso, però, stiamo in sostanza minimizzando un parabolide
=> basta trovare la derivata prima (lineare)
e minimizzare quella per trovare il minimo!
Credo sia un caso abbastanza particolare tho, non sempre
possiamo cavarcela così.
[ultimo punto non svolto]

*/



##########################
data;






end;