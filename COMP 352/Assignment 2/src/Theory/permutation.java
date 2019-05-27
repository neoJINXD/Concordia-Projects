import java.util.Arrays;
public class permutation{

    //HELPER FUNCTIONS START
    private static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private static void reverse(int[] arr, int i, int j)
	{
		// do till two end-points intersect
		while (i < j) {
			swap(arr, i++, j--);
		}
    }
    
    private static void generateRecursively(int n){
        int[] arr = new int[n];
        for (int i = 0, j = 1; i < n; i++, j++){
            arr[i] = j;
        }
        RecursiveP(arr, n);
    }

    //HELPER FUNCTIONS END


    public static void RecursiveP(int[] arr, int n){
        if (n == 1){
            System.out.println(Arrays.toString(arr));
            return;
        }

        for (int i = 0; i<n; i++){
            swap(arr, i, n-1);
            RecursiveP(arr, n-1);
            swap(arr, i, n-1);
        }
        
    }

    public static void RecursiveHeap(int[] a, int n){
        if (n==1){
            System.out.println(Arrays.toString(a));
            return;
        }
        Heap(a, n-1);
        for (int i = 0; i<n-1;i++){
            if (n%2==0){
                swap(a, i, n-1);
            } else {
                swap(a, 0, n-1);
            }
            Heap(a,n-1);
            }
        
    }

	public static void permutations(int[] s, int n)
	{

		while (true)
		{
			// Print current permutation
			System.out.println(Arrays.toString(s));

			/* Below code will rearrange the string to next lexicographically
			 ordered permutation (if any) or return if we are already at
			 highest possible permutation */

			// Find largest index i such that s[i-1] is less than s[i]
			int i = n - 1;
			while (s[i-1] >= s[i])
			{
				// if i is first index of the string, that means we are 
				// already at last possible permutation 
				// (string is sorted in reverse order)
				if (--i == 0)
					return;
			}

			// find highest index j to the right of index i such that 
			// s[j] > s[i–1] (s[i..n-1] is sorted in reverse order)

			int j = n - 1;
			while (j > i && s[j] <= s[i-1])
				j--;

			// Swap characters at index i-1 with index j
			swap(s, i-1, j);

			// reverse the substring s[i..n-1] and return true
			reverse (s, i, n-1);
		}
	}

    public static void main(String[] args){

        generateRecursively(4);
    }


}