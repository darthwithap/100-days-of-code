import java.util.*;
public class MergeIntervals_14 {
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] a = new int[n][2];
        int i=0;
        for (i=0; i<n; i++) {
            a[i][0] = sc.nextInt();
            a[i][1] = sc.nextInt();
        }
        int m=n;
        for(i=0; i<n-1; i++) {
            if (a[i+1][0]<=a[i][1]) {
                a[i+1][0]=a[i][0];
                a[i+1][1] = Math.max(a[i][1], a[i+1][1]);
                m--;
            } else continue;
        }
        for(i=n-m; i<n; i++) {
            System.out.println(a[i][0]+" "+a[i][1]);
        }
       sc.close();
   } 
}
