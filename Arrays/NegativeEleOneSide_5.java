import java.util.*;

class NegativeEleOneSide_5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        int a[] = new int[n];
        int i, j=0;

        for(i=0; i<n; i++)
            a[i] = sc.nextInt();
        for(i=0; i<n; i++) {
            if (a[i]<0) {
                if (j>0) {
                    a[i] += a[j];
                    a[j] = a[i] - a[j];
                    a[i] -= a[j];
                }
                j++;
            }
        }
        for (i=0; i<n; i++) 
            System.out.print(a[i] + " ");
        sc.close();
    }
}