package givemetheinternet.aoc._2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day2Part1 {

  enum Play {
    ROCK(1) {
      int getResult(Play theirPlay) {
        if (theirPlay == PAPER) {
          return 0;
        } else if (theirPlay == SCISSORS) {
          return 6;
        } else {
          return 3;
        }
      }
    },
    PAPER(2) {
      int getResult(Play theirPlay) {
        if (theirPlay == ROCK) {
          return 6;
        } else if (theirPlay == SCISSORS) {
          return 0;
        } else {
          return 3;
        }
      }
    },
    SCISSORS(3) {
      int getResult(Play theirPlay) {
        if (theirPlay == ROCK) {
          return 0;
        } else if (theirPlay == PAPER) {
          return 6;
        } else {
          return 3;
        }
      }
    };

    final int playValue;

    Play(int playValue) {
      this.playValue = playValue;
    }

    abstract int getResult(Play theirPlay);

    static Play from(char playCode) {
      if (playCode == 'A' || playCode == 'X') {
        return Play.ROCK;
      } else if (playCode == 'B' || playCode == 'Y') {
        return Play.PAPER;
      } else {
        return Play.SCISSORS;
      }
    }
  }

  public static void main(String[] args) {
    try (
      BufferedReader br = new BufferedReader(
        new InputStreamReader(Day1Part1.class.getResourceAsStream("/day2.txt"))
      )
    ) {
      String line = br.readLine();
      long totalScore = 0;
      while (line != null) {
        Play theirPlay = Play.from(line.charAt(0));
        Play myPlay = Play.from(line.charAt(2));

        totalScore += myPlay.playValue + myPlay.getResult(theirPlay);

        line = br.readLine();
      }

      System.out.println(totalScore);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
