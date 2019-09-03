import java.util.Set;
import java.util.HashSet;
import javax.swing.JFrame;

class CChess {
  CChess() {
    JFrame f = new JFrame("CHINESE CHESS!!! PLAY NOW!!!");
    f.setSize(800, 800);
    f.setLocation(50, 50);
    f.setVisible(true);
  }

  public static void main(String[] args) {
    CChessBoard brd = new CChessBoard();
    System.out.println(brd);
    new CChess();
  }
}

class CChessBoard {
  final static int rows = 10;
  final static int cols = 9;
  
  private Set<Piece> pieces = new HashSet<>();
  
  void movePiece(int fromCol, int fromRow, int toCol, int toRow) {
    Piece movingP = pieceAt(fromCol, fromRow);
    Piece targetP = pieceAt(toCol, toRow);
    pieces.remove(movingP);
    pieces.remove(targetP);
    pieces.add(new Piece(toCol, toRow, movingP.isRed, movingP.rank));
  }

  private boolean outOfBoard(int col, int row) {
    return col < 0 || col > 8 || row < 0 || row > 9;
  }

  private boolean isStraight(int fromCol, int fromRow, int toCol, int toRow) {
    return fromCol == toCol || fromRow == toRow;
  }
  
  private boolean isDiagonal(int fromCol, int fromRow, int toCol, int toRow) {
    return Math.abs(fromCol - toCol) == Math.abs(fromRow - toRow);
  }

  private int steps(int fromCol, int fromRow, int toCol, int toRow) {
    if (fromCol == toCol) {
      return Math.abs(fromRow - toRow);
    } else if (fromRow == toRow) {
      return Math.abs(fromCol - toCol);
    } else if (isDiagonal(fromCol, fromRow, toCol, toRow)) {
      return Math.abs(fromRow - toRow);
    } 
    return 0;
  }

  private boolean outOfPalace(int col, int row, boolean isRed) {
    if (isRed) {
      return col < 3 || col > 5 || row < 0 || row > 2;
    } else {
      return col < 3 || col > 5 || row < 7 || row > 9;
    }
  }

  private boolean selfSide(int row, boolean isRed) {
    return isRed ? row <= 4 : row >= 5;
  }

  private int numPiecesBetween(int fromCol, int fromRow, int toCol, int toRow) {
    if (!isStraight(fromCol, fromRow, toCol, toRow) || steps(fromCol, fromRow, toCol, toRow) < 2) {
      return 0;
    }
  int cnt = 0, head, tail;
    if (fromCol == toCol) { // vertical
      head = Math.min(fromRow, toRow);
      tail = Math.max(fromRow, toRow);
      for (int row = head + 1; row < tail; row++) {
        cnt += (pieceAt(fromCol, row) == null) ? 0 : 1;
      }
    } else {
      head = Math.min(fromCol, toCol);
      tail = Math.max(fromCol, toCol);
      for (int col = head + 1; col < tail; col++) {
        cnt += (pieceAt(col, fromRow) == null) ? 0 : 1;
      }
    }
    return cnt;
  }

  private boolean selfKilling(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
    Piece target = pieceAt(toCol, toRow);
    return target != null && target.isRed == isRed;
  }

  private boolean isValidGuardMove(int fromCol, int fromRow, int toCol,   int toRow, boolean isRed) {
    if (outOfPalace(toCol, toRow, isRed)) { 
      return false; 
    }
    return isDiagonal(fromCol, fromRow, toCol, toRow) && steps(fromCol, fromRow, toCol, toRow) == 1;
  }

  private boolean isValidKingMove(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
    if (outOfPalace(toCol, toRow, isRed)) { 
      return false; 
    }
    return isStraight(fromCol, fromRow, toCol, toRow) && 
           steps(fromCol, fromRow, toCol, toRow) == 1;
  }

