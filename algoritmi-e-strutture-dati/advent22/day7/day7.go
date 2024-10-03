package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type treeNode struct {
	name     string
	size     int
	up       *treeNode
	children []*treeNode
}

type tree struct {
	root *treeNode
}

func newNode(name string, size int) *treeNode {
	return &treeNode{name, size, nil, []*treeNode{}}
}

func findNameAndSize(s string) (string, int) {
	whiteSpaceIndex := 0
	for i := range s {
		if s[i] == ' ' {
			whiteSpaceIndex = i
		}
	}

	if s[:4] == "dir" {
		return strings.TrimSpace(s[whiteSpaceIndex+1:]), 0
	}

	size, _ := strconv.Atoi(s[:whiteSpaceIndex])

	return strings.TrimSpace(s[whiteSpaceIndex+1:]), size
}

func printTree(t tree, indentation int) {
	node := t.root
	if node != nil {
		if node.size != 0 {
			fmt.Println("-", node.name, node.size)
		} else {
			fmt.Println("-", node.name, "(dir)")
		}

		if len(node.children) == 0 {
			return
		} else {
			for _, node := range node.children {
				t.root = node
				for i := 0; i < indentation; i++ {
					fmt.Print("  ")
				}
				printTree(t, indentation+1)

			}
		}
	} else {
		fmt.Println("* ")
	}

}

func dirSize(root *treeNode) int {
	totalSize := 0
	for _, child := range root.children {
		if child.size != 0 {
			totalSize += child.size
		} else {
			totalSize += dirSize(child)
		}
	}
	return totalSize
}

func calc(node *treeNode) int {
	tot := 0
	if node.size == 0 {
		directorySize := dirSize(node)
		if directorySize <= 100000 {
			tot += directorySize
		}
		for _, child := range node.children {
			tot += calc(child)
		}
	}
	return tot
}

//spaceToFree := 2586708 8381165

func findDir(node *treeNode) {
	if node.size == 0 {
		directorySize := dirSize(node)
		if directorySize >= 2586708 {
			fmt.Println(directorySize)
		}
		for _, child := range node.children {
			findDir(child)
		}
	}
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	var fileSystem tree

	fileSystem.root = newNode("/", 0)

	currentDir := fileSystem.root

	for scanner.Scan() {
		line := scanner.Text()

		if line[0] == '$' {
			// Comando
			if line[2] != 'l' {
				// Comando cd
				arg := strings.TrimSpace(line[4:])
				switch arg {
				case "/":
					currentDir = fileSystem.root
				case "..":
					currentDir = currentDir.up
				default:
					for i, child := range currentDir.children {
						if child.name == arg {
							currentDir = currentDir.children[i]
							break
						}
					}
				}
			}
		} else {
			// Righe dopo ls (cose che devo aggiungere)
			name, size := findNameAndSize(line)

			newNode := newNode(name, size)
			currentDir.children = append(currentDir.children, newNode)
			newNode.up = currentDir
		}
	}

	findDir(fileSystem.root)

}

/*

ls -> aggiungi le cose che ci sono fino al prossimo comando come figli della dir dove ti trovi
cd nome -> segui puntatore che ti porta alla dir (tra i figli) che si chiama nome
cd .. -> segui il puntatore up
cd / -> torna alla radice

se non ho un comando:
	se i primi tre caratteri sono "dir" -> ho una directory
	altrimenti ho un file

*/
