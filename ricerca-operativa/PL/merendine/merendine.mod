/*

Dire fino a punto potrebbe diminuire la quantità diponibile di 
zucchero e che impatto avrebbe tale diminuzione sul ricavo dell’azienda

L'analisi di sensitività mostra che potrei usare 290.18705 kg/giorno di zucchero, al posto dei
300 kg/giorno che uso. Posso notarlo sia dal vincolo DefAvanzi, sia da Available.
Nel primo c'è scritta proprio il range di quantità di zucchero in cui posso stare prima che cambi
la base; nel secondo c'è scritto di quanto può variare il valore 300, ma essenzialmente ci ricavo la
stessa informazione.
Se lo zucchero scende a 290.18705, il valore di z scende a 17666.76259.

*/

# DATI
param nP;               # Numero prodotti
set P := 1..nP;         # Insieme dei prodotti
param nI;               # Numero ingredienti
set I := 1..nI;         # Insieme degli ingredienti
param p {P};            # Prezzo di vendita di ciascun prodotto [Euro/Kg]
param comp {P,I};       # Composizione percentuale degli ingredienti per ciascun prodotto [kg^-2/unità]
param q {I};            # Quantità di ingredienti disponibili [Kg/giorno]
param nG;               # Numero di grandezze vendute
set G := 1..nG;         # Insieme delle grandezze
param pa {P,G};         # Produzione attuale (prodotto-grandezza)   [unità/giorno]

# VARIABILI
var x {P,G} >= 0;       # Produzione (prodotto-grandezza)   [unità/giorno]
# (AUSILIARIE)
var avanzi {I};         # [kg/giorno]

# VINCOLI
/*
Definizione degli avanzi (quantità disponibile - utilizzata) [kg/giorno]
*/
subject to DefAvanzi {i in I} :
    avanzi[i] = q[i] - sum {j in P, g in G} (comp[j,i]/100 * x[j,g]);

/*
Non posso eccedere le quantità di ingredienti disponibili [kg/giorno]
*/
subject to Available {i in I} : avanzi[i] >= 0;

/*
La produzione di biscotti speciali deve essere compresa tra il 10% e 
il 25% della produzione di biscotti normali.
*/
subject to Biscotti1 {g in G} : x[4,g] >= 0.10 * x[3,g];
subject to Biscotti2 {g in G} : x[4,g] <= 0.25 * x[3,g];

/*
Gli avanzi di zucchero, marmellata e cioccolato non devono eccedere 
il 10% delle quantità disponibili.
*/
subject to Avanzi {i in I: i = 2 or i = 3 or i = 4} :
    avanzi[i] <= 0.10 * q[i];

/*
Il totale di prodotti venduti in confezioni grandi deve essere compreso 
tra il 40% e il 60% del totale.
Il vincolo deve essere rispettato sia per ogni tipo di prodotto sia 
complessivamente.
*/
subject to Grandi1 {j in P} : x[j,2] >= 0.40 * sum {g in G} x[j,g];
subject to Grandi2 {j in P} : x[j,2] <= 0.60 * sum {g in G} x[j,g];
subject to GrandiTot1 : sum {j in P} x[j,2] >= 0.40 * sum {j in P, g in G} x[j,g];
subject to GrandiTot2 : sum {j in P} x[j,2] <= 0.60 * sum {j in P, g in G} x[j,g];

/*
Ciascun tipo di prodotto deve costituire almeno il 
10% della produzione totale.

Col 10% mi dice che non c'è soluzione.
Fino al 9% ne trova una subito.

Nel file di output ho lasciato la soluzione relativa al 9%.
*/
subject to TenPercent {j in P} : 
    sum {g in G} x[j,g] - 0.09 * sum {h in P, g in G} x[h,g] >= 0;


# OBIETTIVO
/*
Voglio massimizzare i profitti, che in questo caso significa: come posso sfruttare
al meglio le risorse che già ho? Non si tratta di un'obiettivo del tipo
profitti - costi; non abbiamo scelta sui costi.
I profitti sono dati semplicemente dalla somma su ogni prodotto (di ogni
grandezza) per il relativo prezzo di vendita.
*/
maximize z : sum {j in P, g in G} (x[j,g] * p[j]);

##########################
data;

# merendine, brioches, biscotti normali, biscotti speciali, tortine.
#   1           2               3                4             5
# pasta, zucchero, marmellata, cioccolato e acqua.
#   1       2           3           4         5

param nP := 5;
param nI := 5;
param nG := 2;    

param p :=
1     8
2     6
3     12
4     14
5     10;

param comp (tr) : 1      2       3       4       5  :=
1               20      40      70      25      20
2               25      15      10      20      30 
3               40      30       0      10      10  
4               10       0      15      40      30  
5                5      15       5       5      10;

param q :=
1      9999
2      300
3      320
4      240
5      9999;

param pa (tr) :   1      2       3        4       5   :=
1               250     60      180      60      70
2               350     90      250      90      90;

end;