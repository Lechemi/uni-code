/*
Tema d'esame.
*/

# DATI
set P;              # Prodotti
set I;              # Ingredenti
param a {I,P};      # Composizione percentuale [%]
param b {I};        # Quantità di ingr disponibili [Kg/d]
param p {I};        # Prezzo degli ingredienti [€/Kg]
param c {P};        # Prezzo di vendita dei prodotti [€/Kg]

# VARIABILI
var x {P} >= 0;     # Produzione [Kg/d]

# VINCOLI
/*
Vincolo sul consumo di ingredienti [Kg/d]. 
*/
subject to Consumi {i in I}: sum {j in P} x[j] * a[i,j] / 100 <= b[i];

# OBIETTIVO
/*
Massimizzare profitti [€/d].
Non capisco perché qua lui la ponga in questo modo: gli ingredienti sono
già stati acquistati, quindi tutti gli ingredienti sono stati
acquistati nella quantità in cui sono disponibili, quindi vengono 
totalmente esauriti.
Secondo questo ragionamento, i soldi spesi per gli ingredienti acquistati
non sono da considerare in z, perché non costituiscono più una
variabile. La seconda parte è coerente con la prima, ma non capisco
il perché della prima, anche perché abbiamo messo apposta un vincolo
sulle quantità degli ingredienti utilizzati (che non devono eccedere
le quantità disponibili) + non viene usato il prezzo degli ingredienti (red flag).
Io avrei semplicemente, tenendo il vincolo, scritto la funzione obiettivo
come differenza tra soldi spesi per gli ingredienti e soldi ricavati
dai prodotti. i may be built different tho.
Lui però tiene il ragionamento sulla quantità di ingredienti da acquistare 
per l'analisi post-ottimale.
Per ora lo tengo come fa lui.

Spiegazione del perché è giusto come lo fa lui: gli ingredienti disponibili sono quelli
che sono già stati acquistati, non sono quelli disponibili "sul mercato".
Quindi se volessi tenere in conto anche le spese per gli ingredienti, queste sarebbero
semplicemente una costante data da due dati (quantità acquistate e prezzo ingredienti)
e nessuna variabile. Ciò significa che l'andamento della funzione obiettivo non 
cambierebbe, verebbe solo traslata. Sì, cambia il valore finale di z, ma la soluzione 
ottima (quello che ci interessa) rimane tale.
*/
maximize z : sum {j in P} c[j] * x[j];

##########################
data;

set P := Dolce Delizia Bacetto Golosa Sfizio Slurp Sweety;
set I := Fruttosio, Saccarosio, Glucosio, Destrosio,
Estratti_di_erbe, Estratti_di_frutta, Coloranti, Conservanti, Aromatizzanti;

param a :         Dolce Delizia Bacetto Golosa Sfizio Slurp Sweety :=
Fruttosio           30      0       5       5      5    10     10
Saccarosio          20     30       0       5      5     5     10
Glucosio            15     20      30       0      5     5      5
Destrosio           10     15      20      30      0     5      5
Estratti_di_erbe    10     10      15      20     30     0      5
Estratti_di_frutta   5     10      10      15     20    30      0
Coloranti            5      5      10      10     15    20     30
Conservanti          5      5       5      10     10    15     20
Aromatizzanti        0      5       5       5     10    10     15;

param b :=
Fruttosio            9
Saccarosio           5
Glucosio            20
Destrosio           18
Estratti_di_erbe    20
Estratti_di_frutta  17
Coloranti           18.4
Conservanti         12.5
Aromatizzanti       10;

param p := 
Fruttosio           4
Saccarosio          2
Glucosio            1
Destrosio           3.5
Estratti_di_erbe    8
Estratti_di_frutta 10
Coloranti           2
Conservanti         5 
Aromatizzanti       6;

param c := 
Dolce   5
Delizia 4
Bacetto 8
Golosa  5
Sfizio  6
Slurp   7.5
Sweety  4.5;

end;