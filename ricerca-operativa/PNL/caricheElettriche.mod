/*
TESTO
All'estremità di n bastoncini di uguale lunghezza sono 
poste cariche elettriche uguali e dello stesso segno, che quindi si respingono
tra loro con una forza proporzionale al quadrato della loro distanza. All'altra
estremità i bastoncini sono uniti: i loro estremi sono nello stesso punto. 
I bastoncini possono orientarsi liberamente in qualunque direzione nello spazio in
tre dimensioni.
Si vuole trovare la configurazione di equilibrio per diversi valori di n. In
particolare, sono di interesse i casi n = 5 e n = 7, dove la soluzione ottima non
può essere ricavata facilmente con semplici considerazioni geometriche basate
sulla simmetria (come invece è possibile per i casi n = 2, 3, 4 e 6).
N.B. La configurazione di equilibrio è quella di minima energia e l'energia dovuta 
all'interazione tra due cariche è inversamente proporzionale alla loro distanza.

traduzione: tutti i bastoncini hanno un estremo nell'origine degli assi e l'altro (quello carico) libero di muoversi
nello spazio tridimensionale. Chiaramente gli estremi carichi si respingono. E' facile immaginarsi i casi in cui
ci sono 2,3,4 o 6 bastoncini (pensa alle forme delle molecole), ma con 5 o 7 non è banale.
Dobbiamo determinare le posizioni degli estremi liberi dei bastoncini.

NB: la lunghezza dei bastoncini non influenza il risultato, quindi la scelgo unitaria.
*/

# DATI
param n;			# Numero bastoncini
set B := 1..n;		# Insieme dei bastoncini

# VARIABILI
# Coordinate degli estremi liberi
var x {B};
var y {B};
var z {B};
# (AUSILIARIE)
var d {i in B, j in B : j > i} = sqrt((x[i]-x[j])^2 + (y[i]-y[j])^2 + (z[i]-z[j])^2); 	# Distanza tra ogni coppia di estremi

# VINCOLI
# Gli estremi devono trovarsi sulla superficie della sfera di raggio 1 (ho scelto che i bastoncini sono lunghi 1)
subject to LunghezzaBastoncini {b in B} : x[b]^2 + y[b]^2 + z[b]^2 = 1;

# OBIETTIVO
/*
Dati due estremi carichi distanti d, l'energia dovuta alla loro interazione è proporzionale a 1/d.
(quindi posso considerare E = 1/d)
Quindi l'energia totale della configurazione di n bastoncini è la somma di tutte le energie relative alle coppie di bastoncini.
Metto j > i così da non considerare coppie formate dallo stesso bastoncino e per non considerare due volte la stessa coppia. 
*/
minimize E : sum {i in B, j in B: j > i} 1 / d[i,j];

##########################
data;

param n := 7;

# Inizializzazione degli estremi per evitare sqrt(0)
var :	x	y	z :=
1		1	1	2
2		0	0	1
3		1	0	0
4		1	-1	0
5		0	1	1
6		0	1	-1
7		-2	0	3;


end;