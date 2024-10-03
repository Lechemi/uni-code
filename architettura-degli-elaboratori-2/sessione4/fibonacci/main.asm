.data

msg:	.asciiz "Inserisci il numero: "

.text
.globl main
main:
	li $v0 51
	la $a0 msg
	syscall
	
	jal fibonacci
	move $t0 $v0
	
	# Stampo il risultato
	li $v0 1
	move $a0 $t0
	syscall	
	
	li $v0 10
	syscall