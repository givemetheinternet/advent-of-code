package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day12Part2 {

  private static final String INPUT_FILE = "/day12.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day12Part2.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      List<List<Node>> grid = new ArrayList<>();
      PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>((n1, n2) ->
        Long.compare(n1.distanceFromStart, n2.distanceFromStart)
      );

      String line = br.readLine();
      int row = 0;
      Set<Node> startPotential = new HashSet<>();
      Node end = null;
      while (line != null) {
        List<Node> currentRow = new ArrayList<>(line.length());
        grid.add(currentRow);
        for (int col = 0; col < line.length(); col++) {
          char height = line.charAt(col);
          Node node = new Node(new Coords(row, col), height);
          currentRow.add(node);
          unvisitedNodes.add(node);

          if (height == 'S' || height == 'a') {
            startPotential.add(node);
          } else if (height == 'E') {
            end = node;
          }
        }
        line = br.readLine();
        row++;
      }

      printGrid(grid);
      buildGraph(grid);

      long minDistance = Long.MAX_VALUE;
      System.out.println(startPotential.size() + " potential hike starts");
      int count = 0;
      for (Node startNode : startPotential) {
        if (count++ % 100 == 0) {
          System.out.print('.');
        }

        startNode.distanceFromStart = 0;
        PriorityQueue<Node> copyQueue = new PriorityQueue(unvisitedNodes);
        copyQueue.remove(startNode);
        copyQueue.add(startNode);

        Set<Node> visitedNodes = new HashSet<>();
        while (!copyQueue.isEmpty()) {
          Node current = copyQueue.poll();
          visitedNodes.add(current);
          for (Node n : current.neighbors) {
            if (visitedNodes.contains(n)) {
              continue;
            }
            if (n.distanceFromStart > current.distanceFromStart + 1) {
              n.distanceFromStart = current.distanceFromStart + 1;
              copyQueue.remove(n);
              copyQueue.add(n);
            }
          }
        }
        if (end.distanceFromStart < minDistance) {
          minDistance = end.distanceFromStart;
        }

        visitedNodes.forEach(n -> n.distanceFromStart = Integer.MAX_VALUE);
      }

      System.out.println("\nShortest hike to E: " + minDistance);
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
    long distanceFromStart = Long.MAX_VALUE;

    public Node(Coords coords, char height) {
      this.coords = coords;
      if (height == 'S') {
        this.height = 'a';
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
