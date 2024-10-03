/*
La regione ammissibile è concava (basta spostare) un cerchietto facendolo attraversare tutti gli altri).
Quindi il problema non è convesso i think.
*/

# DATI
param K;		# Numero di cerchi unitari
set C := 1..K;	# Insieme di cerchi unitari
set R := 0..2;	# Insieme delle rette che compongono il triangolo

# VARIABILI
# Coordinate dei centri dei cerchi unitari
var x {C};
var y {C};
# Parametri delle rette che compongono il triangolo
var a {R};
var b {R};
var c {R};
# Coordinate del centro e raggio della circonferenza più grande
var xC;
var yC;
var r >= 0;
# Coordinate delle intersezioni tra le rette
var xI {R};
var yI {R};

# VINCOLI
# La distanza tra i centri di qualsiasi coppia di cerchietti dev'essere ≥ 2 (no sovrapposizioni)
subject to SeparazioneCerchi {i in C, j in C: i < j}: 
	sqrt((x[i]-x[j])^2 + (y[i]-y[j])^2) >= 2;
	
# Normalizzazione delle rette
subject to Normalizzazione {i in R}: a[i]^2 + b[i]^2 = 1;

# Le tre rette non possono essere parallele (questo esclude anche che siano coincidenti)
# Forse basta una buona inizializzazione per questo
	
/*
Il triangolo deve contenere tutti i cerchietti, aka:
Per ogni retta, tutti i cerchietti devono starle a destra, aka:
la distanza punto-retta tra centro del cerchietto e retta dev'essere >= 1

(vedi esercizio "classificatoreAutomatico" se sei in dubbio)
*/
subject to Contain {i in R, j in C}:
	a[i]*x[j] + b[i]*y[j] + c[i] >= 1;
	
# Definizione delle intersezioni tra le rette
subject to Appartenenza {i in R}:
	a[i]*xI[i] + b[i]*yI[i] + c[i] = 0;
subject to Intersezione {i in R}:
	a[(i+1) mod 3]*xI[i] + b[(i+1) mod 3]*yI[i] + c[(i+1) mod 3] = 0;
	
# Le tre intersezioni devono appartenere alla circonferenza grande
subject to BigCircle {i in R}: (xI[i]-xC)^2 + (yI[i]-yC)^2 = r^2;

# OBIETTIVO
minimize z: r;

##########################
data;

param K := 8;
var:	x	y	:=
1		1	1
2		-1	-1
3		1	-1
4		-1	1
5		2	2
6		-2	-2
7		2	-2
8		-2	2;

var:	a	b	c	:=
0		0	1	10
1		1	-1	5
2		-1	-1	5;

var:	xI	yI	:=
0		-12	-5
1		0	7
2		12	-5;

var r := 10;

end;
