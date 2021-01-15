import java.util.*;

public class UnionIntersection_6 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int i=0, j=0;

        Set<Integer> set = new HashSet<Integer>();
        
        for (i=0; i<n; i++) 
            set.add(sc.nextInt());
        for (j=0; j<m; j++) 
            set.add(sc.nextInt());
        System.out.println(set.toString());
        sc.close();

    }
}
