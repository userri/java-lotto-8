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
        System.out.println("당첨 번호를 입력해 주세요.");
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
            throw new IllegalArgumentException("쉼표로 구분된 6개의 양의 정수를 입력해주세요.");
        }
        return winningNumbers;
    }

    private void validateCountOfNumbers(List<Integer> winningNumbers) {
        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException("당첨 번호는 6개의 숫자여야 합니다.");
        }
    }

    private void validateRange(List<Integer> winningNumbers) {
        for (Integer i : winningNumbers) {
            if (i < 1 | i > 45) {
                throw new IllegalArgumentException("당첨 번호는 1과 45사이의 숫자로 이루어져야 합니다.");
            }
        }
    }

    private static void validateDistinct(List<Integer> winningNumbers) {
        List<Integer> distinctWinningNumbers = winningNumbers.stream()
                .distinct()
                .toList();

        if (winningNumbers.size() != distinctWinningNumbers.size()) {
            throw new IllegalArgumentException("당첨 숫자는 중복이 없어야 합니다.");
        }
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }
}
