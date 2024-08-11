package lotto;

import java.util.*;
import java.util.stream.Stream;

public class Application {

    static Scanner sc = new Scanner(System.in);
    static List<NavigableSet<Integer>> manualLottos = new ArrayList<>();
    static List<NavigableSet<Integer>> autoLottos = new ArrayList<>();
    static List<NavigableSet<Integer>> totalLottos = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("구입 금액을 입력해 주세요.");
        int money = sc.nextInt();
        int totalLottoCount = money / 1000;

        System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");
        int manualLottoCount = sc.nextInt();

        System.out.println("수동으로 구매할 번호를 입력해 주세요.");
        sc.nextLine();
        for (int i = 0; i < manualLottoCount; i++) {
            String input = sc.nextLine();
            String[] splitInput = input.split(",");
            NavigableSet<Integer> integerList = new TreeSet<>(Stream.of(splitInput).map(Integer::parseInt).toList());
            manualLottos.add(integerList);
        }

        int autoLottoCount = totalLottoCount - manualLottoCount;
        System.out.println("수동으로 " + manualLottoCount + "장, 자동으로 " + autoLottoCount + "개를 구매했습니다.");

        for (int i = 0; i < autoLottoCount; i++) {
            NavigableSet<Integer> integerList = new TreeSet<>();
            Random rand = new Random();
            while (integerList.size() < 6) {
                integerList.add(rand.nextInt(1, 46));
            }
            autoLottos.add(integerList);
        }

        totalLottos.addAll(manualLottos);
        totalLottos.addAll(autoLottos);
        totalLottos.forEach(System.out::println);

        System.out.println("지난 주 당첨 번호를 입력해 주세요");
        String input = sc.nextLine();
        String[] splitInput = input.split(",");
        NavigableSet<Integer> winningLotto = new TreeSet<>(Stream.of(splitInput).map(Integer::parseInt).toList());
        manualLottos.add(winningLotto);

        System.out.println("보너스 볼을 입력해 주세요.");
        Integer bonusBall = sc.nextInt();

        List<LottoStatus> lottoStatuses = new ArrayList<>();
        //totalLotto 안에 Set 안에 Integer가 winningLotto 안에 Integer에 포함되어 있는지 -> 상태 결정
        totalLottos.forEach(lotto -> {
            long count = lotto.stream().filter(winningLotto::contains).count();
            LottoStatus lottoStatus = switch ((int) count){
                case 6 -> LottoStatus.FIRST;
                case 5 -> LottoStatus.THIRD;
                case 4 -> LottoStatus.FOURTH;
                case 3 -> LottoStatus.FIFTH;
                default -> LottoStatus.LOSE;
            };
            if (lottoStatus == LottoStatus.THIRD && lotto.contains(bonusBall)) {
                lottoStatus = LottoStatus.SECOND;
            }
            lottoStatuses.add(lottoStatus);
        });

        System.out.println(lottoStatuses);
        List<WinningStatistics> winningStatistics = List.of(
                new WinningStatistics(LottoStatus.FIRST, 2_000_000_000, lottoStatuses.stream().filter(lottoStatus -> LottoStatus.FIRST == lottoStatus).count()),
                new WinningStatistics(LottoStatus.SECOND, 30_000_000, lottoStatuses.stream().filter(lottoStatus -> LottoStatus.SECOND == lottoStatus).count()),
                new WinningStatistics(LottoStatus.THIRD, 1_500_000, lottoStatuses.stream().filter(lottoStatus -> LottoStatus.THIRD == lottoStatus).count()),
                new WinningStatistics(LottoStatus.FOURTH, 50_000, lottoStatuses.stream().filter(lottoStatus -> LottoStatus.FOURTH == lottoStatus).count()),
                new WinningStatistics(LottoStatus.FIFTH, 5_000, lottoStatuses.stream().filter(lottoStatus -> LottoStatus.FIFTH == lottoStatus).count()),
                new WinningStatistics(LottoStatus.LOSE, 0, lottoStatuses.stream().filter(lottoStatus -> LottoStatus.LOSE == lottoStatus).count())
        );

        long sum = winningStatistics.stream().mapToLong(WinningStatistics::getTotalPrice).sum();
        float revenue = (float) sum / (float) money * 100;

        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.println("3개 일치 (5,000원) - " + lottoStatuses.stream().filter(lottoStatus -> LottoStatus.FIFTH == lottoStatus).count() + "개");
        System.out.println("4개 일치 (50,000원) - " + lottoStatuses.stream().filter(lottoStatus -> LottoStatus.FOURTH == lottoStatus).count() + "개");
        System.out.println("5개 일치 (1,500,000원) - " + lottoStatuses.stream().filter(lottoStatus -> LottoStatus.THIRD == lottoStatus).count() + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - " + lottoStatuses.stream().filter(lottoStatus -> LottoStatus.SECOND == lottoStatus).count() + "개");
        System.out.println("6개 일치 (2,000,000,000원) - " + lottoStatuses.stream().filter(lottoStatus -> LottoStatus.FIRST == lottoStatus).count() + "개");
        System.out.printf("총 수익률은 %.2f%%입니다.\n", revenue);


    }
}
