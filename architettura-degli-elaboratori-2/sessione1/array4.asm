.data
A:
	# Segmento dei dati statici
	.word 0xAA 0xBB 0xCC 0xDD
.text
.globl main
main:
	la $s2 A		# load address: metto in $s2 l'indirizzo di 0xAA
	lw $s0 ($s2)		# load word easy (senza costante)