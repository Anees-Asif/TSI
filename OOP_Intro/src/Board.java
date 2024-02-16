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
                        System.out.print(" ");
                    }
                } else if (cell.isMine()) {
                    // For debugging, remove below comment and comment out the following line
                   // System.out.print("*");
                    System.out.print(".");
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
                    // Check all neighboring cells
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int nx = x + i;
                            int ny = y + j;
                            // Ensure the neighbor is within the board bounds
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
        // Check if within grid and whether the cell is already revealed or is a mine
        if (col < 0 || col >= width || row < 0 || row >= height || cells[row][col].isRevealed() || cells[row][col].isMine()) {
            return;
        }

        cells[row][col].reveal();

        // If the cell has no neighboring mines, recursively reveal its neighbors
        if (cells[row][col].getNeighboringMines() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue; // Skip the cell itself
                    }
                    revealCell(row + i, col + j); // Recursive call
                }
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }




}
