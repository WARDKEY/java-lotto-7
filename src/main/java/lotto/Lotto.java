package lotto;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }

        if (numbers.stream().distinct().toList().size() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되면 안 됩니다.");
        }

        for (Integer number : numbers) {
            if (number == null || String.valueOf(number).isBlank()) {
                throw new IllegalArgumentException("[ERROR] 로또 번호를 입력해주세요.");
            }

            if (number < 0) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 음수를 입력할 수 없습니다.");
            }

            if (number < 1 || number > 45) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1~45 사이의 숫자를 입력해주세요.");
            }

        }

    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
