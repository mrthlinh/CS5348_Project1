import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author mrthl
 *
 */
public class Memory {
	
	final static int mem_size = 2000;
	final static int[] mem = new int[mem_size];	// Initialize Memory
	final int user_program_topstack = 1000;		// User code: 0->999
	final int system_program_topstack = 2000;	// System code: 1000 -> 1999

	public static void main (String args[]){
		try{
			
			Scanner CPU_cmd = new Scanner(System.in); 
			String filename = args[0];			// Take the filename at the input			
			saveInstruction(filename);			// Save the instructions to memory
			
			int num;
			String st;

			// Communicate with processor	---------------------------------------------
			while(true)
			{
				if(CPU_cmd.hasNext())
				{
					st = CPU_cmd.nextLine(); 		// Read the space delimited line sent by the CPU
					
					if(st.length() != 0)
					{
						String [] cmd = st.split(" "); // Split the line to get the necessary tokens

						//  First token: 1 -> read
						if(cmd[0].equals("1"))    
						{
							num = Integer.parseInt(cmd[1]);
							System.out.println(read(num));	// send requested data back to CPU 
						}

						//  First token: 2 -> write
						else if(cmd[0].equals("2"))   
						{
							int i1 = Integer.parseInt(cmd[1]);
							int i2 = Integer.parseInt(cmd[2]);
							write(i1,i2);					// write value to specific address
						}
						else if(cmd[0].equals("3")) 
						{
							CPU_cmd.close();
							System.exit(0);
						}
					}
					else 
						break;
				}
				else
					break;
			}
			// ----------------------------------------------------------------------------------
			CPU_cmd.close();
		}catch (Throwable t){
			t.printStackTrace();
		}
	}

	//	Save the instruction to Memory
	public static void saveInstruction(String filename){
		try{
//			Scanner file = new Scanner(new File("src\\"+filename)); // for running in Eclipse
			Scanner file = new Scanner(new File(filename));			// run in UNIX or Command Line
			int i = 0;
			String st;
			
			while(file.hasNextLine()){
				if (file.hasNextInt()){
					mem[i] = file.nextInt();
					file.nextLine();
					i++;
				}else{
					try{
						st = file.next();
						if (st.charAt(0) == '.')
							i = Integer.parseInt(st.substring(1));
						else
							file.nextLine();
					}catch(Throwable e){
					}

				}
			}
			file.close();
		}catch(FileNotFoundException e){
		}
	}

	// Write data to given address
	public static void write(int addr,int data){
		if (addr >=2000){
			throw new Error("Write Failure: Maximum Size is 2000");
		}else{
			mem[addr] = data;
		}
	}

	// Write data to given address
	public static int read(int addr){
		if (addr >=2000){
			throw new Error("Read Failure: Maximum Size is 2000");
		}else{
			return mem[addr];
		}
	}
}
