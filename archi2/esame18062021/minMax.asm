.globl minMax
.text
# a0 = indirizzo base array
# a1 = numero di elementi 
minMax:
	li $t0 0	# contatore/offset
	sll $a1 $a1 2
	
	lw $t2 ($a0)	# t2 = max (default: primo elemento)
	lw $t3 ($a0)	# t3 = min (default: primo elemento)
	
ciclo:
	bge $t0 $a1 fineCiclo
	
	add $t1 $a0 $t0		# t1 = indirizzo elemento
	lw $t1 ($t1)		# t1 = elemento
	
	# Verifica max
	bge $t2 $t1 notMax
	move $t2 $t1
notMax:

	# Verifica min
	ble $t3 $t1 notMin
	move $t3 $t1
notMin:
	
	addi $t0 $t0 4
	j ciclo
fineCiclo:
	move $t0 $t2
	move $t1 $t3
	
	jr $ra
