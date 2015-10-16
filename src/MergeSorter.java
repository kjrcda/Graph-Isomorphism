
public class MergeSorter 
{
	private int[] numbers = null;
	private int[] temp = null;
	private int length = 0;
	
	public MergeSorter() {}
	
	public MergeSorter(int[] nums)
	{
		set(nums);
	}
	
	public void set(int [] nums)
	{
		length = nums.length;
		numbers = nums;
		temp = new int[length];
	}
	public int[] mergeSort()
	{
		if(numbers == null)
			return null;
		mergeSort(0, length - 1);
		
		int retVal[] = numbers;
		numbers = null;
		temp = null;
		length = 0;
		
		return retVal;
	}
	
	private void mergeSort(int left, int right)
	{
		if( left < right )
		{
			int middle = (left+right) / 2;
			mergeSort(left, middle);
			mergeSort(middle+1, right);
			merge(left, middle+1, right);
		}
	}
	
	private void merge(int left, int right, int end)
	{
		int start = right-1;
        int k = left;
        int num = end - left+1;

        while(left <= start && right <= end)
            if(numbers[left] < numbers[right])
            	temp[k++] = numbers[left++];
            else
            	temp[k++] = numbers[right++];

        while(left <= start)    // Copy rest of first half
        	temp[k++] = numbers[left++];

        while(right <= end)  // Copy rest of right half
        	temp[k++] = numbers[right++];

        // Copy temp back
        for(int i = 0; i < num; i++, end--)
        	numbers[end] = temp[end];
	}
}
