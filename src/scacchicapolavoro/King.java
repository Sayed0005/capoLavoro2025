
package scacchicapolavoro;

public class King extends ChessPiece {
    public King(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♔" : "♚";
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        return dx <= 1 && dy <= 1 && (dx != 0 || dy != 0)
                && (board[endX][endY] == null || board[endX][endY].isWhite() != isWhite());
    }
}