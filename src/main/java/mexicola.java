import java.util.*;
public class mexicola {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.println("Hello! I'm Mexicola");
        System.out.println("What can I do for you?");
        System.out.println(line);
        Scanner sc = new Scanner(System.in);
        while (true) {
            String userInput = sc.nextLine();

            // Check for exit condition
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            // Echo the command
            System.out.println(line);
            System.out.println(" " + userInput);
            System.out.println(line);
        }
    }
}
