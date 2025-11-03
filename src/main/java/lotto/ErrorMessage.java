package lotto;

public enum ErrorMessage {

    INVALID_PRICE("로또 구입 금액은 1,000원 단위의 양의 정수여야 합니다."),
    INVALID_PRICE_UNIT("로또 구입 금액은 1,000원 단위의 양의 정수여야 합니다."),
    PRICE_NOT_POSITIVE("로또 구입 금액은 양수여야 합니다."),
    INVALID_LOTTO_COUNT("로또 번호는 6개여야 합니다."),
    LOTTO_NUMBER_DUPLICATE("로또 번호는 중복이 없어야 합니다."),
    INVALID_BONUS_NUMBER("보너스 번호는 1부터 45 사이의 정수여야 합니다."),
    INVALID_BONUS_RANGE("보너스 번호는 1부터 45 사이의 숫자여야 합니다."),
    BONUS_NUMBER_DUPLICATE("보너스 번호는 당첨 번호와 중복되지 않아야 합니다."),
    INVALID_WINNING_NUMBER("당첨 번호는 쉼표로 구분된 6개의 숫자입니다."),
    INVALID_WINNING_NUMBER_COUNT("당첨 번호는 6개의 숫자여야 합니다."),
    INVALID_WINNING_NUMBER_RANGE("당첨 번호는 1과 45사이의 숫자로 이루어져야 합니다."),
    WINNING_NUMBER_DUPLICATE("당첨 숫자는 중복이 없어야 합니다."),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
