
package scacchicapolavoro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessBoard extends JPanel {
    private final int DIMENSIONE = 8;
    private final int DIM_CASELLA = 80;
    private final ChessSquare[][] squares = new ChessSquare[DIMENSIONE][DIMENSIONE];
    private final ChessPiece[][] pieces = new ChessPiece[DIMENSIONE][DIMENSIONE];
    
    private final Color LIGHT_COLOR = new Color(240, 217, 181);
    private final Color DARK_COLOR = new Color(181, 136, 99);
    private final Color SELECTION_COLOR = new Color(255, 255, 150);
    private final Color POSSIBLE_MOVES_COLOR = new Color(200, 200, 100);
    
    private ChessSquare selectedSquare = null;
    private boolean whiteTurn = true;
    private boolean gameOver = false;
    private Point whiteKingPosition = new Point(4, 7);
    private Point blackKingPosition = new Point(4, 0);
    
    private final ChessGame parent;

    public ChessBoard(ChessGame parent) {
        this.parent = parent;
        setLayout(new GridLayout(DIMENSIONE, DIMENSIONE));
        setPreferredSize(new Dimension(DIMENSIONE * DIM_CASELLA, DIMENSIONE * DIM_CASELLA));
        initializeBoard();
        placeInitialPieces();
    }

    private void initializeBoard() {
        for (int row = 0; row < DIMENSIONE; row++) {
            for (int col = 0; col < DIMENSIONE; col++) {
                squares[col][row] = new ChessSquare(col, row);
                Color color = (col + row) % 2 == 0 ? LIGHT_COLOR : DARK_COLOR;
                squares[col][row].setBackground(color);

                final int x = col;
                final int y = row;
                squares[col][row].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!gameOver) {
                            handleSquareClick(x, y);
                        }
                    }
                });

                add(squares[col][row]);
            }
        }
    }

    private void placeInitialPieces() {
        // Pawns
        for (int x = 0; x < DIMENSIONE; x++) {
            pieces[x][1] = new Pawn(false);
            pieces[x][6] = new Pawn(true);
        }

        // Other pieces
        pieces[0][0] = new Rook(false);
        pieces[0][7] = new Rook(true);

        pieces[1][0] = new Knight(false);
        pieces[1][7] = new Knight(true);

        pieces[2][0] = new Bishop(false);
        pieces[2][7] = new Bishop(true);

        pieces[3][0] = new Queen(false);
        pieces[3][7] = new Queen(true);

        pieces[4][0] = new King(false);
        pieces[4][7] = new King(true);

        pieces[5][0] = new Bishop(false);
        pieces[5][7] = new Bishop(true);

        pieces[6][0] = new Knight(false);
        pieces[6][7] = new Knight(true);

        pieces[7][0] = new Rook(false);
        pieces[7][7] = new Rook(true);

        updateBoard();
    }

    private void handleSquareClick(int x, int y) {
        if (selectedSquare == null) {
            selectPiece(x, y);
        } else {
            movePiece(x, y);
        }
    }

    private void selectPiece(int x, int y) {
        ChessPiece piece = pieces[x][y];
        if (piece != null && piece.isWhite() == whiteTurn) {
            selectedSquare = squares[x][y];
            selectedSquare.setBackground(SELECTION_COLOR);
            highlightPossibleMoves(x, y);
        }
    }

    private void movePiece(int x, int y) {
        Point from = selectedSquare.getPosition();
        ChessPiece piece = pieces[from.x][from.y];

        if (from.x == x && from.y == y) {
            resetSquareColors();
            selectedSquare = null;
            return;
        }

        if (piece.canMove(from.x, from.y, x, y, pieces)) {
            ChessPiece capturedPiece = pieces[x][y];
            pieces[x][y] = piece;
            pieces[from.x][from.y] = null;

            if (piece instanceof King) {
                if (piece.isWhite()) {
                    whiteKingPosition = new Point(x, y);
                } else {
                    blackKingPosition = new Point(x, y);
                }
            }

            if (isKingInCheck(whiteTurn)) {
                // Undo move
                pieces[from.x][from.y] = piece;
                pieces[x][y] = capturedPiece;
                if (piece instanceof King) {
                    if (piece.isWhite()) {
                        whiteKingPosition = new Point(from.x, from.y);
                    } else {
                        blackKingPosition = new Point(from.x, from.y);
                    }
                }
                JOptionPane.showMessageDialog(parent, "Mossa non valida! Il tuo re sarebbe sotto scacco.");
            } else {
                boolean opponentInCheck = isKingInCheck(!whiteTurn);

                if (capturedPiece instanceof King) {
                    endGame(piece.isWhite());
                    return;
                }

                if (piece instanceof Pawn && (y == 0 || y == 7)) {
                    pieces[x][y] = new Queen(piece.isWhite());
                }

                whiteTurn = !whiteTurn;
                parent.updateTurnLabel("Turno: " + (whiteTurn ? "Bianco" : "Nero"));

                if (opponentInCheck) {
                    if (isCheckMate(!whiteTurn)) {
                        endGame(whiteTurn);
                    } else {
                        JOptionPane.showMessageDialog(parent, "Scacco al " + (whiteTurn ? "nero" : "bianco") + "!");
                    }
                }
            }
        }

        resetSquareColors();
        selectedSquare = null;
        updateBoard();
    }

    private void highlightPossibleMoves(int x, int y) {
        ChessPiece piece = pieces[x][y];
        for (int yy = 0; yy < DIMENSIONE; yy++) {
            for (int xx = 0; xx < DIMENSIONE; xx++) {
                if (piece.canMove(x, y, xx, yy, pieces)) {
                    ChessPiece temp = pieces[xx][yy];
                    pieces[xx][yy] = piece;
                    pieces[x][y] = null;

                    Point oldKingPos = piece.isWhite() ? whiteKingPosition : blackKingPosition;
                    Point newKingPos = oldKingPos;
                    if (piece instanceof King) {
                        newKingPos = new Point(xx, yy);
                        if (piece.isWhite()) {
                            whiteKingPosition = newKingPos;
                        } else {
                            blackKingPosition = newKingPos;
                        }
                    }

                    boolean check = isKingInCheck(piece.isWhite());

                    pieces[x][y] = piece;
                    pieces[xx][yy] = temp;
                    if (piece instanceof King) {
                        if (piece.isWhite()) {
                            whiteKingPosition = oldKingPos;
                        } else {
                            blackKingPosition = oldKingPos;
                        }
                    }

                    if (!check) {
                        squares[xx][yy].setBackground(POSSIBLE_MOVES_COLOR);
                    }
                }
            }
        }
    }

    private void resetSquareColors() {
        for (int y = 0; y < DIMENSIONE; y++) {
            for (int x = 0; x < DIMENSIONE; x++) {
                resetSquareColor(squares[x][y]);
            }
        }
    }

    private void resetSquareColor(ChessSquare square) {
        Point pos = square.getPosition();
        Color color = (pos.x + pos.y) % 2 == 0 ? LIGHT_COLOR : DARK_COLOR;
        square.setBackground(color);
    }

    private boolean isKingInCheck(boolean isWhiteKing) {
        Point kingPos = isWhiteKing ? whiteKingPosition : blackKingPosition;

        for (int y = 0; y < DIMENSIONE; y++) {
            for (int x = 0; x < DIMENSIONE; x++) {
                ChessPiece piece = pieces[x][y];
                if (piece != null && piece.isWhite() != isWhiteKing) {
                    if (piece.canMove(x, y, kingPos.x, kingPos.y, pieces)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheckMate(boolean isWhiteKing) {
        for (int y = 0; y < DIMENSIONE; y++) {
            for (int x = 0; x < DIMENSIONE; x++) {
                ChessPiece piece = pieces[x][y];
                if (piece != null && piece.isWhite() == isWhiteKing) {
                    for (int yy = 0; yy < DIMENSIONE; yy++) {
                        for (int xx = 0; xx < DIMENSIONE; xx++) {
                            if (piece.canMove(x, y, xx, yy, pieces)) {
                                ChessPiece temp = pieces[xx][yy];
                                pieces[xx][yy] = piece;
                                pieces[x][y] = null;

                                Point oldKingPos = isWhiteKing ? whiteKingPosition : blackKingPosition;
                                Point newKingPos = oldKingPos;
                                if (piece instanceof King) {
                                    newKingPos = new Point(xx, yy);
                                    if (isWhiteKing) {
                                        whiteKingPosition = newKingPos;
                                    } else {
                                        blackKingPosition = newKingPos;
                                    }
                                }

                                boolean stillInCheck = isKingInCheck(isWhiteKing);

                                // Restore
                                pieces[x][y] = piece;
                                pieces[xx][yy] = temp;
                                if (piece instanceof King) {
                                    if (isWhiteKing) {
                                        whiteKingPosition = oldKingPos;
                                    } else {
                                        blackKingPosition = oldKingPos;
                                    }
                                }

                                if (!stillInCheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void endGame(boolean whiteWon) {
        gameOver = true;
        parent.showGameOverMessage("Scacco matto! Ha vinto il giocatore " + (whiteWon ? "bianco" : "nero"));
    }

    private void updateBoard() {
        for (int y = 0; y < DIMENSIONE; y++) {
            for (int x = 0; x < DIMENSIONE; x++) {
                squares[x][y].removeAll();

                if (pieces[x][y] != null) {
                    JLabel label = new JLabel(pieces[x][y].getSymbol(), SwingConstants.CENTER);
                    label.setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));
                    label.setForeground(pieces[x][y].isWhite() ? Color.WHITE : Color.BLACK);
                    squares[x][y].add(label);
                }

                squares[x][y].revalidate();
                squares[x][y].repaint();
            }
        }
    }
}