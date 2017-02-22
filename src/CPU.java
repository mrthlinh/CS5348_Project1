import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class CPU {
	/*
	 * PC: Program Counter
	 * SP: Stack Pointer
	 * IR: Instruction Register
	 * AC: Accumulator - temporaty storage
	 * X,Y:General registers
	 */
	final static int timer_interrupt_addr	= 1000;
	final static int int_interrupt_addr 	= 1500;
	final static int system_stack 			= 2000;
	final static int user_stack				= 1000;
	static int kernelMode_flag 				= 0;
	static int debug 						= 0; // debug mode
	static int PC							=0;
	static int SP 							= system_stack; // System stack
	static int IR,AC,X,Y;
	static PrintWriter PW;
	static Scanner SC;

	public static void main (String args[]){
		try{
			String program = args[0];
			debug = Integer.parseInt(args[1]); // for Debug
			// Run the process of Memory
			Runtime rt = Runtime.getRuntime();
//			Process proc = rt.exec("java -cp bin Memory program1");
//			Process proc = rt.exec("java -cp bin Memory program1");
			Process proc = rt.exec("java Memory "+program);
			// InputStream:		read from destination (Memory process)
			// OutputStream:	send to destination (Memory process)
			InputStream is  = proc.getInputStream();
			OutputStream os = proc.getOutputStream();
			
			// Initialize Writer to destination
			PrintWriter pw = new PrintWriter(os);
			
			// Initialize Scanner to read returndata from Memory 
			Scanner sc = new Scanner(is);
			PW = pw;
			SC = sc;
			// Sample one read from CPU
//			int address=3;
//			String cmd = "1 "+address+"\n";
//			pw.printf(cmd);
//			pw.flush();
//			System.out.println(cmd);
////			pw.close();
//			while (sc.hasNextLine()){
//				System.out.println(sc.nextLine());
//			}
//			pw.close();
//			proc.waitFor();
//			OutputStream os1 = proc.getOutputStream();
//			PrintWriter pw1 = new PrintWriter(os);
			
//			address=1;
//			cmd = "1 "+address+"\n";
//			pw.printf(cmd);
//			pw.flush();
//			System.out.println(cmd);
////			pw.close();
//			while (sc.hasNextLine()){
//				System.out.println(sc.nextLine());
//			}
//			
//			proc.waitFor();
//			
//			
//			int exitVal = proc.exitValue();
//
//			System.out.println("Process exited: " + exitVal);
			//------------------------------
			
			
			
			// While Loop to communicate with Memory------------
//			int address = 0;
//			String cmd;
//			int instruction;
			int read_status = 0;
			int exec_status = 0;
			// Read all instruction
			While_Loop:
			while (true){
				
				// Send the command to Memory: Read + Address
//				cmd = "1 "+address+"\n";
//				pw.printf(cmd);
//				pw.flush();				
//				// Read the instruction code returned from Memory
//		        if (sc.hasNext())
//		        {
//		            cmd = sc.next();
//		            if(!cmd.isEmpty())
//		            {
//		            	instruction  = Integer.parseInt(cmd);
//		            	System.out.println(instruction);
//		            	IR = instruction;
//		            	programExecution();
//		            }
//		            address++;
//
//		        }else{
//		        	break;
//		        }
		        //-----------------------------------------------------
//		        read_status = readMemory(pw,sc);
		        read_status = readMemory(PC);
		        if (read_status != -1){
		        	exec_status = programExecution();
		        	if (debug == 1){
		        		System.out.println("--------------------------------");
		        	}
		        	if (exec_status == -1)
		        		// Read the end of program
		        		System.exit(0);
		        }else{
		        	break While_Loop;
		        }        
			}
			
			proc.waitFor();
			
			
			int exitVal = proc.exitValue();

			System.out.println("Process exited: " + exitVal);
//			System.exit(0);
//			//--------------------------------------------------

		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	public static int readMemory(int addr){
		String cmd = "1 "+ addr +"\n";
//		if (debug == 1)
//			System.out.println("Addr: "+ addr); // for debug
		PW.printf(cmd);
		PW.flush();
		
		// Read the instruction code returned from Memory
        if (SC.hasNext())
        {
            cmd = SC.next();
            if(!cmd.isEmpty())
            {
            	IR  = Integer.parseInt(cmd);
            	if (debug == 1)
            		System.out.println("Addr: " + addr + " IR: " + IR); // for debug
            		
//            	PC++;
            	return 0;
            }else{
            	return -1;
            }
        }
        return -1;        
	}
	public static void writeMemory(int addr, int value){
		String cmd = "2 " + addr + " " + value + "\n";
		PW.printf(cmd);
		PW.flush();
	}
	public static int readNextParameter(){
		PC++;
		readMemory(PC);
		return IR;
	}
	
	// Execute the instruction retrieved from Memory and increase PC by 1
//	public static int programExecution(PrintWriter pw, Scanner sc){
	public static int programExecution(){
//		int parameter;
		if (debug == 1)
			System.out.println("ACC: "+AC);	
		switch(IR){
		case 1:
			if (debug == 1)
				System.out.println("1.LOAD VALUE");			
			load_value(readNextParameter());
			break;
		case 2:
			if (debug == 1)
				System.out.println("2.LOAD ADDR");
			load_addr(readNextParameter());
			break;
		case 3:
			if (debug == 1)
				System.out.println("3.LOAD IND ");
			loadInd(readNextParameter());
			break;
		case 4:
			if (debug == 1)
				System.out.println("4.LOAD IDX");	
			loadIdxX(readNextParameter());
			break;
		case 5:
			if (debug == 1)
				System.out.println("5.LOAD IDY");	
			loadIdxY(readNextParameter());
			break;
		case 6:	
			if (debug == 1)
				System.out.println("6.LOAD SPX");	
			loadSpX();
			break;			
		case 7:
			if (debug == 1)
				System.out.println("7.STORE");	
			store(readNextParameter());
			break;
		case 8:
			if (debug == 1)
				System.out.println("8.GET RANDOM");
			get();
			break;
		case 9:	
			if (debug == 1)
				System.out.println("9.PUT");
			put(readNextParameter());
			break;
		case 10:
			if (debug == 1)
				System.out.println("10.ADDX");
			addX();
			break;
		case 11:
			if (debug == 1)
				System.out.println("11.ADDY");
			addY();
			break;
		case 12:
			if (debug == 1)
				System.out.println("12.SUB X");	
			subX();
			break;
		case 13:
			if (debug == 1)
				System.out.println("13. SUB Y");	
			subY();
			break;
		case 14:
			if (debug == 1)
				System.out.println("14.COPY TO X");
			copyToX();
			break;
		case 15:
			if (debug == 1)
				System.out.println("15.COPY FROM X");	
			copyFromX();
			break;
		case 16:
			if (debug == 1)
				System.out.println("16.COPY TO X");
			copyToY();
			break;
		case 17:
			if (debug == 1)
				System.out.println("17.COPY FROM Y");	
			copyFromY();
			break;
		case 18:
			if (debug == 1)
				System.out.println("18.COPY TO SP");	
			copytoSP();
			break;
		case 19:
			if (debug == 1)
				System.out.println("19.COPY FROM SP");	
			copyFromSP();
			break;
		case 20:	
			if (debug == 1)
				System.out.println("20.JUMP");
			jump(readNextParameter());
			return 0;
		case 21:
			if (debug == 1)
				System.out.println("21.JUMP IF EQUAL");
			jumpIfEqual(readNextParameter());
			return 0;
		case 22:
			if (debug == 1)
				System.out.println("22.JUMP IF NOT EQUAL");
			jumpIfNotEqual(readNextParameter());
			return 0;
		case 23:
			if (debug == 1)
				System.out.println("23.CALL");
			call(readNextParameter());
			return 0;
		case 24:
			if (debug == 1)
				System.out.println("24.RET");
			ret();
			return 0;
		case 25:
			if (debug == 1)
				System.out.println("25.INC X");
			incX();
			break;
		case 26:
			if (debug == 1)
				System.out.println("26.DEC X");
			decX();
			break;
		case 27:
			if (debug == 1)
				System.out.println("27.PUSH");
			push(AC);
			break;			
		case 28:
			if (debug == 1)
				System.out.println("28.POP");
			AC = pop();
			break;
		case 29:
			if (debug == 1)
				System.out.println("29.SYSTEM CALL");
			Int();
			return 0;
		case 30:	
			if (debug == 1)
				System.out.println("30.RETURN SYSTEM CALL");
			IRet();
			return 0;
		case 50:	
//			End();
			if (debug == 1)
				System.out.println("END");
			return -1;
		}
		PC ++;
		return 0;
	}
	//	1.Load the value into the AC
	public static void load_value(int value){
		AC = value;		
	}

	//	2.Load the value at the address into the AC
	public static void load_addr(int addr){
		AC = addr;
	}

	//	3.Load the value from the address found in the given address into the AC
	//	(for example, if LoadInd 500, and 500 contains 100, then load from 100).
	public static void loadInd(int addr){
		readMemory(addr);
		AC = IR;
	}

	//	4.Load the value at (address+X) into the AC
	//	(for example, if LoadIdxX 500, and X contains 10, then load from 510).
	public static void loadIdxX(int addr){
		readMemory(addr + X);
		AC = IR;
	}

	//	5. Load the value at (address+Y) into the AC
	public static void loadIdxY(int addr){
		readMemory(addr + Y);
		AC = IR;
	}

	//	6. Load from (Sp+X) into the AC 
	//	(if SP is 990, and X is 1, load from 991).
	public static void loadSpX(){
		readMemory(SP + X);
		AC = IR;
	}

	//	7. Store the value in the AC into the address
	public static void store(int addr){
		writeMemory(addr,AC);
	}

	//	8. Gets a random int from 1 to 100 into the AC
	public static void get(){
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt(100) + 1;
	    AC = randomNum;
	}

	//	9. If port=1, writes AC as an int to the screen
	//	If port=2, writes AC as a char to the screen
	public static void put(int port){
		if (port == 1){
			if (debug == 1)	
				System.out.println("Result is: "+ AC);
			else
				System.out.print(AC);
		}else if (port == 2){
			if (debug == 1)	
				System.out.println("Result is: "+ (char)AC);
			else
				System.out.print((char)AC);
		}
	}

	//	10. Add the value in X to the AC
	public static void addX(){
		AC = AC + X;
	}

	//	11. Add the value in Y to the AC
	public static void addY(){
		AC = AC + Y;
	}

	//	12. Subtract the value in X from the AC
	public static void subX(){
		AC = AC - X;
	}

	//	13. Subtract the value in Y from the AC
	public static void subY(){
		AC = AC - Y;
	}

	//	14. Copy the value in the AC to X
	public static void copyToX(){
		X = AC;
	}

	//	15. Copy the value in X to the AC
	public static void copyFromX(){
		AC = X;
	}

	//	16. Copy the value in the AC to Y
	public static void copyToY(){
		Y = AC;
	}

	//	17. Copy the value in Y to the AC
	public static void copyFromY(){
		AC = Y;
	}

	//	18. Copy the value in AC to the SP
	public static void copytoSP(){
		SP = AC;
	}

	//	19. Copy the value in SP to the AC 
	public static void copyFromSP(){
		AC = SP;
	}

	//	20. Jump to the address
	public static void jump(int addr){
		PC = addr;
	}

	//	21. Jump to the address only if the value in the AC is zero
	public static void jumpIfEqual(int addr){
		PC = (AC == 0) ? addr : PC + 1 ;
	}

	//	22. Jump to the address only if the value in the AC is not zero
	public static void jumpIfNotEqual(int addr){
		PC = (AC != 0) ? addr : PC + 1 ;
	}

	//	23. Push return address onto stack, jump to the address
	public static void call(int addr){
		SP --;
		writeMemory(SP, PC+1); // PC + 1 because there is one value following this instruction
		jump(addr);
	}

	//	24. Pop return address from the stack, jump to the address
	public static void ret(){
		readMemory(SP);
		SP ++;
		jump(IR);
	}

	//	25. Increment the value in X
	public static void incX(){
		X++;
	}

	//	26. Decrement the value in X
	public static void decX(){
		X--;
	}

	//	27. Push AC onto stack
	public static void push(int value){
		SP --;
		writeMemory(SP, value);
	}

	//	28. Pop from stack into AC
	public static int pop(){
		readMemory(SP);
//		AC = IR; // perform in execution
		SP ++;
		return IR;
	}

	//	29. Perform system call
	public static void Int(){
		kernelMode();
		PC = int_interrupt_addr; //1500
	}
	public static void kernelMode(){
		push(PC+1);
		push(AC);
		push(SP);
		push(IR);
		push(X);
		push(Y);
		SP = 2000;
		kernelMode_flag = 1;
		
	}
	//	30. Return from system call
	public static void IRet(){
		Y	= pop();
		X	= pop();
		IR	= pop(); 
		SP	= pop();
		AC	= pop();
		PC	= pop();
		kernelMode_flag = 0;
	}

	//	50. End execution
	public static void End(){
		
//		System.exit(0);
	}
}	
