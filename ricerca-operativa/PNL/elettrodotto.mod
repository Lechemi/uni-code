/*
Devo localizzare la cabina di trasformazione per 
ciascun paese.
*/

# DATI
param nP;			# Numero paesi
set P := 1..nP;		# Insieme paesi
param xP {P};		# Coordinata x paese
param yP {P};		# Coordinata y paese
param dLimite; 		# Distanza limite
param pred {P}; 	# Vettore dei padri per rappresentare l'albero

# VARIABILI
# Coordinate delle cabine di trasformazione
var xT {P};
var yT {P};

# VINCOLI
# Elevo tutto al quadrato per togliere la radice
subject to DistanzaLimite {i in P}:
	(xT[i]-xP[i])^2 + (yT[i]-yP[i])^2 <= dLimite^2;

# OBIETTIVO
/*
La soluzione di Tizio è banale: ogni cabina coincide con ogni centralina
(ossia con ogni paese). Quindi se voglio minimizzare la lunghezza complessiva 
delle linee interrate, questa vale 0.
In ogni caso si può scrivere la funzione obiettivo come sotto, minimizzando le 
distanze tra ogni centralina (paese) e la relativa cabina.
*/
#minimize zTizio : sum {i in P} sqrt((xT[i]-xP[i])^2 + (yT[i]-yP[i])^2);

/*
Caio chiede che la distanza tra una cabina e la precedente sia minima.
Per questo serve descrivere l'albero formato dai paesi, per sapere qual è il 
precedente.
*/
#minimize zCaio : sum {i in P: i != 1} sqrt((xT[i]-xT[pred[i]])^2 + (yT[i]-yT[pred[i]])^2);

/*
Sempronio chiede che vengano minimizzati i costi complessivi.
Ogni chilometro di scavo costa una volta e mezzo un chilometro di elettrodotto, quindi
se minimizzare i km di elettrodotto (Caio) ha priorità 1, minimizzare i km di scavo (Tizio) ha
priorità 1.5.

Ossia: zSempronio = zCaio + 1.5 * zTizio
*/
minimize zSempronio: (sum {i in P: i != 1} sqrt((xT[i]-xT[pred[i]])^2 + (yT[i]-yT[pred[i]])^2)) 
					+ 1.5 * (sum {i in P} sqrt((xT[i]-xP[i])^2 + (yT[i]-yP[i])^2));

##########################
data;

# A B C D E F G H I L  M  N  O  P  Q  R
# 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16

param nP := 16;

param dLimite := 2;

param pred :=
1	.
2	1		
3	2
4	3
5	4
6	5
7	6
8	7
9	8
10	4
11	10
12	11
13	12
14	6
15	14
16	15;

param:	xP		yP	:=
1		0		0
2		4		8
3		10		12
4		15		12
5		22		28
6		31		30
7		40		34
8		42		46
9		50		50
10		25		15
11		32		15
12		37		10
13		46		13
14		31		38
15		28		45
16		35		54;

end;