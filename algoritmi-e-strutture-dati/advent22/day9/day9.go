package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
)

func isNear(first, second *knot) bool {
	xDistance := math.Abs(float64(first.x) - float64(second.x))
	yDistance := math.Abs(float64(first.y) - float64(second.y))

	if xDistance > 1 || yDistance > 1 {
		return false
	}

	return true
}

func slicesAreEqual(a, b []int) bool {
	for i := range a {
		if a[i] != b[i] {
			return false
		}
	}
	return true
}

func contains(s [][]int, x []int) bool {
	for _, a := range s {
		if slicesAreEqual(a, x) {
			return true
		}
	}
	return false
}

type knot struct {
	x, y int
}

func makeMove(dir string, knot *knot) {

	switch dir {
	case "U":
		knot.y++
	case "D":
		knot.y--
	case "R":
		knot.x++
	case "L":
		knot.x--
	case "UL":
		knot.x--
		knot.y++
	case "UR":
		knot.x++
		knot.y++
	case "DL":
		knot.x--
		knot.y--
	case "DR":
		knot.x++
		knot.y--
	}
}

func findDirection(first, second *knot) string {
	if first.x > second.x {
		if first.y > second.y {
			return "UR"
		} else if first.y < second.y {
			return "DR"
		} else {
			return "R"
		}
	} else if first.x < second.x {
		if first.y > second.y {
			return "UL"
		} else if first.y < second.y {
			return "DL"
		} else {
			return "L"
		}
	} else {
		if first.y > second.y {
			return "U"
		} else if first.y < second.y {
			return "D"
		}
	}
	return ""
}

func main() {
	moves := make([][]string, 0)

	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		line := scanner.Text()

		move := []string{string(line[0]), string(line[2:])}
		moves = append(moves, move)
	}

	numberOfKnots := 10
	var rope = make([]*knot, numberOfKnots)

	for i := range rope {
		rope[i] = &knot{0, 0}
	}

	var visitedPositions = make([][]int, 0)
	var x = make([]int, 2)
	x[0], x[0] = 0, 0
	visitedPositions = append(visitedPositions, x)

	for _, move := range moves {

		direction := string(move[0])
		steps, _ := strconv.Atoi(move[1])

		for i := 0; i < steps; i++ {
			for j := range rope {
				if j == 0 {
					makeMove(direction, rope[j])
				} else {
					if !isNear(rope[j-1], rope[j]) {
						dir := findDirection(rope[j-1], rope[j])
						makeMove(dir, rope[j])

						if j == numberOfKnots-1 {
							// Devo segnarmi le posizioni nuove esplorate
							var ehi = make([]int, 2)
							ehi[0] = rope[j].x
							ehi[1] = rope[j].y
							if !contains(visitedPositions, ehi) {
								visitedPositions = append(visitedPositions, ehi)
							}
						}

					}
				}
			}
		}
	}

	fmt.Println(len(visitedPositions))

}
