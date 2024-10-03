/*
*/

# DATI
param n;				# Numero nodi
set N := 1..n;			# Insieme dei nodi
param nA;				# Numero di archi
set A := 1..nA;			# Insieme degli archi
param da {A};			# Origine di ogni arco
param a {A};			# Destinazione di ogni arco
param tb {A};			# Tempo base per ogni arco
param k {A};			# Coefficiente per ogni arco
param nOD;				# Numero di coppie origine-destinazione
set OD := 1..nOD;		# Insieme di coppie origine-destinazione
param o {N};			# Origine per ogni coppia o-d
param d {N};			# Destinazione per ogni coppia o-d
param traffico {N}; 	# Traffico previsto per ogni coppia origine-destinazione
param M := sum {i in OD} traffico[i];	# Vedi vincolo Binary

# VARIABILI
var x {OD,A} >= 0;		# x[od,a] = flusso di macchine che devono fare il viaggio od che passano sull'arco a
# Giusto tenere x continua perché è un flusso e non una quantità
# (AUSILIARIE)
var time {A} >= 0;		# Tempo di percorrenza per ogni nodo
var y {A} binary;		# y[a] = 1 se viene usato l'arco a 
# (y è superflua in qs caso, ma la tengo perché è tecnicamente corretta)

# VINCOLI
/*
Per ogni coppia od, il numero di macchine che parte dal nodo o (su qualsiasi arco che inizia sul nodo o)
dev'essere uguale al corrispondente traffico.
Stesso ragionamento per le destinazioni.
*/
subject to Partenza {i in OD}: sum {j in A: da[j] = o[i]} x[i,j] = traffico[i];
subject to Arrivo {i in OD}: sum {j in A: a[j] = d[i]} x[i,j] = traffico[i];

/*
Conservazione del flusso per ogni nodo (per ogni tipo di viaggio)
Unica eccezione sono i nodi di origine e destinazione per i viaggi interessati.
*/
subject to Flux {i in OD, j in N: j != d[i] and j != o[i]}: 
	sum {h in A: a[h] = j} x[i,h] = sum {h in A: da[h] = j} x[i,h];

# Definizione di y (M è un numero più grande di qualsiasi valore del primo membro)
subject to Binary {i in A}: (sum {j in OD} x[j,i]) <= M * y[i];  

# Definizione di time
subject to DefTime {i in A}: time[i] = y[i]*(tb[i] + k[i] * (sum {j in OD} x[j,i])^2);

# OBIETTIVO
minimize z: sum {i in A} time[i];

##########################
data;

param n := 5;
param nA := 16;
param nOD := 4;

param:	da	a	tb			k	:=
1 		1   2   20         0.1
2 		1   4   14         0.1
3 		1   5    7         0.2
4       2   1   20         0.1
5       2   3   22         0.1
6       2   5   15         0.2
7       3   2   22         0.1
8       3   4   15         0.1
9       3   5   10         0.2
10      4   1   14         0.1
11      4   3   15         0.1
12      4   5   10         0.2
13      5   1    7         0.2
14      5   2   15         0.2
15      5   3   10         0.2
16      5   4   10         0.2;

param:	o	d		traffico	:=
1       1	3           60
2       2	4           80
3       3	1          120
4       4	2           50;

end;
