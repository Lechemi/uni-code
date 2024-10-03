.data

A: .space 400	# voglio un vettore di 100 elementi
# ogni elemento occupa 4 byte, quindi devo occupare 4x100 = 400 byte

B: .space 12   
10   
.space 384
# Inizializzo B[i] a 10, sapendo già che i = 3
# Lascio 3 parole (12 byte), poi metto B[3] = 10, poi lascio
# le restanti parole (96 x 4 = 384)

C: 10
i: 3

.text
.globl main
main:

la $t0 C
lw $t0 ($t0)		# t0 = C

la $t1 i
lw $t1 ($t1)		# t1 = i

# A[99] = 5 + B[i] + C

# Prendo B[i]:
la $t3 B		# t3 = indirizzo (di inizio) di B
sll $t2 $t1 2		# t2 = t1 * 4 (shifto t1 a sinistra di 2 posizioni)
add $t3 $t3 $t2		# t3 = indirizzo di B[i]
lw $t3 ($t3)		# t3 = B[i]


addi $t4 $t3 5		# t4 = B[i] + 5
add $t4 $t4 $t0		# t4 = t4 + C = B[i] + 5 + C

la $t5 A		# t5 = indirizzo (di inizio) di A

sw $t4 396($t5)		# 99 x 4 = 396, scrivo t4 in A[99]
