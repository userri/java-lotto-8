package lotto;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    // TODO: 게터 지우도록 구현해야함
    public List<Integer> getNumbers() {
        return numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        for (Integer i : numbers) {
            List<Integer> distinctNumbers = numbers.stream()
                    .distinct()
                    .toList();

            if (numbers.size() != distinctNumbers.size()) {
                throw new IllegalArgumentException("당첨 숫자는 중복이 없어야 합니다.");
            }
        }
    }

    // TODO: 추가 기능 구현
}
