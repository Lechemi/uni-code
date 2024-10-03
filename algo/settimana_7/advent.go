package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

// se left e right sono nil, allora il nodo è una foglia
type treeNode struct {
	item            int
	left, right, up *treeNode
}

type tree struct {
	root *treeNode
}

func newNode(val int) *treeNode {
	return &treeNode{val, nil, nil, nil}
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

func stringToTree(s string) *treeNode {

	current := newNode(0)

	for i, c := range s {

		if i == 0 || i == len(s)-1 {
			continue
		}

		if c == '[' && s[i-1] == '[' {
			current.left = newNode(0)
			current.left.up = current
			current = current.left
		} else if c == '[' && s[i-1] == ',' {
			current.right = newNode(0)
			current.right.up = current
			current = current.right
		} else if c == ']' {
			current = current.up
		} else if c != ',' {
			if s[i-1] == '[' {
				num, _ := strconv.Atoi(string(c))
				current.left = newNode(num)
				current.left.up = current
			} else {
				num, _ := strconv.Atoi(string(c))
				current.right = newNode(num)
				current.right.up = current
			}
		}

	}

	return current
}

// potrei cambiare questo nome in "addToNearestLeaf"
func searchNearestLeaf(n *treeNode, toSum int, direction byte) {
	current := n

	var keepRight = true
	if direction == 'r' {
		keepRight = false
	}

	prev := current
	current = current.up

	for current != nil {
		if current.right == prev {
			// Arrivo da destra
			if keepRight {
				ok := rightFirst(current.left, toSum)
				if ok {
					break
				}
			}
		} else {
			// Arrivo da sinistra
			if !keepRight {
				ok := leftFirst(current.right, toSum)
				if ok {
					break
				}
			}
		}
		prev = current
		current = current.up
	}

}

func rightFirst(n *treeNode, toSum int) bool {
	if n == nil {
		return false
	}

	if n.left == nil && n.right == nil {
		// Sono in una foglia
		n.item += toSum
		return true
	}

	ok := rightFirst(n.right, toSum)
	if ok {
		return ok
	}
	return rightFirst(n.left, toSum)
}

func leftFirst(n *treeNode, toSum int) bool {
	if n == nil {
		return false
	}

	if n.left == nil && n.right == nil {
		// Sono in una foglia
		n.item += toSum
		return true
	}

	ok := leftFirst(n.left, toSum)
	if ok {
		return ok
	}
	return leftFirst(n.right, toSum)

}

func explode(n *treeNode) {

	leftNum := n.left.item
	rightNum := n.right.item

	n.left = nil
	n.right = nil
	n.item = 0

	searchNearestLeaf(n, leftNum, 'l')
	searchNearestLeaf(n, rightNum, 'r')
}

func isLeaf(n *treeNode) bool {
	return n.left == nil && n.right == nil
}

func findFirstReduction(n *treeNode, level int) bool {

	if n == nil {
		return false
	}

	if isLeaf(n) {
		if n.item >= 10 {
			split(n)
			return true
		}
	} else {
		if level == 4 && isLeaf(n.left) && isLeaf(n.right) {
			explode(n)
			return true
		}
	}

	return findFirstReduction(n.left, level+1) || findFirstReduction(n.right, level+1)
}

func split(n *treeNode) {

	/* level := 0
	scout := n

	for scout != nil {
		scout = scout.up
		level++
	}

	if level > 5 {
		return
	} */

	n.left = newNode(n.item / 2)
	n.left.up = n
	if n.item%2 == 0 {
		n.right = newNode(n.item / 2)
	} else {
		n.right = newNode(n.item/2 + 1)
	}
	n.right.up = n
	n.item = 0 // solo per comodità mia, si può anche togliere
}

func reduce(n *treeNode) {
	var t tree
	t.root = n
	reduced := true

	for reduced {
		reduced = findFirstReduction(n, 0)
	}
}

func explore(n *treeNode) {
	if n == nil {
		return
	}

	fmt.Println(n)
	explore(n.left)
	explore(n.right)

}

func sum(a, b *treeNode) *treeNode {
	s := newNode(0)
	s.left = a
	a.up = s
	s.right = b
	b.up = s
	return s
}

func main() {
	var t tree

	scanner := bufio.NewScanner(os.Stdin)

	firstLine := true

	for scanner.Scan() {
		line := scanner.Text()

		if firstLine {
			t.root = stringToTree(line)
			firstLine = false
		} else {
			t.root = sum(t.root, stringToTree(line))
			reduce(t.root)
			fmt.Println("FINAL RESULT:")
			printTree(t, 1)
		}
	}

}
