package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
)

func main() {

	var n, start, finish int
	_, _ = start, finish
	var graph [][]int

	scanner := bufio.NewScanner(os.Stdin)

	firstLine := true
	for scanner.Scan() {
		line := scanner.Text()

		if firstLine {
			n, _ = strconv.Atoi(string(line[0]))
			start, _ = strconv.Atoi(string(line[4]))
			finish, _ = strconv.Atoi(string(line[6]))
			firstLine = false
			graph = make([][]int, n)
			// graph[i][j] = costo dell'arco da i a j

			for i := 0; i < n; i++ {
				for j := 0; j < n; j++ {
					graph[i] = append(graph[i], int(math.Inf(1)))
				}
			}
			continue
		}

		from, _ := strconv.Atoi(string(line[0]))
		to, _ := strconv.Atoi(string(line[2]))
		cost, _ := strconv.Atoi(string(line[4]))
		graph[from][to] = cost

	}

	fmt.Println(graph)

}
