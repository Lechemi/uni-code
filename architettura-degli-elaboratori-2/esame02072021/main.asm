.data

S: 	.asciiz "itopinonavevanonipoti"

A1:	.word 1 9 5 9 5 1 222
A2:	.word 4 3 3 8 14 1 111

.globl main
.text 
main:
	la $a0 A1
	la $a1 A2
	li $a2 7
	jal conta_multipli
	move $s0 $v0
	
	li $v0 1
	move $a0 $s0
	syscall
	
	li $v0 10
	syscall