package main

import (
	"fmt"
)

func getNumberOfOrbits(orbitMap map[string]string) int {
	numberOfOrbits := 0

	for planet := range orbitMap {
		nextPlanet := orbitMap[planet]
		numberOfOrbits++
		for nextPlanet != "COM" {
			nextPlanet = orbitMap[nextPlanet]
			numberOfOrbits++
		}
	}

	return numberOfOrbits
}

type treeNode struct {
	planet          string
	left, right, up *treeNode
}

type tree struct {
	root *treeNode
}

func newNode(planet string) *treeNode {
	return &treeNode{planet, nil, nil, nil}
}

// Trovo il padre (che è un valore), i suoi figli sono delle chiavi, che trovo con findKeys
func buildTree(orbits map[string]string, planetName string) *treeNode {
	node := newNode(planetName)
	// trovo i satelliti di planet
	foundOne := false
	for satellite, planet := range orbits {
		if planet == planetName && !foundOne {
			foundOne = true
			node.left = buildTree(orbits, satellite)
			node.left.up = node
		} else if planet == planetName && foundOne {
			node.right = buildTree(orbits, satellite)
			node.right.up = node
		}
	}

	return node
}

func printTree(t tree, indentation int) {
	node := t.root
	if node != nil {
		fmt.Println("*", node.planet)
		if node.left == nil && node.right == nil {
			return
		} else {
			t.root = node.left
			for i := 0; i < indentation; i++ {
				fmt.Print("  ")
			}
			printTree(t, indentation+1)
			t.root = node.right
			for i := 0; i < indentation; i++ {
				fmt.Print("  ")
			}
			printTree(t, indentation+1)
		}
	} else {
		fmt.Println("* ")
	}

}

// Trova il nodo wanted e stampa il percorso fino alla radice
func find(node *treeNode, wanted string) {

	if node == nil {
		return
	}

	if node.planet == wanted {
		var path []string
		// Ho trovato wanted
		for node.planet != "COM" {
			path = append(path, node.planet)
			node = node.up
		}
		fmt.Println(path)
	} else {
		find(node.left, wanted)
		find(node.right, wanted)
	}

}

func main() {
	/* orbits := make(map[string]string)

	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		orbits[scanner.Text()[4:7]] = scanner.Text()[0:3]
	} */
	// orbits[pianeta] = "orbita attorno a"

	// PARTE 1
	// fmt.Println(getNumberOfOrbits(orbits))

	/* var t tree

	t.root = buildTree(orbits, "COM")

	find(t.root, "YOU")
	find(t.root, "SAN") */

	youPath := "3LN 9LY BNZ 9Z9 MRM VD5 LR4 BJH Y7B 9CB PWQ Y7M 9KL HRK KB8 ZWJ 14X FWY ZT8 2JQ NCQ 4Q5 RMB HQC YHR 7ZP 49N S9Z HNB BHR 57R R87 WF9 CN9 66V GHS 9WF NDM DH1 RP8 51Y 4VK 967 ZW2 7Q6 7J9 GKK C5G C2S G6D XQV YHX 5HT NKG RNM HMN FPB T2B WPF B8Z 82K FQK KW3 HX8 PS1 QS2 CK4 7SQ P1W PSM 2PT FN8 H42 FJ2 8D8 PWV FRD 9CJ MK4 66S 2MQ SCR 8MQ MXD 8WJ Q84 25X NHB Q1L GD9 JPY 8WS 4T5 F74 6V1 FB6 DBK LQR JNL 3VV 62D 2M9 CPQ LVG RCQ 5HN BN2 C4K 25G X4N G5S M29 8TZ 6MS 689 17B MNL XGN Y64 YN2 MNY CLX YCW 622 HMC HML 326 58J GQ1 MF7 SCQ GMM 22B 14K XDD BG3 MR7 5VH N57 X94 2PS TVQ 4XF BFL FCQ 6LF DFW F62 MJN Q4C 21T TDZ 1Q1 3XK ZXH JH5 BDC DZ7 K6G 6ZB RRX JTG BDB TQ8 ZJ3 LML NJ2 LGH 54W 2QC 45M QD9 JNB 3J9 Q1M 3Q3 YJG DQT TJW 31C CKQ "
	sanPath := "Q8B B4D LL9 F89 J8T ZL4 Q2W 96Q 14F HQL TJD KRR 3VR 5BT VC9 YYL KDN 82D 2RN LYJ FTN BSN F7D LX6 W6D SF6 W5M 8SP NGV PR8 FHY 2PV X9N 5CV XWS YVZ T7S 1QJ 8ZL Z8W Y9F 1RH RWX FFY QRY RY5 DQ4 HBK RHQ YPK LM3 J6S 69V T4H GQ3 511 JL3 SLS K8K 29K 8F1 3RD DCJ T1Z 6R1 H12 Z2L 5KX DDD MKY VPH P66 67Y K2M GYG D13 99V DZG Y34 PDK YDC 1JP JR9 WCS GK8 HWF FDR SND FF3 NF1 NYY 2JM WZ4 8PM R7R RYS QBT J55 BTJ M4Y TQ4 JG6 1YC XWV QKX PDY ZDY NHF KNC 3GN Y27 57D SC4 X2J HYC Z21 2Y2 ZTZ 3JF WKM NBF PGC 3X2 K2Y BZK 9VZ MLX GK2 WNL 42M VK9 2SM KML DQW D98 CQD 3PV LMK Z7P C9D JHL "

	cont := 0
	for _, char := range youPath {
		if char == ' ' {
			cont++
		}
	}
	fmt.Println(cont)
	cont = 0
	for _, char := range sanPath {
		if char == ' ' {
			cont++
		}
	}
	fmt.Println(cont)

}
