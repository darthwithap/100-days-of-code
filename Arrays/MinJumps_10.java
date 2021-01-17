import java.util.*;

public class MinJumps_10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a =new int[n];
        int i=0;
        for(i=0; i<n; i++) a[i] = sc.nextInt();
        int count=0, sum=0;
        while(sum<n-1) {
            sum+=a[sum];
            count++;
        }
        System.out.print(count);
        sc.close();
    }
}
