import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StreamingApp {
    public static void main(String[] args) throws IOException {

        final StreamingSystem s = new StreamingSystem();
        byte selection = 0;

        // Loop to show menu until user inputs a valid choice or exits program
        do {
            s.showMenu();
            System.out.println();
            final Scanner input = new Scanner(System.in);

            do { // Loop until we have valid input then decides where to go or to exit program
                System.out.print("Your choice: ");
                try {
                    selection = input.nextByte(); // Grabs user input
                    if (selection > 0 && selection < 6) {
                        break;
                    } else {
                        System.out.println("Please enter a valid integer option!\n");
                        continue; // restart loop, wrong number
                    }
                } catch (final InputMismatchException e) {
                    System.out.println("Your guess must be a valid integer!\n");
                    input.next(); // discard non-Byte input
                    continue; // restart loop, didn't get an integer input
                }
            } while (true);

            s.runMenu(selection);
        } while (selection != 5);

    }
}