import java.util.*;

public class KthMaxMinArray_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        int a[] = new int[n];
        int i;

        for (i = 0; i < n; i++)
            a[i] = sc.nextInt();
        Arrays.sort(a);
        System.out.print("kth min: " + a[k - 1] + " kth min: " + a[n - k]);
    }
}