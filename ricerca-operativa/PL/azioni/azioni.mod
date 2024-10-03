/*
Le azioni sono o tutte e tre in crescita o tutte e tre in recessione.
*/

# DATI
param nA;           # Numero di tipi di azioni
set A := 1..nA;     # Insieme delle azioni
param p {A};        # Prezzi di acquisto delle azioni [euro/unità]
param rc {A};       # Rendimenti percentuali attesi in crescita [%]
param rr {A};       # Rendimenti percentuali attesi in recessione [%]
param budget;       # [euro]
param alfa;         # Per analisi parametrica, si può vedere come la probabilità che il mercato cresca

# VARIABILI
var x {A} >= 0;     # Numero di azioni da comprare per ciascun tipo [unità]
# (AUSILIARIE)
var f1;             # Profitti in caso di crescita [euro]
var f2;             # Profitti in caso di recessione [euro]

# VINCOLI
# Devo rientrare nel budget [euro]
subject to Budget: sum {a in A} x[a] * p[a] <= budget;

# Di nessun tipo si possono acquistare più azioni che di tutti gli altri tipi sommati insieme
subject to Quantity {a in A}: sum {i in A: i != a} x[i] >= x[a];

# Definizione dei due obiettivi
subject to Def1: f1 = sum {a in A} x[a] * p[a] * (0 + rc[a]/100);
subject to Def2: f2 = sum {a in A} x[a] * p[a] * (0 + rr[a]/100);

# OBIETTIVO
maximize z: alfa * f1 + (1-alfa) * f2;

/*
Le soluzioni di base paretiane sono soltanto quelle che nello spazio degli obiettivi hanno 
coordinate A=(-2181.82, 8145.45) e B=(320, 5440). A è quella che massimizza f1, mentre 
B massimizza f2. (Ci sono in totale 4 basi che producono due il punto A e due il punto B).

Queste soluzioni paretiane sono quelle che si ottengono per qualsiasi valore di alfa tra 0 e 1 compresi,
ossia per qualsiasi combinazione convessa di f1 e f2.
*/

##########################
data;

param alfa := 0.2;

param nA := 3;
param budget := 80000;
param:  p       rc      rr      :=
1       50      5       1
2       75      8       0
3       90      12      -5;

end;
