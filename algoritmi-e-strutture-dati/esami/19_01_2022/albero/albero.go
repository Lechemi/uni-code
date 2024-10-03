package main

import "fmt"

type treeNode struct {
	item, y     int
	left, right *treeNode
}

type tree struct {
	root *treeNode
}

func newNode(val int) *treeNode {
	return &treeNode{val, 0, nil, nil}
}

func printTree(node *treeNode, indentation int) {
	if node == nil {
		return
	}

	for i := 0; i < 2*indentation; i++ {
		fmt.Print(" ")
	}
	fmt.Println("*", node.y)
	printTree(node.left, indentation+1)
	printTree(node.right, indentation+1)
}

func f(node *treeNode, x int) {
	if node == nil {
		return
	}

	node.y = node.item + x
	f(node.left, node.y)
	f(node.right, node.y)

}

func main() {
	var t tree
	t.root = newNode(4)
	t.root.left = newNode(5)
	t.root.left.left = newNode(4)

	t.root.right = newNode(2)
	t.root.right.right = newNode(1)
	t.root.right.left = newNode(13)

	t.root.right.right.right = newNode(7)
	t.root.right.right.left = newNode(3)

	f(t.root, 0)

	printTree(t.root, 0)
}
