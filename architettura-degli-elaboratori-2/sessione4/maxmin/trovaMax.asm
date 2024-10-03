.text
.globl trovaMax
trovaMax:
	li $t0 0		# t0 = contatore
	li $t4 -1		# t4 è il minimo
ciclo:		
	bge $t0 $a0 fineCiclo
	
	# Prendo l'elemento in posizione t0
	mul $t1 $t0 4 		# t1 = offset
	add $t1 $t1 $a1		# t1 = address (= offset + base)
	lw $t2 0($t1)		# t2 = array[t0]
	
	# If (t2 >= t4) then t4 = t0
	blt $t2 $t4 fineIf
	move $t4 $t2
fineIf:	
	addi $t0 $t0 1
	j ciclo
		
fineCiclo:
	move $v0 $t4	# Metto il risultato in v0
	jr $ra

