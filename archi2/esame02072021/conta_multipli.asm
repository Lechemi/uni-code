.globl conta_multipli
.text
# a0 = indirizzo base del primo array
# a1 = indirizzo base del secondo array
# a2 = N
conta_multipli:
	# PREAMBOLO
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


	li $s0 0	# Contatore/offset
	li $s1 0	# Contatore dei multipli
	move $s2 $a0
	move $s3 $a1
	sll $s4 $a2 2
	
ciclo:
	bge $s0 $s4 fineCiclo
	
	# Prendo gli elementi alla stessa posizione nei due array
	add $t0 $s2 $s0
	add $t1 $s3 $s0
	lw $t0 ($t0)
	lw $t1 ($t1)
	
	# Invoco multiplo sui due elementi
	move $a0 $t0
	move $a1 $t1
	jal multiplo
	
	# Incremento s1 solo se v0 = 1, ossia se t0 e t1 sono multipli
	beqz $v0 noMultiplo
	addi $s1 $s1 1	
noMultiplo:
	
	addi $s0 $s0 4
	j ciclo
fineCiclo:
	move $v0 $s1
	
	# EPILOGO
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



