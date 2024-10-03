/*
Esercizio un po' nascosto nella pagina del Righini
(vedi videolezioni per scoprire dove)
*/

# DATI
param nL;               # Numero di luoghi
param nU;               # Numero di utenti
set L := 1..nL;         # Luoghi
set U := 1..nU;         # Utenti
param costo {L};        # Costo per luogo [€]
param copertura {L,U};  # Copertura dei luoghi per ogni utente (dato binario)

# VARIABILI
/*
L'obiettivo qua è coprire tutti gli utenti col minor costo
possibile: dobbiamo scegliere in quali luoghi erogare il servizio.
*/
var x {L} binary;       # 1 -> i eroga il servizio; 0 -> i non eroga

# VINCOLI
# Ogni utente dev'essere coperto 
# x[i] * copertura[i,j] = scelgo di erogare AND il servizio è disponibile
# Non basta che x = 1, deve anche essere copertura[i,j] = 1
subject to Copertura {j in U} : sum {i in L} x[i] * copertura[i,j] >= 1;

# OBIETTIVO
minimize z : sum {i in L} x[i] * costo[i];

##########################
data;






end;