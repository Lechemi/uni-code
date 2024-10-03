// Ricorsione e iterazione

package main

import "fmt"

func f_rec(n int) uint64 {
	if n==1 || n==2 {
		return 1
	}
	return f_rec(n-1) + f_rec(n-2)
}

func main(){
	n := 5
	for i:=2; i<=n; i++ {
		fmt.Println("d")
	}

	fmt.Println()

	for n>=3 {
		n--
		fmt.Println("d")
	}
}

/* 

1. 
f_rec(6) + f_rec(5) = 
f_rec(5) + f_rec(4) + f_rec(4) + f_rec(3) =
f_rec(4) + f_rec(3) + f_rec(3) + f_rec(2) + f_rec(3) + f_rec(2) + f_rec(2) + f_rec(1) = 
f_rec(3) + f_rec(2) + f_rec(2) + f_rec(1) + f_rec(2) + f_rec(1) + 1 + f_rec(2) + f_rec(1) + 1 + 1 + 1 =
f_rec(2) + f_rec(1) + 11 = 13

2.
IDK

3.
Restituisce l'ennesimo numero della sequenza di Fibonacci

4.
No, f_iter2 fa un'iterazione in più 

5.
Non entrambe



*/