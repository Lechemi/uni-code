package main

import "fmt"

type treeNode struct {
	name     string
	children []*treeNode
	parent   *treeNode
}

type tree struct {
	root *treeNode
}

func newNode(name string) *treeNode {
	return &treeNode{name, nil, nil}
}

func printTree(node treeNode, indentation int) {
	for i := 0; i < 2*indentation; i++ {
		fmt.Print(" ")
	}
	fmt.Println("*", node.name)
	for _, child := range node.children {
		printTree(*child, indentation+1)
	}
}

func findNode(name string, root *treeNode) *treeNode {
	stack := make([]*treeNode, 0)
	stack = append(stack, root)

	for len(stack) > 0 {
		node := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		if node.name == name {
			return node
		}
		for _, child := range node.children {
			stack = append(stack, child)
		}
	}

	return nil
}

func subordinati(name string, root *treeNode) []*treeNode {
	dipendente := findNode(name, root)

	if dipendente == nil {
		return nil
	}

	return dipendente.children
}

func supervisore(name string, root *treeNode) *treeNode {
	dipendente := findNode(name, root)

	if dipendente == nil {
		return nil
	}

	return dipendente.parent
}

func super(name string, root *treeNode) []*treeNode {
	dipendente := findNode(name, root)
	var super []*treeNode

	if dipendente == nil {
		return nil
	}

	for dipendente.parent != nil {
		dipendente = dipendente.parent
		super = append(super, dipendente)
	}

	return super
}

func visit(n *treeNode) {
	if n == nil {
		return
	}

	fmt.Print("Dipendente:", n.name)
	if n.parent != nil {
		fmt.Println(" Supervisore:", n.parent.name)
	}
	fmt.Println()

	for _, child := range n.children {
		visit(child)
	}
}

func countLeaves(n *treeNode) int {
	if n == nil {
		return 0
	}

	if n.children == nil {
		return 1
	} else {
		var s int
		for _, child := range n.children {
			s += countLeaves(child)
		}
		return s
	}
}

type Queue []*treeNode

func (q *Queue) enqueue(n *treeNode) {
	*q = append(*q, n)
}

func (q *Queue) dequeue() *treeNode {
	n := (*q)[0]
	(*q) = (*q)[1:]
	return n
}

func (q *Queue) isEmpty() bool {
	return len(*q) == 0
}

func stampaPerLivello(root *treeNode) {
	var q Queue
	q.enqueue(root)

	for !q.isEmpty() {
		n := q.dequeue()
		fmt.Println(n.name)
		for _, child := range n.children {
			q.enqueue(child)
		}
	}
}

func main() {
	azienda := make([]*tree, 0)
	var t tree
	t.root = newNode("Anna")
	t.root.children = append(t.root.children, newNode("Bruno"))
	t.root.children = append(t.root.children, newNode("Carlo"))
	t.root.children = append(t.root.children, newNode("Daniela"))
	t.root.children[0].children = append(t.root.children[0].children, newNode("Enrico"))
	t.root.children[0].children = append(t.root.children[0].children, newNode("Francesco"))
	t.root.children[0].children[1].children = append(t.root.children[0].children[1].children, newNode("Irene"))
	azienda = append(azienda, &t)
	var t2 tree
	t2.root = newNode("Gianni")
	t2.root.children = append(t2.root.children, newNode("Harry"))
	azienda = append(azienda, &t2)

	for _, tree := range azienda {
		stampaPerLivello(tree.root)
		fmt.Println()
	}

}
