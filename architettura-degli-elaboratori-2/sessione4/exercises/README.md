# [Sessione 4] Procedure

## [s4.1] Elaboratore

*nome del file sorgente: elaboratore.asm*

Si scriva una procedura assembly, chiamata Elaboratore, che esegue la somma, differenza, moltiplicazione e divisione tra due numeri interi.

Input: i due operandi e un terzo parametro per la selezione dell’operazione.

Output: risultato (nel caso della divisione restituisce anche il resto).

Si scriva poi il main dove:

* vengono chiesti all’utente operandi e operatore;
* il risultato dell’operazione è mostrato a terminale.

## [s4.2] Max e min

*nome del file sorgente: maxmin.asm*

Si implementi la procedura *max* così definita:

- Input: un intero `N` e un array `A` di `N` interi
- Output: il valore massimo in `A`

Si implementi la procedura *min* così definita:

- Input: un intero `N` e un array `A` di `N` interi
- Output: il valore minimo in `A`

Si implementi infine il *main* che acquisisca i dati, chiami *max* e *min* e stampi i risultati restituiti.

## [s4.3] Branch3Equal

*nome del file sorgente: branch3equal.asm*

Si implementi la procedura branch3equal così definita:

- Input: tre valori interi, due indirizzi del segmento testo
- Output: nessuno

Se i tre valori interi sono uguali viene fatto un salto al primo indirizzo del segmento
testo. Se i tre valori sono tutti diversi fra loro viene fatto un salto al secondo indirizzo del segmento testo. In ogni altro caso, non viene effettuato nessun salto e l’esecuzione procede da PC + 4.

## [s4.4] Somma selettiva

*nome del file sorgente: sommaSelettiva.asm*

Si scriva un programma che:

* chieda all’utente di inserire un array di interi di dimensione arbitraria (si scelga una dimensione massima da non eccedere). 
* invochi una procedura P 
* stampi il valore ritornato da P

La procedura P è definita come segue:

* Input: l’array inserito dall’utente e un parametro k
* se k=0 la procedura calcola la somma di tutti gli interi in posizione (indice nell’array) dispari
* se k=1 sommerà quelli in posizioni pari.

Suggerimento: allocare l’array staticamente in memoria e passare alla procedura il base address (passaggio per indirizzo).



