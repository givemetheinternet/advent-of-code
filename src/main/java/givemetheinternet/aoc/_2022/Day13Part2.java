package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13Part2 {

  private static final String INPUT_FILE = "/day13.txt";
  private static final List DIV1 = List.of(List.of(2));
  private static final List DIV2 = List.of(List.of(6));

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day13Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      String left = br.readLine();
      String right = br.readLine();
      int pairIndex = 1;
      List<List> orderedPairs = new ArrayList<>();
      orderedPairs.add(DIV1);
      orderedPairs.add(DIV2);
      while (left != null) {
        index = 1;
        List leftList = createNumberList(left);
        index = 1;
        List rightList = createNumberList(right);

        orderedPairs.add(leftList);
        orderedPairs.add(rightList);

        br.readLine();
        left = br.readLine();
        right = br.readLine();
      }

      orderedPairs.sort((p1, p2) -> isDisordered(p1, p2, 0) ? 1 : -1);

      System.out.println(
        "Decoder key: " +
        (orderedPairs.indexOf(DIV1) + 1) *
        (orderedPairs.indexOf(DIV2) + 1)
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
