package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Day3Part1 {

  private static final String INPUT_FILE = "/day3.txt";
  private static final String PRIORITY_STRING =
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day3Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();

      int prioritySum = 0;
      while (line != null) {
        prioritySum += getSharedPriority(line);
        line = br.readLine();
      }

      System.out.print(prioritySum);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static int getSharedPriority(String line) {
    System.out.println();
    Set<Character> seenItemsOne = new HashSet<>();
    Set<Character> seenItemsTwo = new HashSet<>();
    for (int i = 0; i < line.length() / 2; i++) {
      char firstItem = line.charAt(i);
      if (seenItemsTwo.contains(firstItem)) {
        int priority = PRIORITY_STRING.indexOf(firstItem) + 1;
        System.out.println("Common item: " + firstItem + " priority " + priority);
        return priority;
      }
      seenItemsOne.add(firstItem);

      char itemInOtherPart = line.charAt(i + (line.length() / 2));
      if (seenItemsOne.contains(itemInOtherPart)) {
        int priority = PRIORITY_STRING.indexOf(itemInOtherPart) + 1;
        System.out.println("Common item: " + itemInOtherPart + " priority " + priority);
        return priority;
      }
      seenItemsTwo.add(itemInOtherPart);
    }

    throw new RuntimeException("What the heck");
  }
}
