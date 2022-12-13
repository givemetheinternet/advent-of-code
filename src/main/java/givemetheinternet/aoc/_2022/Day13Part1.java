package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13Part1 {

  private static final String INPUT_FILE = "/day13.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day13Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String left = br.readLine();
      String right = br.readLine();
      int pairIndex = 1;
      Set<Integer> orderedPairs = new HashSet<>();
      while (left != null) {
        index = 1;
        List leftList = createNumberList(left);
        index = 1;
        List rightList = createNumberList(right);

        Boolean result = isDisordered(leftList, rightList, 0);
        if (result != null && !isDisordered(leftList, rightList, 0)) {
          orderedPairs.add(pairIndex);
        }

        System.out.println(left);
        System.out.println(right);
        System.out.println("Ordered? " + orderedPairs.contains(pairIndex));
        System.out.println();

        br.readLine();
        left = br.readLine();
        right = br.readLine();
        pairIndex++;
      }

      System.out.println(
        "Ordered pair sum: " + orderedPairs.stream().reduce(0, Integer::sum)
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Boolean isDisordered(List left, List right, int index) {
    while (index < left.size() && index < right.size()) {
      Object leftElem = left.get(index);
      Object rightElem = right.get(index);

      if (leftElem instanceof Integer && rightElem instanceof Integer) {
        if ((int) rightElem < (int) leftElem) {
          return true;
        } else if ((int) rightElem > (int) leftElem) {
          return false;
        }
      } else {
        Boolean recurse = isDisordered(
          leftElem instanceof List ? (List) leftElem : List.of((int) leftElem),
          rightElem instanceof List ? (List) rightElem : List.of((int) rightElem),
          0
        );
        if (recurse != null) {
          return recurse;
        }
      }

      index++;
    }
    if (index < left.size() && index >= right.size()) {
      return true;
    } else if (index < right.size() && index >= left.size()) {
      return false;
    }

    return null;
  }

  private static final Set<Character> DIGIT_TOKEN_END = Set.of(',', ']');
  private static int index = 1;

  private static List createNumberList(String inputText) {
    List container = new ArrayList();
    while (index < inputText.length() - 1) {
      if (Character.isDigit(inputText.charAt(index))) {
        if (DIGIT_TOKEN_END.contains(inputText.charAt(index + 1))) {
          container.add(Integer.parseInt("" + inputText.charAt(index)));
          if (inputText.charAt(index + 1) == ',') {
            index += 2;
          } else {
            index++;
          }
        } else {
          container.add(Integer.parseInt(inputText.substring(index, index + 2)));
          if (inputText.charAt(index + 2) == ',') {
            index += 3;
          } else {
            index += 2;
          }
        }
      } else if (inputText.charAt(index) == '[') {
        index++;
        container.add(createNumberList(inputText));
      } else if (inputText.charAt(index) == ']') {
        index++;
        return container;
      } else {
        index++;
      }
    }

    return container;
  }
}
