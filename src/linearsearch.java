
public class linearsearch {
	public static void main(String args[]){
		int[] arr = {0,4,1,5,3,12,23,5,19,20};
		int size = 10;
		int i = 0;
		int count = 0;
		for (i=0; i<size;i++){
			if (arr[i] == 5)
				count++;
		}
		
		System.out.println(count);
		
		
	}
}
