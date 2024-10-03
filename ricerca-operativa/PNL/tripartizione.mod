/*

*/

# DATI
param nC;						# Numero di cluster di punti
set C := 0..nC-1;					# Insieme dei cluster

param nP {C}; 					# Cardinalità di ogni cluster
set P {k in C} := 1..nP[k];		# Insieme dei punti di ogni cluster

param x {k in C, P[k]};			# Coordinata x del punto P[k] del cluster k
param y {k in C, P[k]};			# Coordinata y del punto P[k] del cluster k 		

# VARIABILI
# Coodinate dell'origine delle semirette
var xc;			
var yc;
/*
Siccome ci possono essere semirette verticali, non va bene la forma
y = mx + q, perché m può essere infinito. Quindi serve la forma esplicita:
ax + bx + c = 0

Questi sono gli a,b,c relativi alla retta k, che separa il cluster k-1 dal 
cluster k+1
*/
var a {C};
var b {C};
var c {C};

# VINCOLI


/*
Normalizzazione di a,b
Nella videolezione spiega perché bisogna fare questa cosa, ma onestamente
non ho capito del tutto.
*/
subject to Normalizzazione {k in C}:
	a[k]^2 + b[k]^2 = 1;

/*
Dobbiamo imporre che ciascuna (semi)retta passi dall'origine delle semirette.
*/
subject to PassOrigine {k in C}:
	a[k] * xc + b[k] * yc + c[k] = 0; 
	
/*
Separazione dei cluster

La retta k separa il cluster k-1 dal cluster k+1
(ragionare in termini di rette è uguale a ragionare in termini
di semirette in qs caso)

Avrò due vincoli di separazione: uno per il cluster k-1 e uno per il 
cluster k+1; tali vincoli si traducono in disequazioni.
I punti del cluster k-1 dovranno soddisfare la disequazione col <=, perché
stanno a sinistra della retta; quelli del cluster k+1 invece
stanno a destra (decido io).

x[(k-1+nC) mod nC, i] vuol dire tutte le x dei punti del cluster k-1.
Devo fare (k-1+nC) mod nC chiaramente per la situazione k = 0

*/
subject to Sinistra {k in C, i in P[(k-1+nC) mod nC]}:
	a[k] * x[(k-1+nC) mod nC, i] + b[k] * y[(k-1+nC) mod nC,i] + c[k] <= 0;
subject to Destra {k in C, i in P[(k+1) mod nC]}:
	a[k] * x[(k+1) mod nC, i] + b[k] * y[(k+1) mod nC,i] + c[k] >= 0;

# OBIETTIVO
/*
Origine delle semirette più vicina possibile a (0,0)
Sarebbe una distanza punto-punto, ma tolgo la radice quadrata perché minimizzare
quella o minimizzare il suo contenuto è la stessa cosa (si tratta sempre di 
quantità positive).
*/
#minimize z1: xc^2 + yc^2;

# Obiettivo 2 (sicuramente non convessa)
maximize z1: xc^2 + yc^2;


##########################
data;

param nC := 3;

param nP := 
0	8
1	6
2	12;

/*
Per comodità ho trasposto la matrice per avere 0 1 2 (gli indici
del cluster) come colonne.
I punti servono a riempire la matrice dove non ci sono dati, serve perché
la cardinalità dei 3 cluster è diversa.
*/
param x (tr)	0		1		2	:=
1   			-5		-10		-3
2   			10		-4		-1
3    			5		-19		1
4   			3		-11		-2
5   			-1		-7		11
6    			-2		-12		2
7    			2		.		3
8    			10		.		0
9    			.		.		1
10    			.		.		2 
11   			.		.		-3
12   			.		.		-4;


param y (tr)	0		1		2	:=
1   			12		2		-6
2   			8		3		-8
3    			5		15		-5
4   			0		0		-9
5   			3		-3		-1
6    			9		-9		-9
7    			10		.		0
8    			6		.		-2
9    			.		.		-8
10    			.		.		-2 
11   			.		.		-6
12   			.		.		-9;

/*
Inizializzazione per non partire con a,b,c = 0
Sta cosa il prof l'ha fatta guardando un grafico, kinda cheating imo.
*/
var:	a		b		c	:=
0		-0.7	0.7		0
1		0		-1		0
2		0.7		0.7		0;

end;