/*
*/

# DATI
param nB;           # Numero banchi di memoria/strumenti
set B := 1..nB;     # Insieme banchi
param nI;           # Numero intervalli temporali
set I := 1..nI;     # Insieme intervalli
param p {I,B};      # Produzione di dati di ciascuno strumento in ogni intervallo [Mbit]
param c {B};        # Capacità di ogni memoria [Mbit]
param oI {B};       # Occupazione iniziale di ogni memoria [Mbit]
param t {I};        # Durata di ogni intervallo [sec]
param br {I};       # Velocità di trasmissione di ogni intervallo [Kbit/sec]

# VARIABILI
var x {I,B} >= 0;   # x[i,b] = per quanto tempo dell'intervallo i scarico dati dalla memoria b [sec]
# (AUSILIARIE)
var d {I,B} >= 0;   # Quantità di dati presenti in ogni memoria in ogni intervallo [Mbit]
var maxRatio;       # Massimo rapporto (quantità di dati/capacità) tra le memorie e su ogni intervallo

# VINCOLI

/*
Vincolo sul tempo a disposizione per scaricare i dati
[sec]
*/
subject to Time {i in I}: sum {b in B} x[i,b] <= t[i];

/*
Definizione di d [Mbit]
Per il primo intervallo è uguale a: 
occupazione iniziale + dati prodotti - dati scaricati
Dal secondo intervallo in poi è:
occupazione intervallo precedente + dati prodotti - dati scaricati

nota che 1 Mbit = 1000 Kbit
*/
subject to Data0 {b in B}: d[1,b] = oI[b] + p[1,b] - x[1,b] * br[1]/1000;
subject to Data {i in I, b in B: i > 1}: d[i,b] = d[i-1,b] + p[i,b] - x[i,b] * br[i]/1000;

# OBIETTIVO
minimize z : maxRatio;
subject to MinMax {i in I, b in B}: maxRatio >= d[i,b]/c[b];

##########################
data;

param nB := 6;
param nI := 9;

param p :   1    2    3    4    5    6    :=
1           4   11   31    3   18   27
2           6    8   34    4   19   23
3           7   23   38    5   21   19
4           3   31   35    6   15   18
5           3   14   37    7   14   23
6           8    8   35    6   14   24
7           1   10   31    5   14   25
8           3   20   40    4   18   20
9           4   13   28    5   19   13;

param c :=
1         32
2         60
3        100
4         30
5         50
6         80;

param oI :=
1         8      
2        15      
3        25      
4         5      
5        16      
6        23;

param :     t       br  :=
1          490      195
2          420      160
3          460      180
4          485      195
5          400      160
6          455      180
7          480      195
8          380      160
9          450      180;

end;