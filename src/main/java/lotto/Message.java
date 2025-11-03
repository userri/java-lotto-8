package lotto;

public enum Message {

    FIRST("1등"),
    SECOND("2등"),
    THIRD("3등"),
    FOURTH("4등"),
    FIFTH("5등");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
