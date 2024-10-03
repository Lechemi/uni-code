/*

Anche qua il prof non è stato chiaro nella formulazione del problema.
Innanzitutto la stanza va vista in pianta, quindi è un problema a 
2 dimensioni. Il sensore vede a 360°, quindi vede sempre tutte le pareti,
ma l'angolo con cui vede ogni quadro è definito dal triangolo formato
da: sensore, estremo A del quadro, estremo B del quadro.
Quindi l'angolo di cui si parla è quello compreso tra i segmenti 
sensore-A e sensore-B, ovviamente calcolabile usando la formula 
suggerita. 
Essendo che questa contiene il coseno, il problema è non-lineare.

La posizione ideale è ovviamente quella in cui il sensore vede ogni
quadro il meglio possibile, ossia con l'angolo maggiore possibile.
Qua entrano in gioco i progettisti, che hanno ciascuno la propria idea
sul modo in cui ottimizzare la visuale del sensore.

*/

# DATI
param nQ;			# Numero quadri
set Q := 1..nQ;		# Insieme quadri
param xa {Q};		# Coordinata x dell'estremo a di ciascun quadro
param xb {Q};		# Coordinata x dell'estremo b di ciascun quadro
param ya {Q};		# Coordinata y dell'estremo a di ciascun quadro
param yb {Q};		# Coordinata y dell'estremo b di ciascun quadro
param L; 			# Lato della stanza
param dmin;			# Distanza minima dalle pareti

# VARIABILI
# Inserisco direttamente il vincolo sulla distanza minima dalle pareti
# e inizializzo il sensore in modo che sia al centro della stanza
var x >= dmin, <= L-dmin, := L/3;		# Coordinata x del sensore
var y >= dmin, <= L-dmin, := L/3;		# Coordinata y del sensore
# (AUSILIARIE)
/*
Questo è l'angolo di cui si parla nel commento iniziale (uno per quadro).
Lo vincolo ad essere compreso tra 0 e pi/2, perché voglio escludere tutti
i suoi multipli, che comunque soddisfano l'equazione ma non mi interessano.
*/
var angolo {Q} >= 0, <= 3.14;

/*
Lunghezze dei segmenti di ciascun triangolo.

A = sensore-A
B = sensore-B
C = A-B
(In realtà potrei definire C come parametro, visto che dipende solo da dati)

*/
var A {i in Q} = sqrt((x-xa[i])^2 + (y-ya[i])^2);
var B {i in Q} = sqrt((x-xb[i])^2 + (y-yb[i])^2);
param C {i in Q} = sqrt((xb[i]-xa[i])^2 + (yb[i]-ya[i])^2);

# Ausiliaria per max-min
var angoloMinimo;

# VINCOLI
# Literally la formula del suggerimento
subject to Angolo {i in Q}:
	C[i]^2 = A[i]^2 + B[i]^2 - 2 * A[i] * B[i] * cos(angolo[i]);

# OBIETTIVO
/*
Tizio: massimizzare somma degli angoli

Considerazioni post-soluzione: questo approccio fa si che ci siano 3 massimi locali, uno per ciascun
quadro. Ogni massimo è del tipo: minimizzo la distanza dal quadro i e me ne sbatto degli altri 2.
(non so di preciso perché è così e non si ha l'ottimo, per esempio, cercando di bilanciare le tre
distanze).
Il massimo a cui giunge il solutore dipende ovviamente dall'inizializzazione. 

Si tratta quindi di un problema non convesso.
*/
#maximize zTizio: sum {i in Q} angolo[i];

/*
Caio: massimizzare il minimo angolo (max-min)

La soluzione è unica (non ho capito perché)
*/
maximize zCaio: angoloMinimo;
subject to Maxmin {i in Q}:
	angoloMinimo <= angolo[i];

##########################
data;

param nQ := 3;
param L := 16;
param dmin := 1;

param:	xa	ya	xb	yb	:=
1		0	0	0	4
2		0	10	6	16
3		15	0	10	0;

end;