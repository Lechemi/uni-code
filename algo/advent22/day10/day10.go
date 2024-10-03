package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	xRegister := 1
	cycle := 0

	instructions := make([]string, 0)

	for scanner.Scan() {
		line := scanner.Text()
		instructions = append(instructions, line)
	}

	instructionRegister := 0
	drawnPixel := 0

	for instructionRegister != len(instructions)-1 {

		cycle++
		// INIZIO CICLO

		if drawnPixel >= xRegister-1 && drawnPixel <= xRegister+1 {
			fmt.Print("#")
		} else {
			fmt.Print(".")
		}

		if (cycle)%40 == 0 {
			fmt.Println()
			drawnPixel = -1
		}

		drawnPixel++
		// FINE CICLO

		switch instructions[instructionRegister][:4] {
		case "addx":
			cycle++
			// INIZIO CICLO

			if drawnPixel >= xRegister-1 && drawnPixel <= xRegister+1 {
				fmt.Print("#")
			} else {
				fmt.Print(".")
			}

			if (cycle)%40 == 0 {
				fmt.Println()
				drawnPixel = -1
			}

			toAdd, _ := strconv.Atoi(instructions[instructionRegister][5:])
			xRegister += toAdd

			drawnPixel++
			// FINE CICLO
		case "noop":
			break
		}
		instructionRegister++
	}
	fmt.Println()

}

// fmt.Println("#", "disegno in posizione:", drawnPixel, "sprite position:", xRegister)
