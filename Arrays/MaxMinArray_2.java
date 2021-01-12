import java.util.*;

public class MaxMinArray_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int a[] = new int[n];
        int i;

        for (i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int[] res = MaxMin(a, 0, n - 1);
        System.out.print("min: " + res[0] + " max: " + res[1]);
    }

    static int[] MaxMin(int[] a, int i, int j) {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        if (i == j) {
            min = a[i];
            max = a[j];
        } else if (i == j - 1) {
            if (a[i] < a[j]) {
                min = a[i];
                max = a[j];
            } else {
                min = a[j];
                max = a[i];
            }
        } else {
            int m = (i + j) / 2;
            int[] a1 = MaxMin(a, i, m);
            int[] a2 = MaxMin(a, m + 1, j);

            if (a1[0] <= a2[0])
                min = a1[0];
            else min = a2[0];
            if (a1[1] > a2[1])
                max = a1[1];
            else max = a2[1];
        }
        return new int[] {
                min,
                max
        };
    }
}