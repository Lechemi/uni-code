/*
*/

# DATI
param nP;					# Numero di punti
set P := 1..nP;				# Insieme dei punti
param x {P};				# x dei punti
param y {P};				# y dei punti

# VARIABILI
var a;
var b;
var c >= 0.000000001;
var d >= 0.000000001;
var ySigm {i in P} = (exp((x[i]+a)/c) / (1 + exp((x[i]+a)/c)) + b) * d;
# var maxError >= 0;
# var absError {P} >= 0;

# VINCOLI

# OBIETTIVO
/*
1) minimizzare il massimo errore in valore assoluto;

minimize z1 : maxError;
subject to maxerr1 {i in P} : maxError >= ySigm[i] - y[i];
subject to maxerr2 {i in P} : maxError >= y[i] - ySigm[i]; 
*/

/*
2) minimizzare la somma degli errori in valore assoluto;

minimize z2 : sum {i in P} absError[i];
subject to Abs1 {i in P}: absError[i] >= ySigm[i] - y[i];
subject to Abs2 {i in P}: absError[i] >= y[i] - ySigm[i];
*/

/*
3) minimizzare l’errore quadratico medio.
*/
minimize z3 : (sum {i in P} (ySigm[i] - y[i])^2) / nP;

##########################
data;

param nP := 12;

param :	x	y  :=
1	2 		28
2	1 		20
3	8 		40
4	5 		36
5	-6 		16
6	-2 		16
7	0 		16
8	-3		16
9	7 		40
10	6 		40
11	-4 		16
12	3 		32;

var c := 10;

end;