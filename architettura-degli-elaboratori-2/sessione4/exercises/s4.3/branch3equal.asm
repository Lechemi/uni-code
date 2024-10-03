# INPUT:
# $a0 $a1 $a2 tre interi
# $a3 primo indirizzo di salto
# sullo stack: secondo indirizzo di salto

	.text
	.globl branch3equal

branch3equal:
	# carico il quinto parametro (pop dallo stack)
	lw $t5 0($sp)
	add $sp $sp 4
	
	bne $a0 $a1 neq1
	# intero_0==intero_1
	beq $a1 $a2 jump1
	j nojump

neq1:
	# intero_0!=intero_1
	bne $a1 $a2 neq2
	j nojump

neq2:
	# intero_1!=intero_2
	bne $a0 $a2 jump2
	j nojump
jump1:
	# tutti uguali
	jr $a3
jump2:
	# tutti diversi
	jr $t5
nojump:
	jr $ra





