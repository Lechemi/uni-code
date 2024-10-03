/*
RISPOSTE ALLE DOMANDE   
- sì, l'esperimento conviene. I profitti sono 43237.5 euro.
- Non ci serve altro terreno, non lo usiamo neanche tutto (ne usiamo solo 320 m^2).
- La pianta più redditizia è il Giaggiolo: mi rende 100 * 7000 = 700 000 euro.
- Conviene adottare questo sale, infatti i profitti, utilizzandolo, salgono a 1.77737.5e+05 euro.
    (per valutare l'opzione ho semplicemente modificato i dati portando 
    il prezzo del sali a 1 e raddoppiando il fabbisogno di sali per ogni pianta)
- 1632 euro/m^2. Fino a 1631 la base non cambia e se ne produce solo la quantità minima. Da 1632 in poi 
    conviene produrre la quantità massima.
*/

# DATI
set P;              # Insieme delle piante
set E;              # Insieme degli elementi nutrizionali
param c {E};        # Costi di approvvigionamento degli elementi nutrizionali
param f {P, E};     # Fabbisogno giornaliero per ogni metro quadrato coltivato per ogni tipo di pianta
param t {P};        # Tempo di crescita per pianta [giorni]
param r {P};        # Ricavi stimati per pianta [euro/m^2 coltivato]

# VARIABILI
var x {P} >= 0;     # Metri quadrati coltivati per pianta [m^2]

# Ausiliarie
var a {E} >= 0;     # Quantità acquistata di ogni elemento nutrizionale

# VINCOLI
/*
Definizione di a.

f[p,e] * x[p] è la quantità dell'elemento e che mi serve ogni giorno per la pianta p
f[p,e] * x[p] * t[p] è la quantità totale dell'elemento e che mi serve per la pianta p
sum {p in P} f[p,e] * x[p] * t[p] è la quantità totale dell'elemento e che mi serve (ossia a)

NB: in questa "variante" del mix produttivo ottimale, è come se avessi a disposizione infinite materie
prime; non c'è nessun vincolo sulla quantità di elemento e che posso comprare. Devo solo preoccuparmi 
di definire quanto spendo, posso permettermi di comprare esattamente le quantità di elementi nutrizionali 
di cui ho bisogno.
*/
subject to Def_Quantity {e in E} :
    a[e] = sum {p in P} f[p,e] * x[p] * t[p];

/*
Devo rientrare nei limiti minimi e massimi di superfici coltivate [m^2]
*/
subject to SuperficieMin {p in P} :
    x[p] >= 5;
subject to SuperficieMax {p in P} :
    x[p] <= 100;
subject to SuperficieTot:
    sum {p in P } x[p] <= 500;

# OBIETTIVO
# Massimizzare i profitti (ricavi - costi) [euro]
maximize z : sum {p in P} x[p] * r[p] - sum {e in E} a[e] * c[e] - 50000;

##########################
data;

set P := Azalea, Begonia, Carota, Datteri, Erba_cipollina, Fragolina_di_bosco, Giaggiolo;
set E := Acqua_dolce, Sali_minerali, Calore, Luce, Vitamine;

param c := 
Acqua_dolce      1
Sali_minerali    4
Calore           1
Luce             1.5
Vitamine         3.6;

param f :       Acqua_dolce     Sali_minerali   Calore      Luce    Vitamine    := 
Azalea               0.5       20                   10        100       1.5
Begonia              0.8        3                   12          3       0.5
Carota               0.1        5                    5          5       2.4
Datteri              0.2       90                   50        450         3
Erba_cipollina       0.3       30                    0         50         0
Fragolina_di_bosco   0.8       60                   20         40       1.5
Giaggiolo            0.7        0                   25        150         9;

param:              t           r       :=
Azalea              30          6000
Begonia             40          4000
Carota              10          2000
Datteri             100         18000
Erba_cipollina      5           100
Fragolina_di_bosco  5           500
Giaggiolo           20          7000;

end;
