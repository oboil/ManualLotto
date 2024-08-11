package lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Application {

    static Scanner sc = new Scanner(System.in);
    static List<List<Integer>> manualLottos = new ArrayList<>();
    static List<List<Integer>> autoLottos = new ArrayList<>();
    static List<List<Integer>> totalLottos = new ArrayList<>();


    public static void main(String[] args) {
        System.out.println("구입 금액을 입력해 주세요.");
        int money = sc.nextInt();
        int totalLottoCount = money / 1000;

        System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");
        int manualLottoCount = sc.nextInt();

        System.out.println("수동으로 구매할 번호를 입력해 주세요.");
        sc.nextLine();
        for (int i = 0; i<manualLottoCount; i++) {
            String input = sc.nextLine();
            String[] splitInput =  input.split(",");
            List<Integer> integerList = Stream.of(splitInput).map(Integer::parseInt).toList();
            manualLottos.add(integerList);
        }

        int autoLottoCount = totalLottoCount - manualLottoCount;
        System.out.println("수동으로 " + manualLottoCount + "장, 자동으로 " + autoLottoCount + "개를 구매했습니다.");



        for (int i = 0; i < autoLottoCount; i++) {
            List<Integer> range = new ArrayList<>();
            for (int j = 0; j < 45; j++) {
                range.add(j + 1);
            }
            Collections.shuffle(range);
            List<Integer> integerList = range.subList(0,6);
            autoLottos.add(integerList);
        }

        totalLottos.addAll(manualLottos);
        totalLottos.addAll(autoLottos);
        totalLottos.forEach(System.out::println);

        System.out.println("지난 주 당첨 번호를 입력해 주세요");
        String input = sc.nextLine();
        String[] splitInput =  input.split(",");
        List<Integer> integerList = Stream.of(splitInput).map(Integer::parseInt).toList();
        manualLottos.add(integerList);

        System.out.println("보너스 볼을 입력해 주세요.");
        Integer bonusBall = sc.nextInt();
        System.out.println(bonusBall);


    }


}
