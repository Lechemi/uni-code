package main

import (
	"bufio"
	"fmt"
	"os"
)

// Ritorna true se s non contiene caratteri duplicati
func doesNotHaveDuplicates(s string) bool {
	for i, c := range s {
		for _, r := range s[i+1:] {
			if r == c {
				return false
			}
		}
	}
	return true
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		line := scanner.Text()

		for i := 13; i < len(line)-1; i++ {
			if doesNotHaveDuplicates(line[i-13 : i+1]) {
				fmt.Println(i+1, line[i-13:i+1], string(line[i]))
				break
			}
		}
	}

}
