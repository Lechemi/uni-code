/*
Il peso equivale al volume, a meno della costante densità, quindi posso usare il volume al posto del peso.
*/

# DATI
param V;			# Volume della scatoletta

# VARIABILI
var r >= 0;			# Raggio della base della scatoletta
var h >= 0;			# Altezza della scatoletta

# VINCOLI
subject to Volume: 3.14*r^2*h = V;

# OBIETTIVO
minimize A: 2*3.14*r*(r+h);

##########################
data;

param V := 3.2514;

var r = 1;
var h = 1;

end;

