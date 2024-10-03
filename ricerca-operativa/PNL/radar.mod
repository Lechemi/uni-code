/*
*/

# DATI
param nM;			# Numero di misurazioni
set M := 1..nM;		# Insieme misurazioni
param t {M};		# Angolo relativo ad ogni misurazione [rad]
param d {M};		# Distanza relativa ad ogni misurazione [km]

# VARIABILI
# Parametri della retta che meglio rappresenta la traiettoria rettilinea della nave
var a;
var b;
var c;
# (AUSILIARIE)
var error {M};			# Errore come definito nel testo (sia positivo che negativo) [km]
var maxAbsErr >= 0;		# Massimo errore in valore assoluto [km]
var absError {M} >= 0;	# Errore in valore assoluto relativo a ciascuna misurazione [km]
var y {M} binary;		# se y[m] = 1, allora posso trascurare la misurazione m	

# VINCOLI
# Vincolo di normalizzazione della retta
subject to Normalizzazione: a^2 + b^2 = 1;

/*
Definizione di error [km]
Differenza tra: 
la distanza tra il radar e il punto rilevato: sqrt((d[m] * cos(t[m]))^2 + ((d[m] * sin(t[m]))^2)
e
la distanza tra il radar e il punto calcolato
Il punto calcolato è l'intersezione tra la retta, avente angolo rilevato, passante per l'origine e la retta calcolata.
Quindi ha coordinate: x = -c/(a+bm) e y = -mc/(a+bm) 

Ogni punto rilevato dal radar ha coordinate x = dcos(t), y = dsin(t)

[per l'ultimo punto]
Moltiplico l'errore per 1-y, per renderlo 0 nel caso dell'outlier (y = 1), che non fa testo
*/
subject to DefError {m in M}: 
	error[m] = (1-y[m]) * (sqrt((d[m] * cos(t[m]))^2 + ((d[m] * sin(t[m]))^2)) - 
			   sqrt((-c / (a + b*t[m]))^2 + (-c*t[m] / (a + b*t[m]))^2));
			   
# Posso trascurare al massimo una misurazione
subject to Outlier: sum {m in M} y[m] <= 0;

# OBIETTIVO
/*
1) minimizzare il massimo errore in valore assoluto [km]
Si tratta di un problema min-max.
Anche se è problema già non lineare (basta guardare i vincoli), ho linearizzato il valore assoluto per non 
introddurre altre non-linearità non necessarie.

Quello trovato è un ottimo locale. Anche se la regione ammissibile è convessa 
(l'unico vincolo sulla retta è quello di normalizzazione), la funzione obiettivo non lo è.
Basta osservare che, ruotando la retta ottenuta di 180 gradi su un suo punto qualsiasi, si ottiene lo stesso
valore ottimo di z, passando per dei valori sub-ottimi durante la rotazione.  

Objective 1.520058592
a = 0.426144
b = -0.904655
c = 3.23018

minimize z1: maxAbsErr;
subject to maxerr1 {m in M} : maxAbsErr >= error[m];
subject to maxerr2 {m in M} : maxAbsErr >= -error[m]; 
*/



/*
2) Minimizzare la somma dei valori assoluti degli errori [km]
Anche qua linearizzo ciascun valore assoluto.

Per lo stesso motivo descritto nel commento relativo all'obiettivo del punto 1, anche questo ottimo è locale.

Objective 4.771602763
a = 0.42998
b = -0.902838
c = 3.20105

minimize z2 : sum {m in M} absError[m];
subject to Abs1 {m in M}: absError[m] >= error[m];
subject to Abs2 {m in M}: absError[m] >= -error[m];
*/

/*
3) minimizzare l’errore quadratico medio. [km^2]
Di nuovo si tratta di un ottimo locale.

Objective 0.8496959182
a = 0.430445
b = -0.902617
c = 3.25979
*/
minimize z3 : (sum {m in M} error[m]^2) / nM;

/*
Outlier
L'outlier in questo caso è l'osservazione 6.
a = 0.429971
b = -0.902843
c = -3.18633
Objective 0.1522450609

Di nuovo si tratta di un ottimo locale.
*/

##########################
data;

param nM := 8;
param:	t		d	:=
1	-0.036	 7.5
2	 0.124	10.5
3	 0.208	13.5
4	 0.260	16.5
5	 0.294	19.5
6	 0.318	26
7	 0.337	27
8	 0.351	30;

# Inizializzazione delle variabili
var a := -0.3;
var b := 0.8;
var c := 3;

end;
