package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day6Part1 {

  private static final String INPUT_FILE = "/day6.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day6Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      while (line != null) {
        for (int i = 0; i < line.length() - 14; i++) {
          String code = line.substring(i, i + 14);
          if (code.chars().distinct().count() == 14) {
            System.out.println(i + 14);
            return;
          }
        }
        line = br.readLine();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
