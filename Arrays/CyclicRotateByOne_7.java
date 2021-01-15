import java.util.*;

public class CyclicRotateByOne_7 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];

        for(int i=0; i<n; i++)
            a[i] = sc.nextInt();
        
        int t = a[n-1];

        for(int i=n-1; i>0; i--) {
            a[i] = a[i-1];
        }

        a[0] = t;
        
        for (int i=0; i<n; ++i)
            System.out.print(a[i]+" ");

        sc.close();
        
    }
}
