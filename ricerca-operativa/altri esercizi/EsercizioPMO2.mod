/*
Classico problema di mix produttivo ottimale.

Il problema qua è tradurre in linguaggio matematico le opinioni
dei consiglieri.

A) si ottimizza usando solo c2 -> maximize z: f2

B) si ottimizza usando solo c1 -> maximize z: f1

C) L'obiettivo per questo consigliere si può esprimere col metodo dei
pesi: z:  0.7 * f1 + 0.3 * f2
La produzione ottima è semplicemente costituita dalle x restituite
dal solutore (x[1], x[2], x[3], x[4]);

D) L'obiettivo per questo consigliere si può esprime col metodo dei
pesi: z:  0.25 * f1 + 0.75 * f2
(Nel grafico di f1 e f2, si nota che A e D coincidono)
Ma il consigliere scemo vuole calcolare la produzione ottima 
a modo suo, con la media pesata con coefficienti 25% e 75% delle 
due produzioni ottime nei due scenari possibili.
Ossia, si prende x[i] trovato in A e si fa la media pesata con
x[i] trovato con B, per trovare x[i] di D.
Seguendo questo procedimento, si ottiene una soluzione dominata.

E) Solo perché ci sono infiniti possibili valori dei pesi, non significa
che non si possa calcolare la soluzione ottima: si può esprimere la 
probabilità come parametro e risolvere con l'analisi parametrica,
che restituisce sempre un numero finito di segmenti (uno per base, 
vedi teoria).
Purtroppo con gli strumenti che abbiamo dovremmo fare l'analisi a mano,
fattibile ma noiosa lol.

F) Si tratta del caso max-min! Come per il problema sulle scommesse
sui cavalli, alla fine "ottimizzare il caso peggiore" risulta nel
fare in modo che tutti i casi siano equivalenti => f1 = f2.
Si traccia quindi la bisettrice del primo quadrante nel grafico
delle soluzioni, e si vede dove questa bisettrice interseca
la curva pareto-ottima.

G) Questo consigliere vuole semplicemente ottimizzare la funzione "migliore",
che è f2, non curandosi di f1.
(f2 è meglio perché ha un valore max, calcolato nel punto A, maggiore
di quello di f1, calcolato in B).
È come se facesse all-in su f2: se gli va bene, guadagni alle stelle;
se gli va male, solo perdite.

H) joke

*/

# DATI
set P := 1..4;
set R := 1..4;
param b {R};
param a {R, P};
param c1 {P};
param c2 {P};

# VARIABILI
var x {P} >= 0;
# Ausiliarie:
var f1;     # Esprime il valore dell'obiettivo usando i coefficienti c1
var f2;     # Esprime il valore dell'obiettivo usando i coefficienti c2

# VINCOLI
subject to Consumi {i in R} : sum {j in P} a[i,j] * x[j] <= b[i];

subject to Def_f1 : f1 = sum {j in P} c1[j] * x[j];
subject to Def_f2 : f2 = sum {j in P} c2[j] * x[j];

# OBIETTIVO
maximize z: f1;

##########################
data;

param b := 
1   100
2   120
3   90
4   110;

param a:    1   2   3   4 :=
1           3   2   5   4
2           8   10  1   1
3           1   3   6   9
4           2   0   8   11;

param:  c1      c2  :=
1       1.0     1.7
2       1.5     0.4
3       1.3     2.0
4       2.5     0.7;

end;