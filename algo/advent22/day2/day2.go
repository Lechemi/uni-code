package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	totalScore := 0

	for scanner.Scan() {
		line := scanner.Text()

		oppsMove := string(line[0])
		myMove := string(line[2])
		outcome := 0

		switch oppsMove {
		case "A":
			// Sasso
			switch myMove {
			case "X":
				outcome = 3
			case "Y":
				outcome = 4
			case "Z":
				outcome = 8
			}
		case "B":
			// Carta
			switch myMove {
			case "X":
				outcome = 1
			case "Y":
				outcome = 5
			case "Z":
				outcome = 9
			}
		case "C":
			// Forbice
			switch myMove {
			case "X":
				outcome = 2
			case "Y":
				outcome = 6
			case "Z":
				outcome = 7
			}
		}

		totalScore += outcome

	}

	fmt.Println(totalScore)

}
