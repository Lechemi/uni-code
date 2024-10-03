.text 
.globl main

main:

#addi $s1 $s0 5		# $s1 = 0 + 5; carico 5 nel registro $s1

li $s1 5		# Questa è una pseudo-istruzione (load immediate) che rappresenta addi $s1 $s0 5 (un pelo più ad alto livello)
li $s2 7
add $s0 $s1 $s2