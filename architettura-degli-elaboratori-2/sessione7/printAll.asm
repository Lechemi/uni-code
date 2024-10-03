.data
space:	.asciiz " "
.globl printAll
.text
# Stampa gli elementi del vettore dinamico
# Parametri: a0 = indirizzo di inizio del vettore
printAll:

	# PREAMBOLO
	move $t0 $fp
	addi $fp $sp -4
	sw $ra ($fp)
	sw $sp -4($fp)
	sw $t0 -8($fp)
	sw $s0 -12($fp)
	sw $s1 -16($fp)
	sw $s2 -20($fp)
	addi $sp $fp -120
	move $s2 $a0
	li $s0 2	# s0 = contatore 
	lw $s1 ($a0)	# s1 = numero di elementi del vettore
	addi $s1 $s1 2	# s1 = (indice dell'ultimo elemento del vettore) + 1
	# Da indice della parola a indice del byte
	mul $s0 $s0 4
	mul $s1 $s1 4
	
for:
	bge $s0 $s1 fineFor
	
	add $t2 $s0 $s2	# t2 = indirizzo dell'elemento da stampare
	lw $t2 ($t2)		# t2 = elemento da stampare
	
	move $a0 $t2
	li $v0 1
	syscall
	li $v0 4
	la $a0 space
	syscall
	
	addi $s0 $s0 4
	j for
fineFor:
	# EPILOGO
	lw $ra ($fp)
	lw $sp -4($fp)
	lw $t0 -8($fp)
	lw $s0 -12($fp)
	lw $s1 -16($fp)
	lw $s2 -20($fp)
	move $fp $t0
	
	jr $ra