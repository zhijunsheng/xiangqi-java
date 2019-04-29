import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

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
    XiangqiPanel xiangqiPanel = new XiangqiPanel();
    xiangqiFrame.getContentPane().add(xiangqiPanel);
    xiangqiFrame.setVisible(true);
  }
}

class XiangqiPanel extends JPanel {
  final static int ORIGIN_X = 20;
  final static int ORIGIN_Y = 30;
  final static int CELL_WIDTH = 23;
  final static int CELL_HEIGHT = 31;
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.white);
    g.fillRect(10, 10, 400, 500);

    g.setColor(Color.black);
    drawGrid(g);
  }

  private void drawGrid(Graphics g) {
    for (int i = 0; i < XiangqiEngine.COLS; i++) {
      g.drawLine(ORIGIN_X + i * CELL_WIDTH, ORIGIN_Y, ORIGIN_X + i * CELL_WIDTH, ORIGIN_Y + 4 * CELL_HEIGHT); 
      g.drawLine(ORIGIN_X + i * CELL_WIDTH, ORIGIN_Y + 5 * CELL_HEIGHT, ORIGIN_X + i * CELL_WIDTH, ORIGIN_Y + (XiangqiEngine.ROWS - 1) * CELL_HEIGHT); 
    }

    for (int i = 0; i < XiangqiEngine.ROWS; i++) {
      g.drawLine(ORIGIN_X, ORIGIN_Y + i * CELL_HEIGHT, ORIGIN_X + (XiangqiEngine.COLS - 1) * CELL_WIDTH, ORIGIN_Y + i * CELL_HEIGHT); 
    }

    for (int i = 0; i < 2; i++) {
      g.drawLine(ORIGIN_X + 3 * CELL_WIDTH, ORIGIN_Y + i * 7 * CELL_HEIGHT, ORIGIN_X + 5 * CELL_WIDTH, ORIGIN_Y + (2 + i * 7) * CELL_HEIGHT); 
      g.drawLine(ORIGIN_X + 5 * CELL_WIDTH, ORIGIN_Y + i * 7 * CELL_HEIGHT, ORIGIN_X + 3 * CELL_WIDTH, ORIGIN_Y + (2 + i * 7) * CELL_HEIGHT); 
      g.drawLine(ORIGIN_X + 8 * i * CELL_WIDTH, ORIGIN_Y + 4 * CELL_HEIGHT, ORIGIN_X + 8 * i * CELL_WIDTH, ORIGIN_Y + 5 * CELL_HEIGHT); 
    }
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
