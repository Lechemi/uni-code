/*

*/

# DATI
param N; 			# Numero oggetti/punti
set P := 1..N;		# Insieme punti
param x {P};		# Coordinata x di ogni punto
param y {P};		# Coordinata y di ogni punto
param l {P};		# Attributo di ogni punto
/*
Dimensionamento di M (vedi sotto per sapere a cosa serve M)

Quanto possono essere lontani dalla retta i punti "sbagliati"? Sicuramente non più 
della distanza massima tra tutte le coppie di punti. La retta necessariamente sta tra i due punti
più distanti tra loro, quindi questa distanza può essere una buona calibrazione per M.
*/
param M := max {i in P, j in P} sqrt((x[i] - x[j])^2 + (y[i] - y[j])^2); 

# VARIABILI
# Parametri della retta nel piano
var a;
var b;
var c;
/*
Variabile binaria che serve a disattivare il vincolo nel caso in cui non sia possibile
separare linearmente i punti. Se un punto non può far altro che capitare dalla parte
sbagliata, allora per lui il vincolo viene disattivato.
*/
var w {P} binary;

# VINCOLI
subject to Normalizzazione: a^2 + b^2 = 1;

/*
Separazione dei punti
Da un lato della retta metto quelli veri, dall'altro quelli falsi.

M = numero molto grande
Al posto di fare >= 0, faccio >= -M * w[i], così se w[i] = 1 (ossia se il punto i è 
dalla parte sbagliata) il vincolo viene disattivato, perché sicuramente 
a * x[i] + b * y[i] + c >= -M

*/
subject to Destra {i in P : l[i] = 1}:
	a * x[i] + b * y[i] + c >= -M * w[i];
subject to Sinistra {i in P : l[i] = 0}:
	a * x[i] + b * y[i] + c <= M * w[i];

# OBIETTIVO
/*
Alla fine l'obiettivo è minimizzare il numero di punti che capitano dalla parte
sbagliata, quindi minimizzare il numero di punti per cui disattivo il vincolo,
quindi minimizzare la somma delle w
*/
minimize z : sum {i in P} w[i];


##########################
data;

param N := 20;

param:	x	y	  l	  :=
1    12   29      1
2    16   26      1
3    24   25      1
4     8    7      1
5    30   50      1
6    11   41      1
7     5    2      1
8     6   11      1
9    40   12      1
10    23   27     1
11    21   43     1
12    51   18     1
13     2   36     0
14     2   33     0
15    11    6     0
16    33    7     0
17    28   45     0
18    25   42     0
19    20   50     0
20    20   18     0;

# Inizializzazione per non farle partire da 0
# Bisettrice del primo quadrante
var a := 0.7;
var b := -0.7;
var c := 0;
 
end;