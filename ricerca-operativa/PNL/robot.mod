/*
Devo collocare 6 circonferenze nel piano in modo che non si sovrappongano.
*/

# DATI
param nR;			# Numero di robot
set R := 1..nR;		# Insieme dei robot
param r {R}; 		# Raggio d'azione [cm]

# VARIABILI
# (x[i], y[i]) rappresenta il centro della circonferenza relativa al robot i
var x {R};
var y {R};

# VINCOLI
/*
Imponiamo che in ogni coppia di robot i,j (considero solo i casi i<j, mi basta
considerare ogni coppia solo una volta) non ci siano sovrapposizioni.

Mi basta imporre che la distanza tra i due centri sia maggiore o uguale alla 
somma dei raggi.

Eleviamo entrambi i membri al quadrato per togliere la radice quadrata.
*/
subject to NoOverlaps {i in R,j in R : i < j}:
	(x[i]-x[j])^2 + (y[i]-y[j])^2 >= (r[i] + r[j])^2;

# OBIETTIVO
/*
Devo minimizzare la distanza che c'è tra ogni coppia di robot
*/
minimize z : sum {i in R, j in R : i < j} sqrt((x[i]-x[j])^2 + (y[i]-y[j])^2);

##########################
data;

param nR := 6;

param r := 
1      120
2       80
3      100
4       70
5       45
6      120;

/*
Inizializzo le variabili in modo che due qualsiasi circonferenze non abbiano
lo stesso centro, cosa che causerebbe problemi nella funzione obiettivo (sempre
per il solito porblema della derivata della radice quadrata).

Inoltre, presto attenzione al fatto che la disposizione iniziale sia ammissibile.
Se ne mettessi una inammissibile, il solutore cercherebbe come prima cosa
l'ammissibilità, sparando via i robot lontanissimi l'uno dall'altro, trovandosi
quindi a lavorare con numeri grandi (che non gli piacciono => badly scaled).
*/
var: 	x	y :=
1		100	200
2		400	500
3		700	800
4		800	700
5		500	400
6		200	100;

end;