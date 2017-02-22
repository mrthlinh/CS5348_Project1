public class Foo {
   public static void main(String[] args) {
      if (args.length < 2) {
         System.out.println("Write your name and a site you like when you run this class");
      } else {
         System.out.println("Yo " + args[0] + ", so you like " + args[1] + "?");
      }
   }
}