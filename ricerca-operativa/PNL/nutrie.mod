/*
Nutrie. Per contrastare la diffusione delle nutrie che rovinano i raccolti 
e devastano le rive dei fossi, gli amministratori di alcuni paesi hanno deciso 
di assoldare squadre di cacciatori che tentino di abbattere o catturare gli animali.
Sono state fatte eseguite alcune stime sulla popolazione di nutrie in ciascuno 
dei paesi e si vuole ora ripartire il compito tra i cacciatori in modo che 
ciascuno di essi possa cacciare un numero di nutrie pressappoco uguale. Ogni 
cacciatore potrà operare nel territorio di non più di 3 paesi, senza sconfinare negli altri.
In ogni paese si assume che la popolazione stimata di nutrie sia equamente 
suddivisa tra tutti i cacciatori assegnati a quel paese. In base agli assegnamenti 
dei cacciatori ai paesi si può calcolare così il numero di nutrie che ci si aspetta 
da ogni cacciatore (il "target" del cacciatore). Si vuole minimizzare il massimo
di tali valori target.
*/

# DATI
param nP;				# Numero paesi
set P := 1..nP;			# Insieme dei paesi
param n {P}; 			# Numero di nutrie per paese
param maxp;				# Massimo di paesi per cacciatore
param nC;				# Numero di cacciatori
set C := 1..nC; 		# Insieme dei cacciatori

# VARIABILI
var x {P,C} binary;		# x[p,c] = 1 se assegno al paese p il cacciatore c

# VINCOLI
subject to PaesiPerCacciatore {c in C} : sum {p in P} x[p,c] <= maxp;
subject to CacciatoriPerPaese {p in P} : sum {c in C} x[p,c] >= 1;

# (AUSILIARIE)
var k {p in P} = sum {c in C} x[p,c];	# Numero di cacciatori assegnati ad ogni paese
# Quota di nutrie assegnata ad ogni cacciatore
var qu {c in C} = sum {p in P} n[p]/k[p] * x[p,c];
var maxq;

# OBIETTIVO
# Classico minmax
minimize z : maxq;
subject to minmax {c in C} : maxq >= qu[c];

##########################
data;

param nP := 10;
param nC := 5;
param maxp := 3;

param n := 
1	20
2	30
3	24
4	36
5	80
6	72
7	54
8	37
9	25
10	47;

end;