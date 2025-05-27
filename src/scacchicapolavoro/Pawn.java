
package scacchicapolavoro;

public class Pawn extends ChessPiece {
    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♙" : "♟";
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        int direction = isWhite() ? -1 : 1;

        if (startX == endX && board[endX][endY] == null) {
            if (endY == startY + direction) {
                return true;
            }
            if (startY == (isWhite() ? 6 : 1) && endY == startY + 2 * direction
                    && board[endX][endY - direction] == null) {
                return true;
            }
        } else if (Math.abs(endX - startX) == 1 && endY == startY + direction) {
            return board[endX][endY] != null && board[endX][endY].isWhite() != isWhite();
        }

        return false;
    }
}