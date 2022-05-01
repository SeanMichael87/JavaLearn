import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[][] tBoard = new char[3][3];
        int first = 0;
        int second = 0;
        boolean flag = true;
        boolean playerOne = true;
//        System.out.print("Enter cells");
//        String board = scan.nextLine();
        //Places each character in the string into a multi-array
        int c = 0;
        for (int i = 0; i < tBoard.length; i++) {
            for (int j = 0; j < tBoard[i].length; j++) {
                tBoard[i][j] = ' ';
            }
        }
        //Gameboard method that creates the gameboard based on user input
        gameBoard(tBoard);
        //game will continue until a winner is pronounced
        while (flag) {
            System.out.print("Enter the coordinates:");
            String coordinates = scan.nextLine();

            //Catches values that are not numeric
            try {
                String[] pieces = coordinates.split(" ");
                first = Integer.parseInt(pieces[0]);
                second = Integer.parseInt(pieces[1]);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (first > 3 || second > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }else if (tBoard[first-1][second-1] == 'X' || tBoard[first-1][second-1] == 'O'){
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }else {
                //Provides win conditions then validation for edge cases
                int[] conditions = conditionList(tBoard);
                boolean valid = validation(playerOne, first, second, tBoard, conditions);
                playerOne = playerState(playerOne);

                //needed to call the new updated condition list and validate for any winners after user input
                gameBoard(tBoard);
                conditions = conditionList(tBoard);
                boolean winner = validation(conditions);
//                gameBoard(tBoard);

                //If valid is true, enter winner method then change the flag to exit loop
                if (valid || winner) {
                    String declareWin = winner(tBoard, conditions);
                    System.out.println(declareWin);
                    if (declareWin.equals("X wins") || declareWin.equals("O wins")) flag = false;
                } else {
                    System.out.println("Impossible!");
                }
            }
        }
    }
    //Player state method to switch playerOne off and on for each turn
    public static boolean playerState(boolean playerOne){
        if (playerOne){
            return !playerOne;
        }
        return playerOne = true;
    }
    //Gameboard
    public static void gameBoard(char[][] tBoard){
        System.out.println("---------");
        for (char[] chars : tBoard) {
            System.out.print("| ");
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.print("|\n");
        }
        System.out.println("---------");
    }
    //All possible winning conditions placed in a list
    public static int[] conditionList(char[][] tBoard){
        //Row conditions
        int cond1 = tBoard[0][0] + tBoard[0][1] + tBoard[0][2];
        int cond2 = tBoard[1][0] + tBoard[1][1] + tBoard[1][2];
        int cond3 = tBoard[2][0] + tBoard[2][1] + tBoard[2][2];
        //Column conditions
        int cond4 = tBoard[0][0] + tBoard[1][0] + tBoard[2][0];
        int cond5 = tBoard[0][1] + tBoard[1][1] + tBoard[2][1];
        int cond6 = tBoard[0][2] + tBoard[1][2] + tBoard[2][2];
        //Diagonal conditions
        int cond7 = tBoard[0][0] + tBoard[1][1] + tBoard[2][2];
        int cond8 = tBoard[0][2] + tBoard[1][1] + tBoard[2][0];

        return new int[]{cond1, cond2, cond3, cond4, cond5, cond6, cond7, cond8};
    }
    //Overloaded method for validation after user input with updated condition list
    public static boolean validation(int[] condition){
        for (int sum : condition) {
            if (sum == ('X' * 3) || sum == ('O' * 3)) {
                if (sum == ('X' * 3) || sum == ('O' * 3)) return true;
            }
        }
        return false;
    }
    public static boolean validation(boolean playerOne, int first, int second, char[][] tBoard, int[] condition){
        int count = 0;
        int xs = 0;
        int os =0;

        for (int sum : condition) {
            if (sum == ('X' * 3) || sum == ('O' * 3)) {
                count++;
            }
        }
        for (char[] chars : tBoard) {
            for (char aChar : chars) {
                if (aChar == 'X') xs++;
                if (aChar == 'O') os++;
            }
        }
        for (int i = 0; i < tBoard.length; i++) {
            for (int j = 0; j < tBoard[i].length; j++) {
                if (i == first - 1 && j == second - 1) {
                    if (playerOne) {
                        tBoard[i][j] = 'X';
                    } else {
                        tBoard[i][j] = 'O';
                    }
                }
            }
        }
        if (count > 1){
            return false;
        }
        if (Math.abs(xs - os) > 1) {
            return false;
        }
        return true;
    }
    public static String winner(char[][] tBoard, int[] condition) {
        int xs = 0;
        int os = 0;

        for (int sum : condition) {
             if (sum == ('X' * 3) || sum == ('O' * 3)) {
                if (sum == ('X' * 3)) return "X wins";
                if (sum == ('O' * 3)) return "O wins";
            }
        }
        for (char[] chars : tBoard) {
            for (char aChar : chars) {
                if (aChar == 'X') xs++;
                if (aChar == 'O') os++;
            }
        }
        if (xs + os == 9) return "Draw";
        return "Game not finished";
    }
}