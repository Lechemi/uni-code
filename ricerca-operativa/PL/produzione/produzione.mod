/*
5429500
*/

# DATI
param nP;           # Numero di prodotti
set P := 1..nP;     # Insieme dei prodotti
set M := 1..12;     # Insieme dei mesi
param pr {P,M};     # Costi di produzione per ogni prodotto in ogni mese [euro/unità]
param st {M};       # Costi di stoccaggio in ogni mese [euro/unità]
param ca {P,M};     # Capacità produttiva per ogni prodotto in ogni mese [unità]
param do {P,M};     # Domanda per ogni prodotto in ogni mese [unità]
param caTot;        # Capacità totale del magazzino [unità]

# VARIABILI
var x {P,M} >= 0;   # x[p,m] = unità prodotte del prodotto p nel mese m [unità]
# (AUSILIARIE)
var s {P,M} >= 0;   # s[p,m] = unità del prodotto p in magazzino all'inizio del mese m [unità]

# VINCOLI

/*
Definizione di s    [unità]

(quello che ho prodotto il mese prima + quello che avevo in magazzino il mese prima) 
- (la domanda del mese prima)
*/
subject to Stock1 {p in P} : s[p,1] = 0;    # A inizio gennaio non ho niente in magazzino (assumo io)
subject to Stock {p in P, m in M: m > 1}:
    s[p,m] = x[p,m-1] + s[p,m-1] - do[p,m-1];

/*
Soddisfare la domanda   [unità]
x[p,m] + s[p,m] - do[p,m] >= 0
che è come dire
x[p,m-1] + s[p,m-1] - do[p,m-1] >= 0
ossia, dal vincolo Stock:
s[p,m] >= 0

Non preoccuparti per gennaio: lo stock che ho a inizio mese a febbraio dev'essere >= 0, quindi devo
aver soddisfatto la domanda di gennaio.
Devo però preoccuparmi per dicembre, perché se ho 0 stock a inizio mese sono bello 
che a posto anche senza produrre niente e quindi senza soddisfare la domanda.
*/
subject to Demand {p in P, m in M : m < 12} :
    s[p,m] >= 0;
subject to DemandDecember {p in P} : s[p,12] + x[p,12] >= do[p,12];

/*
Non eccedere la capacità produttiva mensile     [unità]
*/
subject to ProdCapacity {p in P, m in M} :
    x[p,m] <= ca[p,m];

/*
Non eccedere la capacità totale del magazzino   [unità]
*/
subject to Capacity {m in M} :
    sum {p in P} s[p,m] <= caTot;

# OBIETTIVO
/*
L'obiettivo è minimizzare i costi totali, che comprendono anche quelli di produzione e stoccaggio [euro].
*/
minimize z : sum {p in P, m in M} (x[p,m] * pr[p,m] + s[p,m]*st[m]);

##########################
data;

param nP := 2;

param pr (tr) :     1            2     :=
1                  125          240
2                  140          245
3                  140          230
4                  150          230
5                  130          225
6                  120          220
7                  100          215
8                  170          240
9                  135          225
10                 145          235
11                 160          240
12                 175          250;

param st :=
1          45
2         45
3            40
4           25
5           20
6           10
7           10
8           10
9        15
10          25
11         35
12         40;

param ca (tr) :     1       2    :=
1         2400         1000
2        2300          900
3           2500         1000
4          2500         1000
5          2450         1000
6          2550         1000
7          2300          900
8          1200          400
9       2200         1000
10         2500         1000
11        2500         1000
12        1800          700;

param do (tr) : 1       2      :=
1         1800          500
2        1700          400
3           1800          500
4          1800          600
5          1900          700
6          2300          800
7          2500          900
8          2500         1000
9       2000          800
10         1800          800
11        1700          800
12        2000         1000;

param caTot := 2500;

end;
