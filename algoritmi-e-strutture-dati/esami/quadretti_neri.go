package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func numeroPercorsiCompleti(mappa [][]bool) int {
	// numeroPercorsiCompleti[i, j] = numero di percorsi completi dalla casella [i, j]
	numeroPercorsiCompleti := make([][]int, len(mappa))
	for i := range numeroPercorsiCompleti {
		numeroPercorsiCompleti[i] = make([]int, len(mappa[i]))
	}

	// Inizializzazione
	for i := range mappa {
		for j := range mappa[i] {

			isLast := false
			if i == len(mappa)-1 || j == len(mappa[i])-1 {
				numeroPercorsiCompleti[i][j] = 1
				isLast = true
			}

			if mappa[i][j] == true {
				numeroPercorsiCompleti[i][j] = 0
			}

			if !isLast && mappa[i][j] == false {
				numeroPercorsiCompleti[i][j] = -1
			}
		}
	}

	for _, row := range numeroPercorsiCompleti {
		fmt.Println(row)
	}
	fmt.Println()

	// Aggiornamento
	for i := len(numeroPercorsiCompleti) - 1; i >= 0; i-- {
		for j := len(numeroPercorsiCompleti[i]) - 1; j >= 0; j-- {
			if numeroPercorsiCompleti[i][j] != 0 && i < len(numeroPercorsiCompleti)-1 && j < len(numeroPercorsiCompleti[i])-1 {
				numeroPercorsiCompleti[i][j] = numeroPercorsiCompleti[i+1][j] + numeroPercorsiCompleti[i][j+1]
			} else if numeroPercorsiCompleti[i][j] != 0 && i == len(numeroPercorsiCompleti)-1 && j == len(numeroPercorsiCompleti[i])-1 {
				// angolo in basso a destra
				numeroPercorsiCompleti[i][j] = 1
			} else if numeroPercorsiCompleti[i][j] != 0 && i == len(numeroPercorsiCompleti)-1 {
				// ultima riga
				numeroPercorsiCompleti[i][j] = numeroPercorsiCompleti[i][j+1]
			} else if numeroPercorsiCompleti[i][j] != 0 && j == len(numeroPercorsiCompleti[i])-1 {
				// ultima colonna
				numeroPercorsiCompleti[i][j] = numeroPercorsiCompleti[i+1][j]
			}
		}
	}

	for _, row := range numeroPercorsiCompleti {
		fmt.Println(row)
	}

	return numeroPercorsiCompleti[0][0]
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	mappa := make([][]bool, 0)

	for scanner.Scan() {
		line := scanner.Text()
		row := make([]bool, 0)
		caselle := strings.Split(line, "")
		for _, cacasella := range caselle {
			if cacasella == "0" {
				row = append(row, false)
			} else {
				row = append(row, true)
			}
		}
		mappa = append(mappa, row)
	}

	fmt.Println("Numero di percorsi completi a partire dalla casella in alto a sinistra:", numeroPercorsiCompleti(mappa))

}
