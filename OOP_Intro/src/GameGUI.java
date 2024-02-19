import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class GameGUI {

    public static void createAndShowGUI(Board board) {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                            updateButtonAppearance(button, cell);

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
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Board board = new Board(3, 3, 2);
        SwingUtilities.invokeLater(() -> createAndShowGUI(board));
    }

    private static void updateButtonAppearance(JButton button, Cell cell) {
        if (cell.isRevealed()) {
            if (cell.isMine()) {

                button.setBackground(Color.RED);
            } else {
                int neighboringMines = cell.getNeighboringMines();
                button.setText(neighboringMines > 0 ? Integer.toString(neighboringMines) : "0");
            }
        } else if (cell.isFlagged()) {
            button.setBackground(Color.ORANGE);
            button.setText("F");
        } else {
            button.setText("");
        }
        button.setEnabled(false);
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
