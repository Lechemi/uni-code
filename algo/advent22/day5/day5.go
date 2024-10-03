package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type Stack []rune

func (s *Stack) push(symbol rune) {
	*s = append(*s, symbol)
}

func (s *Stack) pop() rune {
	popped := (*s)[len(*s)-1]

	*s = (*s)[:len(*s)-1]

	return popped
}

func (s *Stack) isEmpty() bool {
	return len(*s) == 0
}

func (s *Stack) peek() rune {
	return (*s)[len(*s)-1]
}

func main() {
	stackList := make([]*Stack, 9)

	// Inizializzazione stack
	{
		// Primo stack
		stackList[0] = new(Stack)
		stackList[0].push('D')
		stackList[0].push('B')
		stackList[0].push('J')
		stackList[0].push('V')

		// Secondo stack
		stackList[1] = new(Stack)
		stackList[1].push('P')
		stackList[1].push('V')
		stackList[1].push('B')
		stackList[1].push('W')
		stackList[1].push('R')
		stackList[1].push('D')
		stackList[1].push('F')

		// Terzo stack
		stackList[2] = new(Stack)
		stackList[2].push('R')
		stackList[2].push('G')
		stackList[2].push('F')
		stackList[2].push('L')
		stackList[2].push('D')
		stackList[2].push('C')
		stackList[2].push('W')
		stackList[2].push('Q')

		// Quarto stack
		stackList[3] = new(Stack)
		stackList[3].push('W')
		stackList[3].push('J')
		stackList[3].push('P')
		stackList[3].push('M')
		stackList[3].push('L')
		stackList[3].push('N')
		stackList[3].push('D')
		stackList[3].push('B')

		// Quinto stack
		stackList[4] = new(Stack)
		stackList[4].push('H')
		stackList[4].push('N')
		stackList[4].push('B')
		stackList[4].push('P')
		stackList[4].push('C')
		stackList[4].push('S')
		stackList[4].push('Q')

		// Sesto stack
		stackList[5] = new(Stack)
		stackList[5].push('R')
		stackList[5].push('D')
		stackList[5].push('B')
		stackList[5].push('S')
		stackList[5].push('N')
		stackList[5].push('G')

		// Settimo stack
		stackList[6] = new(Stack)
		stackList[6].push('Z')
		stackList[6].push('B')
		stackList[6].push('P')
		stackList[6].push('M')
		stackList[6].push('Q')
		stackList[6].push('F')
		stackList[6].push('S')
		stackList[6].push('H')

		// Ottavo stack
		stackList[7] = new(Stack)
		stackList[7].push('W')
		stackList[7].push('L')
		stackList[7].push('F')

		// Nono stack
		stackList[8] = new(Stack)
		stackList[8].push('S')
		stackList[8].push('V')
		stackList[8].push('F')
		stackList[8].push('M')
		stackList[8].push('R')
	}

	/* {
		stackList[0] = new(Stack)
		stackList[0].push('Z')
		stackList[0].push('N')

		stackList[1] = new(Stack)
		stackList[1].push('M')
		stackList[1].push('C')
		stackList[1].push('D')

		stackList[2] = new(Stack)
		stackList[2].push('P')
	} */

	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		line := scanner.Text()

		fPosition := 0
		for i := range line {
			if line[i] == 'f' {
				fPosition = i
			}
		}

		quantity, _ := strconv.Atoi(strings.TrimSpace(line[5:fPosition]))
		sourceIndex, _ := strconv.Atoi(string(line[fPosition+5]))
		sourceIndex--
		destinationIndex, _ := strconv.Atoi(string(line[fPosition+10]))
		destinationIndex--

		itemsToMove := []rune{}

		for i := 0; i < quantity; i++ {
			if !stackList[sourceIndex].isEmpty() {
				item := stackList[sourceIndex].pop()
				itemsToMove = append(itemsToMove, item)
			} else {
				fmt.Println("Stack vuoto ")
			}
		}

		for i := len(itemsToMove) - 1; i >= 0; i-- {
			stackList[destinationIndex].push(itemsToMove[i])
		}

	}

	for _, s := range stackList {
		fmt.Print(string(s.peek()))
	}
	fmt.Println()

}

/*
Stack iniziali:

        [Q] [B]         [H]
    [F] [W] [D] [Q]     [S]
    [D] [C] [N] [S] [G] [F]
    [R] [D] [L] [C] [N] [Q]     [R]
[V] [W] [L] [M] [P] [S] [M]     [M]
[J] [B] [F] [P] [B] [B] [P] [F] [F]
[B] [V] [G] [J] [N] [D] [B] [L] [V]
[D] [P] [R] [W] [H] [R] [Z] [W] [S]
 1   2   3   4   5   6   7   8   9
*/
