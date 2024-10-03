package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

type listNode struct {
	item int
	next *listNode
}

type linkedList struct {
	head *listNode
}

type graph struct {
	n         int
	adiacenti []*linkedList
}

func newGraph(n int) *graph {
	a := make([]*linkedList, n)
	return &graph{n, a}
}

func newNode(v int) *listNode {
	return &listNode{v, nil}
}

func addNodeToHead(node *listNode, list *linkedList) {
	if list.head == nil {
		list.head = node
		return
	}
	node.next = list.head
	list.head = node
}

func printLinkedList(list linkedList) {
	node := list.head
	for node != nil {
		fmt.Print(" -> ", node.item)
		node = node.next
	}
}

func contains(list linkedList, val int) bool {
	node := list.head
	for node != nil {
		if node.item == val {
			return true
		}
		node = node.next
	}
	return false
}

func containsArc(g *graph, a int, b int) bool {
	return contains(*g.adiacenti[a], b)
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	var myGraph *graph

	for scanner.Scan() {
		line := scanner.Text()

		if len(line) == 1 {
			n, _ := strconv.Atoi(line)
			myGraph = newGraph(n)

			for i := range myGraph.adiacenti {
				newList := new(linkedList)
				myGraph.adiacenti[i] = newList
			}

		} else {
			firstVertex, _ := strconv.Atoi(string(line[0]))
			secondVertex, _ := strconv.Atoi(string(line[2]))

			// Aggiorno, se necessario, sia la lista del primo vertice che del secondo

			if !contains(*myGraph.adiacenti[firstVertex], secondVertex) {
				addNodeToHead(newNode(secondVertex), myGraph.adiacenti[firstVertex])
			}

			if !contains(*myGraph.adiacenti[secondVertex], firstVertex) {
				addNodeToHead(newNode(firstVertex), myGraph.adiacenti[secondVertex])
			}
		}
	}

	for i := range myGraph.adiacenti {
		fmt.Print(i)
		printLinkedList(*myGraph.adiacenti[i])
		fmt.Println()
	}

	fmt.Println(containsArc(myGraph, 1, 5))
	fmt.Println(containsArc(myGraph, 5, 1))
	fmt.Println(containsArc(myGraph, 0, 3))

}

/*
domanda: ma perché siamo così fissati coi puntatori? perché bisogna usare, al posto di una struct,
il puntatore ad una struct (che già di per sè è un puntatore)?
in questo caso, al posto di usare una variabile graph (struct), usamo una variabile di tipo *graph (puntatore a struct)
*/
