.globl generaSequenza
.text
# Dato un numero N (a0), restituisce un array di N numeri crescenti
# Restituisce in v0 l'indirizzo dell'array allocato
generaSequenza:
	move $t0 $fp
	addi $fp $sp -4
	sw $t0 ($fp)
	sw $sp -4($fp)
	sw $ra -8($fp)
	sw $s0 -12($fp)
	addi $sp $fp -12

	move $s0 $a0		# s0 = numero di elementi
	mul $a0 $s0 4		# a0 = numero di byte da allocare (4 per numero)
	li $v0 9
	syscall			# ora v0 = indirizzo base dell'array
	
	li $t0 0
ciclo:
	bge $t0 $s0 fineCiclo
	
	mul $t2 $t0 4		# t2 = offset
	add $t1 $v0 $t2	# t1 = indirizzo (base + offset)
	add $t3 $t0 1
	sw $t3 ($t1)
	
	addi $t0 $t0 1
	j ciclo
fineCiclo:
	lw $t0 ($fp)
	lw $sp -4($fp)
	lw $ra -8($fp)
	lw $s0 -12($fp)
	move $fp $t0
	
	jr $ra
