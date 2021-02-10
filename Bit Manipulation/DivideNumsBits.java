import java.util.*;

public class DivideNumsBits {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in) ;
        System.out.println(divide(1145875222, 3));
        sc.close();
    }

    public static int divide(int A, int B) {
        int count=0;
        if (A<B && A<0) return -1;
        else {
            A = Math.abs(A);
            B = Math.abs(B);
            while (A>=B) {
                A -= B;
                count++;
            }
        }
        return count;
    }
}