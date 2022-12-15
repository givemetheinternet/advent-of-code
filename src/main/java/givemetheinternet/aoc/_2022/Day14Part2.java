package givemetheinternet.aoc._2022;

import givemetheinternet.aoc._2022.Day14Part1.Element;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day14Part2 {

  private static final String INPUT_FILE = "/day14.txt";

  enum Element {
    SAND('o'),
    ROCK('#'),
    AIR('.');

    char gfx;

    Element(char gfx) {
      this.gfx = gfx;
    }
  }

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day14Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      Map<Coords, Element> cave = buildCaveRocks(br);

      drawCave(cave);
      long restingSandCount = 0;
      while (true) {
        boolean sandBlocked = false, sandStopped = false;
        long x = 500, y = 0;
        while (!sandBlocked && !sandStopped) {
          if (getElementAt(x, y, cave) == Element.SAND) {
            sandBlocked = true;
            break;
          }

          if (getElementAt(x, y + 1, cave) == null) {
            y++;
          } else if (getElementAt(x - 1, y + 1, cave) == null) {
            x--;
            y++;
          } else if (getElementAt(x + 1, y + 1, cave) == null) {
            x++;
            y++;
          } else {
            cave.put(new Coords(x, y), Element.SAND);
            restingSandCount++;
            sandStopped = true;

            if (globalMin.x > x) {
              globalMin = new Coords(x, globalMin.y);
            }
            if (globalMax.x < x) {
              globalMax = new Coords(x, globalMax.y);
            }
            if (globalMax.y < y) {
              globalMax = new Coords(globalMax.x, y);
            }
          }
          //drawCave(cave);
        }

        if (sandBlocked) {
          break;
        }
      }

      drawCave(cave);
      System.out.println("Total sand: " + restingSandCount);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Element getElementAt(long x, long y, Map<Coords, Element> cave) {
    if (y >= infiniteFloorY) {
      return Element.ROCK;
    } else {
      return cave.get(new Coords(x, y));
    }
  }

  private static Map<Coords, Element> buildCaveRocks(BufferedReader br)
    throws IOException {
    String line = br.readLine();
    Map<Coords, Element> cave = new HashMap<>();

    while (line != null) {
      String[] points = line.split(" -> ");
      for (int i = 0; i < points.length - 1; i++) {
        Coords begin = buildCoords(points, i);
        Coords end = buildCoords(points, i + 1);
        populateCaveRock(cave, begin, end);
      }
      line = br.readLine();
    }

    infiniteFloorY = globalMax.y + 2;
    return cave;
  }

  private static void drawCave(Map<Coords, Element> cave) {
    for (long y = 0; y <= globalMax.y; y++) {
      for (long x = globalMin.x; x <= globalMax.x; x++) {
        Element element = cave.get(new Coords(x, y));
        if (element == null) {
          element = Element.AIR;
        }
        System.out.print(element.gfx);
      }
      System.out.println();
    }
    System.out.println();
  }

  private static Coords buildCoords(String[] allPoints, int startIndex) {
    String[] point = allPoints[startIndex].split(",");
    return new Coords(Long.parseLong(point[0]), Long.parseLong(point[1]));
  }

  private static Coords globalMin = new Coords(500, 0);
  private static Coords globalMax = new Coords(500, 0);
  private static long infiniteFloorY = 0;

  private static void populateCaveRock(
    Map<Coords, Element> cave,
    Coords start,
    Coords end
  ) {
    long minX = Math.min(start.x, end.x);
    long maxX = Math.max(start.x, end.x);
    long minY = Math.min(start.y, end.y);
    long maxY = Math.max(start.y, end.y);

    for (long x = minX; x <= maxX; x++) {
      for (long y = minY; y <= maxY; y++) {
        cave.put(new Coords(x, y), Element.ROCK);
      }
    }

    if (globalMin.x > minX) {
      globalMin = new Coords(minX, globalMin.y);
    }
    if (globalMax.x < maxX) {
      globalMax = new Coords(maxX, globalMax.y);
    }
    if (globalMax.y < maxY) {
      globalMax = new Coords(globalMax.x, maxY);
    }
  }

  private static class Coords {

    final long x, y;

    public Coords(long x, long y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Coords coords = (Coords) o;
      return x == coords.x && y == coords.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public String toString() {
      return "[" + x + "," + y + "]";
    }
  }
}
