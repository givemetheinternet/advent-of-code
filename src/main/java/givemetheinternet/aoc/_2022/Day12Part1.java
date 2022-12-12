package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day12Part1 {

  private static final String INPUT_FILE = "/day12.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day12Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      List<List<Node>> grid = new ArrayList<>();
      PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>((n1, n2) ->
        Integer.compare(n1.distanceFromStart, n2.distanceFromStart)
      );

      String line = br.readLine();
      int row = 0;
      Node start = null, end = null;
      while (line != null) {
        List<Node> currentRow = new ArrayList<>(line.length());
        grid.add(currentRow);
        for (int col = 0; col < line.length(); col++) {
          char height = line.charAt(col);
          Node node = new Node(new Coords(row, col), height);
          currentRow.add(node);
          unvisitedNodes.add(node);

          if (height == 'S') {
            start = node;
          } else if (height == 'E') {
            end = node;
          }
        }
        line = br.readLine();
        row++;
      }

      printGrid(grid);
      buildGraph(grid);

      Set<Node> visitedNodes = new HashSet<>();
      while (!unvisitedNodes.isEmpty()) {
        Node current = unvisitedNodes.poll();
        visitedNodes.add(current);
        for (Node n : current.neighbors) {
          if (visitedNodes.contains(n)) {
            continue;
          }
          if (n.distanceFromStart > current.distanceFromStart + 1) {
            n.distanceFromStart = current.distanceFromStart + 1;
            unvisitedNodes.remove(n);
            unvisitedNodes.add(n);
          }
        }
      }

      System.out.println("Distance to E: " + end.distanceFromStart);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void buildGraph(List<List<Node>> grid) {
    for (int nodeRow = 0; nodeRow < grid.size(); nodeRow++) {
      for (int nodeCol = 0; nodeCol < grid.get(nodeRow).size(); nodeCol++) {
        Node current = grid.get(nodeRow).get(nodeCol);
        if (nodeRow > 0) {
          Node above = grid.get(nodeRow - 1).get(nodeCol);
          if (above.height <= current.height + 1) {
            current.neighbors.add(above);
          }
        }
        if (nodeRow < grid.size() - 1) {
          Node below = grid.get(nodeRow + 1).get(nodeCol);
          if (below.height <= current.height + 1) {
            current.neighbors.add(below);
          }
        }
        if (nodeCol > 0) {
          Node left = grid.get(nodeRow).get(nodeCol - 1);
          if (left.height <= current.height + 1) {
            current.neighbors.add(left);
          }
        }
        if (nodeCol < grid.get(nodeRow).size() - 1) {
          Node right = grid.get(nodeRow).get(nodeCol + 1);
          if (right.height <= current.height + 1) {
            current.neighbors.add(right);
          }
        }
      }
    }
  }

  private static void printGrid(List<List<Node>> grid) {
    grid.forEach(row -> System.out.println(row));
  }

  private static class Coords {

    final int row, col;

    public Coords(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Coords coords = (Coords) o;
      return row == coords.row && col == coords.col;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col);
    }

    @Override
    public String toString() {
      return "[" + row + "," + col + "]";
    }
  }

  private static class Node {

    Coords coords;
    char height;
    List<Node> neighbors = new ArrayList<>();
    int distanceFromStart = Integer.MAX_VALUE;

    public Node(Coords coords, char height) {
      this.coords = coords;
      if (height == 'S') {
        this.height = 'a';
        this.distanceFromStart = 0;
      } else if (height == 'E') {
        this.height = 'z';
      } else {
        this.height = height;
      }
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node node = (Node) o;
      return coords.equals(node.coords);
    }

    @Override
    public int hashCode() {
      return Objects.hash(coords);
    }

    @Override
    public String toString() {
      return height + ":" + coords;
    }
  }
}
