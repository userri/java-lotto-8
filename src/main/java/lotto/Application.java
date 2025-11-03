package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.*;


public class Application {
    public static int TICKET_PRICE = 1000;

    public static void main(String[] args) {
        int buyPrice;
        buyPrice = getBuyPriceByInput();
        int ticketNumbersOfLottos = buyPrice / TICKET_PRICE;
        LottoManager lottoManager = new LottoManager(ticketNumbersOfLottos);
        lottoManager.printLottos();
        lottoManager.initWinning();

        HashMap<String, Integer> winningResults = new HashMap<>();
        lottoManager.initWinningResults(winningResults);

        lottoManager.countWinner();
        lottoManager.getResults(buyPrice);
    }

    private static int getBuyPriceByInput() {
        int buyPrice;
        while (true) {
            try {
                System.out.println("구입금액을 입력해주세요");
                String buyPriceInput = Console.readLine();
                buyPrice = getBuyPrice(buyPriceInput);
                break;
            } catch (IllegalArgumentException e) {
                Error error = new Error(e.getMessage());
                error.printMessage();
            }
        }
        return buyPrice;
    }

    private static int getBuyPrice(String buyPriceInput) {
        int buyPrice = 0;
        try {
            buyPrice = Integer.parseInt(buyPriceInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("로또구입 금액은 1,000원 단위의 양의 정수여야 합니다.");
        }
        if (buyPrice <= 0) {
            throw new IllegalArgumentException("로또구입 금액은 양수여야 합니다.");
        }
        if (buyPrice % TICKET_PRICE != 0) {
            throw new IllegalArgumentException("로또구입 금액은 1,000원 단위여야 합니다.");
        }
        return buyPrice;
    }

}
