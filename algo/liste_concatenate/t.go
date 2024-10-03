package main

type listNode struct {
	item int
	next *listNode
}

type linkedList struct {
	head *listNode
}

func (l *linkedList) removeNode(x int) {
	curr := l.head
	var prev *listNode

	for curr != nil {
		if curr.item == x {
			break
		}
		prev = curr
		curr = curr.next
	}

	prev.next = curr.next
	curr.next = nil

}

func main() {

}
