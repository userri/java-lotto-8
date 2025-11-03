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

        lottoManager.initWinningResults();

        lottoManager.countWinner();
        lottoManager.getResults(buyPrice);
    }

    private static int getBuyPriceByInput() {
        int buyPrice;
        while (true) {
            try {
                System.out.println(Message.INPUT_BUY_PRICE);
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
            throw new IllegalArgumentException(ErrorMessage.INVALID_PRICE.getMessage());
        }
        if (buyPrice <= 0) {
            throw new IllegalArgumentException(ErrorMessage.PRICE_NOT_POSITIVE.getMessage());
        }
        if (buyPrice % TICKET_PRICE != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PRICE_UNIT.getMessage());
        }
        return buyPrice;
    }

}
