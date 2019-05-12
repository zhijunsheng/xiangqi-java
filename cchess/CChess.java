import java.util.Set;
import java.util.HashSet;

class CChess {
  public static void main(String[] args) {
    CChessBoard brd = new CChessBoard();
    System.out.println(brd);
  }
}

class CChessBoard {
  final static int rows = 10;
  final static int cols = 9;

  private Set<Piece> pieces = new HashSet<>();

  CChessBoard() {
    for (int i = 0; i < 2; i++) {
      pieces.add(new Piece(0 + i * 8, 0, true, Rank.ROOK));
      pieces.add(new Piece(1 + i * 6, 0, true, Rank.KNIGHT));
      pieces.add(new Piece(2 + i * 4, 0, true, Rank.BISHOP));
      pieces.add(new Piece(3 + i * 2, 0, true, Rank.GUARD));
      pieces.add(new Piece(1 + i * 6, 2, true, Rank.CANNON));
      
      pieces.add(new Piece(0 + i * 8, 9, false, Rank.ROOK));
      pieces.add(new Piece(1 + i * 6, 9, false, Rank.KNIGHT));
      pieces.add(new Piece(2 + i * 4, 9, false, Rank.BISHOP));
      pieces.add(new Piece(3 + i * 2, 9, false, Rank.GUARD));
      pieces.add(new Piece(1 + i * 6, 7, false, Rank.CANNON));
    }
    pieces.add(new Piece(4, 0, true, Rank.KING));
    pieces.add(new Piece(4, 9, false, Rank.KING));
    for (int i = 0; i < 5; i++) {
      pieces.add(new Piece(i * 2, 3, true, Rank.PAWN));
      pieces.add(new Piece(i * 2, 6, false, Rank.PAWN));
    }
  }

  private Piece pieceAt(int col, int row) {
    for (Piece piece : pieces) {
      if (piece.col == col && piece.row == row) {
        return piece;
      }
    }
    return null;
  }

  public String toString() {
    String brdStr = "";
    brdStr += " ";
    for (int i = 0; i < cols; i++) {
      brdStr += " " + i;
    }
    brdStr += "\n";

    for (int row = 0; row < rows; row++) {
      brdStr += row + "";
      for (int col = 0; col < cols; col++) {
        Piece p = pieceAt(col, row);
        if (p == null) {
          brdStr += " .";
        } else {
          switch (p.rank) {
            case ROOK: brdStr += p.isRed ? " R" : " r"; break;
            case KNIGHT: brdStr += p.isRed ? " N" : " n"; break;
            case BISHOP: brdStr += p.isRed ? " B" : " b"; break;
            case GUARD: brdStr += p.isRed ? " G" : " g"; break;
            case KING: brdStr += p.isRed ? " K" : " k"; break;
            case CANNON: brdStr += p.isRed ? " C" : " c"; break;
            case PAWN: brdStr += p.isRed ? " P" : " p"; break;
          }
        }
      }
      brdStr += "\n";
    }
    return brdStr;
  }
}

enum Rank {
  ROOK,
  KNIGHT,
  BISHOP,
  GUARD,
  KING,
  CANNON,
  PAWN
}

class Piece {
  int col;
  int row;
  boolean isRed;
  Rank rank;

  Piece(int col, int row, boolean isRed, Rank rank) {
    this.col = col;
    this.row = row;
    this.isRed = isRed;
    this.rank = rank;
  }
}
