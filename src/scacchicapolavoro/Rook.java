
package scacchicapolavoro;

public class Rook extends ChessPiece {
    public Rook(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♖" : "♜";
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        if (startX != endX && startY != endY) {
            return false;
        }

        int stepX = Integer.compare(endX, startX);
        int stepY = Integer.compare(endY, startY);

        int x = startX + stepX;
        int y = startY + stepY;

        while (x != endX || y != endY) {
            if (board[x][y] != null) {
                return false;
            }
            x += stepX;
            y += stepY;
        }

        return board[endX][endY] == null || board[endX][endY].isWhite() != isWhite();
    }
}