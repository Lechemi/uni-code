package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type intervallo struct {
	inizio, fine, valore int
}

func (i *intervallo) print() {
	fmt.Print("inizio: ", i.inizio, " fine: ", i.fine, " valore: ", i.valore)
	fmt.Println()
}

func heapifyDown(h []*intervallo, i, l int) {
	for {
		j := i*2 + 1

		if i >= l || j >= l {
			break
		}

		j2 := i*2 + 2
		if j2 < l && h[j2].fine > h[j].fine {
			j = j2
		}

		if h[i].fine >= h[j].fine {
			break
		}

		h[i], h[j] = h[j], h[i]

		i = j
	}
}

func createHeap(h []*intervallo) {
	for i := len(h) - 1; i >= 0; i-- {
		heapifyDown(h, i, len(h))
	}
}

func heapSort(h []*intervallo) {
	createHeap(h)
	l := len(h) - 1

	for l > 0 {
		h[0], h[l] = h[l], h[0]
		heapifyDown(h, 0, l)
		l--
	}
}

func p(j int, h []*intervallo) int {
	inizioj := h[j].inizio
	for i := j - 1; i >= 0; i-- {
		if h[i].fine < inizioj {
			return i
		}
	}
	return -1
}

func max(x, y int) int {
	if x > y {
		return x
	} else {
		return y
	}
}

func value(l []*intervallo) int {
	v := 0
	for _, inte := range l {
		v += inte.valore
	}
	return v
}

func maxValue(l1, l2 []*intervallo) []*intervallo {
	if value(l1) > value(l2) {
		return l1
	} else {
		return l2
	}
}

func schedule(intervalli []*intervallo) [][]*intervallo {
	ottimali := make([][]*intervallo, len(intervalli))

	ottimali[0] = []*intervallo{intervalli[0]}

	for j := range intervalli {
		if j == 0 {
			continue
		}
		if p(j, intervalli) != -1 {
			ottimali[j] = maxValue(ottimali[j-1], append(ottimali[p(j, intervalli)], intervalli[j]))
		} else {
			h := []*intervallo{intervalli[j]}
			ottimali[j] = maxValue(ottimali[j-1], h)
		}
	}

	return ottimali
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	intervalli := make([]*intervallo, 0)

	for scanner.Scan() {
		line := scanner.Text()
		i := new(intervallo)
		parti := strings.Split(line, " ")

		valore, _ := strconv.Atoi(parti[1])
		i.valore = valore

		tempi := strings.Split(parti[0], "-")
		inizio, _ := strconv.Atoi(tempi[0])
		fine, _ := strconv.Atoi(tempi[1])
		i.inizio = inizio
		i.fine = fine

		intervalli = append(intervalli, i)
	}

	heapSort(intervalli)

	opt := schedule(intervalli)

	fmt.Println("Soluzione ottimale:")
	fmt.Println("Valore:", value(opt[len(opt)-1]))
	for _, intervallo := range opt[len(opt)-1] {
		intervallo.print()
	}

}
