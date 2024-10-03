/*
Discussione ottimalità e unicità.
Il problema non è convesso; basta osservare che, con opportune trasformazioni geometriche, posso scambiare la 
posizione di due rette e ottenere lo stesso rettangolo.

*/

# DATI
param nP;           # Numero di punti
set P := 1..nP;     # Insieme punti
param x {P};        # Coordinate x dei punti
param y {P};        # Coordinate y dei punti
set R := 0..3;		# Insieme delle rette necessarie a fare un rettangolo

# VARIABILI
var a {R};		
var b {R};
var c {R};
# Coordinate di un punto generico appartenente alla retta 0 e di un punto della retta 2
var x0;
var y0;		 
var x1;
var y1;
var d1 = abs(a[2]*x0 + b[2]*y0 + c[2]);		# Distanza tra retta 0 e retta 2
var d2 = abs(a[3]*x1 + b[3]*y1 + c[3]);		# Distanza tra retta 1 e retta 3

# VINCOLI
subject to Normalizzazione {r in R}: a[r]^2 + b[r]^2 = 1;

# Ogni retta dev'essere perpendicolare alla precedente
subject to Perpendicolari {r in R}: a[r]*a[(r+1) mod 4] + b[r]*b[(r+1) mod 4] = 0;

/*
Questi vincoli impongono che la "destra" della retta 0 sia la "sinistra" della retta 2 (stesso per 1 e 3)
Immagina di percorrere il rettangolo in senso orario. Questi vincoli impongono che la tua destra sia sempre rivolta
verso il centro del rettangolo. Serve per le disequazioni! Vedi vincolo Destra. Senza questi due vincoli, quel vincolo
potrebbe essere rispettato anche da due coppie di rette coincidenti, che hanno la destra dalla stessa parte e quindi
soddisfano il vincolo.
*/
subject to Verso_a {r in R}: a[r] = -a[(r+2) mod 4];
subject to Verso_b {r in R}: b[r] = -b[(r+2) mod 4];

# I punti devono stare a destra di ogni retta
subject to Destra {r in R, p in P}: a[r]*x[p] + b[r]*y[p] + c[r] >= 0; 

# Impongo il passaggio dei punti generici per le rispettive rette
subject to Punto0: a[0]*x0 + b[0]*y0 + c[0] = 0;
subject to Punto1: a[1]*x1 + b[1]*y1 + c[1] = 0;

# OBIETTIVO
/*
Minimizzare l'area del rettangolo, ossia:
distanza punto-retta tra il punto 0 e la retta 2
moltiplicata per
distanza punto-retta tra il punto 1 e la retta 3

Altra possibile forma di obiettivo: (c[0]+c[2])*(c[1]+c[3])
(c[0]+c[2]) dà la distanza tra la retta 0 e la retta 2 (funziona solo se le rette "vanno in senso opposto", come 
descritto dai vincoli Verso_a e Verso_b)

*/
minimize z: d1 * d2;

##########################
data;

param nP := 10;

param x (tr) :  1   2   3   4   5   6   7   8   9  10 :=
x              -7  -3  -4  10  11   6   0  -6   9  -7;
param y (tr) :  1   2   3   4   5   6   7   8   9  10 :=
y              -2   5  -5   5   2   9  -6   2   0   0;

# Inizializzazione di a,b,c
var: 	a	b	c	:=
0		-1	1	16
1		1	1	16
2		1	-1	16
3		-1	-1	16;

end;
