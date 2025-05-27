
package scacchicapolavoro;

public class Queen extends ChessPiece {
    public Queen(boolean white) {
        super(white);
    }

    @Override
    public String getSymbol() {
        return isWhite() ? "♕" : "♛";
    }

    @Override
    public boolean canMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        Rook rook = new Rook(isWhite());
        Bishop bishop = new Bishop(isWhite());
        return rook.canMove(startX, startY, endX, endY, board)
                || bishop.canMove(startX, startY, endX, endY, board);
    }
}