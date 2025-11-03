package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class LottoManager {
    private List<Lotto> lottos;
    private WinningLotto winningLotto = null;

    private Integer bonusNumber = 0;

    private Map<String, Integer> winningResults = null;

    public LottoManager(int ticketNumberOfLottos) {
        this.lottos = generateLottos(ticketNumberOfLottos);
    }

    private List<Lotto> generateLottos(int ticketNumbersOfLottos) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < ticketNumbersOfLottos; i++) {
            List<Integer> lottoNumbers = generateLottoNumbers();
            lottoNumbers = lottoNumbers.stream().sorted().toList();
            Lotto lotto = new Lotto(lottoNumbers);
            lottos.add(lotto);
        }
        System.out.println();
        System.out.println(ticketNumbersOfLottos + "개를 구매했습니다.");
        return lottos;
    }

    private List<Integer> generateLottoNumbers() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6);
    }

    public void printLottos() {
        for (Lotto i : lottos) {
            System.out.println(i.getNumbers().stream().sorted().toList());
        }
    }

    public void initWinning() {
        this.winningLotto = new WinningLotto();
        initBonusNumber();
    }

    private void initBonusNumber() {
        String bonusNumberInput;
        while (true) {
            try {
                System.out.println("보너스 번호를 입력해주세요");
                bonusNumberInput = Console.readLine();
                bonusNumber = parseBonusNumber(bonusNumberInput);
                validateBonusNumber();
                break;
            } catch (IllegalArgumentException e) {
                Error error = new Error(e.getMessage());
                error.printMessage();
            }
        }
    }

    private void validateBonusNumber() {
        validateRangeOfBonusNumber(bonusNumber);
        validateDistinctWithWinningNumbers(this.winningLotto.getWinningNumbers(), bonusNumber);
    }

    private static int parseBonusNumber(String bonusNumberInput) {
        int bonusNumber;
        try {
            bonusNumber = Integer.parseInt(bonusNumberInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("보너스 번호는 1부터 45 사이의 정수여야 합니다.");
        }
        return bonusNumber;
    }

    private static void validateRangeOfBonusNumber(int bonusNumber) {
        if (bonusNumber < 1 | bonusNumber > 45) {
            throw new IllegalArgumentException("보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private static void validateDistinctWithWinningNumbers(List<Integer> winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복되지 않아야 합니다.");
        }
    }

    public void initWinningResults(HashMap<String, Integer> winningResults) {
        this.winningResults = new HashMap<>();
        winningResults.put("1등", 0);
        winningResults.put(Message.SECOND.getMessage(), 0);
        winningResults.put("3등", 0);
        winningResults.put(Message.FOURTH.getMessage(), 0);
        winningResults.put(Message.FIFTH.getMessage(), 0);
    }

    public void countWinner() {
        List<Integer> intersection;

        for (Lotto lotto : lottos) {
            intersection = lotto.getNumbers().stream()
                    .filter(l -> winningLotto.getWinningNumbers().stream()
                            .anyMatch(Predicate.isEqual(l)))
                    .toList();
            addResult(lotto, intersection);
        }
    }

    private void addResult(Lotto i, List<Integer> intersection) {
        if (intersection.size() == 3) {
            winningResults.put(Message.FIFTH.getMessage(), winningResults.get(Message.FIFTH.getMessage()) + 1);
        }
        if (intersection.size() == 4) {
            winningResults.put(Message.FOURTH.getMessage(), winningResults.get(Message.FOURTH.getMessage()) + 1);
        }
        if (intersection.size() == 5 && !i.getNumbers().contains(bonusNumber)) {
            winningResults.put("3등", winningResults.get("3등") + 1);
        }
        if (intersection.size() == 5 && i.getNumbers().contains(bonusNumber)) {
            winningResults.put(Message.SECOND.getMessage(), winningResults.get(Message.SECOND.getMessage()) + 1);
        }
        if (intersection.size() == 6) {
            winningResults.put("1등", winningResults.get("1등") + 1);
        }
    }

    public void getResults(Integer buyPrice) {
        printMatchingResults();

        int totalPrize = getTotalPrize();

        printProfitRate(buyPrice, totalPrize);
    }

    private static void printProfitRate(Integer buyPrice, double totalPrize) {
        double profitRate = totalPrize / buyPrice * 100;
        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);
    }

    private void printMatchingResults() {
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.print("3개 일치 (5,000원) - ");
        System.out.println(winningResults.get(Message.FIFTH.getMessage()) + "개");
        System.out.print("4개 일치 (50,000원) - ");
        System.out.println(winningResults.get(Message.FOURTH.getMessage()) + "개");
        System.out.print("5개 일치 (1,500,000원) - ");
        System.out.println(winningResults.get(Message.THIRD.getMessage()) + "개");
        System.out.print("5개 일치, 보너스 볼 일치 (30,000,000원) - ");
        System.out.println(winningResults.get(Message.SECOND.getMessage()) + "개");
        System.out.print("6개 일치 (2,000,000,000원) - ");
        System.out.println(winningResults.get(Message.FIRST.getMessage()) + "개");
    }

    private int getTotalPrize() {
        int totalPrize = 0;
        totalPrize = winningResults.get(Message.FIFTH.getMessage()) * 5000
                + winningResults.get(Message.FOURTH.getMessage()) * 50000
                + winningResults.get(Message.THIRD.getMessage()) * 1500000
                + winningResults.get(Message.SECOND.getMessage()) * 30000000
                + winningResults.get(Message.FIRST.getMessage()) * 2000000000;
        return totalPrize;
    }

}
