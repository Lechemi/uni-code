.globl minPotenza
.text
# a0 = x
# a1 = b
# v0 = minima potenza di b maggiore o uguale ad x
# v1 = distanza tra x e v0
minPotenza:
	move $t0 $a1
	# Prima testo per b^0
	ble $a0 1 zeroPower
	
	# Da qua testo le potenze con esponente da 1 in su
ciclo:
	bge $a1 $a0 foundPower
	mul $a1 $a1 $t0
	j ciclo
foundPower:
	move $v0 $a1
	sub $v1 $v0 $a0
	j fine
zeroPower:
	li $v0 1
	sub $v1 $v0 $a0
	j fine
fine:
	jr $ra
	