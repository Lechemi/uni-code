package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func numberOfIncrements(data []int, n, m int) int {
	var processedData []int
	var numberOfIncrements int

	toAdd := 0
	for j := 0; j < m; j++ {
		toAdd += data[j]
	}
	processedData = append(processedData, toAdd)
	secondPart := toAdd - data[0]

	for i := 1; i < n-m+1; i++ {

		toAdd = secondPart + data[i+m-1]
		processedData = append(processedData, toAdd)
		secondPart = toAdd - data[i]

	}

	fmt.Println(processedData)

	for i := 0; i < len(processedData)-1; i++ {
		if processedData[i+1] > processedData[i] {
			numberOfIncrements++
		}
	}

	return numberOfIncrements
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	firstLine := true
	var data []int
	var n, m int

	// Prendo i dati
	for scanner.Scan() {
		line := scanner.Text()
		if firstLine {
			parts := strings.Split(line, " ")
			n, _ = strconv.Atoi(parts[0])
			m, _ = strconv.Atoi(parts[1])
			firstLine = false
		} else {
			x, _ := strconv.Atoi(line)
			data = append(data, x)
		}
	}

	fmt.Println(numberOfIncrements(data, n, 1), numberOfIncrements(data, n, m))

}

/*

STIMA DELLE RISORSE PER numberOfIncrements
[Assumo tempo costante per somme e confronti]

Numero di somme:
	Ho (2n - m + 3) somme, quindi O(n) somme (siccome m < n)

Numero di confronti:
	in processedData ho sempre (n - m + 1) elementi
	confronto ogni elemento con il successivo per vedere se quest'ultimo è più grande
	=> O(n-m+1) = O(n)

Tempo di esecuzione:
	Se considero il tempo per ogni somma e per ogni confronto uguale ad una costane k,
	ottengo un tempo totale di esecuzione pari a O(kn) + O(kn) = O(kn + kn) = O(kn)

*/
