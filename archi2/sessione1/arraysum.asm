.data

A: .word 12 23 34 45 56 67 78 89 98 -2 0xA 11 -8 
0 0xCC

# posso anche non specificare .word, di default è già così

h : .word 0xAABBCC

# same thing

.text
.globl main
main:
la $t1 h	# copio in $t1 l'indirizzo di h
lw $t2 ($t1)

la $t3 A	# copio l'indirizzo di inizio di A
lw $t4 32($t3)	# prendo l'ottavo elemento di A (8x4 = 32, devo spostarmi di 32 byte)

add $t5 $t2 $t4	# t5 = h + A[8]

sw $t5 48($t3)	# memorizzo t5 in A[12] 