package scacchicapolavoro;

import javax.swing.*;
import java.awt.*;

public class ScacchiCapoLavoro extends JFrame {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ChessGame game = new ChessGame();
            game.setVisible(true);
        });
    }       
}
