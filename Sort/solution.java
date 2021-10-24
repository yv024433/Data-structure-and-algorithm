import CITS2200.Sort;
import java.util.Arrays;

public class Sorter implements Sort
{
   private int count;
   
	public int getCount(){
		return count;
	}	
		
	public void reset(){
		count = 0;
	}
	
	public void insertionSort(long[] a)
    	{
			int n = a.length;
    		for (int j = 1; j<n; j++) 
    		{
    			long key = a[j];
    			int i = j-1;
    		
    			while (i>=0 && a[i] > key)
    			{
    				a[i+1] = a[i];
    				i = i-1;
    				count++;
    			}
    			a[i+1] = key;
    		}
		}

	
	public void quickSort(long[] a){
		quickSort(a, 0, a.length-1);		
	}
	
	private void quickSort(long[] a, int p, int r) {
		if (p<r) {
			int pi = partition(a, p, r);
			quickSort(a, p, pi-1);
			quickSort(a, pi+1, r);
		}
	}
	
	private int partition(long[] a, int p, int r) {
		long pivot = a[r];
		int low = (p-1);
		for (int j = p; j<=(r-1); j++) {
			if (a[j] <= pivot) {
				low++;
				long temp = a[low];
				a[low] = a[j];
				a[j] = temp;
			}
			count++;
		}
		
		long temp = a[low+1];
		a[low+1] = a[r];
		a[r] = temp;

		
		return low+1;
	}
	
	public void mergeSort(long[] a){
	mergeSort(a, 0, a.length-1);
	}
	
	private void merge(long[] a, int p, int q, int r)
	{
	int n = q-p+1;
	int m = r-q;
	long[] an = new long[n];
	long[] am = new long[m];
	for(int i = 0; i<n; i++) {
	an[i] = a[p+i];
	count++;
	}
	for(int i = 0; i<m; i++){
	am[i] = a[q+i+1];
	count++;
	}
	int i = 0;
	int j = 0;
	for(int k = p; k<=r; k++){
	if(i==n) a[k] = am[j++];
	else if(j==m || an[i]<am[j]) a[k] = an[i++];
	else a[k] = am[j++];
	count++;
	}
	}
	
	private void mergeSort(long[] a, int p, int r)
	{
	if(p<r){
	int i = (p+r)/2;
	mergeSort(a,p,i);
	mergeSort(a,i+1,r);
	merge(a, p,i,r);
	}
	}
	


  
  }