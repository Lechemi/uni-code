package e04.rational;

public class Main {
    public static void main(String[] args) {
        Rational r1 = new Rational(28, 5);
        Rational r2 = new Rational(-30, 5);

        System.out.println(r1.sum(r2).getNum() + "," + r1.sum(r2).getDen());
        System.out.println(r1.subtractBy(r2).getNum() + "," + r1.subtractBy(r2).getDen());
        System.out.println(r1.multiplyBy(r2).getNum() + "," + r1.multiplyBy(r2).getDen());
        System.out.println(r1.divideBy(r2).getNum() + "," + r1.divideBy(r2).getDen());
    }
}
