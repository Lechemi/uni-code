package main

import "fmt"

type treeNode struct {
	item        int
	left, right *treeNode
}

type tree struct {
	root *treeNode
}

func printTree(node *treeNode, indentation int) {

	for i := 0; i < 2*indentation; i++ {
		fmt.Print(" ")
	}

	if node == nil {
		fmt.Println("*")
		return
	}

	fmt.Println("*", node.item)
	if node.left != nil || node.right != nil {
		printTree(node.left, indentation+1)
		printTree(node.right, indentation+1)
	}

}

func newNode(item int) *treeNode {
	return &treeNode{item, nil, nil}
}

func rightNodeIndex(pre []int) int {
	root := pre[0]
	i := -1

	for x := range pre {
		if x != 0 && pre[x] >= root {
			i = x
			break
		}
	}

	return i
}

func fromPreToTree(pre []int) *treeNode {
	if len(pre) == 0 {
		return nil
	}

	root := newNode(pre[0])

	rightNodeIndex := rightNodeIndex(pre)

	if rightNodeIndex == -1 {
		// C'è solo il figlio sinistro
		root.left = fromPreToTree(pre[1:])
	} else if rightNodeIndex == 1 {
		// C'è solo il figlio destro
		root.right = fromPreToTree(pre[1:])
	} else {
		// Ci sono entrambi i figli
		root.left = fromPreToTree(pre[1:rightNodeIndex])
		root.right = fromPreToTree(pre[rightNodeIndex:])
	}

	return root
}

func preOrderVisit(node *treeNode) {
	if node == nil {
		return
	}

	fmt.Print(node.item, " ")
	preOrderVisit(node.left)
	preOrderVisit(node.right)
}

func inOrderVisit(node *treeNode) {
	if node == nil {
		return
	}

	inOrderVisit(node.left)
	fmt.Print(node.item, " ")
	inOrderVisit(node.right)
}

func postOrderVisit(node *treeNode) {
	if node == nil {
		return
	}

	postOrderVisit(node.left)
	postOrderVisit(node.right)
	fmt.Print(node.item, " ")
}

func colloca(val int, root *treeNode) *treeNode {
	curr := root

	for {
		if val < curr.item {
			if curr.left == nil {
				curr.left = newNode(val)
				break
			} else {
				curr = curr.left
			}
		} else {
			if curr.right == nil {
				curr.right = newNode(val)
				break
			} else {
				curr = curr.right
			}
		}

	}

	return root
}

func alberoDiRicerca(pre []int) *treeNode {
	root := newNode(pre[0])

	pre = pre[1:]

	for _, val := range pre {
		root = colloca(val, root)
	}

	return root
}

func main() {
	pre := []int{15, 18, 24, 33, 12, 16, 14, 13}

	var t tree

	t.root = alberoDiRicerca(pre)

	printTree(t.root, 0)

	inOrderVisit(t.root)
	fmt.Println()
	postOrderVisit(t.root)
	fmt.Println()
}
