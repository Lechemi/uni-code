public class Test {
    public static void main(String[] args) {
        int[] myIntArray = {1, 2, 3, 4};
        int[] arr = {1, 2, 6, 1};
        MatriceQuadrata m1 = new MatriceQuadrataGenerica(2, myIntArray);
        MatriceQuadrata m2 = new MatriceQuadrataGenerica(2, arr);

        System.out.println(m1.toString());
        System.out.println(m2.toString());

        System.out.println(m2.per(m1).toString());


    }
}
