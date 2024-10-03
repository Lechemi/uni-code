/*
Ennesima variante del mix produttivo ottimale.

RISPOSTE ALLE DOMANDE:

1) Quali tipi di motociclo sarebbe più conveniente 
assemblare e vendere se la manodopera fosse disponibile in quantità molto grande?
1 e 3, infatti le x ottime, se non viene messo alcun vincolo sulla manodopera, sono:
1  0.14
2  0
3  6.4

2) In tal caso quale dovrebbe essere la variazione del prezzo di vendita dei 
motocicli non convenienti per renderli convenienti?
Ossia, come rendo il motociclo 2 conveniente da produrre?
Bisogna consultare il costo ridotto, che indica quanto manca a quella variabile
a diventare conveniente.
I costi ridotti sono:
1    -1.13687e-13
2  -428
3     0
Nota che per il motociclo 1 possiamo considerare 0 il suo costo ridotto, in quanto
è un numero molto piccolo.
Il prezzo di 2 dovrebbe aumentare di 428 €, che appare con un meno perché la z viene
automaticamente espressa in forma standard (diventa di minimizzazione).

3) Sempre nella stessa ipotesi (manodopera infinita), i componenti verrebbero smaltiti allo stesso 
ritmo o l’assemblaggio potrebbe provocare rimanenze di pezzi non assemblati? 
Quanto grandi?
Dobbiamo consultare le variabili di slack dei vincoli sui consumi.
Consumi.slack [*] :=
1   0
2   0.9
3  -7.10543e-15
Si osserva che per i componenti 1 e per 3 non ci sono avanzi (lo slack è 0), mentre 
per 2 c'è un avanzo di 0.9 pezzi/mese.
Occhio che qua 1,2 e 3 non sono più i prodotti, ma i tipi di componenti.

4) Per quali valori della manodopera (ossia dei tempi di assemblaggio) non 
sarebbe conveniente la produzione supplementare?
Per quali valori invece sarebbe conveniente dedicare alla produzione 
supplementare tutta la manodopera necessaria a massimizzarne il ricavo e qual 
è tale valore?
Qual è la soluzione ottima se il costo della manodopera è di 1000 Keuro/giorno-uomo?
Per rispondere a queste domande possiamo avvalerci dell'analisi parametrica, usando
come parametro i costi della manodopera (espressi in funzione dei tempi di 
assemblaggio).
La funzione obiettivo diventa il ricavo totale, ossia guadagni - costiManodop:
z - kt
dove k è il costo della manodopera.
Vedi video per tutti i dettagli.


*/

# DATI
param nP;
param nC;
set P := 1..nP;
set C := 1..nC;
param c {P};		# Prezzi di vendita [€/motociclo]
param a {C, P};		# Numero pezzi di ogni componente necessari per ogni motociclo [pezzi/motociclo]
param b {C};		# Disponibilità dei componenti [pezzi/mese]
param t {P};		# Tempi di assemblaggio [ore-uomo/motociclo]
param epsilon; 		# Termine noto per l'analisi parametrica [ore-uomo/mese]

# VARIABILI
var x {P} >= 0; 			# Produzione [motociclo/mese]

# Ausiliarie
var f1;		# Ricavi dalle vendite [€/mese]
var f2;		# Manodopera [ore-uomo/mese]

# VINCOLI
subject to Consumi {i in C} :
	sum {j in P} x[j] * a[i,j] <= b[i];

# Ricorda che f2 rappresenta la manodopera, infatti check le unità di misura
subject to Manodopera :
	sum {j in P} t[j] * x[j] <= f2;

# Ricavi dalle vendite
subject to Def1 : f1 = sum {j in P} c[j] * x[j];

# OBIETTIVO
/*
Massimizzazione dei profitti [€/mese]
Il "vincolo" sotto non è proprio un vincolo: è solo un modo per impostare
l'analisi parametrica, utilizzando la manodopera come parametro.
Nel grafico z/f2, questo vincolo rappresenta la retta verticale che limita 
il valore di f2 e che ci permette quindi di modularla per stabilire i vari segmenti
corrispondenti alle varie basi.
*/
maximize z: f1;
subject to Parametric: f2 <= epsilon;

##########################
data;

param epsilon := 1.006;

/*
Farò un commento per ogni valore di epsilon

0) valore iniziale solo per collocarci sul primo segmento
ovviamente con questo valore ci collochiamo nell'origine del grafico z/f2, quindi
z = 0
f1 = 0
f2 = 0
x [*] :=
1  0
2  0
3  0
Il costo ridotto della variabile di slack del vincolo Parametric, ossia il
coefficiente angolare del segmento nel grafico, è:
Parametric = 1300
Questo è il prezzo ombra della manodopera, infatti quando ce n'è poca
siamo disposti a pagarla bene, mentre coi successivi cambi di basi questo prezzo
diminuirà sicuramente.

7848) fissiamo il valore massimo di f1, che già conosciamo, per scoprire qual è il
relativo valore minimo di f2. Graficamente, vogliamo sapere esattamente per quale
valore di f2 si ha l'inizio del segmento orizzontale.
questo valore, dato dal solutore, è f2 = 6.54
Grazie a Parametric possiamo trovare il coefficiente angolare del segmento
prima rispetto a quello orizzontale, perché è al suo estremo che realmente si
giunge a f1 = 7848 (a quanto ho capito).
Questo coefficiente è l'inverso di:
Parametric = 0.00102804
perché abbiamo "invertito" f1 e f2 per questo giochino, quindi:
coeff = 1/0.00102804 = 972.72
Il coeff viene più piccolo di quello trovato prima (1300), come previsto.

1000)

*/

param nP := 3;
param nC := 3;

param c :=
1      1200
2      1300
3      1200;

param t :=
1        1
2        1
3        1;

param a:  1		   2	   3 :=
1         10      12      14
2          5       7       6
3         10      15       9;

param b :=
1       91
2       40
3       59;


end;
