package main

import (
	"fmt"
	"math/rand"
)

type vertice struct {
	nome int
	// scelgo di chiamare il vertice con un intero per comodità (potrei usare una stringa ma sbatta)
	// qua volendo posso aggiungere altri campi che descrivono il vertice
}

type grafo struct {
	vertici   map[int]*vertice
	adiacenti map[vertice][]*vertice
}

type treeNode struct {
	item        int
	left, right *treeNode
}

type tree struct {
	root *treeNode
}

func newNode(n int) *treeNode {
	return &treeNode{n, nil, nil}
}

func nuovoGrafo(numeroNodi int) *grafo {
	v := make(map[int]*vertice, numeroNodi)
	a := make(map[vertice][]*vertice, numeroNodi)

	for k := 0; k < numeroNodi; k++ {
		v[k] = &vertice{k}
	}

	return &grafo{v, a}
}

func contiene(listaAdiacenza []*vertice, ver *vertice) bool {
	for _, v := range listaAdiacenza {
		if v == ver {
			return true
		}
	}
	return false
}

func contieneArco(g *grafo, a, b *vertice) bool {
	return contiene(g.adiacenti[*a], b)
}

func aggiungiArco(g *grafo, a, b *vertice) {
	g.adiacenti[*a] = append(g.adiacenti[*a], b)
	g.adiacenti[*b] = append(g.adiacenti[*b], a)
}

func stampaGrafo(g *grafo) {

	for nomeVertice := range g.vertici {
		fmt.Print(nomeVertice)
		for _, vertice := range g.adiacenti[*g.vertici[nomeVertice]] {
			fmt.Print(" -> ", vertice.nome)
		}
		fmt.Println()
	}
}

func creaGrafoCasuale(numeroNodi int, p float64) *grafo {
	g := nuovoGrafo(numeroNodi)

	for i := range g.vertici {
		for j := range g.vertici {
			if j != i {
				// Controllo se l'arco (i,j) è già presente
				if !contieneArco(g, g.vertici[i], g.vertici[j]) {
					// Se non è presente lo aggiungo con probabilità p
					rand := rand.Float64()
					if rand < p {
						aggiungiArco(g, g.vertici[i], g.vertici[j])
					}
				}
			}
		}
	}

	return g
}

func grado(g *grafo, v vertice) int {
	return len(g.adiacenti[v])
}

func dfs(g *grafo, v vertice, aux map[vertice]bool) {

	aux[v] = true

	for _, v2 := range g.adiacenti[v] {
		if !aux[*v2] {
			dfs(g, *v2, aux)
		}
	}

}

func dfsColor(g *grafo, v vertice, aux map[vertice]int, prev int) {
	// 0 -> non colorato
	// 1 -> bianco
	// 2 -> nero

	if prev == 1 {
		aux[v] = 2
	} else {
		aux[v] = 1
	}

	for _, v2 := range g.adiacenti[v] {
		if aux[*v2] == 0 {
			dfsColor(g, *v2, aux, aux[v])
		}
	}

}

func buildTree(g *grafo, aux map[vertice]bool, root *treeNode) {
	coda := []*treeNode{root}
	aux[*g.vertici[root.item]] = true

	for len(coda) > 0 {
		v := coda[0]
		coda = coda[1:]

		for _, v2 := range g.adiacenti[*g.vertici[v.item]] {
			if !aux[*v2] {
				node := newNode(v2.nome)
				if v.left == nil {
					v.left = node
					coda = append(coda, node)
					aux[*v2] = true
				} else if v.right == nil {
					v.right = node
					coda = append(coda, node)
					aux[*v2] = true
				}
			}

		}

	}

}

func cammino(g *grafo, v, w vertice) bool {
	m := make(map[vertice]bool, len(g.vertici))
	for _, v := range g.vertici {
		m[*v] = false
	}
	dfs(g, v, m)
	return m[w] == true
}

func ccc(g *grafo) int {

	numeroComponentiConnesse := 0

	explored := make(map[vertice]bool, len(g.vertici))

	for _, v := range g.vertici {
		explored[*v] = false
	}

	for _, v := range g.vertici {
		if !explored[*v] {
			numeroComponentiConnesse++
			dfs(g, *v, explored)
		}
	}

	return numeroComponentiConnesse
}

func span(g *grafo, root vertice) tree {

	var t tree
	t.root = newNode(root.nome)

	m := make(map[vertice]bool, len(g.vertici))
	for _, v := range g.vertici {
		m[*v] = false
	}

	buildTree(g, m, t.root)

	return t
}

func printTree(t tree, indentation int) {
	node := t.root
	if node != nil {
		fmt.Println("*", node.item)
		if node.left == nil && node.right == nil {
			return
		} else {
			t.root = node.left
			for i := 0; i < indentation; i++ {
				fmt.Print("  ")
			}
			printTree(t, indentation+1)
			t.root = node.right
			for i := 0; i < indentation; i++ {
				fmt.Print("  ")
			}
			printTree(t, indentation+1)
		}
	} else {
		fmt.Println("* ")
	}

}

func twoColor(g *grafo, v vertice) bool {
	color := make(map[vertice]int, len(g.vertici))
	for _, v := range g.vertici {
		color[*v] = 0
	}

	dfsColor(g, v, color, 2)

	fmt.Println()
	fmt.Println(color)

	for _, v := range g.vertici {
		for _, adiacente := range g.adiacenti[*v] {
			if color[*adiacente] == color[*v] {
				return false
			}
		}
	}

	return true

}

func main() {

	numeroNodi := 6
	prob := 0.2

	mioGrafo := creaGrafoCasuale(numeroNodi, prob)
	stampaGrafo(mioGrafo)

	fmt.Println(twoColor(mioGrafo, *mioGrafo.vertici[0]))

}
