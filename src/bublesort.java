
public class bublesort {
	public static void main(String args[]){
		int[] list = {10,9,8,7,6,5,4,3,2,1,0};
		arrayprint(list);
		int temp = 0;
		for (int i = 0; i< list.length; i++){
			for (int j = 0; j<list.length - 1; j++){
				if (list[j] > list[j+1]){
					temp = list[j+1];
					list[j+1]	= list[j];
					list[j]		= temp;
				}
			}
		}
		arrayprint(list);
	}
	public static void arrayprint(int[] arr){
		for (int i = 0; i<arr.length; i++){
			System.out.print(arr[i] +" ");
		}
		System.out.println();
	}
}
