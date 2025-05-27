
package scacchicapolavoro;
import javax.swing.*;
import java.awt.*;

public class ChessSquare extends JPanel {
    private final Point position;

    public ChessSquare(int x, int y) {
        this.position = new Point(x, y);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public Point getPosition() {
        return position;
    }
}