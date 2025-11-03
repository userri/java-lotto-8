package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;
import java.util.List;

public class WinningLotto {
    private final List<Integer> winningNumbers;

    public WinningLotto() {
        this.winningNumbers = generateWinningNumbers();
    }

    private List<Integer> generateWinningNumbers() {
        List<Integer> winningNumbers;
        while (true) {
            try {
                winningNumbers = getInputNumbers();
                validate(winningNumbers);
                break;
            } catch (IllegalArgumentException e) {
                Error error = new Error(e.getMessage());
                error.printMessage();
            }
        }
        return winningNumbers;
    }

    private List<Integer> getInputNumbers() {
        List<Integer> winningNumbers;
        System.out.println(Message.INPUT_WINNING_NUMBER);
        String winningNumbersInput = Console.readLine();
        winningNumbers = parseWinningNumbers(winningNumbersInput);
        return winningNumbers;
    }

    private void validate(List<Integer> winningNumbers) {
        validateCountOfNumbers(winningNumbers);
        validateRange(winningNumbers);
        validateDistinct(winningNumbers);
    }

    private List<Integer> parseWinningNumbers(String winningNumbersInput) {
        List<Integer> winningNumbers;
        try {
            winningNumbers = Arrays.stream(winningNumbersInput.split(","))
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed().toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_WINNING_NUMBER.getMessage());
        }
        return winningNumbers;
    }

    private void validateCountOfNumbers(List<Integer> winningNumbers) {
        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_WINNING_NUMBER_COUNT.getMessage());
        }
    }

    private void validateRange(List<Integer> winningNumbers) {
        for (Integer i : winningNumbers) {
            if (i < 1 | i > 45) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_WINNING_NUMBER_RANGE.getMessage());
            }
        }
    }

    private static void validateDistinct(List<Integer> winningNumbers) {
        List<Integer> distinctWinningNumbers = winningNumbers.stream()
                .distinct()
                .toList();
        if (winningNumbers.size() != distinctWinningNumbers.size()) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBER_DUPLICATE.getMessage());
        }
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }
}
