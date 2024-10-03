package main

import "fmt"

func multiply(x, y int) int { 
	if x == 0 {
		return 0 
	} else {
		return x + multiply(y-1, x) 
	}
}

func main() {
	a := 12
	b := 1234
	fmt.Println(multiply(a, b))
}