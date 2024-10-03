/*
Dovevo considerare il volume totale, non solo quello a partire da metà bicchiere
*/

# DATI
param ro;		# Raggio del bicchiere all'orlo
param rb;		# Raggio del bicchiere alla base
param h;		# Altezza del bicchiere
param rm = rb + (ro - rb) * (1/2);	# raggio a metà bicchiere

# VARIABILI
var la >= 0, <= h/2; 		# Livello dell'acqua a partire da metà bicchiere
# (AUSILIARIE)
# Raggio del bicchiere al livello la
var ra = rb + (ro - rb) * (1/2 + la/h);

# VINCOLI

# OBIETTIVO
maximize z: (1/3)*3.14*la*(ra^2 + rm^2 + ra*rm) * (1-(la/(h/2)));

##########################
data;

param ro := 4;
param rb := 3;
param h := 8;

#var la = 0;

end;
