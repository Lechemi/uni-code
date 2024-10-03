/*
Dobbiamo scegliere dove collocare nel piano le piattaforme, che
automaticamente determinano poi la disposizione delle passerelle.

L'HO FATTO DIVERSO DAL PROF E MI VIENE GIUSTO!!! ON GANG!!! CON MENO VARIABILI!
(o almeno sembra essere giusto)

Il problema è convesso?
Per ogni piattaforma, fissate le altre, il sotto-problema è convesso!
Infatti c'è solo un minimo per ogni piattaforma (fissate le altre obv) e la 
regione ammissibile è convessa.
Quindi sì, il problema è convesso e quindi la soluzione è ottimo globale.
*/

# DATI
param nV;				# Numero vicoli
set V := 1..nV;			# Insieme dei vicoli
param nP;				# Numero di piattaforme
set P := 1..nP;			# Insieme delle piattaforme
#Coordinate degli imbocchi dei vicoli
param xV {V};
param yV {V};

# VARIABILI
# Coordinate delle piattaforme
var xP {P};
var yP {P};

#Distanza del vicolo v dalla piattaforma più vicina
var dVP {v in V} = min {p in P} sqrt((xV[v]-xP[p])^2 + (yV[v]-yP[p])^2);

# Distanza della piattaforma p dalla piattaforma più vicina
var dPP {i in P} = min {j in P: j != i} sqrt((xP[i]-xP[j])^2 + (yP[i]-yP[j])^2);

# VINCOLI
# No vincoli lol (o meglio, gli unici sono nella definizione delle distanze)

# OBIETTIVO
/*
Devo minimizzare la lunghezza totale delle passerelle, ossia la distanza totale
tra vicoli e piattaforme  e tra piattaforme e piattaforme.
*/
minimize z : (sum {v in V} dVP[v]) + (sum {p in P} dVP[p]);

##########################
data;

param nV := 10;
param nP := 3;

param xV (tr): 1  2  3  4  5  6  7  8  9 10 :=
0			   0  0  3  5  9 11 11 11  8  6;

param yV (tr): 1  2  3  4  5  6  7  8  9 10 :=
0			   1  3  7  9  9  8  7  4  1  1;

var:	xP		yP 	:=
1		3		5
2		6		5
3		9		5;

end;