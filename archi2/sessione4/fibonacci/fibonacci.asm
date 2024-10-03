.text
.globl fibonacci

# f(n) = f(n-1) + f(n-2)
# f(0) = 0
# f(1) = 1
# In a0 ricevo n
# In v0 restituisco f(n)

fibonacci:
	# PREAMBOLO
	move $t0 $fp
	# Metto il frame pointer subito sotto lo stack pointer. Ora so dove parte il mio fp.
	addi $fp $sp -4
	# Salvo i registri s che ho sporcato
	sw $s0 ($fp)
	sw $s1 -4($fp)
	sw $s2 -8($fp)
	sw $s3 -12($fp)
	# Salvo il return address altrimenti finita questa funzione (ultima riga) non sa dove tornare
	sw $ra -16($fp)
	# Salvo i vecchi stack pointer e frame pointer: devo tornare al chiamante con lo 
	# stack nelle stesse condizioni in cui era alla chiamata (il perché è intuitivo)
	sw $sp -20($fp)
	sw $t0 -24($fp)
	# Aggiorno lo stack pointer dopo tutte le push (sw) che ho fatto. Ricorda che sp
	# sta sempre sotto a fp. fp è da dove si parte, sp dove si arriva. Aggiorno lo sp
	# perché comunque potrebbe servirmi fare altre push e pop nel corpo della funzione.
	addi $sp $fp -24
	
	# CORPO
	beqz $a0 casoBase0
	beq $a0 1 casoBase1
	
	# Imposto s0 ed s1 rispettivamente a n-1 e n-2
	move $s0 $a0
	move $s1 $a0
	addi $s0 $s0 -1
	addi $s1 $s1 -2
	
	# Chiamate ricorsive
	move $a0 $s0
	jal fibonacci
	move $s2 $v0		# Ora s2 contiene f(n-1)
	move $a0 $s1
	jal fibonacci
	move $s3 $v0		# Ora s3 contiene f(n-2)
	li $v0 1
	
	add $v0 $s2 $s3		# v0 = f(n-1) + f(n-2)
	j fine
casoBase0:
	li $v0 0
	j fine
casoBase1:
	li $v0 1
fine:	
	# EPILOGO
	# Pulisco tutti i registri che ho sporcato
	lw $s0 ($fp)
	lw $s1 -4($fp)
	lw $s2 -8($fp)
	lw $s3 -12($fp)
	lw $ra -16($fp)
	lw $sp -20($fp)
	lw $t0 -24($fp)
	move $fp $t0
	
	jr $ra