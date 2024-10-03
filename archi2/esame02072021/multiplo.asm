.globl multiplo
.text
# a0 = primo numero
# a1 = secondo numero
multiplo:
	# Prima trovo il più piccolo dei due
	# faccio in modo che t0 contenga il più piccolo, t1 il più grande
	
	bge $a0 $a1 a0Bigger
	# a1 è il più grande
	move $t0 $a0
	move $t1 $a1
	j fineIf
a0Bigger:
	# a0 è il più grande
	move $t0 $a1
	move $t1 $a0
fineIf:
	# Itero per tutti i multipli di t0 finché non raggiungo o sorpasso t1
	move $t3 $t0
ciclo:
	bge $t3 $t1 fineCiclo

	add $t3 $t3 $t0
	j ciclo
fineCiclo:
	# Se t3 è uguale a t1, allora t0 è multiplo di t1; altrimenti no
	beq $t3 $t1 multi
	li $v0 0
	j fine
multi:
	li $v0 1
fine:		
	jr $ra
	
	
	
	
	