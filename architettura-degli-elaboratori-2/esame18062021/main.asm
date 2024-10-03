.data
A:	.word 34

frase:	.asciiz ""

.globl main
.text
main:
	la $a0 frase
	jal contaParole
	move $s0 $v0
	
	li $v0 1
	move $a0 $s0
	syscall
	
	li $v0 10
	syscall