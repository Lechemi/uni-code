.text 
.globl main
main:
	# vogliamo scrivere la condizione:
	# if x ≤ y < x^2 then z = 1
	
	# x : s1
	# y : s2
	# z : s3
	
	# Può sempre essere utile pensare, anziché ad una condizione, al suo contrario
	# Se il contrario è verificato, allora esco dall'if
	bgt $s1 $s2 fineIf	# al posto di x ≤ y, x > y
	# (Nota la lazy evaluation)
	mul $t0 $s1 $s1
	bgt $s2 $t0 fineIf	# y ≥ x^2
then:	# Etichetta non strettamente necessaria
	li $s3 1
fineIf: