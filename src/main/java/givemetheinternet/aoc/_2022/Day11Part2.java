package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day11Part2 {

  private static final String INPUT_FILE = "/day11.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day11Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      Map<Integer, Monkey> monkeysById = buildMonkeyByIdMap(br);

      long uberFactor = 1;
      for (Monkey m : monkeysById.values()) {
        uberFactor *= m.divisorTest;
      }

      for (int round = 0; round < 10_000; round++) {
        if (round == 1 || round == 20 || (round > 20 && round % 1000 == 0)) {
          System.out.println(
            "-------------------\nRound " + round + "\n-------------------"
          );
          System.out.println(monkeysById);
        }

        for (Monkey monkey : monkeysById.values()) {
          while (!monkey.items.isEmpty()) {
            long item = monkey.items.pop();
            monkey.inspectionCount++;
            long compressedWorry = calcWorryLevel(monkey, item) % uberFactor;

            if (compressedWorry % monkey.divisorTest == 0) {
              Monkey recipient = monkeysById.get(monkey.testPositiveMonkeyId);
              recipient.items.add(compressedWorry);
            } else {
              Monkey recipient = monkeysById.get(monkey.testNegativeMonkeyId);
              recipient.items.add(compressedWorry);
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

  private static Map<Integer, Monkey> buildMonkeyByIdMap(BufferedReader br)
    throws IOException {
    String line = br.readLine();
    Map<Integer, Monkey> monkeysById = new LinkedHashMap<>();
    while (line != null) {
      int id = Integer.parseInt(line.substring(7, line.length() - 1));
      List<Long> items = Arrays
        .asList(br.readLine().substring(18).split(", "))
        .stream()
        .map(Long::parseLong)
        .collect(Collectors.toList());
      String[] operation = br.readLine().substring(23).split(" ");
      long divisor = Long.parseLong(br.readLine().substring(21));
      int monkeyPositive = Integer.parseInt(br.readLine().substring(29));
      int monkeyNegative = Integer.parseInt(br.readLine().substring(30));
      br.readLine();

      Monkey newMonkey = new Monkey(id, divisor, monkeyPositive, monkeyNegative);
      newMonkey.items.addAll(items);
      newMonkey.operationElems[0] = operation[0];
      newMonkey.operationElems[1] =
        "old".equals(operation[1]) ? -1 : Long.parseLong(operation[1]);
      monkeysById.put(id, newMonkey);

      line = br.readLine();
    }
    return monkeysById;
  }

  private static long calcWorryLevel(Monkey monkey, long item) {
    long newWorry;
    if ("*".equals(monkey.operationElems[0])) {
      if ((long) monkey.operationElems[1] == -1) {
        newWorry = item * item;
      } else {
        newWorry = item * (long) (monkey.operationElems[1]);
      }
    } else {
      if ((long) monkey.operationElems[1] == -1) {
        newWorry = item + item;
      } else {
        newWorry = item + (long) (monkey.operationElems[1]);
      }
    }
    return newWorry;
  }

  private static class Monkey implements Comparable<Monkey> {

    final int id;
    final LinkedList<Long> items = new LinkedList<>();
    final Object[] operationElems = new Object[2];
    final long divisorTest;
    final int testPositiveMonkeyId;
    final int testNegativeMonkeyId;

    long inspectionCount = 0;

    public Monkey(
      int id,
      long divisorTest,
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
      return Long.compare(o.inspectionCount, this.inspectionCount);
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
