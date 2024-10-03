package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	caloriesByElf := make([]int, 0)
	singleElfCalories := 0

	for scanner.Scan() {
		line := scanner.Text()

		if line == "" {
			caloriesByElf = append(caloriesByElf, int(singleElfCalories))
			singleElfCalories = 0
		} else {
			itemCalories, _ := strconv.Atoi(line)
			singleElfCalories += itemCalories
		}
	}

	c := 0

	for range make([]int, 3) {
		max := 0
		maxIndex := 0

		for i, c := range caloriesByElf {
			if c > max {
				max = c
				maxIndex = i
			}
		}

		caloriesByElf[maxIndex] = caloriesByElf[len(caloriesByElf)-1]
		caloriesByElf = caloriesByElf[:len(caloriesByElf)-1]
		c += max
	}
	fmt.Println(c)
}
