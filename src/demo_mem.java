import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class demo_mem {
	   public static void main(String args[]) throws FileNotFoundException
	   {
		   if(args.length < 1)
			{
				System.err.println("Not enough arguments: Need input program");
				System.exit(1);
			}
			String inputPath = args[0];
			Scanner file = new Scanner(new File("instruction\\"+inputPath));
			
			System.out.println(file.nextLine());
	      Scanner sc = new Scanner(System.in);
	      int[] memory={1,72,9,2,1,73,9,2};
	      int name;
	      
//	      System.out.print(sc.nextLine());
	      if (sc.hasNextLine()){
	    	  name = Integer.parseInt(sc.nextLine());
	    	  if (name == 1){
	    		  name = Integer.parseInt(sc.nextLine());
	    		  System.out.print(memory[name]);
	    	  }
	      }	      
	   }

}
