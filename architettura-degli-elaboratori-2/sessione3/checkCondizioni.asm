.data

in_a:	.asciiz "Inserisci a: "
in_b:	.asciiz "Inserisci b: "
in_c:	.asciiz "Inserisci c: "
errore:	.asciiz "Errore"
z:	.asciiz "z = "

.text
.globl main
main:
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
	
	# If ( (a>=b) && (c!=0) )	
	blt $s0 $s1 else	# se a < b
	beqz $s2 else
	add $t0 $s0 $s1
	mul $s3 $s2 $t0
	la $a0 z
	li $v0 4
	syscall
	move $a0 $s3
	li $v0 1
	syscall
	j fineIf		
else:
	la $a0 errore
	li $v0 4
	syscall
fineIf:
	
	
	
	