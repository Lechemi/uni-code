.data

in_a:		.asciiz "Inserisci a: "
in_b:		.asciiz "Inserisci b: "
in_c:		.asciiz "Inserisci c: "
errore:		.asciiz "comando non riconosciuto "
array:		.word 1 2 3 4 5 6 7 8 9 10 11 12 13

.text
.globl main
main: 
	la $s5 array	# Indirizzo base di array

	# a : a0
	# b : a1
	# c : a2

	la $a0 in_a
	li $v0 51
	syscall
	move $s0 $a0
	
	la $a0 in_b
	li $v0 51
	syscall
	move $s1 $a0
	
	la $a0 in_c
	li $v0 51
	syscall
	move $s2 $a0
	
	# Decremento a e b così il primo elemento ha indice 1
	addi $s0 $s0 -1
	addi $s1 $s1 -1
	mul $s0 $s0 4		# offset dell'a-esimo elemento
	add $s0 $s0 $s5		# Indirizzo dell'a-esimo elemento
	mul $s1 $s1 4		# offset dell'b-esimo elemento
	add $s1 $s1 $s5		# Indirizzo del b-esimo elemento
	
	# IF
	beqz $s2 primoCaso
	beq $s2 1 secondoCaso
	beq $s2 -1 terzoCaso
	
	# Se c non è nessuno dei 3 valori
	la $a0 errore
	li $v0 4
	syscall
	j fineIf
primoCaso:
	lw $t1 0($s0)	# Metto in t1 la a-esima parola
	lw $t2 0($s1)	# Metto in t2 la b-esima parola
	sw $t1 0($s1)	# Metto t1 nella b-esima parola
	sw $t2 0($s0)	# Metto t2 nella a-esima parola
	j fineIf
secondoCaso:
	lw $t1 0($s0)	# Metto in t1 la a-esima parola
	sw $t1 0($s1)	# Metto t1 nella b-esima parola
	j fineIf
terzoCaso:
	lw $t2 0($s1)	# Metto in t2 la b-esima parola
	sw $t2 0($s0)	# Metto t2 nella a-esima parola
fineIf:
