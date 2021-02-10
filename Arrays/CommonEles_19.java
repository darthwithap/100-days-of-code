import java.util.*;

public class CommonEles_19 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n1 = sc.nextInt();        
        int n2 = sc.nextInt();        
        int n3 = sc.nextInt();        
        int[] a1 = new int[n1];
        int[] a2 = new int[n2];
        int[] a3 = new int[n3];
        int i=0, j=0, k=0;
        for (i=0; i<n1; i++) a1[i] =sc.nextInt();
        for (i=0; i<n2; i++) a2[i] =sc.nextInt();
        for (i=0; i<n3; i++) a3[i] =sc.nextInt();
        i=0;
        while (i<n1 && j<n2 && k<n3) {
            if (a1[i]==a2[j] && a2[j]==a3[k]) {
                i++;
                j++;
                k++;
                System.out.print(a1[i]+" ");
            }    
            else if(a1[i]<a2[j] && a1[i]<a3[k]) i++;
            else if(a2[j]<a1[i] && a2[j]<a3[k]) j++;
            else if(a3[k]<a1[i] && a3[k]<a2[j]) k++;
        }
        sc.close();
    }
}
