package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day9Part2 {

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
        new InputStreamReader(Day9Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      Set<String> visitedCoords = new HashSet<>();
      Map<Integer, int[]> knotCoordinates = new HashMap<>();

      while (line != null) {
        Direction d = Direction.valueOf(line.substring(0, 1));
        int numMoves = Integer.parseInt(line.substring(2));

        for (int move = 0; move < numMoves; move++) {
          for (int knotIndex = 0; knotIndex < 10; knotIndex++) {
            int[] currentKnotCoords = knotCoordinates.computeIfAbsent(
              knotIndex,
              key -> new int[] { 0, 0 }
            );
            if (knotIndex == 0) {
              currentKnotCoords[0] += d.xDelta;
              currentKnotCoords[1] += d.yDelta;
              continue;
            }

            int[] previousKnotCoords = knotCoordinates.get(knotIndex - 1);
            if (
              !touching(
                currentKnotCoords[0],
                currentKnotCoords[1],
                previousKnotCoords[0],
                previousKnotCoords[1]
              )
            ) {
              if (currentKnotCoords[0] == previousKnotCoords[0]) {
                currentKnotCoords[1] +=
                  previousKnotCoords[1] > currentKnotCoords[1] ? 1 : -1;
              } else if (currentKnotCoords[1] == previousKnotCoords[1]) {
                currentKnotCoords[0] +=
                  previousKnotCoords[0] > currentKnotCoords[0] ? 1 : -1;
              } else {
                currentKnotCoords[1] +=
                  previousKnotCoords[1] > currentKnotCoords[1] ? 1 : -1;
                currentKnotCoords[0] +=
                  previousKnotCoords[0] > currentKnotCoords[0] ? 1 : -1;
              }
            }
          }
          visitedCoords.add(knotCoordinates.get(9)[0] + "|" + knotCoordinates.get(9)[1]);
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
