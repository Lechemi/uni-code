/*
Vedi video per spiegazione dettagliata.
In sostanza, ogni piano è rappresentabile come una retta in un piano con
ascisse  : tempo di utilizzo
ordinate : costo di utilizzo
più è alto il costo cf di prenotazione, più è basso il coefficiente 
angolare della retta, in modo che, per utilizzi prolungati, convenga
affrontare i costi di prenotazione.
In breve conviene sempre scegliere, in ogni tratto del piano, la retta che sta sotto 
alle altre -> si ottiene una funzione lineare a tratti.

Ricavare la curva dei costi minimi in funzione della capacità complessiva 
richiesta (ore di calcolo richieste in un anno) significa stabilire per quali
valori della capacità richiesta cambia il piano ottimale da acquistare.
Geometricamente, dobbiamo stabilire le x (nel senso le ascisse) per cui 
la funzione lineare a tratti cambia, ossia le x relative ai vertici dei tratti.
Combinando queste x con le rette che rappresentano i vari piani, otteniamo 
la curva minima richiesta.

*/

# DATI
param n; 
set S := 1..n;
param cf {S};	# Costo fisso per tutto l'anno [€]
param cv {S};	# Costo variabile [€/h]
param epsilon;	# [ore]

# VARIABILI
var costo;	# [€]
var q;		# Capacità richiesta [ore]

# VINCOLI
# costo dev'essere sempre <= di ogni retta
subject to Minimo {i in S} :
	costo <= cf[i] + cv[i] * q;

# OBIETTIVO
/*
L'obiettivo è trovare, per ogni valore di q (capacità richiesta) la retta minima
(ossia il piano acquistabile più conveniente) tra quelle presenti.
E' il caso già capitato di linearizzazione della funzione min(), che 
si fa massimizzando la variabile ausiliaria costo, la quale sottosta al vincolo
Minimo. In questo modo, costo viene massimizzata finché non incontra la retta
più bassa (in corrispondenza sempre di q).
*/
maximize z: costo;
subject to Parametric : q <= epsilon;

##########################
data;

param epsilon := 5479;

param n := 4;

param :  cf		 cv		:=
1		1560	0.128
2		1280	0.192
3		552		0.312
4		0		0.640;

end;
