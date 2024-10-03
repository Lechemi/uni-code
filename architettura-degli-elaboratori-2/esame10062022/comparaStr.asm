.globl comparaStr
.text
# a0 = indirizzo base prima stringa
# a1 = indirizzo base seconda stringa
# v0 = 0 oppure 1
comparaStr:
	li $t0 0		# t0 = contatore
ciclo: 
	add $t1 $a0 $t0
	lb $t1 ($t1)			# ora t1 = carattere della prima stringa
	
	add $t2 $a1 $t0
	lb $t2 ($t2)			# ora t2 = carattere della seconda
	
	bne $t2 $t1 diverse		# if t1 != t2 then break
	
	beqz $t1 fineCiclo		# Se t1,t2 = 0, è terminata la stringa
	addi $t0 $t0 1
	j ciclo
fineCiclo:
	# Se arrivo qua non ho trovato caratteri diversi
	li $v0 1
	j fine
diverse:
	# Se arrivo qua ho trovato un carattere diverso
	li $v0 0
fine:
	jr $ra