package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

func readMatrix() [][]int {
	var m [][]int

	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		line := scanner.Text()
		stringRow := strings.Split(line, "")
		var row []int
		for _, c := range stringRow {
			n, _ := strconv.Atoi(string(c))
			row = append(row, n)
		}
		m = append(m, row)
	}

	return m
}

func printMatrix(m [][]int) {
	for _, row := range m {
		fmt.Println(row)
	}
}

type arco struct {
	from, to, cost int
}

type grafo struct {
	numeroVertici int
	archi         []*arco
}

func listaDiArchi(m [][]int) *grafo {
	g := new(grafo)

	arcoId := 1

	for y := range m {
		for x := range m[y] {
			// Per ogni elemento, aggiungo gli archi entranti
			cost := m[y][x]
			if x >= 1 {
				g.archi = append(g.archi, &arco{arcoId - 1, arcoId, cost})
			}

			if x < len(m[y])-1 {
				g.archi = append(g.archi, &arco{arcoId + 1, arcoId, cost})
			}

			if y >= 1 {
				g.archi = append(g.archi, &arco{arcoId - len(m[y]), arcoId, cost})
			}

			if y < len(m)-1 {
				g.archi = append(g.archi, &arco{arcoId + len(m[y]), arcoId, cost})
			}

			arcoId++
		}
	}

	g.numeroVertici = arcoId - 1

	return g
}

func bellmanFord(g *grafo, s int) []int {

	d := make([]int, g.numeroVertici)
	for i := range d {
		d[i] = math.MaxUint32
	}
	d[s-1] = 0

	for k := 1; k < g.numeroVertici-1; k++ {
		for _, arco := range g.archi {
			if d[arco.from-1]+arco.cost < d[arco.to-1] {
				d[arco.to-1] = d[arco.from-1] + arco.cost
			}
		}
	}

	return d
}

func dijkstra(m [][]int, s int) []int {

	d := make([]int, int(math.Pow(float64(len(m)), 2)))
	visitati := make([]bool, int(math.Pow(float64(len(m)), 2)))

	for i := range visitati {
		visitati[i] = false
	}

	d[s-1] = 0

	for k := 0; k < len(d); k++ {
		// prendo il vertice [1,...n] u, tra quelli non visitati, tale che d[u] è minima
		u := findMin(m, d, visitati)

		// vedo se gli archi uscenti da u possono servire a qualcosa
		_ = u

	}

	return d
}

func findMin(m [][]int, d []int, visitati []bool) int {
	var min int

	id := 1

	for i := range m {
		for range m[i] {
			if !visitati[id-1] && d[id-1] < d[min-1] {
				min = id
			}
			id++
		}
	}

	return min
}

type heap struct {
	els *[]int
}

func (h *heap) heapifyMinHeapDown(i int) {
	for {
		j := 2*i + 1
		if j >= len(*h.els) {
			// i è una foglia, già a posto
			break
		}

		j2 := j + 1
		if j2 < len(*h.els) && (*h.els)[j2] < (*h.els)[j] {
			j = j2
		}
		// ora j è il più piccolo dei figli di i

		if (*h.els)[j] >= (*h.els)[i] {
			// se il più piccolo dei figli è comunque più grande del padre, la condizione di minheap è rispettata e non c'è nulla da sistemare
			break
		}

		// se arrivo qua è perché la condizione di minheap non è rispettata e devo scambiare padre con figlio
		x := (*h.els)[i]
		(*h.els)[i] = (*h.els)[j]
		(*h.els)[j] = x

		i = j
	}
}

func (h *heap) heapifyMinHeapUp(j int) {
	for {
		if j == 0 {
			// sono alla radice
			break
		}

		i := (j - 1) / 2 // parent
		if (*h.els)[j] >= (*h.els)[i] {
			// se il figlio è più grande del genitore è tutto ok
			break
		}

		// scambio i con j
		x := (*h.els)[i]
		(*h.els)[i] = (*h.els)[j]
		(*h.els)[j] = x

		j = i
	}
}

func (h *heap) heapify() {
	for i := len(*h.els) - 1; i >= 0; i-- {
		h.heapifyMinHeapDown(i)
	}
}

func main() {
	m := readMatrix()

	for _, row := range dijkstra(m, 0) {
		fmt.Println(row)
	}

}
