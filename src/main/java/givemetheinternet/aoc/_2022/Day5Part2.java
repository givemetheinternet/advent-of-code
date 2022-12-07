package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Day5Part2 {

  private static final String INPUT_FILE = "/day5.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day5Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String line = br.readLine();
      boolean ontoMoves = false;
      Map<Integer, LinkedList<Character>> crates = new HashMap<>();
      while (line != null) {
        if (!ontoMoves && line.isBlank()) {
          ontoMoves = true;
          line = br.readLine();

          System.out.println("read in crates:\n" + crates + "\n");
          continue;
        }

        if (ontoMoves) {
          String[] moveParts = line.split("move | from | to ");
          int numMoves = Integer.parseInt(moveParts[1]);
          int fromCrate = Integer.parseInt(moveParts[2]);
          int toCrate = Integer.parseInt(moveParts[3]);

          System.out.println(
            "Moving " + numMoves + " from " + fromCrate + " to " + toCrate
          );
          LinkedList<Character> batchMove = new LinkedList<>();
          for (int moves = 0; moves < numMoves; moves++) {
            batchMove.push(crates.get(fromCrate).removeLast());
          }
          crates.get(toCrate).addAll(batchMove);

          System.out.println("crates: " + crates + "\n");
        } else if (!Character.isDigit(line.charAt(1))) {
          for (int crateIndex = 1; crateIndex < line.length(); crateIndex += 4) {
            if (!Character.isSpaceChar(line.charAt(crateIndex))) {
              LinkedList<Character> crateLoad = crates.computeIfAbsent(
                ((crateIndex - 1) / 4) + 1,
                k -> new LinkedList<>()
              );
              crateLoad.push(line.charAt(crateIndex));
            }
          }
        }
        line = br.readLine();
      }

      System.out.println("-------");
      for (LinkedList<Character> crate : crates.values()) {
        System.out.print(crate.getLast());
      }
      System.out.println("\n-------");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
