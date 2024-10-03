package main

import "fmt"

type listNode struct {
	item int
	next *listNode
}

type linkedListWithTail struct {
	head, tail *listNode
}

func newNode(val int) *listNode {
	return &listNode{val, nil}
}

func addNodeAtStart(l *linkedListWithTail, val int) {
	node := newNode(val)
	if l.head == nil {
		// Caso in cui node è il primo (e unico) nodo della lista
		l.head = node
		l.tail = l.head
	} else {
		node.next = l.head
		l.head = node
	}

}

func addNodeAtEnd(l *linkedListWithTail, val int) {
	node := newNode(val)
	if l.tail == nil {
		// Caso in cui node è il primo (e unico) nodo della lista
		l.tail = node
		l.head = l.tail
	} else {
		// Caso in cui ci sono già altri nodi
		l.tail.next = node
		l.tail = l.tail.next
	}
}

func printList(l linkedListWithTail) {
	node := l.head
	for node != nil {
		fmt.Println("node:", node.item)
		node = node.next
	}
}

func main() {
	var list linkedListWithTail

	addNodeAtEnd(&list, 0)
	addNodeAtStart(&list, 234)
	addNodeAtStart(&list, 1)
	addNodeAtStart(&list, 67)
	addNodeAtStart(&list, 89)
	addNodeAtStart(&list, 90)
	addNodeAtEnd(&list, 12)

	printList(list)
}
