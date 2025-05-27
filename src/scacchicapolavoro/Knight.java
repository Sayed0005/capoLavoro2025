
package scacchicapolavoro;
public class Knight extends ChessPiece {
    public Knight(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♘" : "♞";
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        return (dx == 2 && dy == 1 || dx == 1 && dy == 2)
                && (board[endX][endY] == null || board[endX][endY].isWhite() != isWhite());
    }
}