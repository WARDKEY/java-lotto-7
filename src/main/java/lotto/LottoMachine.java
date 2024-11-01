package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LottoMachine {

    private Lotto lotto;
    private int cost;
    private int bonusNumber;
    private final List<List<Integer>> lottoNumbers = new ArrayList<>();

    public void run() {
        System.out.println("구입금액을 입력해 주세요.");

        requestCostInput();

        int purchaseCount = cost / 1000;

        System.out.println();

        System.out.println(purchaseCount + "개를 구매했습니다.");

        for (int i = 0; i < purchaseCount; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            Collections.sort(numbers);
            lottoNumbers.add(numbers);
            System.out.println(lottoNumbers.get(i));
        }

        System.out.println();

        System.out.println("당첨 번호를 입력해 주세요.");

        lotto = new Lotto(Arrays.stream(Console.readLine().trim().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList()));

        System.out.println();

        System.out.println("보너스 번호를 입력해주세요.");

        bonusNumber = Integer.parseInt(Console.readLine().trim());

        System.out.println();

        System.out.println("당첨 통계");
        System.out.println("---");

        int threeMatched = 0;
        int fourMatched = 0;
        int fiveMatched = 0;
        int bonusMatched = 0;
        int allMatched = 0;

        for (List<Integer> lottoNumber : lottoNumbers) {
            int lottoCount = 0;
            boolean isBonusMatched = lottoNumber.contains(bonusNumber);

            for (int number : lotto.getNumbers()) {
                if (lottoNumber.contains(number)) {
                    lottoCount++;
                }
            }

            if (lottoCount == 6) {
                allMatched++;
                continue;
            }
            if (lottoCount == 5 && isBonusMatched) {
                bonusMatched++;
                continue;
            }
            if (lottoCount == 5) {
                fiveMatched++;
                continue;
            }
            if (lottoCount == 4) {
                fourMatched++;
                continue;
            }
            if (lottoCount == 3) {
                threeMatched++;
            }
        }

        System.out.println("3개 일치 (5,000원) - " + threeMatched + "개");
        System.out.println("4개 일치 (50,000원) - " + fourMatched + "개");
        System.out.println("5개 일치 (1,500,000원) - " + fiveMatched + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - " + bonusMatched + "개");
        System.out.println("6개 일치 (2,000,000,000원) - " + allMatched + "개");

        int totalWinnings = (5000 * threeMatched) + (50000 * fourMatched) +
                (1500000 * fiveMatched) + (30000000 * bonusMatched) +
                (2000000000 * allMatched);

        double earningRatio = ((double) totalWinnings / cost) * 100;
        String formatEarningRatio = String.format("%,.1f", earningRatio);
        System.out.println("총 수익률은 " + formatEarningRatio + "%입니다.");

    }

    // 금액 입력
    private void requestCostInput() {
        try {
            cost = Integer.parseInt(Console.readLine().trim());

            if (cost % 1000 != 0) {
                throw new IllegalArgumentException("[ERROR] 금액은 1000원 단위로 입력해주세요.");
            }

            if (cost < 0) {
                throw new IllegalArgumentException("[ERROR] 금액은 음수를 입력할 수 없습니다.");
            }

            if (cost == 0 || String.valueOf(cost).isBlank()) {
                throw new IllegalArgumentException("[ERROR] 금액을 입력해주세요.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] 금액은 숫자만 입력할 수 있습니다.");
            requestCostInput();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            requestCostInput();
        }
    }
}
