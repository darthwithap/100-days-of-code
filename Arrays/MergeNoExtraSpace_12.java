import java.util.*;

public class MergeNoExtraSpace_12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int i=0, j=0;
        int[] a = new int[n];
        int[] b = new int[m];
        System.out.print(n+" "+m);
        for(i=0; i<n; i++) a[i] = sc.nextInt();
        System.out.print(a);
        for(j=0; j<m; j++) b[j] = sc.nextInt();
        System.out.print(b);
        i=n-1;
        j=0;
        while (i>=0 && j<m) {
            if (a[i]>b[j]) {
                a[i] += b[j];
                b[j] = a[i]-b[j];
                a[i]-= b[j];
            }
            else continue;
            i--;
            j++;
        }
        Arrays.sort(a);
        Arrays.sort(b);
        sc.close();

    }
}
