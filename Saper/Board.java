package Saper;

public class Board {
    Cell[][] board;
    int[][] nums;
    int numberOfOpened;
    HumanPlayer player;

    public Board() {
        numberOfOpened = 0;
        player = new HumanPlayer();
        nums = new int[10][10];
        board = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
        int count = 0;
        while (count < 10) {
            int x = (int) ((Math.random() * 10));
            int y = (int) ((Math.random() * 10));
            if (board[x][y] == Cell.EMPTY) {
                count++;
                board[x][y] = Cell.MINA;
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int num = 0;
                if (i < 9 && board[i + 1][j] == Cell.MINA) {
                    num++;
                }
                if (i > 0 && board[i - 1][j] == Cell.MINA) {
                    num++;
                }
                if (j < 9 && board[i][j + 1] == Cell.MINA) {
                    num++;
                }
                if (j > 0 && board[i][j - 1] == Cell.MINA) {
                    num++;
                }
                if (j < 9 && i < 9 && board[i + 1][j + 1] == Cell.MINA) {
                    num++;
                }
                if (j > 0 && i < 9 && board[i + 1][j - 1] == Cell.MINA) {
                    num++;
                }
                if (j < 9 && i > 0 && board[i - 1][j + 1] == Cell.MINA) {
                    num++;
                }
                if (j > 0 && i > 0 && board[i - 1][j - 1] == Cell.MINA) {
                    num++;
                }
                nums[i][j] = num;
            }
        }
    }

    public void play() {
        boolean win = true;
        while (numberOfOpened != 90) {
            System.out.println("Enter to numbers:");
            Move move = player.makeMove();
            if (isValid(move) == 1) {
                System.out.println("Your move is out of board sizes");
            } else if (isValid(move) == 2) {
                System.out.println("You press on mina and die");
                getMinas();
                win = false;
                break;
            } else if (isValid(move) == 3) {
                System.out.println("Cell is already opened");
            } else {
                numberOfOpened++;
                board[move.getX()][move.getY()] = Cell.OPENED;
                open(move.getX(), move.getY());
            }
            print();
        }
        if (win) {
            System.out.println("Congratulations!\nYou win!");
        }
    }

    public void open(int x, int y) {
        if (nums[x][y] == 0) {
            if (x > 0 && board[x - 1][y] == Cell.EMPTY) {
                board[x - 1][y] = Cell.OPENED;
                numberOfOpened++;
                open(x - 1, y);
            }
            if (x < 9 && board[x + 1][y] == Cell.EMPTY) {
                board[x + 1][y] = Cell.OPENED;
                numberOfOpened++;
                open(x + 1, y);
            }
            if (y > 0 && board[x][y - 1] == Cell.EMPTY) {
                board[x][y - 1] = Cell.OPENED;
                numberOfOpened++;
                open(x, y - 1);
            }
            if (y < 9 && board[x][y + 1] == Cell.EMPTY) {
                board[x][y + 1] = Cell.OPENED;
                numberOfOpened++;
                open(x, y + 1);
            }
        }
    }

    public int isValid(Move move) {
        if (!(move.getX() >= 0 && move.getY() >= 0 && move.getX() < 10 && move.getY() < 10)) {
            return 1;
        } else if (board[move.getX()][move.getY()] == Cell.MINA) {
            return 2;
        } else if (board[move.getX()][move.getY()] == Cell.OPENED) {
            return 3;
        } else {
            return 0;
        }
    }

    public void print() {
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((i) + "  ");
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == Cell.OPENED) {
                    System.out.print(nums[i][j] + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public void getMinas() {
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((i) + "  ");
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == Cell.MINA) {
                    System.out.print("! ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
