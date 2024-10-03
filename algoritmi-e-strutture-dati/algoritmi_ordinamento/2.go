package main

import (
	"fmt"
	"math/rand"
	"time"
)

func swap(s []int, x, y int) {
	temp := s[x]
	s[x] = s[y]
	s[y] = temp
}

func selectionSort(s []int) []int {

	for k := 0; k < len(s)-1; k++ {
		min := k
		for j := k + 1; j < len(s); j++ {
			if s[j] < s[k] {
				min = j
			}
		}
		swap(s, min, k)
	}

	return s
}

func insertionSort(s []*foo) []*foo {

	for k := 1; k < len(s); k++ {
		x := s[k]
		j := k - 1
		for j >= 0 && s[j].key > x.key {
			s[j+1] = s[j]
			j--
		}
		s[j+1] = x
	}

	return s
}

func bubbleSort(s []int) []int {

	scambiato := true
	i := 0

	for scambiato && i < len(s) {
		scambiato = false
		for j := 1; j < len(s)-i; j++ {
			if s[j] < s[j-1] {
				swap(s, j, j-1)
				scambiato = true
			}
		}
		i++
	}

	return s
}

func merge(a, b []int) (merged []int) {
	merged = make([]int, 0, len(a)+len(b))
	ia, ib := 0, 0

	for ia < len(a) && ib < len(b) {
		if a[ia] < b[ib] {
			merged = append(merged, a[ia])
			ia++
		} else {
			merged = append(merged, b[ib])
			ib++
		}
	}

	// Se è rimasta la parte finale di a
	merged = append(merged, a[ia:]...)
	// Se è rimasta la parte finale di b
	merged = append(merged, b[ib:]...)

	return merged
}

func mergeSort(a []int) []int {
	if len(a) == 1 {
		return a
	}

	m := len(a) / 2

	mergeSort(a[:m])
	mergeSort(a[m:])
	copy(a, merge(a[:m], a[m:]))

	return a
}

func partiziona(a []int, i, f int) int {
	pivot := a[i]
	sx, dx := i, f

	for sx < dx {

		dx--
		for a[dx] > pivot {
			dx--
		}

		sx++
		for a[sx] <= pivot && sx < dx {
			sx++
		}

		if sx < dx {
			swap(a, dx, sx)
		}
	}

	swap(a, i, dx)
	return dx
}

func quickSort(a []int, i, f int) {
	if f-i <= 1 {
		return
	}

	pivot := partiziona(a, i, f)
	//fmt.Println(a[pivot])
	quickSort(a, i, pivot)
	quickSort(a, pivot+1, f)
}

type foo struct {
	key  int
	item string
}

func newFoo(s string, k int) *foo {
	return &foo{k, s}
}

func (f foo) printFoo() {
	fmt.Print(f.key, " ", f.item, " ")
}

func main() {
	rand.Seed(time.Now().Unix())

	// Generate a random slice of length n
	/* vector := []*foo{newFoo("ciao", 1), newFoo("hey", 5), newFoo("sup", 2), newFoo("heeyyy", 11), newFoo("WOW", 3)}

	fmt.Println("Array iniziale:")
	for _, f := range vector {
		f.printFoo()
		fmt.Println()
	}

	insertionSort(vector)

	fmt.Println("Array ordinato:")
	for _, f := range vector {
		f.printFoo()
		fmt.Println()
	} */

	s := []int{27, 34, 16, 13, 18, 9, 40, 6}
	partiziona(s, 0, len(s))
	fmt.Println(s)

}
