.text
.globl fact
fact:
	# PREAMBOLO
	move $t1 $fp
	addi $fp $sp -4
	sw $s0 ($fp)
	sw $ra -4($fp)
	sw $sp -8($fp)
	sw $t1 -12($fp)
	addi $sp $fp -12

	# CORPO
	# in $a0 ho l'input
	move $s0 $a0 		# Ora s0 è il numero di cui calcolare il fattoriale
	# Devo mettere il valore di a0 in s0 perché a0 mi serve anche dopo aver chiamato
	# fact ricorsivamente, per fare a0 * fact(a0-1)
	beqz $s0 casoBase
	
	# Caso ricorsivo
	addiu $a0 $s0 -1
	jal fact
	move $t0 $v0
	mul $v0 $s0 $v0
	j fine
casoBase:	
	li $v0 1
fine:
	# EPILOGO
	lw $s0 ($fp)
	lw $ra -4($fp)
	lw $sp -8($fp)
	lw $t1 -12($fp)
	move $fp $t1

	jr $ra