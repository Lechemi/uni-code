/*
TESTO
In una gara di tiro al piattello si hanno a disposizione due 
colpi per ogni piattello. Il piattello percorre una traiettoria 
a parabola nota: si conoscono infatti la velocità con cui viene
lanciato il piattello e l'angolo di lancio. Il tiratore deve 
scegliere il momento migliore per sparare, tenendo conto di 
due fattori: la visibilità del piattello e la sua stabilità.
Riguardo alla visibilità si definisce un indice di visibilità 
v direttamente proporzionale, tramite un coefficiente a, 
all'altezza h da terra: v = a h. Il valore di a è l'inverso dell'altezza 
massima raggiunta: dal piattello, sicché l'indice di visibilità varia da 0(livello•del•terreno) 
a 1 (altezza massima-della-traiettoria rispetto Il terreno).
La stabilità invece dipende dalle vibrazioni che•possono subentrare nel moto del piattello 
al passare del tempo: si definisce quindi un indice di stabilità s che decresce col 
passare del tempo secondo la legge s=e^(-t/B),dove B è un coefficiente noto e t è il 
tempo•trascorso•dall'istante-di lancio. Anche l'indice di stabilità-varia quindi tra 0 e 1.
Si suppone che il tempo impiegato dal proiettile per colpire il piattello sia trascurabile 
e che l'effetto dell'attrito sulla traiettoria del piattello sia anch'esso trascurabile.
La probabilità di colpire il piattello è pari al prodotto dei due indici v e s.
Sono noti i valori dei due coefficienti, a=0,05 1/m e B = 0,80 s.
I riflessi del tiratore gli consentono di sparare non prima di 0,5 secondi dalla partenza del
piattello.

Domanda 1: Qual è il tempo impiegato dal piattello per 
raggiungere l'apice della sua traiettoria?

Domanda 2: Qual è l'istante ideale per sparare?

Domanda 3: Si supponga che•il tiratore- abbia•a disposizione due colpi per ogni piattello e 
li possa sparare ad un intervallo di tempo pari ad almeno 0,25 secondi. 
Egli vuole massimizzare la probabilità di colpire il piattello nel peggiore dei due casi. 
Quando deve sparare i due colpi?

Domanda 4: Qual-è-laprobabilità-di-colpire-il-piattello in-ciascuno dei-due-casi?
*/

# DATI
param alfa;				# Inverso di ymax
param beta;				# Coefficiente 
param g;
param tmin;				# Tempo di reazione

param v0;				# Velocità iniziale (trovata al primo punto)
param tmax;				# Tempo impiegato per raggiungere l'apice (trovato al 1 punto)

# VARIABILI
#var v0 >= 0;			# Vel iniziale
#var tmax >= 0;			# Tempo impiegato per raggiungere ymax

var tsparo1 >= tmin;		# Istante dello sparo 1 
var tsparo2 >= tmin;		# Istante dello sparo 2
# (AUSILIARIE)
var ysparo1 = 1/2 * g * tsparo1^2 + v0 * tsparo1;	# Altezza dello sparo 1
var v1 = alfa * ysparo1;			# Indice di visibilità 1
var s1 = exp(-tsparo1/beta);		# Indice di stabilità 1
var ysparo2 = 1/2 * g * tsparo2^2 + v0 * tsparo2;	# Altezza dello sparo 2
var v2 = alfa * ysparo2;			# Indice di visibilità 2
var s2 = exp(-tsparo2/beta);		# Indice di stabilità 2

var Pmin;

# VINCOLI
/*
ymax = 1/2 * g * tmax^2 + v0 * tmax;
ymax = 1/alfa;
subject to Parabola:
	1/alfa = 1/2 * g * tmax^2 + v0 * tmax;
*/

# Sto assumendo che il secondo sparo avvenga dopo il primo
subject to DistanzaSpari : tsparo2 - tsparo1 >= 0.25;

# OBIETTIVO
/*
Domanda 1
Si poteva calcolare tutto per via analitica, ma abbiamo scelto di formularlo
come un problema di ottimizzazione. Vogliamo trovare il tempo impiegato dal 
piattello per raggiungere l'apice della traiettoria, quindi col vincolo Parabola
abbiamo imposto che la parabola arrivi all'altezza 1/alfa, ma vogliamo che 
ci arrivi al suo picco, non in un momento qualsiasi del moto. Quindi minimizziamo
la velocità iniziale per trovare la più bassa parabola che raggiunga 1/alfa;
tale parabola sicuramente lo raggiunge al suo picco.
*/
#minimize z1 : v0;

/*
Domanda 2
Dobbiamo massimizzare la probabilità di colpo a segno (data dalla formula
P = v * s)

(risolto)
P = 0.2397050007
E mi faccio dire dal solutore tsparo: 0.647301;
Quando il piattello è all'altezza di: 10.7673;
*/
#maximize z2 : v * s;

/*
Domanda 3
é un problema max-min
lol con minos funziona, con baron e knitro no.
*/
maximize z3 : Pmin;
subject to maxmin1 : Pmin <= v1 * s1;
subject to maxmin2 : Pmin <= v2 * s2;

##########################
data;

param g := -9.81;
param alfa := 0.05;
param beta := 0.80;
param tmin := 0.5;

# Calcolate
param v0 := 19.8090888;
param tmax := 2.01928;

# Inizializzazione
var tsparo2 := 3;
var tsparo1 := 2.5;

end;