
package scacchicapolavoro;

public class Bishop extends ChessPiece {
    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♗" : "♝";
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        if (Math.abs(endX - startX) != Math.abs(endY - startY)) {
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