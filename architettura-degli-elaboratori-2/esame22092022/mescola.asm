.globl mescola
.text
# a0 = indirizzo base dell'array
# a1 = numero di carte
mescola:
	move $t0 $fp
	addi $fp $sp -4
	sw $t0 ($fp)
	sw $sp -4($fp)
	sw $ra -8($fp)
	sw $s0 -12($fp)
	sw $s1 -16($fp)
	sw $s2 -20($fp)
	sw $s3 -24($fp)
	sw $s4 -28($fp)
	addi $sp $fp -28

	move $s3 $a0
	move $s4 $a1
	li $s0 0
ciclo:
	bge $s0 100 fineCiclo

	li $a0 1
	li $v0 41
	syscall
	move $s1 $a0		# s1 = random
	rem $s1 $s1 $s4		# s1 = random % N 
	
	bge $s1 0 nonInvertire1
	mul $s1 $s1 -1
nonInvertire1:

	li $a0 1
	li $v0 41
	syscall
	move $s2 $a0		# s2 = random
	rem $s2 $s2 $s4		# s2 = random % N 
	
	bge $s2 0 nonInvertire2
	mul $s2 $s2 -1
nonInvertire2:
	
	# Scambio le carte aventi indici s1 e s2
	add $s1 $s3 $s1		# s1 = indirizzo prima carta
	add $s2 $s3 $s2		# s2 = indirizzo seconda carta
	
	lb $t0 ($s1)		# t0 = prima carta
	lb $t1 ($s2)		# t1 = seconda carta
	
	# Memorizzo in posizioni invertite
	sb $t1 ($s1)
	sb $t0 ($s2)

	addi $s0 $s0 1
	j ciclo
fineCiclo:
	lw $t0 ($fp)
	lw $sp -4($fp)
	lw $ra -8($fp)
	lw $s0 -12($fp)
	lw $s1 -16($fp)
	lw $s2 -20($fp)
	lw $s3 -24($fp)
	lw $s4 -28($fp)
	move $fp $t0

	jr $ra
