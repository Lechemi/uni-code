.data
MAX: 	29
.globl main
.text
main:
	la $s0 MAX
	lw $s0 ($s0)
	
	move $a0 $s0
	jal generaSequenza
	move $s1 $v0
	
	li $s2 2
ciclo:
	bge $s2 $s0 fineCiclo
	
	move $a0 $s1
	move $a1 $s0
	move $a2 $s2
	jal rimuoviMultipli
	
	addi $s2 $s2 1
	j ciclo
fineCiclo:
	
	
	move $a0 $s1
	move $a1 $s0
	jal stampaSequenza
	
	li $v0 10
	syscall