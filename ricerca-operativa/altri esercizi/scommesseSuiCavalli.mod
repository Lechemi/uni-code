/*
Nota: non abbiamo informazioni sulla probabilità
di vittoria di ogni cavallo. 
L'obiettivo qua è distribuire il budget in modo tale che 
la vincita nel caso peggiore sia massima.
La vincita nel caso peggiore non è altro che la vincita
minore tra quelle possibili (qua ne abbiamo 4 possibili).
Per esempio, se punto tutto sul cavallo più quotato
lasciando 0 agli altri, ci sono 3 casi ugualmente peggiori
in cui vinco 0. Ancora, se punto poco sul cavallo meno
quotato, quella è la vincita peggiore.
Il prodotto quota*puntata mi dice quanto vinco; devo
cercare di renderlo uniforme sui cavalli, in modo
che qualunque sia il vincitore, io guadagni il massimo 
possibile. In sostanza, cerco di fare in modo che tutti
siano i "casi peggiori".
Ho spiegato un po' male spero si capisca.
*/

# DATI
set Cavalli;
param q {Cavalli};  # Quotazione per ogni cavallo [adimensionale]
param budget;       # Soldi disponibili [€]

# VARIABILI
var x {Cavalli} >= 0;    # Puntata su ogni cavallo [€]
var casoPeggiore;        # Vincita minima [€]

# VINCOLI
# La somma totale delle puntate non può superare il budget [€]
subject to Budget: sum {i in Cavalli} x[i] <= budget;

# OBIETTIVO
# Massimizzare la vincita nel caso peggiore [€]
maximize z: casoPeggiore;
# Vincolo per linearizzare la funzione obiettivo max-min [€]
subject to Maxmin {i in Cavalli}: casoPeggiore <= q[i]*x[i];

##########################
data;

set Cavalli := Fulmine Freccia Dardo Lampo;

param q :=
Fulmine 3
Freccia 4
Dardo   5
Lampo   6;

param budget := 57;

end;