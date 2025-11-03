package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;


public class Application {
    public static int TICKET_PRICE = 1000;
    public static int NUMBERS_COUNT_IN_LOTTO = 6;
    public static int ERROR_ITR = 100;

    public static void main(String[] args) {
        int buyPrice = 0;
        int errorCount = 0;
        while (true) {
            try {
                System.out.println("구입금액을 입력해주세요");
                String buyPriceString = Console.readLine();
                buyPrice = getBuyPrice(buyPriceString);
                break;
            } catch (IllegalArgumentException e) {
                Error error = new Error(e.getMessage());
                error.printMessage();
                errorCount++;
                if (errorCount > ERROR_ITR) {
                    break;
                }
            }
        }
        int ticketNumbersOfLottos = buyPrice / TICKET_PRICE;
        List<Lotto> lottos = generateLottos(ticketNumbersOfLottos);
        System.out.println();
        System.out.println(ticketNumbersOfLottos + "개를 구매했습니다.");
        printLottos(lottos);

        List<Integer> winningNumbers = new ArrayList<>();
        errorCount = 0;
        while (true) {
            try {
                System.out.println("당첨 번호를 입력해 주세요.");
                String winningNumbersString = Console.readLine();
                winningNumbers = getWinningNumbers(winningNumbersString);
                validateCountOfWinningNumbers(winningNumbers);
                validateRangeOfWinningNumbers(winningNumbers);
                validateDistinctWinningNumbers(winningNumbers);
                break;
            } catch (IllegalArgumentException e) {
                Error error = new Error(e.getMessage());
                error.printMessage();
                errorCount++;
                if (errorCount > ERROR_ITR) {
                    break;
                }
            }
        }


        String bonusNumberString;
        int bonusNumber = 0;
        errorCount = 0;
        while (true) {
            try {
                System.out.println("보너스 번호를 입력해주세요");
                bonusNumberString = Console.readLine();
                bonusNumber = getBonusNumber(bonusNumberString);
                validateRangeOfBonusNumber(bonusNumber);
                validateDistinctWithWinningNumbers(winningNumbers, bonusNumber);
                break;
            } catch (IllegalArgumentException e) {
                Error error = new Error(e.getMessage());
                error.printMessage();
                errorCount++;
                if (errorCount > ERROR_ITR) {
                    break;
                }
            }
        }


        HashMap<String, Integer> winningResults = new HashMap<>();
        winningResults.put("1등", 0);
        winningResults.put("2등", 0);
        winningResults.put("3등", 0);
        winningResults.put("4등", 0);
        winningResults.put("5등", 0);

        // 당첨 통계라는 하나의 메서드로 묶기
        List<Integer> intersectionList;

        for (Lotto i : lottos) {
            intersectionList = new ArrayList<>();
            // TODO 주석지우기
//            System.out.println(i.getNumbers());
            for (Integer lottoNumber : i.getNumbers()) {
                for (Integer winningNumber : winningNumbers) {
                    if (lottoNumber.equals(winningNumber)) {
                        intersectionList.add(lottoNumber);
                        // TODO 주석지우기
//                        System.out.println(lottoNumber);
                    }
                }
            }
            if (intersectionList.size() == 3) {
                winningResults.put("5등", winningResults.get("5등") + 1);
            }
            if (intersectionList.size() == 4) {
                winningResults.put("4등", winningResults.get("4등") + 1);
            }
            if (intersectionList.size() == 5 && !i.getNumbers().contains(bonusNumber)) {
                winningResults.put("3등", winningResults.get("3등") + 1);
            }
            if (intersectionList.size() == 5 && i.getNumbers().contains(bonusNumber)) {
                winningResults.put("2등", winningResults.get("2등") + 1);
            }
            if (intersectionList.size() == 6) {
                winningResults.put("1등", winningResults.get("1등") + 1);
            }
        }

        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.print("3개 일치 (5,000원) - ");
        System.out.println(winningResults.get("5등") + "개");
        System.out.print("4개 일치 (50,000원) - ");
        System.out.println(winningResults.get("4등") + "개");
        System.out.print("5개 일치 (1,500,000원) - ");
        System.out.println(winningResults.get("3등") + "개");
        System.out.print("5개 일치, 보너스 볼 일치 (30,000,000원) - ");
        System.out.println(winningResults.get("2등") + "개");
        System.out.print("6개 일치 (2,000,000,000원) - ");
        System.out.println(winningResults.get("1등") + "개");

        int totalPrize = 0;
        totalPrize = winningResults.get("5등") * 5000
                + winningResults.get("4등") * 50000
                + winningResults.get("3등") * 1500000
                + winningResults.get("2등") * 30000000
                + winningResults.get("1등") * 2000000000;

        double profitRate = (double) totalPrize / buyPrice * 100;
        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);

    }

    private static int getBonusNumber(String bonusNumberString) {
        int bonusNumber;
        try {
            bonusNumber = Integer.parseInt(bonusNumberString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("보너스 번호는 1부터 45 사이의 정수여야 합니다.");
        }
        return bonusNumber;
    }

    private static void validateDistinctWithWinningNumbers(List<Integer> winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복되지 않아야 합니다.");
        }
    }

    private static void validateRangeOfBonusNumber(int bonusNumber) {
        if (bonusNumber < 1 | bonusNumber > 45) {
            throw new IllegalArgumentException("보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private static void validateRangeOfWinningNumbers(List<Integer> winningNumbers) {
        for (Integer i : winningNumbers) {
            if (i < 1 | i > 45) {
                throw new IllegalArgumentException("당첨 번호는 1과 45사이의 숫자로 이루어져야 합니다.");
            }
        }
    }


    private static List<Integer> getWinningNumbers(String winningNumbersString) {
        List<Integer> winningNumbers;
        try {
            winningNumbers = Arrays.stream(winningNumbersString.split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed().toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("쉼표로 구분된 6개의 양의 정수를 입력해주세요.");
        }
        return winningNumbers;
    }

    private static void validateCountOfWinningNumbers(List<Integer> winningNumbers) {
        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException("당첨 번호는 6개의 숫자여야 합니다.");
        }
    }

    private static int getBuyPrice(String buyPriceString) {
        int buyPrice = 0;
        try {
            buyPrice = Integer.parseInt(buyPriceString);
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

    private static void printLottos(List<Lotto> lottos) {
        for (Lotto i : lottos) {
            System.out.println(i.getNumbers().stream().sorted().toList());
        }
    }

    private static void validatePositiveWinningNumbers(List<Integer> winningNumbers) {
        for (int i : winningNumbers) {
            if (i <= 0) {
                throw new IllegalArgumentException("당첨 번호는 양의 정수여야 합니다.");
            }
        }
    }

    // 로또 클래스 안에 넣음
    private static void validateDistinctWinningNumbers(List<Integer> winningNumbers) {
        List<Integer> distinctWinningNumbers = winningNumbers.stream()
                .distinct()
                .toList();

        if (winningNumbers.size() != distinctWinningNumbers.size()) {
            throw new IllegalArgumentException("당첨 숫자는 중복이 없어야 합니다.");
        }
    }

    private static List<Lotto> generateLottos(int ticketNumbersOfLottos) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < ticketNumbersOfLottos; i++) {
            List<Integer> lottoNumbers = generateLottoNumbers();
            // 정렬 후 보내야 함
            lottoNumbers = lottoNumbers.stream().sorted().toList();
            Lotto lotto = new Lotto(lottoNumbers);
            lottos.add(lotto);
        }
        return lottos;
    }

    private static List<Integer> generateLottoNumbers() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6);
    }
}
