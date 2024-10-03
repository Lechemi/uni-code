package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
)

type Stack []string

func (s *Stack) push(symbol string) {
	*s = append(*s, symbol)
}

func (s *Stack) pop() string {
	popped := (*s)[len(*s)-1]

	*s = (*s)[:len(*s)-1]

	return popped
}

func (s *Stack) isEmpty() bool {
	return len(*s) == 0
}

func (s *Stack) peek() string {
	return (*s)[len(*s)-1]
}

// Se ritorna false, allora la riga è corrotta, altrimenti è incompleta
func lineScore(line string) (int, bool) {
	lineStack := new(Stack)

	for _, symbol := range line {
		if symbol == '(' || symbol == '[' || symbol == '{' || symbol == '<' {
			lineStack.push(string(symbol))
		} else {
			if !lineStack.isEmpty() {
				switch symbol {
				case ')':
					if lineStack.peek() == "(" {
						lineStack.pop()
					} else {
						return 3, false
					}
				case ']':
					if lineStack.peek() == "[" {
						lineStack.pop()
					} else {
						return 57, false
					}
				case '}':
					if lineStack.peek() == "{" {
						lineStack.pop()
					} else {
						return 1197, false
					}
				case '>':
					if lineStack.peek() == "<" {
						lineStack.pop()
					} else {
						return 25137, false
					}
				}
			}

		}
	}

	score := 0

	for !lineStack.isEmpty() {
		switch lineStack.peek() {
		case "(":
			lineStack.pop()
			score *= 5
			score += 1
		case "[":
			lineStack.pop()
			score *= 5
			score += 2
		case "{":
			lineStack.pop()
			score *= 5
			score += 3
		case "<":
			lineStack.pop()
			score *= 5
			score += 4
		}
	}

	return score, true
}

func heapSort(list []int) []int {
	sort.Ints(list)
	return list
}

func main() {

	navigationMap := make([]string, 0)

	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		navigationMap = append(navigationMap, scanner.Text())
	}

	scoreList := make([]int, 0)

	for _, line := range navigationMap {
		score, incomplete := lineScore(line)
		if incomplete {
			scoreList = append(scoreList, score)
		}
	}

	scoreList = heapSort(scoreList)

	fmt.Println(scoreList[25])
}
