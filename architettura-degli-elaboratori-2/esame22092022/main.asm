.data
mazzo:		.byte '1' '2' '3' '4' '5' '6' '7' 'J' 'Q' 'K'
.globl main
.text
main:	
	la $s0 mazzo
	
	move $a0 $s0
	li $a1 10
	jal mescola
	
	li $v0 10
	syscall
	