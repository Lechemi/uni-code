package main

import "fmt"

/* 
Idea:
(sassi nella piramide di altezza h) = (sassi nella piramide di altezza h-1) + h^2
*/

func sassi(height int) int {
	if height == 1 {
		return 1 
	}
	return sassi(height-1) + height*height
}

func main() {
	a := 3
	fmt.Println(sassi(a))
}

