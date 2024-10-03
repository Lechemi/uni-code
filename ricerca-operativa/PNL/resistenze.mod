/*
Questo si capisce easy.
*/

# DATI
param target; 			# Valore di resistenza desiderato
param nT;				# Numero di tipi di resistori
set T := 1..nT;			# Insieme dei tipi di resistori
param r {T};			# Resistenza di ciascun tipo di resistore
param n {T};			# Numero resistori di ciascun tipo
set P := 1..4;			# Insieme delle posizioni (1,2 -> primo blocco; 3,4 -> secondo)

# VARIABILI
var x {P,T} binary;		# Se x[p,t] = 1 => metto in posizione p il resistore di tipo t
# (AUSILIARIE)
# Resistenza in ogni posizione p
var R {p in P} = sum {t in T} r[t] * x[p,t];
# Resistenza finale
var RR = R[1]*R[2]/(R[1]+R[2]) + R[3]*R[4]/(R[3]+R[4]);	
var delta >= 0;			

# VINCOLI
# Ogni posizione dev'essere occupata da esattamente un resistore
subject to Assegnamento {p in P}:
	sum {t in T} x[p,t] = 1;

# Devo rientrare nel numero di resistori disponibili per ogni tipo
subject to Quantita {t in T}:
	sum {p in P} x[p,t] <= n[t];

# OBIETTIVO
/*
Minimizzare |RR - target|
*/
minimize z : delta;
subject to minabs1: delta >= RR - target;
subject to minabs2: delta >= target - RR;

##########################
data;

param nT := 6;

param: n		 r	:=
1      1         12
2      1         15
3      2         20
4      2         22
5      1         30
6      1         40;
 
param target := 65;


end;