package lotto;

public class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println("[ERROR] " + message);
    }
}
