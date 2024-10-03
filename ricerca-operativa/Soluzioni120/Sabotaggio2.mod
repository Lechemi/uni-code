# Esercizio Sabotaggio
# DATI
set N := 1..8;
set A within {N,N};
param c {A};
set O := {j in N: card({(i,j) in A})=0};
set D := {j in N: card({(j,i) in A})=0};

# VARIABILI
var x {(i,j) in A} >=0, <=c[i,j];

# VINCOLI
subject to Flow_cons {j in N diff (O union D)}:
  sum {(i,j) in A} x[i,j] = sum {(j,i) in A} x[j,i];
  
# OBIETTIVO
maximize z: sum {(i,j) in A: j in D} x[i,j];

###################
data;
param: 	 A : 	  c := 
		1 3      58    
		2 4      31    
		3 4      40    
		3 5      12    
		4 6      66    
		5 7      59    
		6 5      25    
		6 8      36;

end;
