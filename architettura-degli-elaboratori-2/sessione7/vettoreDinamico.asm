.data

test: 	.word 4 8 1 2 3 5 0 0 0 0

.text
.globl main
main:

	la $a0 test
	li $a1 12
	jal pushBack

	la $a0 test
	jal printAll
	
	li $v0 10
	syscall
	
