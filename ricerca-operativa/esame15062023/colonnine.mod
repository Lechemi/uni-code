/*

Il problema è abbastanza straight-forward.

Posso rappresentare la città come una matrice bidimensionale a coordinate i,j. C[i,j] mi dice la domanda per quella cella.
Ci sarà poi una matrice per ogni tipo di colonnina: x1 e x2 entrambe a coordinate i,j. x1[i,j] mi dice il numero di colonnine
di tipo 1 che colloco nella cella i,j. Stessa cosa per x2.
griglia ausiliaria in cui g[i,j] = C[i,j] - 

*/

# DATI
param nR;							# Numero righe
param nC;							# Numero colonne
set R := 1..nR;						# Insieme delle righe
set C := 1..nC;						# Insieme delle colonne
param d {R,C};						# Griglia della domanda [n. ricariche]
param nT;							# Numero tipi di colonnine
set T := 1..nT;						# Insieme dei tipi di colonnine
param cost {T};						# Costo di ogni tipo	[k€]
param capacity {T};					# Capacità di ogni tipo [n. ricariche]
param budget;						# Budget disponibile [k€]

# VARIABILI
var x {T,R,C} integer >= 0;			# Se x[t,r,c] = k, allora metto k colonnine di tipo t nella cella r,c

/*
Se c[i,j,r,c] = k, allora metto a disposizione alla cella (i,j) k ricariche dalla colonnina posta in (r,c)
*/
var c {R,C,R,C} integer >= 0;

# (AUSILIARIE)
var spent = sum {t in T, r in R, h in C} x[t,r,h] * cost[t];	# Soldi spesi

# VINCOLI

# Non posso superare il budget
subject to Budget : spent <= budget;

/*
Ogni cella può essere servita solo dalle colonnine dell'area "a mossa di Re".
per ogni i,j e per ogni r,c tali che r,c non stanno nell'area a mossa di re attorno a i,j si ha 
c[i,j,r,c] = 0 
*/
subject to FuoriArea {i in R, j in C, r in R, h in C : h > j+1 or h < j-1 or r > i+1 or r < i-1}:
	c[i,j,r,h] = 0;

/*
Le k ricariche che metto a disposizione alla cella (i,j) devono venire da una colonnina che possa coprirle (e quindi
che ovviamente sia presente).
(non serve esplicitare che r,c dev'essere dentro l'area mossa di re, il vincolo precedente è già abbastanza)

la somma di tutte le ricariche messe a disposizione dalla/e colonnina/e in posizione (r,h) 
dev'essere = alla sua/loro capacità

*/
subject to Capacity {r in R, h in C}:
	sum {i in R, j in C} c[i,j,r,h] = sum {t in T} x[t,r,h] * capacity[t];
	
/*
L'offerta per una cella (i,j) non può superare la domanda della cella (i,j)
Ossia: la somma di tutte le ricariche messe a disposizione dalle colonnine che "si occupano" della cella (i,j)
non può superare la domanda di (i,j).
*/
subject to LimiteDomanda {i in R, j in C}:
	sum {r in R, h in C} c[i,j,r,h] <= d[i,j];


# OBIETTIVO
/*
Servire la massima domanda, ossia massimizzare il numero di ricariche messe a disposizione. I vincoli impongono che 
queste ricariche siano ben collocate, quindi posso preoccuparmi solo di massimizzarne il numero.
*/
maximize z1 : sum {i in R, j in C, r in R, h in C} c[i,j,r,h];

/*
Il secondo obiettivo si può fare solo ragionando, se guardi la soluzione è spiegato bene.
*/

##########################
data;

param nR := 16;
param nC := 9;

param d : 1 2 3 4 5 6 7 8 9 :=
1		0 0 2 0 0 0 1 0 1	
2		1 0 0 0 0 1 0 0 0
3		0 1 0 2 0 0 0 0 0
4		1 0 0 0 0 1 0 0 0
5		0 0 1 0 0 0 0 0 2
6		1 0 0 0 0 1 0 0 0
7		0 0 0 1 0 0 0 0 0
8		0 1 0 0 0 0 0 0 1
9		0 0 0 1 0 2 0 0 0
10		0 0 0 0 0 0 0 0 1
11		1 1 0 0 0 0 0 0 0
12		0 0 0 1 0 2 0 0 1
13		1 0 0 0 0 0 0 0 0
14		0 0 0 1 0 1 0 1 0
15		1 0 0 0 0 0 0 0 1
16		1 0 2 0 0 0 2 0 0;

param nT := 2;

param:  cost		capacity	:=
1		50			  1
2		100			  3;

param budget := 1200;

end;