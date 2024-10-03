.data

array:		.space 40	# array "vuoto" di 10 word
inMsg:		.asciiz "Inserisci l'elemento dell'array (termina con -1): "
kMsg:		.asciiz "Inserisci 0 per dispari e 1 per pari: "
resMsg:		.asciiz "Risultato: "

.text
.globl main
main:
		la $s1 array
		li $t0 0
cicloIn: 	
		bge $t0 40 fineCicloIn		# Ho riempito tutto l'array

		li $v0 51
		la $a0 inMsg
		syscall
		move $t1 $a0			# Ora t1 contiene il numero inserito		
		
		# Se l'utente inserisce -1, ha terminato l'input
		beq $t1 -1 fineCicloIn
		
		# Carico nell'array il numero inserito
		add $t2 $s1 $t0
		sw $t1 0($t2)
		
		addi $t0 $t0 4
		j cicloIn
fineCicloIn:

		li $v0 51
		la $a0 kMsg
		syscall
		move $s2 $a0	# s2 contiene k

		# Chiamo P
		move $a0 $s1
		move $a1 $s2
		jal P
		move $s3 $v0
		
		li $v0 56
		la $a0 resMsg
		move $a1 $s3
		syscall
		
		li $v0 10
		syscall

