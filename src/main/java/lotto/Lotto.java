package lotto;

import java.util.List;

public class Lotto {
    private static final Integer LOTTO_COUNT = 6;
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        validateCount(numbers);
        validateDuplicate(numbers);
    }

    private static void validateDuplicate(List<Integer> numbers) {
        for (Integer i : numbers) {
            List<Integer> distinctNumbers = numbers.stream()
                    .distinct()
                    .toList();
            if (numbers.size() != distinctNumbers.size()) {
                throw new IllegalArgumentException(ErrorMessage.LOTTO_NUMBER_DUPLICATE.getMessage());
            }
        }
    }

    private static void validateCount(List<Integer> numbers) {
        if (numbers.size() != LOTTO_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_COUNT.getMessage());
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
