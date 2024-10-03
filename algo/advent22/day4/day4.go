package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func stringToSections(s string) (int, int) {
	dashIndex := 0
	for i, c := range s {
		if c == '-' {
			dashIndex = i
		}
	}
	a, _ := strconv.Atoi(s[:dashIndex])
	b, _ := strconv.Atoi(s[dashIndex+1:])

	return a, b
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	overlappingPairs := 1000

	for scanner.Scan() {
		line := scanner.Text()

		commaIndex := 0
		for i, c := range line {
			if c == ',' {
				commaIndex = i
			}
		}

		start, finish := stringToSections(line[:commaIndex])
		firstElfSections := []int{start, finish}

		start, finish = stringToSections(line[commaIndex+1:])
		secondElfSections := []int{start, finish}

		if (firstElfSections[0] < secondElfSections[0] && firstElfSections[1] < secondElfSections[0]) || (firstElfSections[0] > secondElfSections[1] && firstElfSections[1] > secondElfSections[1]) {
			overlappingPairs--
		}

		// Prima parte
		/* if (firstElfSections[0] <= secondElfSections[0] && firstElfSections[1] >= secondElfSections[1]) || (secondElfSections[0] <= firstElfSections[0] && secondElfSections[1] >= firstElfSections[1]) {
			overlappingPairs++
		} */
	}

	fmt.Println(overlappingPairs)

}
