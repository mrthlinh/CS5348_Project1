
import java.io.*;
import java.util.Scanner;
import java.lang.Runtime;

public class demo_cpu 
{
	public static void main(String args[])
	{
		try
		{            
			int x;
			Runtime rt = Runtime.getRuntime();

			Process proc = rt.exec("java -cp bin Memory program1");

			InputStream is = proc.getInputStream();
			OutputStream os = proc.getOutputStream();

			PrintWriter pw = new PrintWriter(os);
			int address=5;
			String cmd = "1\n"+address+"\n";
			pw.printf(cmd);
			pw.flush();
			System.out.println(cmd);
			Scanner sc = new Scanner(is);
			while (sc.hasNextLine()){
				System.out.println(sc.nextLine());
			}
			

			proc.waitFor();

			int exitVal = proc.exitValue();

			System.out.println("Process exited: " + exitVal);

		} 
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}

