import java.util.ArrayList;
import java.util.List;

public class ListContainer {
    private List<Integer> myList = new ArrayList<>(List.of(1, 2, 3, 4, 5));

    public ListContainer(List<Integer> myList) {
        this.myList = myList;
    }

    public List<Integer> getMyList() {
        return myList;
    }

    public int getHash() {
        return myList.hashCode();
    }
}
