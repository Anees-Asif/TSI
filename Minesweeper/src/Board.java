import java.util.Random;
public class Board {
    private Cell[][] cells;
    private int width;
    private int height;
    private int mines;

    public Board(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.cells = new Cell[height][width];
        initializeBoard();
        placeMines();
        calculateNeighboringMines();

    }

    private void initializeBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell();
            }
        }
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void placeMines() {
        Random random = new Random();
        int placedMines = 0;
        while (placedMines < mines) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            if (!cells[y][x].isMine()) {
                cells[y][x].setMine(true);
                placedMines++;
            }
        }
    }

    public void displayBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = cells[y][x];
                if (cell.isRevealed()) {
                    if (cell.getNeighboringMines() > 0) {
                        System.out.print(cell.getNeighboringMines());
                    } else {

                        System.out.print("0");
                    }
                } else if (cell.isFlagged()) {
                    System.out.print("F");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }


    private void calculateNeighboringMines() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!cells[y][x].isMine()) {
                    int mineCount = 0;

                    // Check all adjacent cells in a 3x3 block centered on the current cell
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {

                            int nx = x + i;
                            int ny = y + j;


                            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {

                                if (cells[ny][nx].isMine()) {
                                    mineCount++;
                                }
                            }
                        }
                    }
                    cells[y][x].setNeighboringMines(mineCount);
                }
            }
        }
    }


    public void revealCell(int row, int col) {

        if (col < 0 || col >= width || row < 0 || row >= height || cells[row][col].isRevealed() || cells[row][col].isMine()) {
            return;
        }

        cells[row][col].reveal();
       // System.out.println("Revealed cell at (" + row + ", " + col + ")");


        if (cells[row][col].getNeighboringMines() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    revealCell(row + i, col + j);
                }
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }




}
