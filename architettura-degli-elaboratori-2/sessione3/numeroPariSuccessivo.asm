.text 
.globl main
main:
	li $v0 5	# Per leggere il numero da terminale
	syscall
	
	li $t7 2
	div $v0 $t7	# Divido v0 per 2 (devo per forza usare un altro registro)
	mfhi $t1 	# Porto il resto della divisione (da hi) in t1 [in lo c'è il quoziente]
	beqz $t1 pari	# Salto a "pari" sse t1 è 0

dispari: # posso anche togliere sta etichetta, la metto per leggibilità
	addi $a0 $v0 1
	j stampa
pari:
	addi $a0 $v0 2
	
	# Metodo molto più sintetico senza controllo di flusso
	# addi $a0 $v0 1		# a0 = v0 + 1
	# andi $t0 $a0 1		# t0 = v0 AND 1 (t0 = 1 sse il LSB di v0 è 1; vedi 1 come 000...001)
	# add $a0 $t0 $a0		# a0 = a0 + t0

stampa:
	li $v0 1	# Per scrivere il numero su terminale
	syscall
	
qui : 
	li $v0 10	# Per terminare il programma
	syscall