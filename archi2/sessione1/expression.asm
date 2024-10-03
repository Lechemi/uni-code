.text
.globl main
main:
li $s1, 1
li $s2, 2
li $s3, 3
li $s4, 4
# A = B + C - (D+E)
add $t0, $s4, $s3 
add $t1, $s2, $s1
sub $s0, $t1, $t0