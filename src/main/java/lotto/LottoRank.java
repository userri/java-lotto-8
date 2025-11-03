package lotto;

public enum LottoRank {
    RANK1("1등", 2000000000, 6),
    RANK2("3등", 30000000, 5),
    RANK3("3등", 1500000, 5),
    RANK4("4등", 50000, 4),
    RANK5("5등", 5000, 3)
    ;
    private String message;
    private Integer prize;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }

    LottoRank(String message, Integer prize, Integer count) {
        this.message = message;
        this.prize = prize;
        this.count = count;
    }
}
