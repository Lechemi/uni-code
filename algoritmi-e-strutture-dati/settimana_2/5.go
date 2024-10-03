// Torre di hanoi

package main

import (
	"fmt"
)

func detailedHanoi(from, temp, to string) (string, string, string) {
	n := len(from)
	if n == 0 {

	} else if n == 1 {
		// Sposto l'unico disco in to
		fmt.Println(to, ",", temp, ",", from)
	} else {
		// Stampo le mosse per spostare n-1 dischi da from a temp usando to
		from = from[:(n - 1)]
		detailedHanoi(from, to, temp)

		// Sposto il disco più grande da from a to
		detailedHanoi(from, temp, to)

		// Stampo le mosse per spostare n-1 dischi da temp a to usando from
		detailedHanoi(temp, from, to)
	}
	return "a", "b", "c"
}

func hanoi(n, from, temp, to, cont int) int {

	if n == 1 {
		//fmt.Println(from, "->", to) // Sposto l'unico disco in to
		cont++
	} else {
		// Stampo le mosse per spostare n-1 dischi da from a temp usando to
		cont = hanoi(n-1, from, to, temp, cont)

		// Sposto il disco più grande da from a to
		cont = hanoi(1, from, temp, to, cont)

		// Stampo le mosse per spostare n-1 dischi da temp a to usando from
		cont = hanoi(n-1, temp, from, to, cont)
	}
	return cont
}

func main() {
	/* n := float64(hanoi(30, 0, 1, 2, 0))
	fmt.Println(n)
	fmt.Println(math.Log(n)) */
	detailedHanoi("AB", "", "")
}
