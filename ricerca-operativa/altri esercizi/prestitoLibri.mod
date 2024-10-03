/*

*/

# DATI
param nR;           # Numero richieste
set R := 1..nR;     # insieme richieste
param nL;           # numero libri
set L := 1..nL;     # libri
param s {R};        # inizio prestito
param e {R};        # fine prestito
param libro {R};    # libro

# VARIABILI
var x {R} binary;   # x[i] = 1 => richiesta soddisfatta

# VINCOLI
/*
Posso soddisfare solo una di due richieste in conflitto.
Due richieste (distinte) sono in conflitto quando:
- insistono sullo stesso libro AND
- i periodi dei prestiti si sovrappongono
*/
subject to Incompatibility {r1 in R, r2 in R: (r1 < r2) and (libro[r1] = libro[r2]) and (e[r1] >= s[r2]) and (e[r2] >= s[r1])}:
    x[r1] + x[r2] <= 1;

# OBIETTIVO
# Obiettivo 1: massimizzare il numero di richieste soddisfatte
maximize z1 : sum {r in R} x[r];

# Obiettivo 2: massimizzare la durata dei prestiti soddisfatti (estremi inclusi)
maximize z2 : sum {r in R} x[r] * (e[r] - s[r]+1);

##########################
data;






end;
