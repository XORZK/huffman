import java.util.Scanner;

public class Main {
  public static void printOps() {
    System.out.println("COMMANDS:\n" + "1. COMPRESS\n" + "2. QUIT");
  }

  public static void main(String[] args) {
    String line = "";
    Scanner sc = new Scanner(System.in);

    boolean quit = false;

    while (!quit) {
      printOps();
      System.out.print("CMD: ");
      line = sc.nextLine().strip().toUpperCase();

      if (line.equals("QUIT") || line.equals("Q")) {
        quit = true;
      } else if (line.equals("COMPRESS")) {
        String fn = "";
        System.out.print("FILE TO BE COMPRESSED: ");
        fn = sc.nextLine();

        HuffmanCoding compression = new HuffmanCoding(fn);
        String out = compression.writeToFile();

        if (!out.equals("")) {
          System.out.println(String.format("Successfully compressed to: %s", out));
        }
      }
    }
    sc.close();
  }
}
