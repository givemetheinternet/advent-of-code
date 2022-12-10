package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day10Part1 {

  private static final String INPUT_FILE = "/day10.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day10Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      int cycle = 1;
      long x = 1;
      long sumOfX = 0;

      while (line != null) {
        sumOfX = calcCumOfX(cycle, x, sumOfX);

        if ("noop".equals(line)) {
          cycle++;
        } else {
          int delta = Integer.parseInt(line.substring(5));
          cycle++;
          sumOfX = calcCumOfX(cycle, x, sumOfX);

          x += delta;
          cycle++;
        }

        line = br.readLine();
      }
      System.out.println("Total signal: " + sumOfX);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static long calcCumOfX(int cycle, long x, long sumOfX) {
    if (cycle == 20 || (cycle - 20) % 40 == 0) {
      System.out.println(
        "X register at cycle " + cycle + " is " + x + ". Signal: " + (cycle * x)
      );
      sumOfX += cycle * x;
    }
    return sumOfX;
  }
}
