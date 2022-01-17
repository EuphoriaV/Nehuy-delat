package Saper;

import java.util.Scanner;

public class HumanPlayer {
    public Move makeMove() {
        Scanner sc = new Scanner(System.in);
        return new Move(sc.nextInt(), sc.nextInt());
    }
}
