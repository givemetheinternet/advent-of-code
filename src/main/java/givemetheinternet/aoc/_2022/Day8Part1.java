package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8Part1 {

  private static final String INPUT_FILE = "/day8.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day8Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      List<List<Integer>> treeGrid = new ArrayList<>();
      while (line != null) {
        List<Integer> row = new ArrayList<>(line.length());
        //ugh
        row.addAll(
          line
            .chars()
            .mapToObj(c -> Integer.parseInt(Character.valueOf((char) c).toString()))
            .collect(Collectors.toList())
        );

        treeGrid.add(row);
        line = br.readLine();
      }
      System.out.println(treeGrid);

      int numPerimeterTrees = 4 * (treeGrid.size() - 1);
      System.out.println("Trees on the perimeter: " + numPerimeterTrees);

      int numVisibleInteriorTrees = 0;

      for (int row = 1; row < treeGrid.size() - 1; row++) {
        for (int col = 1; col < treeGrid.size() - 1; col++) {
          if (isVisible(row, col, treeGrid)) {
            numVisibleInteriorTrees++;
          }
        }
      }

      System.out.println(
        "Total visible trees: " + (numVisibleInteriorTrees + numPerimeterTrees)
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static boolean isVisible(
    int treeRow,
    int treeCol,
    List<List<Integer>> treeGrid
  ) {
    int currentTreeHeight = treeGrid.get(treeRow).get(treeCol);

    List<Integer> toTheLeft = treeGrid.get(treeRow).subList(0, treeCol);
    if (toTheLeft.stream().noneMatch(height -> height >= currentTreeHeight)) {
      return true;
    }
    List<Integer> toTheRight = treeGrid
      .get(treeRow)
      .subList(treeCol + 1, treeGrid.size());
    if (toTheRight.stream().noneMatch(height -> height >= currentTreeHeight)) {
      return true;
    }

    boolean visibleAbove = true;
    for (int row = 0; row < treeRow; row++) {
      if (treeGrid.get(row).get(treeCol) >= currentTreeHeight) {
        visibleAbove = false;
        break;
      }
    }
    if (visibleAbove) {
      return true;
    }
    boolean visibleBelow = true;
    for (int row = treeRow + 1; row < treeGrid.size(); row++) {
      if (treeGrid.get(row).get(treeCol) >= currentTreeHeight) {
        visibleBelow = false;
        break;
      }
    }
    return visibleBelow;
  }
}
