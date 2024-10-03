.globl main
.text
main:
	li $v0 5	# Prendo numero in input
	syscall
	
	li $s0 0	# Somma (da restituire)
	li $t0 1	# Contatore (parte da 1 perché sono i primi v0-1)
ciclo:
	bge $t0 $v0 fineCiclo	# if (t0 ≥ v0) then break;
	# Mettiamo il ≥ al posto del = perché se ci pensi nel for metteresti for (t0 < v0)
	# L'opposto di t0 < v0 (condizione per restare nel for) è t0 ≥ v0, che è infatti la condizione di uscita
	
	mul $t1 $t0 $t0		# Quadrato 
	add $s0 $s0 $t1		# Somma 
	addi $t0 $t0 1		# Incremento
	j ciclo
	
fineCiclo:
	move $a0 $s0	# Mettiamo in a0 il risultato per poterlo stampare
	li $v0 1	# Per scrivere il numero su terminale
	syscall

	li $v0 10		# Termino programma
	syscall
	