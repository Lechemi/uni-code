package main

import "fmt"

type oggetto struct {
	valore, peso int
}

func zaini(oggetti []*oggetto, p int) [][]*oggetto {
	s := make([][]*oggetto, p)

	return s
}

func main() {
	pesoZaino := 10
	oggetti := []*oggetto{&oggetto{3, 3}, &oggetto{2, 2}, &oggetto{5, 5}, &oggetto{3, 2}, &oggetto{2, 5}}
	zaini := zaini(oggetti, pesoZaino)

	for i, zaino := range zaini {
		fmt.Println("Zaino di peso", i, ":")
		for _, item := range zaino {
			fmt.Print(item.peso, item.valore, ", ")
		}
	}
}
