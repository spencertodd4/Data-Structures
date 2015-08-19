public class LTest {
	public static void main (String [] args) {
		
		LList nums = new LList ();
		
		nums.sortedInsert(12);
		nums.sortedInsert(11);
		nums.sortedInsert(25);
		nums.sortedInsert(44);
		nums.sortedInsert(4);
		nums.sortedInsert(13);
		nums.sortedInsert(13);
		
		System.out.println(nums);
		
		nums.reverse();
		
		System.out.println(nums);

		nums.delete(nums.getTail());
		nums.delete(12);
		
		System.out.println(nums);
		
	}
}



