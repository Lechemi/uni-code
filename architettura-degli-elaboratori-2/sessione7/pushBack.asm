.globl pushBack
.text
# Aggiunge un elemento ($a1) in coda al vettore (base address $a0)
# Se viene ecceduta la capacità del vettore, si alloca un vettore dinamico di dimensione
# doppia rispetto a quella del vettore corrente
pushBack:
	lw $t1 0($a0)		# t1 = numero di elementi
	lw $t2 4($a0)		# t2 = capienza
	blt $t1 $t2 capienzaSufficiente
	
	# Capienza insufficiente
	
	j fine
	
capienzaSufficiente:
	addi $t2 $t1 1
	sw $t2 0($a0)
	
	sll $t3 $t1 2
	add $t3 $a0 $t3
	sw $a1 8($t3)
fine:	
	jr $ra
