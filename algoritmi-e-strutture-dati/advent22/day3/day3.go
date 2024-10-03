package main

import (
	"bufio"
	"fmt"
	"os"
)

func contains(s string, c rune) bool {
	for _, letter := range s {
		if letter == c {
			return true
		}
	}
	return false
}

func reduceString(s string) string {
	var reduced string

	for i := range s {
		if !contains(reduced, rune(s[i])) {
			reduced += string(s[i])
		}
	}

	return reduced
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	sum := 0
	lineCounter := 0

	var group = make([]string, 3)

	for scanner.Scan() {
		line := scanner.Text()

		if lineCounter == 0 {
			group[0] = line
			lineCounter++
			continue
		} else if lineCounter == 1 {
			group[1] = line
			lineCounter++
			continue
		} else {
			group[2] = line
			lineCounter = 0
		}

		if len(group[1]) < len(group[0]) {
			group[1], group[0] = group[0], group[1]
		}
		if len(group[2]) < len(group[0]) {
			group[2], group[0] = group[0], group[2]
		}

		group[0] = reduceString(group[0])

		for _, item := range group[0] {
			if contains(group[1], item) && contains(group[2], item) {
				if item >= 65 && item <= 90 {
					item -= 38
				} else {
					item -= 96
				}
				sum += int(item)

				break
			}
		}

		// PRIMA PARTE
		/* firstHalf := line[:len(line)/2]
		secondHalf := line[len(line)/2:]
		for _, item := range firstHalf {
			if contains(secondHalf, item) {

				if item >= 65 && item <= 90 {
					item -= 38
				} else {
					item -= 96
				}
				sum += int(item)

				break

			}
		}
		*/
	}

	fmt.Println(sum)
}
