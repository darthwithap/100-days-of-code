import java.util.*;

public class KadanesAlgo_13 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int i=0;
        for (i=0; i<n; i++)
            a[i] = sc.nextInt();
        int currSum=0, maxSum=0;
        for(i=0; i<n; i++) {
            currSum = Math.max(currSum + a[i], a[i]);
            maxSum = Math.max(maxSum, currSum);
        }
        System.out.print(maxSum);
        
        sc.close();
    }
}
