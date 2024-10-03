package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

/*
Idea:
- per ogni elemento guardo se i suoi 4 attorno fanno parte di una basin
- un elemento fa parte di una basin solo se è adiacente e +1 rispetto ad almeno un elemento che fa parte di una basin

parto dal low point
per ogni punto adiacente guardo se esso è parte della basin, che ritorna true o false

if

if isBasin(input, up) || isBasin(input, down) || isBasin(input, left) || isBasin(input, right) {
	fmt.Println("Chiamata ricorsiva")
	return true
}else{
	return false
}

*/

func isPartOfBasin(input [][]int, index []int, isCenter bool) bool {
	// index = [row, col]
	var up, down, left, right []int

	if index[0] > 0 {
		// Se c'è spazio sopra
		up = []int{index[0] - 1, index[1]}
	}

	if index[0] < len(input)-1 {
		// Se c'è spazio sotto
		down = []int{index[0] + 1, index[1]}
	}

	if isCenter == true {
		return true
	} else {
		if isPartOfBasin(input, up, false) || isPartOfBasin(input, down, false) || isPartOfBasin(input, left, false) || isPartOfBasin(input, right, false) {
			fmt.Println("Trovata parte di basin")
			return true
		} else {
			return false
		}
	}
}

func main() {
	var inputSlice [][]int
	file, err := os.Open("input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		row := []int{}
		for i := 0; i < len(scanner.Text()); i++ {
			a, _ := strconv.Atoi(string(scanner.Text()[i]))
			row = append(row, a)
		}
		inputSlice = append(inputSlice, row)
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}

	testInput := "21999432103987894921985678989287678967899899965678"
	inputSlice = [][]int{}

	for j := 0; j < 5; j++ {
		row := []int{}
		for i := 0; i < 10; i++ {
			n, _ := strconv.Atoi(string(testInput[j*10+i]))
			row = append(row, n)
		}
		inputSlice = append(inputSlice, row)
	}

	/* var up, down, left, right, element int
	risk := 0

	for row := 0; row < len(inputSlice); row++ {
		for col := 0; col < len(inputSlice[row]); col++ {
			element = inputSlice[row][col]

			if row > 0 {
				up = inputSlice[row-1][col]
			} else {
				up = 10
			}

			if row < len(inputSlice)-1 {
				down = inputSlice[row+1][col]
			} else {
				down = 10
			}

			if col > 0 {
				left = inputSlice[row][col-1]
			} else {
				left = 10
			}

			if col < len(inputSlice[row])-1 {
				right = inputSlice[row][col+1]
			} else {
				right = 10
			}

			if element < up && element < down && element < left && element < right {
				risk += element + 1
			}

		}
	} */

}
