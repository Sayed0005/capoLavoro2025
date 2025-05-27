
package scacchicapolavoro;
import javax.swing.*;
import java.awt.*;

public class ChessGame extends JFrame {
    private final ChessBoard chessBoard;
    private final JLabel labelTurno;

    public ChessGame() {
        setTitle("Scacchi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        labelTurno = new JLabel("Turno: Bianco", SwingConstants.CENTER);
        labelTurno.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelTurno, BorderLayout.NORTH);

        chessBoard = new ChessBoard(this);
        add(chessBoard, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public void updateTurnLabel(String text) {
        labelTurno.setText(text);
    }

    public void showGameOverMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Partita terminata", JOptionPane.INFORMATION_MESSAGE);
    }
}