package main

import (
	"fmt"
	"strconv"
)

type monkey struct {
	name  int
	items []uint64
	op    []string
	test  int
}

type graph struct {
	nodes    map[int]*monkey
	adjacent map[*monkey][]*monkey
}

func inspect(op []string, old uint64) uint64 {
	var secondOperand uint64
	if op[1] == "old" {
		secondOperand = old
	} else {
		h, _ := strconv.Atoi(op[1])
		secondOperand = uint64(h)
	}

	switch op[0] {
	case "+":
		return old + secondOperand
	case "-":
		return old - secondOperand
	case "*":
		return old * secondOperand
	case "/":
		return old / secondOperand
	}

	return 0
}

func createMonkey(name int, items []uint64, op []string, test int) *monkey {
	m := new(monkey)
	m.name = name
	m.items = items
	m.op = op
	m.test = test
	return m
}

func createGraph(monkeys []*monkey, ad map[*monkey][]*monkey) *graph {
	m := make(map[int]*monkey, len(monkeys))
	for k := 0; k < len(monkeys); k++ {
		m[k] = monkeys[k]
	}
	return &graph{m, ad}
}

func printGraph(g *graph) {
	for i := 0; i < len(g.nodes); i++ {
		monkey := g.nodes[i]
		fmt.Println("Monkey", monkey.name, ":", monkey.items)
	}
	fmt.Println()
}

func round(g *graph) []int {
	inspectedItems := make([]int, len(g.nodes))
	for i := range inspectedItems {
		inspectedItems[i] = 0
	}

	for i := 0; i < len(g.nodes); i++ {
		monkey := g.nodes[i]
		for len(monkey.items) > 0 {
			item := monkey.items[0]
			monkey.items = monkey.items[1:]
			item = inspect(monkey.op, item)
			inspectedItems[i]++

			if item%uint64(monkey.test) == 0 {
				g.adjacent[monkey][0].items = append(g.adjacent[monkey][0].items, item)
			} else {
				g.adjacent[monkey][1].items = append(g.adjacent[monkey][1].items, item)
			}

		}
	}
	return inspectedItems
}

func main() {
	/*
		a := createMonkey(0, []int{89, 74}, []string{"*", "5"}, 17)
		b := createMonkey(1, []int{75, 69, 87, 57, 84, 90, 66, 50}, []string{"+", "3"}, 7)
		c := createMonkey(2, []int{55}, []string{"+", "7"}, 13)
		d := createMonkey(3, []int{69, 82, 69, 56, 68}, []string{"+", "5"}, 2)
		e := createMonkey(4, []int{72, 97, 50}, []string{"+", "2"}, 19)
		f := createMonkey(5, []int{90, 84, 56, 92, 91, 91}, []string{"*", "19"}, 3)
		g := createMonkey(6, []int{63, 93, 55, 53}, []string{"*", "old"}, 5)
		h := createMonkey(7, []int{50, 61, 52, 58, 86, 68, 97}, []string{"+", "4"}, 11)
	*/

	a := createMonkey(0, []uint64{79, 98}, []string{"*", "19"}, 23)
	b := createMonkey(1, []uint64{54, 65, 75, 74}, []string{"+", "6"}, 19)
	c := createMonkey(2, []uint64{79, 60, 97}, []string{"*", "old"}, 13)
	d := createMonkey(3, []uint64{74}, []string{"+", "3"}, 17)

	ms := []*monkey{a, b, c, d}
	ad := make(map[*monkey][]*monkey, len(ms))

	ad[a] = []*monkey{c, d}
	ad[b] = []*monkey{c, a}
	ad[c] = []*monkey{b, d}
	ad[d] = []*monkey{a, b}

	/*
		ad[a] = []*monkey{e, h}
		ad[b] = []*monkey{d, c}
		ad[c] = []*monkey{a, h}
		ad[d] = []*monkey{a, c}
		ad[e] = []*monkey{g, f}
		ad[f] = []*monkey{g, b}
		ad[g] = []*monkey{d, b}
		ad[h] = []*monkey{f, e}
	*/
	myGraph := new(graph)
	myGraph = createGraph(ms, ad)

	inspected := make([]int, len(myGraph.nodes))
	for i := range inspected {
		inspected[i] = 0
	}

	rounds := 20
	for i := 0; i < rounds; i++ {
		newlyInspected := round(myGraph)
		for i := range inspected {
			inspected[i] += newlyInspected[i]
		}
	}

	//printGraph(myGraph)
	for i, x := range inspected {
		fmt.Println("Monkey", i, "inspected", x)
	}

}
