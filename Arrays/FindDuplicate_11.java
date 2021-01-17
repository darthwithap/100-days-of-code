import java.util.*;

public class FindDuplicate_11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int i=0;
        for(i=0; i<n; i++) a[i] = sc.nextInt();
        int sum=0;
        for (i=0; i<n; i++) sum+=a[i];
        System.out.print(sum - (n*(n-1))/2);
        sc.close();
    }
}
