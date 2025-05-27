
package scacchicapolavoro;

public abstract class ChessPiece {
    private final boolean white;

    public ChessPiece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    public abstract String getSymbol();

    public abstract boolean canMove(int startX, int startY, int endX, int endY, ChessPiece[][] board);
}