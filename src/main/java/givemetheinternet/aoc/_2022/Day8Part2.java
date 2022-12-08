package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day8Part2 {

  private static final String INPUT_FILE = "/day8.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day8Part2.class.getResourceAsStream(INPUT_FILE))
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

      int maxScenicScore = 0;
      for (int row = 0; row < treeGrid.size(); row++) {
        for (int col = 0; col < treeGrid.size(); col++) {
          int scenicScore = calcScenicScore(row, col, treeGrid);
          if (scenicScore > maxScenicScore) {
            maxScenicScore = scenicScore;
          }
        }
      }

      System.out.println("Max scenic score: " + maxScenicScore);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static int calcScenicScore(
    int treeRow,
    int treeCol,
    List<List<Integer>> treeGrid
  ) {
    int currentTreeHeight = treeGrid.get(treeRow).get(treeCol);

    int scenicScoreLeft = 0;
    int pointer = treeCol - 1;
    while (pointer >= 0) {
      scenicScoreLeft++;
      if (treeGrid.get(treeRow).get(pointer--) >= currentTreeHeight) {
        break;
      }
    }

    int scenicScoreRight = 0;
    pointer = treeCol + 1;
    while (pointer < treeGrid.size()) {
      scenicScoreRight++;
      if (treeGrid.get(treeRow).get(pointer++) >= currentTreeHeight) {
        break;
      }
    }

    int scenicScoreTop = 0;
    pointer = treeRow - 1;
    while (pointer >= 0) {
      scenicScoreTop++;
      if (treeGrid.get(pointer--).get(treeCol) >= currentTreeHeight) {
        break;
      }
    }

    int scenicScoreBottom = 0;
    pointer = treeRow + 1;
    while (pointer < treeGrid.size()) {
      scenicScoreBottom++;
      if (treeGrid.get(pointer++).get(treeCol) >= currentTreeHeight) {
        break;
      }
    }

    return scenicScoreLeft * scenicScoreRight * scenicScoreTop * scenicScoreBottom;
  }
}
