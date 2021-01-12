import java.util.*;

public class Sort012_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int a[] = new int[n];
        int i, z=0, o=0, t=0;

        for (i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            if (a[i] == 0) z++;
            else if (a[i] == 1) o++;
            else if (a[i] ==2) t++;
        }

        for (i=0; i<z; i++) a[i] = 0;
        for (i=z; i<z+o; i++) a[i] = 1;
        for (i=z+o; i<n; i++) a[i] = 2;

        System.out.println(Arrays.toString(a));
    }
}