package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11Part1 {

  private static final String INPUT_FILE = "/day11.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day11Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      Map<Integer, Monkey> monkeysById = new LinkedHashMap<>();
      while (line != null) {
        int id = Integer.parseInt(line.substring(7, line.length() - 1));
        List<Integer> items = Arrays
          .asList(br.readLine().substring(18).split(", "))
          .stream()
          .map(Integer::parseInt)
          .collect(Collectors.toList());
        String[] operation = br.readLine().substring(23).split(" ");
        int divisor = Integer.parseInt(br.readLine().substring(21));
        int monkeyPositive = Integer.parseInt(br.readLine().substring(29));
        int monkeyNegative = Integer.parseInt(br.readLine().substring(30));
        br.readLine();

        Monkey newMonkey = new Monkey(id, divisor, monkeyPositive, monkeyNegative);
        newMonkey.items.addAll(items);
        newMonkey.operationElems[0] = operation[0];
        newMonkey.operationElems[1] =
          "old".equals(operation[1]) ? -1 : Integer.parseInt(operation[1]);
        monkeysById.put(id, newMonkey);

        line = br.readLine();
      }

      for (int round = 0; round < 20; round++) {
        System.out.println(
          "-------------------\nRound " + round + "\n-------------------"
        );
        System.out.println(monkeysById);
        for (Monkey monkey : monkeysById.values()) {
          while (!monkey.items.isEmpty()) {
            Integer item = monkey.items.pop();
            monkey.inspectionCount++;
            int newWorry;
            if ("*".equals(monkey.operationElems[0])) {
              if ((int) monkey.operationElems[1] == -1) {
                newWorry = item * item;
              } else {
                newWorry = item * (int) (monkey.operationElems[1]);
              }
            } else {
              if ((int) monkey.operationElems[1] == -1) {
                newWorry = item + item;
              } else {
                newWorry = item + (int) (monkey.operationElems[1]);
              }
            }

            newWorry /= 3;
            if (newWorry % monkey.divisorTest == 0) {
              monkeysById.get(monkey.testPositiveMonkeyId).items.add(newWorry);
            } else {
              monkeysById.get(monkey.testNegativeMonkeyId).items.add(newWorry);
            }
          }
        }
      }

      List<Monkey> busyMonkeys = monkeysById
        .values()
        .stream()
        .sorted()
        .collect(Collectors.toList());
      System.out.println(
        "Monkey business: " +
        (busyMonkeys.get(0).inspectionCount * busyMonkeys.get(1).inspectionCount)
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static class Monkey implements Comparable<Monkey> {

    final int id;
    final LinkedList<Integer> items = new LinkedList<>();
    final Object[] operationElems = new Object[2];
    final int divisorTest;
    final int testPositiveMonkeyId;
    final int testNegativeMonkeyId;

    int inspectionCount = 0;

    public Monkey(
      int id,
      int divisorTest,
      int testPositiveMonkeyId,
      int testNegativeMonkeyId
    ) {
      this.id = id;
      this.divisorTest = divisorTest;
      this.testPositiveMonkeyId = testPositiveMonkeyId;
      this.testNegativeMonkeyId = testNegativeMonkeyId;
    }

    @Override
    public int compareTo(Monkey o) {
      return Integer.compare(o.inspectionCount, this.inspectionCount);
    }

    @Override
    public String toString() {
      return (
        "Monkey{" +
        "id=" +
        id +
        ", items=" +
        items +
        ", inspectionCount=" +
        inspectionCount +
        "}\n"
      );
    }
  }
}
