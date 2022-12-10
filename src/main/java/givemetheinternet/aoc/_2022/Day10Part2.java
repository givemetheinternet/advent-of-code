package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day10Part2 {

  private static final String INPUT_FILE = "/day10.txt";
  private static final int SCREEN_WIDTH = 40;

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day10Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      int cycle = 1;
      int x = 1;
      StringBuilder crtOutput = new StringBuilder();
      while (line != null) {
        drawImage(cycle, x, crtOutput);
        if ("noop".equals(line)) {
          cycle++;
        } else {
          int delta = Integer.parseInt(line.substring(5));
          cycle++;
          drawImage(cycle, x, crtOutput);

          x += delta;
          cycle++;
        }

        line = br.readLine();
      }
      System.out.println(crtOutput.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void drawImage(int cycle, int x, StringBuilder crtOutput) {
    if (isSpriteVisible(x, cycle)) {
      crtOutput.append('#');
    } else {
      crtOutput.append('.');
    }
    if (cycle % SCREEN_WIDTH == 0) {
      crtOutput.append('\n');
    }
  }

  private static boolean isSpriteVisible(int x, int cycle) {
    int xCoord = (cycle - 1) % SCREEN_WIDTH;
    return xCoord >= (x - 1) && xCoord <= (x + 1);
  }
}
