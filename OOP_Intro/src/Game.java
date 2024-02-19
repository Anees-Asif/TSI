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

    public void startGame() {
        while (!gameOver) {
            System.out.println("Current board state:");
            board.displayBoard();
            System.out.println("Enter row and column numbers to reveal or flag (e.g., '2 3 r' to reveal, '2 3 f' to flag):");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            String action = scanner.next();

            if (isValidCoordinate(row, col)) {
                Cell cell = board.getCell(row, col);
                if ("r".equalsIgnoreCase(action)) {
                    if (cell.isFlagged()) {
                        System.out.println("This cell is flagged and cannot be revealed. Choose another action.");
                    } else {
                        board.revealCell(row, col);
                        if (cell.isMine()) {
                            gameOver = true;
                            System.out.println("You hit a mine! Game over.");
                        } else {
                            if (checkWinCondition()) {
                                System.out.println("Congratulations, you've cleared all non-mine cells!");
                                gameOver = true;
                            }
                        }
                    }
                } else if ("f".equalsIgnoreCase(action)) {
                    if (!cell.isRevealed()) {
                        cell.toggleFlag();
                        System.out.println(cell.isFlagged() ? "Cell flagged." : "Flag removed.");
                    } else {
                        System.out.println("This cell is already revealed and cannot be flagged.");
                    }
                }
            } else {
                System.out.println("Invalid coordinates or action. Please try again.");
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

    private boolean isValidCoordinate(int x, int y) {

        return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight();
    }

    public static void main(String[] args) {
        Game game = new Game(4, 4, 2);
        game.startGame();
    }
}
