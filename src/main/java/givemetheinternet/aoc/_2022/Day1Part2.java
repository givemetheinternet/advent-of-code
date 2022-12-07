package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Day1Part2 {

  private static final String INPUT_FILE = "/day1.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day1Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      PriorityQueue<Long> biggestTotals = new PriorityQueue<>();
      String calorieCount = br.readLine();

      long runningTotal = 0;
      while (calorieCount != null) {
        if (calorieCount.isBlank()) {
          biggestTotals.add(runningTotal);
          if (biggestTotals.size() > 3) {
            biggestTotals.poll();
          }
          runningTotal = 0;
        } else {
          runningTotal += Integer.parseInt(calorieCount);
        }

        calorieCount = br.readLine();
      }

      if (runningTotal != 0) {
        biggestTotals.add(runningTotal);
        if (biggestTotals.size() > 3) {
          biggestTotals.poll();
        }
      }

      System.out.println(
        "And the result is!!! " + biggestTotals.stream().reduce(Long::sum).get()
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
