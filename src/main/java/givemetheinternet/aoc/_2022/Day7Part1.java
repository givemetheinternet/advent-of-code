package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Day7Part1 {

  private static final String INPUT_FILE = "/day7.txt";

  public static void main(String... args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day7Part1.class.getResourceAsStream(INPUT_FILE))
      )
    ) {
      buildFileSystem(br);
      System.out.println(calcTotal(Node.ROOT));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static long calcTotal(Node root) {
    if (!root.isDirectory) {
      return 0;
    }
    long curSize = root.getSize();
    return (
      (curSize <= 100_000 ? curSize : 0) +
      root.children.stream().map(Day7Part1::calcTotal).reduce(0L, Long::sum)
    );
  }

  private static void buildFileSystem(BufferedReader br) throws IOException {
    String line = br.readLine();
    Node root = new Node("/", true);
    Node pointer = root;
    while (line != null) {
      if (line.startsWith("$ cd ")) {
        pointer = pointer.pointAtDirectory(line.substring(5));
      } else if (!line.startsWith("$ ls")) {
        if (line.startsWith("dir")) {
          pointer.addNode(new Node(line.substring(4), true));
        } else {
          String[] fileData = line.split(" ");
          pointer.addNode(new Node(fileData[1], Integer.parseInt(fileData[0])));
        }
      }

      line = br.readLine();
    }
  }

  private static class Node {

    private static final Node ROOT = new Node("/", true);

    private final String name;
    private final boolean isDirectory;
    private final int bytes;

    private final Collection<Node> children;
    private Node parent;

    public Node(String name, boolean isDirectory) {
      this(name, isDirectory, 0);
    }

    public Node(String name, int bytes) {
      this(name, false, bytes);
    }

    private Node(String name, boolean isDirectory, int bytes) {
      this.name = name;
      this.isDirectory = isDirectory;
      this.bytes = bytes;

      if (isDirectory) {
        children = new ArrayList<>();
      } else {
        children = Collections.emptyList();
      }
    }

    public void addNode(Node n) {
      n.parent = this;
      children.add(n);
    }

    public long getSize() {
      if (isDirectory) {
        return children.stream().map(Node::getSize).reduce(0L, Long::sum);
      } else {
        return bytes;
      }
    }

    public Node pointAtDirectory(String nodeName) {
      if ("/".equals(nodeName)) {
        return ROOT;
      } else if ("..".equals(nodeName)) {
        return parent;
      } else {
        return children
          .stream()
          .filter(node -> node.isDirectory && node.name.equals(nodeName))
          .findFirst()
          .get();
      }
    }
  }
}
