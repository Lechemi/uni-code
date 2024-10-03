/*
Nah this is bs fr dawg how tf should i use this fucking data
No fr dati scomodissimi da usare, bruttissimo. 
*/

# DATI
param nT;			# Numero di tipi di palle diverse
set T := 1..nT;		# Insieme dei tipi di palle
param r {T};		# Raggio di ciascun tipo
param n {T};		# Quantità di palle di ciascun tipo
param nMax; 		# Numero massimo di palline per ciascun tipo (in qs caso 4, per il tipo 4)
set P := 1..nMax;	# Insieme di palline per ciascun tipo (ha cardinalità variabile, but dw)

# VARIABILI
# Dimensioni della scatola
var x;
var y;
var z;
# Coordinate spaziali del centro di ogni pallina per ogni tipo
var xp {T,P};
var yp {T,P};
var zp {T,P};

# VINCOLI
# Nessuna pallina può sovrapporsi
subject to Sovrapposizione {t1 in T, p1 in P, t2 in T, p2 in P: p1 <= n[t1] and p2 <= n[t2] and }:
	

# Bisogna rientrare nella scatola

# OBIETTIVO
minimize V: x*y*z;

##########################
data;

param nP := 6;
param:	r		n	:=
1       2          3
2       4          2
3      	3          3
4      	4          4
5     	2          3
6 		3          2;

end;
