import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author mrthl
 *
 */
public class Memory {
	final static int mem_size = 2000;
	final static int[] mem = new int[mem_size];
	// User code: 0->1000
	final int user_program_topstack = 1000;
	// System code: 1001 -> 2000
	final int system_program_topstack = 2000;

	public static void main (String args[]){
		try{
			// Take the filename at the input
			Scanner CPU_cmd = new Scanner(System.in);
			String filename = args[0];
//			String filename = "sample3.txt";

			// Save the instructions to memory
			saveInstruction(filename);

			// Listen to the CPU command
			//			Scanner CPU_cmd = new Scanner(System.in);
			int num;
			String n;

			// One sample running
			//			if (CPU_cmd.hasNextLine()){
			//				
			//				n = CPU_cmd.nextLine();
			//				String [] j = n.split(" "); 
			//				int opcode = Integer.parseInt(j[0]);
			//				int addr    = Integer.parseInt(j[1]);
			//				if (opcode == 1){
			//					System.out.print(read(addr));					
			//				}		
			//			}
			//----------------------------------------------------

			// while loop running -----------------------------------
			while(true)
			{
				if(CPU_cmd.hasNext())
				{
					n = CPU_cmd.nextLine(); //read the comma delimited line sent by the CPU
					if(!n.isEmpty())
					{
						String [] j = n.split(" "); //split the line to get the necessary tokens

						//  if first token is 1 then CPU is requesting to read 
						//  from an address
						if(j[0].equals("1"))    
						{
							num = Integer.parseInt(j[1]);
							System.out.println(read(num));// send requested data to CPU 
						}

						//  else it will be 2, which means CPu is requesting to 
						//  write data at a particular address
						else if(j[0].equals("2"))   
						{
							int i1 = Integer.parseInt(j[1]);
							int i2 = Integer.parseInt(j[2]);
							write(i1,i2); // invoke the write function
						}
					}
					else 
						break;
				}
				else
					break;
			}

			// ----------------------------------------------------
			//			CPU_cmd.close();
		}catch (Throwable t){
			t.printStackTrace();
		}
	}

	//	Save the instruction to the memory array
	//	throw error if cannot find file
	public static void saveInstruction(String filename){
		try{
			//			Scanner file = new Scanner(new File(filename));
//			Scanner file = new Scanner(new File("src\\"+filename));
			Scanner file = new Scanner(new File(filename));
			int i = 0;
			String st;
			
			while(file.hasNextLine()){
//				System.out.println("Loop: "+i);
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


	//
	public static void write(int addr,int data){
		if (addr >2000){
			throw new Error("Write Failure: Maximum Size is 2000");
		}else{
			mem[addr] = data;
		}
	}

	//
	public static int read(int addr){
		if (addr >2000){
			throw new Error("Read Failure: Maximum Size is 2000");
		}else{
			return mem[addr];
		}
	}
}
