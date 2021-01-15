import java.util.*;

public class ReverseTheArray_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int a[] = new int[n];
        int i, j = n - 1;

        for (i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        i = 0;
        while (i < j) {
            a[i] += a[j];
            a[j] = a[i] - a[j];
            a[i] -= a[j];
            i++;
            j--;
        }

        for (i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
        sc.close();
    }
}