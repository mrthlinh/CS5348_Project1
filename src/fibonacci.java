
public class fibonacci {
	public static void main(String args[]){
		int temp1 = 0;
		int temp2 = 1;
		int sum = 0;
		System.out.print(temp1+" ");
		System.out.print(temp2+" ");
		for (int i = 0; i< 20; i++){
			sum 	= temp1 + temp2;
			System.out.print(sum+" ");
			temp1 = temp2;
			temp2 = sum;
		}
	}
}
