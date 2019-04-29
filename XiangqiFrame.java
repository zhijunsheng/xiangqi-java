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
      XiangqiPiece pawn = new XiangqiPiece();
      pawn.x = 2 * i;
      pawn.y = 3;
      pawn.rank = 0;
      pawn.isRed = true;
      pieces.add(pawn);
    }
  }

  public String toString() {
    String brdStr = "";
    for (int y = 0; y < ROWS; y++) {
      for (int x = 0; x < COLS; x++) {
        XiangqiPiece piece = pieceAt(x, y);
        if (piece == null) {
          brdStr += " .";
        } else {
          if (piece.rank == 0) {
            brdStr += " p";
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
  int rank;
  boolean isRed;
}
