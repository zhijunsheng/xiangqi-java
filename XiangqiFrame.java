import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

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
    XiangqiPanel xiangqiPanel = new XiangqiPanel(xiangqiEngine);
    xiangqiFrame.getContentPane().add(xiangqiPanel, BorderLayout.CENTER);
    xiangqiFrame.setVisible(true);
  }
}

class XiangqiPanel extends JPanel implements MouseListener, MouseMotionListener {
  final static int ORIGIN_X = 83;
  final static int ORIGIN_Y = 31;
  final static int CELL_WIDTH = 43;
  final static int CELL_HEIGHT = 53;

  private BufferedImage pickedPieceImage;
  private int pickedPieceX;
  private int pickedPieceY;
  private Point logicalFrom;

  private XiangqiEngine xiangqiEngine;
  private Map<String, BufferedImage> keyNameValueImage;

  XiangqiPanel(XiangqiEngine xiangqiEngine) {
    this.xiangqiEngine = xiangqiEngine;
    addMouseListener(this);
    addMouseMotionListener(this);
    keyNameValueImage = new HashMap<String, BufferedImage>();
  }

  // MouseListener
  
  public void mouseClicked(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseExited(MouseEvent me) {}

  private Point screenToLogical(Point screenPoint) {
    return new Point((int)((screenPoint.x - ORIGIN_X + 0.5 * CELL_WIDTH) / CELL_WIDTH), (int)((screenPoint.y - ORIGIN_Y + 0.5 * CELL_HEIGHT) / CELL_HEIGHT));  
  }

  public void mousePressed(MouseEvent me) {
    Point p = screenToLogical(me.getPoint());
    XiangqiPiece pickedPiece = xiangqiEngine.pieceAt(p.x, p.y);
    if (pickedPiece != null) {
      logicalFrom = p;
      pickedPieceImage = getPieceImage(pickedPiece.imgName); 
    }
  }

  public void mouseReleased(MouseEvent me) {
    Point p = screenToLogical(me.getPoint());
    System.out.println(p);
    pickedPieceImage = null;
    xiangqiEngine.move(logicalFrom, p);
    repaint();
    System.out.println(xiangqiEngine);   
  }

  // MouseMotionListener
  
  public void mouseDragged(MouseEvent me) {
    Point p = me.getPoint();
    pickedPieceX = p.x - CELL_WIDTH / 2;
    pickedPieceY = p.y - CELL_WIDTH / 2;
    repaint();
  }

  public void mouseMoved(MouseEvent me) {}
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.black);
    drawGrid(g);
    drawPieces(g);
    g.drawImage(pickedPieceImage, pickedPieceX, pickedPieceY, null);
  }

  // may return null
  private BufferedImage getPieceImage(String imgName) {
    BufferedImage img = keyNameValueImage.get(imgName);
    if (img == null) {
      try {
        String path = imgName + ".png";
        File file = new File(path);
        img = resize(ImageIO.read(file), CELL_WIDTH, CELL_WIDTH);
        keyNameValueImage.put(imgName, img);
      } catch(IOException ioe) {
        System.out.println("failed to load image " + imgName);
      }
    }
    return img;
  }

  private BufferedImage resize(BufferedImage img, int width, int height) {
    Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resized.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    return resized;
  }

  private void drawPieces(Graphics g) {
    for(XiangqiPiece piece: xiangqiEngine.getPieces()) {
      g.drawImage(getPieceImage(piece.imgName), ORIGIN_X + CELL_WIDTH * piece.x - (int)(0.5 * CELL_WIDTH), ORIGIN_Y + CELL_HEIGHT * piece.y - (int)(0.5 * CELL_HEIGHT), this);
    }
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
  private Set<XiangqiPiece> pieces;
  
  XiangqiEngine() {
    pieces = new HashSet<XiangqiPiece>();
    addInitialPieces();
  }

  Set<XiangqiPiece> getPieces() {
    return pieces;
  }

  void move(Point from, Point to) {
    XiangqiPiece piece = pieceAt(from.x, from.y);
    if (piece == null) {
      return;
    }
    pieces.remove(piece);
    addPiece(to.x, to.y, piece.rank, piece.isRed, piece.imgName);
    System.out.println(pieces.size());
  }

  private void addInitialPieces() {
    for (int i = 0; i < 5; i++) {
      addPiece(2 * i, 3, Rank.PAWN, true, "rz");
      addPiece(2 * i, 6, Rank.PAWN, false, "bz");
    }

    for (int i = 0; i < 2; i++) {
      addPiece(8 * i, 0, Rank.ROOK, true, "rj");
      addPiece(8 * i, 9, Rank.ROOK, false, "bj");
      addPiece(1 + 6 * i, 0, Rank.KNIGHT, true, "rm");
      addPiece(1 + 6 * i, 9, Rank.KNIGHT, false, "bm");
      addPiece(2 + 4 * i, 0, Rank.BISHIP, true, "rx");
      addPiece(2 + 4 * i, 9, Rank.BISHIP, false, "bx");
      addPiece(3 + 2 * i, 0, Rank.GUARD, true, "rs");
      addPiece(3 + 2 * i, 9, Rank.GUARD, false, "bs");
      addPiece(1 + 6 * i, 2, Rank.CANNON, true, "rp");
      addPiece(1 + 6 * i, 7, Rank.CANNON, false, "bp");
    }
    
    addPiece(4, 0, Rank.KING, true, "rb");
    addPiece(4, 9, Rank.KING, false, "bb");

  }

  private void addPiece(int col, int row, Rank rank, boolean isRed, String imgName) {
    pieces.add(new XiangqiPiece(col, row, rank, isRed, imgName));

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

  XiangqiPiece pieceAt(int x, int y) {
    for (XiangqiPiece piece: pieces) {
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
  String imgName;

  XiangqiPiece(int x, int y, Rank rank, boolean isRed, String imgName) {
    this.x = x;
    this.y = y;
    this.rank = rank;
    this.isRed = isRed;
    this.imgName = imgName;
  }
}
