// Implementazione di liste concatenate semplici

package main

import (
	"fmt"
)

type listNode struct {
	item int
	next *listNode
}

type linkedList struct {
	head *listNode
}

// Crea un nodo
func newNode(val int) *listNode {
	return &listNode{item: val, next: nil}
}

// Aggiunge un nodo in testa alla lista, che passo per riferimento
func addNewNode(l *linkedList, val int) {
	node := newNode(val)
	node.next = l.head // non uso l'asterisco perché la struct è già un puntatore
	l.head = node
}

// Stampa gli item della lista
func printList(l linkedList) {
	node := l.head
	for node != nil {
		fmt.Print(node.item, " ")
		node = node.next
	}
	fmt.Println()
}

// Stampa la lista
func printFullList(l linkedList) {
	c := 0
	node := l.head
	fmt.Println("testa:", l)
	for node != nil {
		fmt.Println("elemento numero", c, ":", *node)
		node = node.next
		c++
	}
	fmt.Println()
}

func searchList(l linkedList, val int) (bool, *listNode) {
	node := l.head
	for node != nil {
		if node.item == val {
			return true, node
		}
		node = node.next
	}
	return false, nil
}

// Cancella il primo nodo avente item = val
func deleteItem(l *linkedList, val int) bool {
	var curr, prev *listNode = l.head, nil
	for curr != nil {
		if curr.item == val {
			if prev == nil {
				// se il nodo da cancellare è la testa
				l.head = curr.next
			} else {
				prev.next = curr.next
			}
			return true
		}
		prev = curr
		curr = curr.next
	}
	return false
}

type a struct {
	c, d int
}

func main() {
	/* scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()
	input := scanner.Text()
	fmt.Println(input) */

	var list linkedList
	/* var bado a
	o := &bado
	fmt.Println(o) */

	addNewNode(&list, 13)
	addNewNode(&list, 3)
	addNewNode(&list, 6)
	addNewNode(&list, 234)
	addNewNode(&list, 76)

	fmt.Print("Item della lista: ")
	printList(list)
	fmt.Println()

	fmt.Println("La lista è una struct contenente solo l'indirizzo di memoria di head:", list)
	fmt.Println()

	fmt.Println("list.head è il puntatore al primo nodo:", list.head)
	fmt.Println("quel '&' che c'è prima delle parentesi indica che sto stampando il puntatore ad una struct (struct = il primo nodo).")
	fmt.Println()

	fmt.Println("dereferenziando list.head con '*' ottengo il primo nodo:", *(list.head))
	fmt.Println("ricorda che con '*' passo dal puntatore al dato effettivo")
	fmt.Println("con list.head.next ottengo il puntatore contenuto nel primo nodo:", list.head.next)
	fmt.Println()

	fmt.Println("lista così com'è: ")
	printFullList(list)
	fmt.Println()

}
