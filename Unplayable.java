public class Unplayable extends RuntimeException {
    Unplayable() {
        super();
    }

    // Prints exception method for invalid inputs
    Unplayable(String exceptionMessage) {
        super(exceptionMessage);
    }
}
