import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class GameGUI {

    public static void createAndShowGUI() {
        int width = Integer.parseInt(JOptionPane.showInputDialog("Enter board width:"));
        int height = Integer.parseInt(JOptionPane.showInputDialog("Enter board height:"));
        int mines = Integer.parseInt(JOptionPane.showInputDialog("Enter number of mines:"));

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
                                    button.setBackground(Color.RED);
                                    showMessageDialog(frame, "Game over. You hit a mine!");
                                } else if (checkWinCondition(board)) {
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
            System.exit(1); // Exit the application if the input is invalid
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> createAndShowGUI());
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
        if (cell.isRevealed()) {
            if (cell.isMine()) {
                button.setBackground(Color.RED);
            } else {
                int neighboringMines = cell.getNeighboringMines();
                button.setText(neighboringMines > 0 ? Integer.toString(neighboringMines) : "");
                button.setEnabled(false);
            }
        } else if (cell.isFlagged()) {
            button.setText("F");
            button.setEnabled(false); // Disable flagged buttons
        } else {
            button.setText("");
            button.setEnabled(true); // Enable the button if it is not revealed or flagged
        }
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


}
