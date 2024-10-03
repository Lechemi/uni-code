package main

import (
	"fmt"
	"math/rand"
	"time"
)

type person struct {
	name string
	key  int
}

func reorder(n int, people *[]person) int {
	cont := 0
	for i, p := range *people {
		if p.key == n {
			// Trovo la persona con key n e la scambio con chi è in posizione len(n) - n (es: trovo la 9 e la scambio con chi è in posizione 0)
			if p != (*people)[len(*people)-n] {
				box := (*people)[len(*people)-n]
				(*people)[len(*people)-n] = p
				cont++
				(*people)[i] = box
				cont++
			}
		}
	}
	return cont
}

func shuffleVector(vector *[]person) {
	rand.Seed(time.Now().UnixNano())
	rand.Shuffle(len((*vector)), func(i, j int) { (*vector)[i], (*vector)[j] = (*vector)[j], (*vector)[i] })
}

func main() {
	vector := []person{{name: "Francesco", key: 6}, {name: "Andrea", key: 1}, {name: "Elisa", key: 5}, {name: "Beatrice", key: 2}, {name: "Carlo", key: 3}, {name: "Dino", key: 4}, {name: "Giorgia", key: 7}, {name: "Irene", key: 9}, {name: "Henry", key: 8}}
	somma := 0
	sample_size := 1000

	for j := 0; j < sample_size; j++ {
		shuffleVector(&vector)
		assignments := 0
		for i := len(vector); i > 0; i-- {
			assignments += reorder(i, &vector)
		}
		somma += assignments
	}
	fmt.Println(float64(somma) / float64(sample_size))
}
