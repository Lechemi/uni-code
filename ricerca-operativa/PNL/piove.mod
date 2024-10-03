/*
*/

# DATI
param H;	# Altezza della persona
param S;	# Altezza dell'impugnatura
param B;	# Lunghezza del manico
param L;	# Diametro dell'ombrello
param alfa_max;	# Inclinazione massima del corpo
param v;	# Velocità di caduta della pioggia
param w;	# Velocità del vento
param pi;
param l1 := sqrt((L/2)^2 + B^2);

# VARIABILI
var alfa >= pi/2+0.4, <= pi;
var beta >= 0, <= pi/2; 
var r >= 0, <= 0;		# Velocità della persona
# (AUSILIARIE)
var gamma >= 0, <= pi/2;
var lambda = alfa - pi/2 - beta - gamma;
var h1 = l1 * sin(lambda);
var h2 = -S * cos(alfa);
var h3 = h2 + h1;
var l2 = sin(alfa + beta + gamma) * l1;
var l3 = l2 + sqrt(S^2 - h2^2);
var l4 = L * sin(gamma + lambda);
var h4 = L * cos(gamma + lambda);

# VINCOLI
# Definizione di gamma
subject to Gamma: sin(gamma)/cos(gamma) = L/(2*B);

# Piedi
subject to Piedi: -1/v * h3 * (w + r) + l3 >= 0;

# Testa
subject to Testa: -H * cos(alfa) <= v/w * H * sin(alfa) - (l3 - l4) * v/w + h3 + h4;

# OBIETTIVO
maximize z: r;

##########################
data;

param H := 1.80;
param S := 1.40;
param B := 0.90;
param L := 0.80;
param alfa_max := 0.4;
param v := 1.00;
param w := 0.75;
param pi := 3.14;

end;
