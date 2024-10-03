.globl rimuoviMultipli
.text
# a0 = indirizzo base dell'array
# a1 = numero di elementi
# a2 = k
rimuoviMultipli:
	li $t0 0		# t0 = contatore
	mul $t1 $a2 2		# t1 = 2k
ciclo:
	bge $t0 $a1 fineCiclo
	
	addi $t2 $t0 1
	bne $t2 $t1 nonMultiplo
	sll $t2 $t0 2		# t2 = offset
	add $t2 $t2 $a0		# t2 = indirizzo (base + offset)
	sw $zero ($t2)		# Sostituisco con zero
	add $t1 $t1 $a2		# passo al prossimo multiplo
nonMultiplo:
	addi $t0 $t0 1
	j ciclo
fineCiclo:

	jr $ra