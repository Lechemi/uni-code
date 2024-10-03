/*

Ogni vertice non coincide necessariamente con l'appiglio, 
ma è collegato all'appiglio tramite un filo!!!!!!

*/

# DATI
set N := 0..2;						# Set degli appigli
param x {N};						# Coordinata x di ciascun appiglio 
param y {N};						# Coordinata y di ciascun appiglio
param z {N};						# Coordinata z di ciascun appiglio
param A;							# Area minima 

# VARIABILI
var xv {i in N} := x[i]/2;			# Coordinata x di ciascun vertice
var yv {i in N} := y[i]/2;			# Coordinata y di ciascun vertice
var zv {i in N} := z[i]/2;			# Coordinata z di ciascun vertice
# (AUSILIARIE)
/*
Definizione dei lati

Il lato i è quello che sta di fronte al vertice i, quindi la sua
lunghezza corrisponde alla distanza tra i vertici diversi da i.

[Al posto di definire la lunghezza del lato, definisco il suo quadrato
per sbarazzarmi della radice quadrata a secondo membro. Devo cercare di 
togliere le radici quadrate dove posso perché la loro derivata è una
frazione che non sempre esiste, il che può portare ad errori del 
solutore e a soluzioni sub-ottime.] -> SIKE!!!!!
Questa cosa ha portato ad errori di "bad-scaling", al solutore non piaceva!!!
Quindi abbiamo fatto normalmente con la radice quadrata.

Per dire che la lunghezza del lato i corrisponde alla distanza 
tra i vertici diversi da i, utilizzo la classe di resto Z3, quindi
i due vertici saranno ((i+1) mod 3) e ((i+2) mod 3).
*/
var lato {i in N} = sqrt((xv[(i+1) mod 3] - xv[(i+2) mod 3])^2 
			  + (yv[(i+1) mod 3] - yv[(i+2) mod 3])^2 
			  + (zv[(i+1) mod 3] - zv[(i+2) mod 3])^2);
			  
# Semiperimetro
var sp = (sum {i in N} lato[i]) / 2;			


/*
Link = lunghezza del collegamento che congiunge vertice ad appiglio
Semplicemente è la distanza dall'appiglio i al vertice i (distanza punto-punto)
*/
var link {i in N} = sqrt((x[i] - xv[i])^2 + (y[i] - yv[i])^2 + (z[i] - zv[i])^2);		

# VINCOLI

/*
Vincolo sull'area minima

Utilizzando la formula di Erone (suggerimento):
sqrt(p*(p-a)*(p-b)*(p-c)) >= A

Il vincolo "originale" sarebbe:
sqrt(sp*(sp-lato[0])*(sp-lato[1])*(sp-lato[2])) >= A;

Ma elevo entrambi i membri al 
quadrato per togliere la radice (stessa motivazione della cosa dei lati)

*/
subject to Area:
	 sp*(sp-lato[0])*(sp-lato[1])*(sp-lato[2]) >= A^2;

# OBIETTIVO
# Minimizzare la lunghezza della struttura portante, ossia i fili dei link e dei lati
minimize w : sum {i in N} (lato[i] + link[i]);

##########################
data;

param : x	y	z :=
0		30	50	50
1		60	10	45
2		40	30	10;

param A := 100;


end;