import java.io.*;
import java.lang.Runtime;

public class Proc 
{
	public static void main(String args[])
	{
		try
		{            
			int x;

			Runtime rt = Runtime.getRuntime();

			Process proc = rt.exec("java -cp bin Hello");

			//			   String current = new java.io.File( "." ).getCanonicalPath();       
			//			   System.out.println("Current dir:"+current);
			//Process proc = rt.exec("cat hello.java");

			InputStream is = proc.getInputStream();
			OutputStream os = proc.getOutputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null){
				System.out.println(line); 
			}

			reader.close();
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

