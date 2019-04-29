import javax.swing.JFrame;
import java.util.ArrayList;

class XiangqiFrame extends JFrame {

  XiangqiFrame() {
    setTitle("Xiang Qi");
    setSize(600, 600);
    setLocation(200, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public static void main(String[] args) {
    XiangqiEngine xiangqiEngine = new XiangqiEngine();
    System.out.println(xiangqiEngine);   

    XiangqiFrame xiangqiFrame = new XiangqiFrame();
    xiangqiFrame.setVisible(true);
  }
}

enum Rank {
  PAWN,
  CANNON,
  GUARD,
  BISHIP,
  KNIGHT,
  ROOK,
  KING
}

class XiangqiEngine {
  final static int ROWS = 10;
  final static int COLS = 9;
  private ArrayList<XiangqiPiece> pieces;
  
  XiangqiEngine() {
    pieces = new ArrayList<XiangqiPiece>();
    addInitialPieces();
    
  }

  private void addInitialPieces() {
    for (int i = 0; i < 5; i++) {
      pieces.add(new XiangqiPiece(2 * i, 3, Rank.PAWN, true));
      pieces.add(new XiangqiPiece(2 * i, 6, Rank.PAWN, false));
    }

    for (int i = 0; i < 2; i++) {
      pieces.add(new XiangqiPiece(8 * i, 0, Rank.ROOK, true));
      pieces.add(new XiangqiPiece(8 * i, 9, Rank.ROOK, false));
      pieces.add(new XiangqiPiece(1 + 6 * i, 0, Rank.KNIGHT, true));
      pieces.add(new XiangqiPiece(1 + 6 * i, 9, Rank.KNIGHT, false));
      pieces.add(new XiangqiPiece(2 + 4 * i, 0, Rank.BISHIP, true));
      pieces.add(new XiangqiPiece(2 + 4 * i, 9, Rank.BISHIP, false));
      pieces.add(new XiangqiPiece(3 + 2 * i, 0, Rank.GUARD, true));
      pieces.add(new XiangqiPiece(3 + 2 * i, 9, Rank.GUARD, false));
      pieces.add(new XiangqiPiece(1 + 6 * i, 2, Rank.CANNON, true));
      pieces.add(new XiangqiPiece(1 + 6 * i, 7, Rank.CANNON, false));
    }
    
    pieces.add(new XiangqiPiece(4, 0, Rank.KING, true));
    pieces.add(new XiangqiPiece(4, 9, Rank.KING, false));

  }

  public String toString() {
    String brdStr = "";
    for (int y = 0; y < ROWS; y++) {
      for (int x = 0; x < COLS; x++) {
        XiangqiPiece piece = pieceAt(x, y);
        if (piece == null) {
          brdStr += " .";
        } else {
          switch (piece.rank) {
            case PAWN: brdStr += piece.isRed ? " P" : " p"; break;
            case CANNON: brdStr += piece.isRed ? " C" : " c"; break;
            case GUARD: brdStr += piece.isRed ? " G" : " g"; break;
            case BISHIP: brdStr += piece.isRed ? " B" : " b"; break;
            case KNIGHT: brdStr += piece.isRed ? " N" : " n"; break;
            case ROOK: brdStr += piece.isRed ? " R" : " r"; break;
            case KING: brdStr += piece.isRed ? " K" : " k"; break;
          }
        }
      }
      brdStr += "\n";
    }
    return brdStr;
  }

  private XiangqiPiece pieceAt(int x, int y) {
    for (int i = 0; i < pieces.size(); i++) {
      XiangqiPiece piece = pieces.get(i);
      if (piece.x == x && piece.y == y) {
        return piece;
      }
    }
    return null;

  }
}

class XiangqiPiece {
  int x;
  int y;
  Rank rank;
  boolean isRed;

  XiangqiPiece(int x, int y, Rank rank, boolean isRed) {
    this.x = x;
    this.y = y;
    this.rank = rank;
    this.isRed = isRed;
  }
}
