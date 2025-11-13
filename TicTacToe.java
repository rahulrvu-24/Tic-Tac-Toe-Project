import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JPanel implements ActionListener {

    private boolean playerX = true;
    private boolean gameDone = false;
    private int winner = -1;
    private int player1wins = 0, player2wins = 0;
    private int[][] board = new int[3][3]; // 0 empty, 1 X, 2 O

    private JButton newGameButton;

    // Colors
    private final Color turtle = new Color(152, 109, 142);
    private final Color offwhite = new Color(0xf7f7f7);
    private final Color darkgray = new Color(239, 227, 208);
    private final Color pink = new Color(130, 92, 121);

    public TicTacToe() {
        setPreferredSize(new Dimension(420, 300));
        setLayout(null);

        newGameButton = new JButton("New Game");
        newGameButton.setBounds(310, 220, 100, 30);
        newGameButton.addActionListener(this);
        add(newGameButton);

        addMouseListener(new XOListener());
        resetGame();
    }

    private void resetGame() {
        playerX = true;
        winner = -1;
        gameDone = false;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = 0;
        newGameButton.setVisible(false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(turtle);
        drawBoard(g);
        drawUI(g);
        drawGame(g);
    }

    private void drawBoard(Graphics g) {
        g.setColor(darkgray);
        int x = 15, y = 15;
        int offset = 90;
        int size = 260;
        int line = 5;

        // horizontal
        g.fillRoundRect(x, y + offset, size, line, 5, 5);
        g.fillRoundRect(x, y + 2 * offset, size, line, 5, 5);
        // vertical
        g.fillRoundRect(x + offset, y, line, size, 5, 5);
        g.fillRoundRect(x + 2 * offset, y, line, size, 5, 5);
    }

    private void drawUI(Graphics g) {
        g.setColor(pink);
        g.fillRect(300, 0, 120, 300);

        g.setColor(offwhite);
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString("Win Count", 310, 30);
        g.drawString("X: " + player1wins, 310, 60);
        g.drawString("O: " + player2wins, 310, 85);

        g.setFont(new Font("Serif", Font.ITALIC, 16));
        if (gameDone) {
            if (winner == 1)
                g.drawString("X Wins!", 320, 130);
            else if (winner == 2)
                g.drawString("O Wins!", 320, 130);
            else
                g.drawString("Tie Game!", 320, 130);
        } else {
            g.drawString((playerX ? "X's Turn" : "O's Turn"), 325, 130);
        }

        g.setFont(new Font("Courier", Font.BOLD, 13));
        g.drawString("Tic Tac Toe", 320, 280);
    }

    private class XOListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (gameDone) return;
            int a = e.getX(), b = e.getY();

            int selX = -1, selY = -1;
            if (a > 15 && a < 95) selX = 0;
            else if (a > 100 && a < 185) selX = 1;
            else if (a > 190 && a < 275) selX = 2;

            if (b > 15 && b < 95) selY = 0;
            else if (b > 100 && b < 185) selY = 1;
            else if (b > 190 && b < 275) selY = 2;

            if (selX != -1 && selY != -1 && board[selX][selY] == 0) {
                board[selX][selY] = playerX ? 1 : 2;
                playerX = !playerX;
                checkWinner();
                }
            }
        }
    }

    private void checkWinner() {
        if (gameDone) return;

        int temp = -1;

        // Checking rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                temp = board[i][0];
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                temp = board[0][i];
        }

        // Checking diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            temp = board[0][0];
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            temp = board[0][2];

        // Checking For a tie
        if (temp == -1) {
            boolean full = true;
            for (int[] row : board)
                for (int c : row)
                    if (c == 0) full = false;
            if (full) temp = 3;
        }

        if (temp > 0) {
            winner = temp;
            if (winner == 1) player1wins++;
            else if (winner == 2) player2wins++;
            gameDone = true;
            newGameButton.setVisible(true);
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetGame();
    }

    private class XOListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (gameDone) return;
            int a = e.getX(), b = e.getY();

            int selX = -1, selY = -1;
            if (a > 15 && a < 95) selX = 0;
            else if (a > 100 && a < 185) selX = 1;
            else if (a > 190 && a < 275) selX = 2;

            if (b > 15 && b < 95) selY = 0;
            else if (b > 100 && b < 185) selY = 1;
            else if (b > 190 && b < 275) selY = 2;

            if (selX != -1 && selY != -1 && board[selX][selY] == 0) {
                board[selX][selY] = playerX ? 1 : 2;
                playerX = !playerX;
                checkWinner();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        TicTacToe gamePanel = new TicTacToe();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        // When window closes, reset all values
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gamePanel.player1wins = 0;
                gamePanel.player2wins = 0;
            }
        });
    }
}
