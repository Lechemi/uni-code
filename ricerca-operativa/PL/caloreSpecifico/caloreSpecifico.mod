/*
Il calore specifico di una sostanza è il rapporto 
tra la quantità di calore fornita e la variazione di temperatura conseguente:
cs[i] = Q[i] / deltaT[i]

=>  Q[i] = cs[i] * deltaT[i]

La somma dei calori assorbiti da ciascuna sostanza dev'essere uguale al calore totale fornito dal fornelletto.
[questo per ciascun esperimento, Qtot infatti dipende dalla durata dell'esperimento]
Q[1] + Q[2] + ... Q[nA] = Qtot

Per ogni esperimento e
Qtot[e]/d[e] = f    =>    Qtot[e] = f * d[e]
*/

# DATI
param nS := 12;         # Numero di studenti
param nA := 8;          # Numero ampolle
set S := 1..nS;         # Insieme degli studenti
set A := 1..nA;         # Insieme delle ampolle
param d {S};            # Durata di ciascun esperimento [min]
param deltaT {S,A};     # Aumento di temperatura per ogni ampolla in ogni esperimento [°C]
param f;                # Quantità di calore per unità di tempo erogata dal fornelletto [cal/min]

# VARIABILI
/*
Ciò che voglio trovare è il calore specifico di ciascuna sostanza. 
Mi basterebbero 8 esperimenti perché ho 8 sostanze, quindi se ogni esperimento corrisponde 
ad un'equazione => 8 variabili (8 calori specifici) per 8 equazioni e il gioco è fatto (poi vedi che equazioni sono nei vincoli).
Qua però ho 12 equazioni, più di quelle necessarie, perché ci sono anche errori dovuti alla dispersione 
del calore nell'ambiente, quindi più misurazioni fanno bene.
(l'unità di misura è quella giusta del calore specifico, ma la formula data dal prof è leggermente sbagliata, quindi 
darebbe un'altra unità di misura. fa niente.)
*/
var c {A} >= 0;         # Calore specifico di ciascuna sostanza [J / (kg * K)]
var error {S};          # Errore sul calore specifico per ogni misurazione [J / (kg * K)]

# VINCOLI
/*
La somma dei calori assorbiti dalle sostanze dev'essere uguale al calore totale fornito dal fornelletto. [cal]
(vedi calcoli sopra per capire).
*/
subject to CaloriAssorbiti {e in S} :
    f * d[e] = sum {a in A} c[a] * deltaT[e,a];

# OBIETTIVO
/*
Minimizzare l'errore quadratico medio.
*/


##########################
data;

param f := 10;

param d :=
1   1.0
2   2.0
3   1.0
4   1.5
5   0.5
6   1.0
7   1.0
8   2.0
9   1.2
10  2.5
11  0.2
12  0.5;

param deltaT : 1    2   3   4   5   6   7   8   :=
1           0.2 0.4 0.5 0.6 0.6 0.5 0.2 0.2
2           0.1 1.0 0.1 1.2 1.1 1.0 0.6 0.3
3           0.1 0.5 0.5 0.7 0.5 0.5 0.1 0.2
4           0.2 0.6 0.6 0.8 0.6 0.6 0.2 0.3
5           0.1 0.4 0.2 0.3 0.2 0.2 0.0 0.2
6           0.2 0.5 0.4 0.7 0.5 0.4 0.2 0.3
7           0.3 0.4 0.5 0.8 0.4 0.4 0.2 0.2
8           0.6 0.8 1.0 1.5 1.3 0.6 0.5 0.6
9           0.3 0.4 0.5 0.7 0.5 0.5 0.3 0.2
10          0.5 0.9 1.1 1.3 0.9 1.2 0.8 0.5
11          0.0 0.2 0.0 0.0 0.1 0.0 0.0 0.1
12          0.0 0.5 0.2 0.3 0.2 0.1 0.1 0.3;

end;
