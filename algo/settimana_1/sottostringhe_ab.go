package main

import "fmt"

func main() {
	var s string
	ab_string_counter := 0
	a_counter := 0
	fmt.Print("Insert string: ")
	fmt.Scan(&s)
	for _, char := range s {
		if char == 'a' {
			a_counter++
		} else if char == 'b' {
			ab_string_counter += a_counter
		}
	}
	fmt.Println(ab_string_counter)
	return
}
