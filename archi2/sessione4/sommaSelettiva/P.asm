.text 
.globl P

P:	
	# Ho in a0 il base address dell'array
	# Ho in a1 k
	
	# Il contatore (t0) parte da 1-k e viene incrementato di 2 ogni volta
	# Così se k è 0 faccio i dispari; se è 1 faccio i pari
	
	li $s5 0	# s5 contiene la somma
	
	li $t0 1
	sub $t0 $t0 $a1
	mul $t0 $t0 4			# Offset
	
ciclo:
	bge $t0 40 fineCiclo		# L'array ha al massimo 10 elementi (10 * 4 byte)
	
	# Prendo l'elemento dell'array
	add $t1 $a0 $t0			# Ora t1 contiene l'indirizzo
	lw $t1 0($t1)			# Ora t1 contiene il valore all'indirizzo
	
	# Incremento la somma
	add $s5 $s5 $t1			
	
	# Passo NON alla word successiva (+4) ma a quella dopo (+8)
	addi $t0 $t0 8
	j ciclo
	
fineCiclo:
	move $v0 $s5
	jr $ra