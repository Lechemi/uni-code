.data

miaStringa: # .byte 67 73 65 79 0 	# "CIAO" in caratteri ASCII (ogni numero è un byte)
# 0 in fondo -> null terminated string
.asciiz "CIAO FINALMENTE NON CACCA"
# asciiz = ascii con aggiunta di 0 automaticamente in fondo

.text
.globl main
main:

li $v0 1 		# 1 = codice della syscall "scrivi un intero a schermo"
li $a0 4200		# 42 = primo parametro della syscall, ossia l'intero che scrivo
syscall

li $v0 4		# 4 = "stampa una stringa a video" (ovviamente usando ASCII)
la $a0 miaStringa
syscall


li $v0 10		# 1 = codice della syscall "termina le funzioni di SO"
syscall
