.data

A:	.word 12 5 10 40 16
n:	.word 5

.globl main
.text
main:
	la $s0 A
	la $s1 n
	lw $s1 ($s1)
	sll $s1 $s1 2
			
	li $s4 9999999		# distanza del campione
	li $s5 0		# indice del campione
	
	li $s2 0
ciclo:
	bge $s2 $s1 fineCiclo

	add $s3 $s0 $s2
	lw $s3 ($s3)		# s3 = elemento dell'array
	
	move $a0 $s3
	li $a1 2
	jal minPotenza
	
	bge $v1 $s4 noChamp
	move $s4 $v1
	move $s5 $s2
noChamp:
	addi $s2 $s2 4
	j ciclo
fineCiclo:
	
	add $s5 $s5 $s0
	lw $a0 ($s5)
	li $v0 1
	syscall
	
	li $v0 10
	syscall
