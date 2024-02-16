import java.util.Scanner;

public class Game {
    private Board board;
    private boolean gameOver;
    private Scanner scanner;

    public Game(int width, int height, int mines) {
        this.board = new Board(width, height, mines);
        this.gameOver = false;
        this.scanner = new Scanner(System.in);
    }

    private boolean isValidCoordinate(int x, int y) {

        return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight();
    }

    public void startGame() {
        while (!gameOver) {
            System.out.println("Current board state:");
            board.displayBoard();
            System.out.println("Enter row and column numbers to reveal (e.g., '2 3' for row 2, column 3):");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;

            if (isValidCoordinate(row, col)) {
                board.revealCell(row, col);

                if (board.getCell(row, col).isMine()) {
                    gameOver = true;
                    System.out.println("You hit a mine! Game over.");

                } else {

                    if (checkWinCondition()) {
                        System.out.println("Congratulations, you've cleared all non-mine cells!");
                        gameOver = true;
                    }
                }
            } else {
                System.out.println("Invalid coordinates. Please try again.");
            }
        }
        scanner.close();
    }

    private boolean checkWinCondition() {

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCell(y, x);
                if (!cell.isMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }



    public static void main(String[] args) {
        Game game = new Game(4, 4, 2); // Example setup
        game.startGame();
    }
}
