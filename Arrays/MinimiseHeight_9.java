import java.util.*;

public class MinimiseHeight_9 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] a = new int[n];
        int i=0;
        for (i=0; i<n; i++)
            a[i] = sc.nextInt();
        Arrays.sort(a);
        int diff = a[n-1]-a[0];
        for (i=0; i<n; i++) {
            if (a[i]<k) {
                a[i]+=k;
                continue;
            }
            else {
                int sub = a[i]-k;
                int add = a[i]+k;
                
            }

        }
        sc.close();
    }
}
