public class RuntimeDemo {

   public static void main(String[] args) {
   try {

   // print a message
   System.out.println("Executing notepad.exe");

   // create a process and execute notepad.exe
   Process process = Runtime.getRuntime().exec("notepad.exe");

   // print another message
   System.out.println("Notepad should now open.");

   } catch (Exception ex) {
   ex.printStackTrace();
   }

   }
}