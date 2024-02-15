public class Main {
    public static void main(String[] args) {

        int width = 10;
        int height = 10;
        int mines = 10;

        Board board = new Board(width, height, mines);

        board.displayBoard();
    }
}
