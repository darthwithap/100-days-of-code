import java.util.*; 

public class CountInversion_16 { 

	private static int mergeCount(int[] a, 
								int l, int m, int r) 
	{ 

		int[] left = Arrays.copyOfRange(a, l, m + 1); 
		int[] right = Arrays.copyOfRange(a, m + 1, r + 1); 

		int i=0, j=0, k=l, count=0; 

		while (i < left.length && j < right.length) 
		{ 
			if (left[i] <= right[j]) 
				a[k++] = left[i++]; 
			else { 
				a[k++] = right[j++]; 
				count += (m+1) - (l+i); 
			} 
		} 
		return count; 
	} 

	private static int merge(int[] a, int l, int r) 
	{ 
		int count = 0; 
		if (l < r) { 
			int m = (l+r)/2; 
			count += merge(a, l, m); 
			count += merge(a, m + 1, r); 
			count += mergeCount(a, l, m, r); 
		} 
		return count; 
	}
	public static void main(String[] args) 
	{ 
		Scanner sc = new Scanner(System.in); 
        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i=0; i<n; i++) a[i] = sc.nextInt();
        System.out.println(merge(a, 0, n-1));
        sc.close();
	} 
} 