package tic_tac_toe;

import java.util.Random;
import java.util.Scanner;

class TicTacToe {

    static char[][] board;

    public TicTacToe() {
        board = new char[3][3];
        initBoard();
    }

    void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    void dispBoard() {
        System.out.println("-------------");

        for (int i = 0; i < board.length; i++) {
            System.out.print("|");

            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" " + board[i][j] + " |");
            }

            System.out.println();
            System.out.println("-------------");
        }
    }

    static void placemark(int row, int col, char mark) {
        if (row >= 0 && row <= 2 && col >= 0 && col <= 2) {
            board[row][col] = mark;
        } else {
            System.out.println("invalid option");
        }
    }

    static boolean checkcolwin() {
        for (int j = 0; j <= 2; j++) {
            if (board[0][j] != ' ' &&
                board[0][j] == board[1][j] &&
                board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    static boolean checkrowwin() {
        for (int i = 0; i <= 2; i++) {
            if (board[i][0] != ' ' &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    static boolean checkdiagonalwin() {
        if (board[0][0] != ' ' &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]) {
            return true;
        }

        if (board[0][2] != ' ' &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]) {
            return true;
        }

        return false;
    }

    static boolean isboardfull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkdraw() {
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}

//parent cls
abstract class player {

    String name;
    char mark;

    abstract void makemove();

    static boolean isvalidmove(int row, int col) {
        if (row >= 0 && row <= 2 &&
            col >= 0 && col <= 2) {
            return TicTacToe.board[row][col] == ' ';
        }
        return false;
    }
}

//child cls
class humanplayer extends player {

    Scanner sc;

    humanplayer(String name, char mark, Scanner sc) {
        this.name = name;
        this.mark = mark;
        this.sc = sc;
    }

    @Override
    void makemove() {
        int row;
        int col;

        do {
            System.out.println(name + " turn");
            System.out.println("enter the row and col");
            row = sc.nextInt();
            col = sc.nextInt();

            if (!isvalidmove(row, col)) {
                System.out.println("Invalid move. Try an empty cell.");
            }
        } while (!isvalidmove(row, col));

        TicTacToe.placemark(row, col, mark);
    }
}

//child cls
class aiplayer extends player {

    Random r = new Random();

    aiplayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    @Override
    void makemove() {
        int row;
        int col;

        System.out.println("TAI turn");

        do {
            row = r.nextInt(3);
            col = r.nextInt(3);
        } while (!isvalidmove(row, col));

        TicTacToe.placemark(row, col, mark);
    }
}

public class LaunchGame {

    public static void main(String[] args) {
        TicTacToe t = new TicTacToe();
        Scanner sc = new Scanner(System.in);

        humanplayer p1 = new humanplayer("bob", 'x', sc);
        aiplayer p2 = new aiplayer("TAI", 'o');
        player cp = p1;

        t.dispBoard();

        while (true) {
            cp.makemove();
            t.dispBoard();

            if (TicTacToe.checkcolwin() ||
                TicTacToe.checkrowwin() ||
                TicTacToe.checkdiagonalwin()) {
                System.out.println(cp.name + " has won");
                break;
            } else if (TicTacToe.checkdraw()) {
                System.out.println("game is draw");
                break;
            } else {
                if (cp == p1) {
                    cp = p2;
                } else {
                    cp = p1;
                }
            }
        }

        sc.close();
    }
}