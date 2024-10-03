.data

stringa: .asciiz "Inserisci un numero intero: "

.align 2	
# QUESTO SERVE PER ALLINEARE LO SPAZIO RISERVATO AD A PER EVITARE 
# "store address not aligned on word boundary"
A: .space 8
# Voglio un vettore di 2 elementi (2 * 4byte = 8 byte)

.text
.globl main
main:
	la $a0 stringa	
	li $v0 4	# 4 = stampa la stringa a video
	syscall 
	
	li $v0 5	# intero in input
	syscall
	move $t2 $v0
	
	addi $t0 $t2 1
	
	la $s1 A
	sw $t2 0($s1)
	sw $t0 4($s1)
	
	move $a0 $v0
	li $v0 1	# intero in output
	syscall
	move $a0 $t0
	li $v0 1
	syscall