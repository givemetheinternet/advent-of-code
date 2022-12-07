package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day4Part2 {

  private static final String INPUT_FILE = "/day4.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day4Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      int coverageCount = 0;
      while (line != null) {
        String[] parts = line.split(",");

        String[] parts1 = parts[0].split("-");
        int min1 = Integer.parseInt(parts1[0]);
        int max1 = Integer.parseInt(parts1[1]);

        String[] parts2 = parts[1].split("-");
        int min2 = Integer.parseInt(parts2[0]);
        int max2 = Integer.parseInt(parts2[1]);

        if (
          (min1 >= min2 && min1 <= max2) ||
          (max1 >= min2 && max1 <= max2) ||
          (min2 >= min1 && min2 <= max1) ||
          (max2 >= min1 && max2 <= max1)
        ) {
          coverageCount++;
        }
        line = br.readLine();
      }
      System.out.print(coverageCount);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
