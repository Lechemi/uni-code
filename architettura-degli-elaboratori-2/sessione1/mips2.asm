.text
.globl main
main:
li $s0 45
li $s1 100
mult $s1 $s0		# Si omette il registro destinazione perché è dato da hi e lo
# Questo perché così posso gestire overflow, che con le moltiplicazioni è molto frequente
mflo $s2		# Move from low (self explanatory)
# pseudo istruzione: mul $s2 $s0 $s1 (fa le ultime due istruzioni insieme, lasciando eventualmente "sporco" hi)