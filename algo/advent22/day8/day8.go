package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func isGreatest(s []int, x int) bool {
	for _, y := range s {
		if y >= x {
			return false
		}
	}
	return true
}

func topBottomRows(xAxis, yAxis int, m [][]int) ([]int, []int) {
	topRow := make([]int, 0)
	bottomRow := make([]int, 0)

	for i := range m {
		if i < yAxis {
			topRow = append(topRow, m[i][xAxis])
		} else if i > yAxis {
			bottomRow = append(bottomRow, m[i][xAxis])
		}
	}

	return topRow, bottomRow
}

func scenicScore(topRow, bottomRow, leftRow, rightRow []int, tree int) int {

	rightScore := 0
	for _, t := range rightRow {
		if t < tree {
			rightScore++
		} else {
			rightScore++
			break
		}
	}

	bottomScore := 0
	for _, t := range bottomRow {
		if t < tree {
			bottomScore++
		} else {
			bottomScore++
			break
		}
	}

	topScore := 0
	for i := len(topRow) - 1; i >= 0; i-- {
		if topRow[i] < tree {
			topScore++
		} else {
			topScore++
			break
		}
	}

	leftScore := 0
	for i := len(leftRow) - 1; i >= 0; i-- {
		if leftRow[i] < tree {
			leftScore++
		} else {
			leftScore++
			break
		}
	}

	return topScore * bottomScore * leftScore * rightScore
}

func countVisible(m [][]int) int {
	count := 0
	for y := range m {
		if y == 0 || y == len(m[y])-1 {
			count += len(m[y])
			continue
		}
		for x := range m[y] {
			if x == 0 || x == len(m[y])-1 {
				count++
			} else {
				curr := m[y][x]
				topRow, bottomRow := topBottomRows(x, y, m)
				leftRow := m[y][:x]
				rightRow := m[y][x+1:]
				if isGreatest(topRow, curr) || isGreatest(bottomRow, curr) || isGreatest(leftRow, curr) || isGreatest(rightRow, curr) {
					count++
				}
			}
		}
	}
	return count
}

func bestScenicScore(m [][]int) int {
	best := 0
	for y := range m {
		for x := range m[y] {
			curr := m[y][x]
			topRow, bottomRow := topBottomRows(x, y, m)
			leftRow := m[y][:x]
			rightRow := m[y][x+1:]
			treeScore := scenicScore(topRow, bottomRow, leftRow, rightRow, curr)
			if treeScore > best {
				best = treeScore
			}
		}
	}
	return best
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	var myMap = make([][]int, 0)

	lineCounter := 0
	for scanner.Scan() {
		line := scanner.Text()
		row := make([]int, len(line))
		for i, c := range line {
			n, _ := strconv.Atoi(string(c))
			row[i] = n
		}
		myMap = append(myMap, row)
		lineCounter++
	}

	/* for _, row := range myMap {
		fmt.Println(row)
	} */

	fmt.Println(bestScenicScore(myMap))
}
