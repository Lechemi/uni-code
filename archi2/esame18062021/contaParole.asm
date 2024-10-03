.globl contaParole
.text
# a0 = indirizzo base della stringa
contaParole:
	li $t0 0	# contatore/offset
	li $t2 0	# contatore degli spazi
ciclo:
	# Prendo il carattere in posizione t0
	add $t1 $a0 $t0
	lb $t1 ($t1)		# ora t1 contiene il carattere
	beqz $t1 fineCiclo
	
	# Controllo se t1 è uno spazio o no
	bne $t1 32 notSpace
	addi $t2 $t2 1
notSpace:

	addi $t0 $t0 1
	j ciclo
fineCiclo:
	beqz $t0 empty
	add $v0 $t2 1
empty:	
	jr $ra