  private boolean isValidKnightMove(int fromCol, int fromRow, int toCol, int toRow) {
    if (Math.abs(fromCol - toCol) == 1 && Math.abs(fromRow - toRow) == 2) {
      return pieceAt(fromCol, (fromRow + toRow)/2) == null;
    } else if (Math.abs(fromCol - toCol) == 2 && Math.abs(fromRow - toRow) == 1) {
      return pieceAt((fromCol + toCol)/2, fromRow) == null;
    }
    return false;
  }

  private boolean isValidBishopMove(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
    return selfSide(toRow, isRed) 
      && pieceAt((fromCol + toCol)/2, (fromRow + toRow)/2) == null 
      && isDiagonal(fromCol, fromRow, toCol, toRow)
      && steps(fromCol, fromRow, toCol, toRow) == 2;
  }

  private boolean isValidRookMove(int fromCol, int fromRow, int toCol, int toRow) {
    return isStraight(fromCol, fromRow, toCol, toRow) 
      && numPiecesBetween(fromCol, fromRow, toCol, toRow) == 0;
  }

  private boolean isValidCannonMove(int fromCol, int fromRow, int toCol, int toRow) {
    if (pieceAt(toCol, toRow) == null) {
      return isValidRookMove(fromCol, fromRow, toCol, toRow);
    }
    return numPiecesBetween(fromCol, fromRow, toCol, toRow) == 1;
  }

  private boolean isValidPawnMove(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
    if (steps(fromCol, fromRow, toCol, toRow) != 1) {
      return false;
    }
    return isRed && toRow > fromRow || !isRed && toRow < fromRow || !selfSide(fromRow, isRed);
  }

  boolean validMove(int fromC, int fromR, int toC, int toR) {
    if (fromC == toC && fromR == toR || outOfBoard(toC, toR)) {
      return false;
    }
    Piece p = pieceAt(fromC, fromR);
    if (p == null || selfKilling(fromC, fromR, toC, toR, p.isRed)) {
      return false;
    }
    boolean ok = false;
    switch (p.rank) {
      case GUARD:
        ok = isValidGuardMove(fromC, fromR, toC, toR, p.isRed);
        break;
      case KING:
        ok = isValidKingMove(fromC, fromR, toC, toR, p.isRed);
        break;
      case BISHOP:
        ok = isValidBishopMove(fromC, fromR, toC, toR, p.isRed);
        break;
      case KNIGHT:
        ok = isValidKnightMove(fromC, fromR, toC, toR);
        break;
      case ROOK:
        ok = isValidRookMove(fromC, fromR, toC, toR);
        break;
      case CANNON:
        ok = isValidCannonMove(fromC, fromR, toC, toR);
        break;
      case PAWN:
        ok = isValidPawnMove(fromC, fromR, toC, toR, p.isRed);
        break;
    }
    return ok;
  }

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

  Piece pieceAt(int col, int row) {
    for (Piece piece : pieces) {
      if (piece.col == col && piece.row == row) {
        return piece;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    String brdStr = "";
    brdStr += "  ";
    for (int i  = 0; i < cols; i++) {
      brdStr += i + " ";
    }
    brdStr += "\n";
    
    for (int row = 0; row < rows; row++) {
      brdStr += row + " ";
      for (int col = 0; col < cols; col++) {
        Piece p  = pieceAt(col, row);
        if (p == null) {
          brdStr += ". ";
        } else {
          switch(p.rank) {
            case ROOK: brdStr += p.isRed ? "R " : "r "; break;
            case KNIGHT: brdStr += p.isRed ? "N " : "n "; break;
            case BISHOP: brdStr += p.isRed ? "B " : "b "; break;
            case GUARD: brdStr += p.isRed ? "G " : "g "; break;
            case KING: brdStr += p.isRed ? "K " : "k "; break;
            case CANNON: brdStr += p.isRed ? "C " : "c "; break;
            case PAWN: brdStr += p.isRed ? "P " : "p "; break;
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
