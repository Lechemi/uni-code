/*

Nota: il prof nella videolezione fa un disegno molto esplicativo
del problema. Se vuoi capire meglio, guardalo.
Occhio che secondo me lui scrive l'obiettivo sbagliato.

I costi iniziali non entrano nell'obiettivo, perché non influenzano il profitto mensile.
Sono solo una costante che trasla la funzione obiettivo ma non la modifica: se 
ho dei profitti mensili, prima o poi rientro nei costi iniziali.

Conviene entrare? sì, con x = 70 (z = 691).

Quanto tempo sarà necessario ...? f/z = 1000/691 = 1.44 mesi.

Fino a che livello sarebbe tollerabile ...? 
vedi videolezione lol

*/

# DATI
param f;	 			# costi iniziali [€]
param v;				# costi variabili [€/Kg]
param k;				# costante prezzo di acquisto materia prima [€*sqrt(Kg/mese)]
param V;				# quantità massima che il mercato può assorbire [Kg/mese]
param P;				# prezzo massimo di vendita (a cui venderemo il prodotto) [€/Kg]

# VARIABILI
/*
Allora innanzitutto impongo non-negatività;
Poi posso direttamente imporre che la produzione non
superi la quantità massima di prodotto che il mercato
può assorbire;
Infine inizializzo la variabile a 1, perché se viene inizializzata a
zero (come il solutore automaticamente farebbe), la prima valutazione
della derivata della funzione obiettivo sarebbe incomputabile:
la derivata di (k / sqrt(0)) non esiste!!!
Come vedo dalla soluzione finale, bastava inizializzare x a 1 per 
far convengere il solutore alla soluzione ottimale, dove x = 70.

*/
var x >= 0, <= V, := 1; # produzione mensile [Kg/mese]

# OBIETTIVO
# Massimizzare i ricavi (profitti - costi)
/*
Profitti = prezzo (massimo) di vendita per produzione mensile = (P * x)
Costi:
di produzione = costi variabili di produzione per produzione mensile = (v * x)
della materia prima = k/sqrt(quantità materia prima) = (k div sqrt(x))
NB: siccome il 100% della materia prima viene convertito in prodotto, la quantità
di materia prima acquistata mensilmente coincide con la quantità prodotta mensilmente,
quindi posso usare x :) 
*/
maximize z : (P * x) - (v * x) - (k div sqrt(x));

##########################
data;
param f := 1000.00; 
param v := 10.00;
param k := 80;
param V := 70;
param P := 20.00;

end;
