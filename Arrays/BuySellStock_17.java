import java.util.*;

public class BuySellStock_17 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int i=0;
        for (i=0; i<n; i++) a[i]=sc.nextInt();
        int p=0, low=Integer.MAX_VALUE;
        for (i=0; i<n; i++) {
            low = Math.min(low, a[i]);
            p=Math.max(p, a[i]-low);
        }
        System.out.println(p);
        sc.close();
    }    
}
