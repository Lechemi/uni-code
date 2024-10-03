package main

import "fmt"

/*

il grafo è definito come una slice di slice di interi
grafo[i] = {nodo1, nodo2, nodo3, nodo4, nodo5, nodo6}
i è il nodo (casella) che sto considerando
ciascuno dei nodi dela slice è il nodo che si raggiunge ottenendo il numero corrispondente col dado

array in cui, in ciascuna posizione di indice i, ho il predecessore del nodo i
lo uso al posto della mappa ausiliaria
appena trovo il nodo che sto cercando, risalgo al percorso grazie ai predecessori

poi dal grafo posso risalire alla sequenza di mosse che servono per fare quel percorso

*/

func creaGriglia(dimensione int, salti []int) [][]int {
	grafo := make([][]int, dimensione)

	for casella := range grafo {
		grafo[casella] = make([]int, 6)
		for j := range grafo[casella] {
			mossa := j + 1
			destinazione := casella + mossa

			if destinazione >= dimensione {
				destinazione = (dimensione-1)*2 - destinazione
			}

			if salti[destinazione] == 0 {
				grafo[casella][j] = destinazione
			} else {
				grafo[casella][j] = salti[destinazione] - 1
			}

		}
	}

	return grafo
}

func bfs(grafo [][]int, pred []int) {
	coda := []int{0}
	pred[0] = 0

	for len(coda) > 0 {
		v := coda[0]
		coda = coda[1:]

		if v == len(grafo)-1 {
			return
			// Posso anche fermarmi perché ho raggiunto il nodo che cerco
		}

		for _, v2 := range grafo[v] {
			if pred[v2] == -1 {
				coda = append(coda, v2)
				pred[v2] = v
			}
		}

	}

}

func trovaCamminoMinimo(grafo [][]int) []int {
	var steps []int

	pred := make([]int, len(grafo))
	for i := range pred {
		pred[i] = -1
	}

	bfs(grafo, pred)

	x := len(grafo) - 1
	steps = append(steps, x)
	for x != 0 {
		steps = append(steps, pred[x])
		x = pred[x]
	}

	reverse := make([]int, len(steps))

	for i, x := range steps {
		reverse[len(reverse)-1-i] = x
	}

	return reverse
}

func indexOf(element int, s []int) int {
	for i := range s {
		if s[i] == element {
			return i
		}
	}
	return -1
}

func mosse(grafo [][]int, camminoMinimo []int) []int {
	var mosse []int

	curr := 0
	for _, mossa := range camminoMinimo[1:] {
		mosse = append(mosse, indexOf(mossa, grafo[curr])+1)
		curr = grafo[curr][indexOf(mossa, grafo[curr])]
	}

	return mosse
}

func main() {

	size := 16
	salti := []int{0, 7, 0, 0, 3, 0, 0, 10, 0, 0, 0, 0, 0, 0, 6, 0}
	board := creaGriglia(size, salti)

	/* for i, c := range board {
		fmt.Println("casella", i, c)
	} */

	fmt.Println(mosse(board, trovaCamminoMinimo(board)))

}
