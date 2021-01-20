import java.util.*;

public class NextPerm_15 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int i=0;
        for(i=0; i<n; i++) a[i]=sc.nextInt();
        boolean flag = false;
        for(i=n-1; i>0; i--) {
            if(a[i]>a[i-1]) {
                flag = true;
                break;
            }
        } 
        if (flag) {
            int j=0;
            for (j=n-1; j>0; j--) {
                if (a[j]>a[i-1]) {
                    swap(a, j, i-1);
                    break;
                }
            }
        }
        reverse(a, i, n-1);
        for (i=0; i<n; i++) System.out.print(a[i]+" ");

        sc.close();        
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp; 
    }

    public static void reverse(int[] a, int i, int j) {
        while (i<j) swap(a, i++, j--);
    }
}
