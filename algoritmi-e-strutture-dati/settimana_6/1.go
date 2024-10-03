package main

import (
	"fmt"
	"math/rand"
)

type treeNode struct {
	item        int
	left, right *treeNode
}

type tree struct {
	root *treeNode
}

func newNode(val int) *treeNode {
	return &treeNode{val, nil, nil}
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

func buildTree(s []int, i int) *treeNode {
	node := newNode(s[i])
	if (2*i)+2 <= len(s) {
		node.left = buildTree(s, (2*i)+1)
	}
	if (2*i)+3 <= len(s) {
		node.right = buildTree(s, (2*i)+2)
	}
	return node
}

func preOrder(n *treeNode) {
	if n == nil {
		return
	}
	fmt.Print(n.item, " ")
	preOrder(n.left)
	preOrder(n.right)
}

func inOrder(n *treeNode) {
	if n == nil {
		return
	}
	inOrder(n.left)
	fmt.Print(n.item, " ")
	inOrder(n.right)
}

func postOrder(n *treeNode) {
	if n == nil {
		return
	}
	postOrder(n.left)
	postOrder(n.right)
	fmt.Print(n.item, " ")
}

func main() {
	sliceLength := 10
	s := make([]int, sliceLength)
	for i := 0; i < sliceLength; i++ {
		s[i] = rand.Intn(100)
	}

	fmt.Println(s)

	var t tree
	t.root = buildTree(s, 0)
	printTree(t, 1)

	preOrder(t.root)
	fmt.Println()
	inOrder(t.root)
	fmt.Println()
	postOrder(t.root)
	fmt.Println()

}
