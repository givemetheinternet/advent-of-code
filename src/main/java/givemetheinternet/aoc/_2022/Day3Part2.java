package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day3Part2 {

  private static final String INPUT_FILE = "/day3.txt";
  private static final String PRIORITY_STRING =
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day3Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();

      int prioritySum = 0;
      List<String> groupSacks = new ArrayList<>();
      while (line != null) {
        groupSacks.add(line);
        if (groupSacks.size() == 3) {
          prioritySum += getSharedPriority(groupSacks);
          groupSacks.clear();
        }
        line = br.readLine();
      }

      System.out.print(prioritySum);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static int getSharedPriority(List<String> group) {
    System.out.println();
    for (int i = 0; i < group.get(0).length(); i++) {
      char firstItem = group.get(0).charAt(i);
      if (
        group.get(1).contains("" + firstItem) && group.get(2).contains("" + firstItem)
      ) {
        int priority = PRIORITY_STRING.indexOf(firstItem) + 1;
        System.out.println("Common item: " + firstItem + " priority " + priority);
        return priority;
      }
    }

    throw new RuntimeException("What the heck");
  }
}
