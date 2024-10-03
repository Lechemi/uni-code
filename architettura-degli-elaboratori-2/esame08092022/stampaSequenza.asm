.data
space:		.asciiz " "
.globl stampaSequenza
.text
# a0 = indirizzo base dell'array
# a1 = numero di elementi
stampaSequenza:
	move $t0 $fp
	addi $fp $sp -4
	sw $t0 ($fp)
	sw $sp -4($fp)
	sw $ra -8($fp)
	sw $s0 -12($fp)
	sw $s1 -16($fp)
	sw $s2 -20($fp)
	addi $sp $fp -20

	move $s0 $a0		# s0 = indirizzo base
	mul $s2 $a1 4		# s2 = offset limite
	li $s1 0		# s1 = offset/contatore
ciclo:
	bge $s1 $s2 fineCiclo
	
	add $t0 $s0 $s1		# t0 = indirizzo (base + offset)
	lw $t0 ($t0)		# t0 = elemento da stampare
	
	beqz $t0 nonStampare
	
	# Stampo il numero
	li $v0 1
	move $a0 $t0
	syscall
	la $a0 space
	li $v0 4
	syscall
nonStampare:
	addi $s1 $s1 4
	j ciclo
fineCiclo:
	sw $t0 ($fp)
	lw $sp -4($fp)
	lw $ra -8($fp)
	lw $s0 -12($fp)
	lw $s1 -16($fp)
	lw $s2 -20($fp)
	move $fp $t0
	
	jr $ra


