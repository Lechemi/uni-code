.globl palindroma
.text
# a0 = indirizzo base della stringa
palindroma:
	
	li $t1 0
ciclo1:
	add $t2 $a0 $t1
	lb $t2 ($t2)
	
	beqz $t2 fineCiclo1
	
	addi $t1 $t1 1
	j ciclo1
fineCiclo1:
	addi $t1 $t1 -1
	# Ora t1 è l'indice dell'ultimo carattere della stringa
	
	li $t0 0
	# faccio scorrere t0 da sinistra a destra e t1 da destra a sinistra e confronto
	# parola[t0] con parola[t1] ad ogni iterazione
ciclo2:
	bgt $t0 $t1 fineCiclo2

	add $t2 $a0 $t0
	add $t3 $a0 $t1
	lb $t2 ($t2)
	lb $t3 ($t3)
	
	bne $t2 $t3 noPalindroma
	
	addi $t0 $t0 1
	addi $t1 $t1 -1
	j ciclo2
noPalindroma:
	li $v0 0
	j fine
fineCiclo2:
	li $v0 1
fine:
	jr $ra