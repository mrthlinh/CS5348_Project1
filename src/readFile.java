import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readFile {
	public static void main(String arg[]) throws FileNotFoundException{
		Scanner file = new Scanner(new File("instruction/program1"));
		while(file.hasNextLine()){
			System.out.println(file.nextInt());
			file.nextLine();
		}
		
	}
	
}
