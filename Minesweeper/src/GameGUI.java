import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class GameGUI {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
    public static void createAndShowGUI() {
        int width = getPositiveIntFromUser("Enter board width:");
        int height = getPositiveIntFromUser("Enter board height:");
        int mines = -1;
        while (mines < 0 || mines >= width * height) {
            mines = getPositiveIntFromUser("Enter number of mines:");
            if (mines >= width * height) {
                JOptionPane.showMessageDialog(null, "Number of mines must be less than the total number of cells.");
            }
        }

        if (width > 0 && height > 0 && mines < width * height) {
            Board board = new Board(width, height, mines);
            JFrame frame = new JFrame("Minesweeper");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(800, 600));

            JPanel gridPanel = new JPanel(new GridLayout(board.getHeight(), board.getWidth()));
            JButton[][] buttonGrid = new JButton[board.getHeight()][board.getWidth()];

            for (int y = 0; y < board.getHeight(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {
                    JButton button = new JButton();
                    buttonGrid[y][x] = button;
                    int finalY = y;
                    int finalX = x;


                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                Cell cell = board.getCell(finalY, finalX);
                                if (!cell.isRevealed()) {
                                    cell.toggleFlag();
                                    button.setText(cell.isFlagged() ? "F" : "");
                                    button.setBackground(cell.isFlagged() ? Color.ORANGE : null);
                                }
                            }
                        }
                    });


                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Cell cell = board.getCell(finalY, finalX);
                            if (!cell.isRevealed() && !cell.isFlagged()) {
                                board.revealCell(finalY, finalX);
                                refreshGrid(board, buttonGrid);

                                if (cell.isMine()) {
                                    revealAllMines(board, buttonGrid);
                                    disableAllButtons(buttonGrid);
                                    showMessageDialog(frame, "Game over. You hit a mine!");
                                } else if (checkWinCondition(board)) {
                                    revealAllMines(board, buttonGrid);
                                    disableAllButtons(buttonGrid);
                                    showMessageDialog(frame, "Congratulations, you've cleared all non-mine cells!");
                                }
                            }
                        }
                    });

                    gridPanel.add(button);
                }
            }

            frame.add(gridPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else{
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid dimensions and number of mines.");
            System.exit(1);
        }
    }


    private static void refreshGrid(Board board, JButton[][] buttonGrid) {
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                JButton button = buttonGrid[y][x];
                Cell cell = board.getCell(y, x);
                updateButtonAppearance(button, cell);
            }
        }
    }



    private static void updateButtonAppearance(JButton button, Cell cell) {
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, 35));
        if (cell.isRevealed()) {
            if (cell.isMine()) {
                button.setBackground(Color.RED);
                button.setText("*");
            } else {
                int neighboringMines = cell.getNeighboringMines();
                button.setText(neighboringMines > 0 ? Integer.toString(neighboringMines) : "0");
                button.setBackground(null);
            }
        } else {
            button.setText(cell.isFlagged() ? "F" : "");
            button.setBackground(cell.isFlagged() ? Color.ORANGE : null);
        }
        button.setEnabled(!cell.isRevealed());
    }


    private static boolean checkWinCondition(Board board) {
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
    private static void revealAllMines(Board board, JButton[][] buttonGrid) {
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCell(y, x);
                if (cell.isMine()) {
                    JButton button = buttonGrid[y][x];
                    button.setBackground(Color.RED);
                }
            }
        }
    }

    private static void disableAllButtons(JButton[][] buttonGrid) {
        for (int y = 0; y < buttonGrid.length; y++) {
            for (int x = 0; x < buttonGrid[y].length; x++) {
                buttonGrid[y][x].setEnabled(false);
            }
        }
    }

    private static int getPositiveIntFromUser(String prompt) {
        int value = -1;
        while (value <= 0) {
            try {
                String input = JOptionPane.showInputDialog(prompt);
                if (input == null) { // User clicked cancel
                    System.exit(0); // Exit the application
                }
                value = Integer.parseInt(input);
                if (value <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive integer greater than 0.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.");
            }
        }
        return value;
    }


}
