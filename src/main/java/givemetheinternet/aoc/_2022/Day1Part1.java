package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day1Part1 {

  private static final String INPUT_FILE = "/day1.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day1Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      long maxCalories = -1;
      long runningTotal = 0;
      String calorieCount = br.readLine();
      while (calorieCount != null) {
        if (calorieCount.isBlank()) {
          if (maxCalories < runningTotal) {
            maxCalories = runningTotal;
          }
          runningTotal = 0;
        } else {
          runningTotal += Integer.parseInt(calorieCount);
        }

        calorieCount = br.readLine();
      }

      System.out.println("And the result is!!! " + maxCalories);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
