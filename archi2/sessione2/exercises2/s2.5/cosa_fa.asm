	.data
v:	.byte 2,0,0,0,4,0,0,0
array:	.byte 2,0,0,0,3,0,0,0,5,0,0,0,7,0,0,0,11,0,0,0,13,0,0,0,17,0,0,0,19,0,0,0

	.text
	.globl main
main:
	la $s1 array
	la $s2 v

	lw $t0 0($s2)		# in t0 metto il primo elemento di v (2)
	addi $t0 $t0 -1		# Gli sottraggo 1
	mul $t0 $t0 4		# Lo moltiplico per 4
	add $t1 $s1 $t0		# metto in t1 la somma tra t0 (4) e s1 (indirizzo base di array)
	lw $t2 0($t1)		# metto in t2 la word all'indirizzo t1 di array (la seconda word: 3) 
	addi $t2 $t2 1		# aggiungo 1 a t2 (ora fa 4)

	lw $t0 4($s2)
	addi $t0 $t0 -1
	mul $t0 $t0 4
	add $t3 $s1 $t0
	lw $t4 0($t3)
	addi $t4 $t4 -1

	sw $t2 0($t3)
	sw $t4 0($t1)

	li $v0 10
	syscall
