package main

import "fmt"

type node struct {
	nome            string
	item            int
	op              rune
	left, right, up *node
}

type albero struct {
	radice *node
}

func nuovoNodo(nome string, i int, op rune) *node {
	return &node{nome, i, op, nil, nil, nil}
}

func calcolaNumero(n *node) int {
	if n.op == ' ' {
		return n.item
	}

	switch n.op {
	case '+':
		return calcolaNumero(n.left) + calcolaNumero(n.right)
	case '*':
		return calcolaNumero(n.left) * calcolaNumero(n.right)
	case '-':
		return calcolaNumero(n.left) - calcolaNumero(n.right)
	case '/':
		return calcolaNumero(n.left) / calcolaNumero(n.right)
	}

	return 0
}

func stampaNumero(n *node) {
	if n.op == ' ' {
		fmt.Print(n.item)
		return
	}

	fmt.Print("(")
	stampaNumero(n.left)
	fmt.Print(" ", string(n.op), " ")
	stampaNumero(n.right)
	fmt.Print(")")
}

func main() {
	var a albero
	a.radice = nuovoNodo("sedia", 0, '+')
	a.radice.left = nuovoNodo("tavolo", 5, ' ')
	a.radice.right = nuovoNodo("divano", 0, '*')
	a.radice.right.left = nuovoNodo("frigo", 7, ' ')
	a.radice.right.right = nuovoNodo("letto", 2, ' ')

	fmt.Println(calcolaNumero(a.radice))
	stampaNumero(a.radice)
	fmt.Println()
}
