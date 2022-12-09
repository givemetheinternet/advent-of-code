package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Day9Part1 {

  private static final String INPUT_FILE = "/day9.txt";

  enum Direction {
    R(1, 0),
    L(-1, 0),
    U(0, 1),
    D(0, -1);

    int xDelta, yDelta;

    Direction(int xDelta, int yDelta) {
      this.xDelta = xDelta;
      this.yDelta = yDelta;
    }
  }

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day9Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      Set<String> visitedCoords = new HashSet<>();
      int xHead = 0, yHead = 0, xTail = 0, yTail = 0;
      visitedCoords.add(xTail + "|" + yTail);
      while (line != null) {
        Direction d = Direction.valueOf(line.substring(0, 1));
        int numMoves = Integer.parseInt(line.substring(2));

        for (int move = 0; move < numMoves; move++) {
          xHead += d.xDelta;
          yHead += d.yDelta;

          if (!touching(xTail, yTail, xHead, yHead)) {
            if (xTail == xHead) {
              yTail += yHead > yTail ? 1 : -1;
            } else if (yTail == yHead) {
              xTail += xHead > xTail ? 1 : -1;
            } else {
              yTail += yHead > yTail ? 1 : -1;
              xTail += xHead > xTail ? 1 : -1;
            }

            visitedCoords.add(xTail + "|" + yTail);
          }
        }
        line = br.readLine();
      }

      System.out.println(visitedCoords.size());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static boolean touching(int x1, int y1, int x2, int y2) {
    return !(Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1);
  }
}
