/*
Anche qua non era chiaro: bisogna assegnare lo spin a ciascuna particella e in base a quello calcolare
l'energia totale del vetro.
*/

# DATI
param nP;			# Numero di particelle
set P := 1..nP;		# Insieme delle particelle
param V {P,P};		# Vetro di spin, rappresentato come matrice triangolare superiore con diagonale nulla
# (L'energia di interazione tra i e j è uguale a quella tra j e i e va contata una volta sola)

# VARIABILI
var s {P} binary;	# Spin di ciascuna particella 

# VINCOLI

# OBIETTIVO
/*
La funzione (2*x[i]-1) * (2*x[j]-1) restituisce 1 se x[i] e x[j]
sono entrambi uguali a 1 o a 0; restituisce -1 altrimenti.
*/
minimize z : sum {i in P, j in P: j>i} -V[i,j] * (2*s[i]-1) * (2*s[j]-1);

##########################
data;

param nP := 10;

param V: 1  2  3  4  5  6  7  8  9  10 :=
1        .  3 -1 -4  5 -8  4 -2 -3 -1
2        .  . -2  2 -4  7 -1  2 -2  2
3        .  .  . -3 -3 -3  5 -2 -1 -3
4        .  .  .  .  2 -1 -2 -2 -2 -7
5        .  .  .  .  .  3 -7  7 -2  8
6        .  .  .  .  .  . -5  3 -3 -1
7        .  .  .  .  .  .  . -1  9 -5
8        .  .  .  .  .  .  .  .  1  9
9        .  .  .  .  .  .  .  .  . -6
10       .  .  .  .  .  .  .  .  .  .;

/*
L'energia di interazione col campo magnetico esterno è descritta dai coefficienti seguenti.
Il contributo all'energia è positivo per le particelle con spin "su" e negativo per quelle con spin "giù".

1 8 2 9 1 9 1 8 2 7
*/


end;