/*
*/

# DATI
set C := 1..10;          # Insieme delle città
param d {C,C};           # Distanze tra le città [km]

# VARIABILI
var x {C} binary;       # se x[c] = 1, allora scelgo c come terminale
var maxDistance >= 0;

# VINCOLI
# Devo scegliere 2 terminali
subject to Scelta: sum {c in C} x[c] = 2;

# Non posso scegliere tra i non candidati
subject to Candidati: sum {c in C: c = 7 or c = 9 or c = 10} x[c] = 0;

# OBIETTIVO
minimize z: maxDistance;
subject to MinMax {m in C, t in C: m != 7 and m != 9 and m != 10}: maxDistance >= d[m,t] * x[m];

##########################
data;

param d:	1   2   3   4   5   6   7   8   9   10 :=
1		0	    565	    401	    529	    295	    720	    550	    817	  891	539
2		565	    0	    210	    474	    799	    665	    760	    936	  1008	979
3	401	    210	    0	    309	    635	    500	    674	    771	  949	838
4		529	    474	    309	    0	    375	    194	    377	    465	  643	579
5		295	    799	    635	    375	    0	    411	    259	    394	  599	293
6		720	    665	    500	    194	    411	    0	    281	    861	  450	570
7     0       0       0       0       0       0       0       0     0     0
8		817	    936	    771     465	    394	    861	    117	    0	  220	378
9       0       0       0       0       0       0       0       0     0     0
10      0       0       0       0       0       0       0       0     0     0;

/*
Costi di attivazione:
Tolosa     40 
Nizza      50
Marsiglia  40
Lione      40
Limoges    40
Digione    40
Parigi     60 

Budget: 100
*/

end;
