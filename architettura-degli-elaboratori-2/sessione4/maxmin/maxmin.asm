.data

mioArray:	.word 12 3 31 56 222 0
msgMin: 	.asciiz "Il minore dei numeri è: "
aCapo:		.asciiz "\n"
msgMax: 	.asciiz "Il maggiore dei numeri è: "

.text
.globl main
main:
	la $a1 mioArray		# Indirizzo base di mioArray
	li $a0 6		# Lunghezza mioArray
	jal trovaMin		# Salto alla funzione trovaMin
	move $s0 $v0		# Metto il risultato ottenuto da trovaMin in s0
	jal trovaMax		# Salto alla funzione trovaMax
	move $s1 $v0		# Metto il risultato ottenuto da trovaMax in s1
	
	li $v0 4
	la $a0 msgMin
	syscall			# Stampo msgMin
	
	li $v0 1
	move $a0 $s0
	syscall			# Stampo s0ù
	
	li $v0 4
	la $a0 aCapo
	syscall			# Stampo l'a-capo
	
	li $v0 4
	la $a0 msgMax
	syscall			# Stampo msgMax
	
	li $v0 1
	move $a0 $s1
	syscall			# Stampo s1
	
	li $v0 10
	syscall			# Uscita dal programma (per evitare loop)
