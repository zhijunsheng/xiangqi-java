import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import java.util.Arrays;
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
    setSize(800, 800);
    setLocation(200, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private static Map<String, Image> createPieceImages() throws IOException {
    Map<String, Image> keyNameValueImage= new HashMap<>();
    Set<String> names = new HashSet<>(Arrays.asList(
          "bj", "bm", "bx", "bs", "bb", "bp", "bz", 
          "rj", "rm", "rx", "rs", "rb", "rp", "rz"));
    for (String imgName : names) {
        String path = "./img/" + imgName + ".png";
        File file = new File(path);
        keyNameValueImage.put(imgName, ImageIO.read(file).getScaledInstance(XiangqiPanel.CELL_WIDTH, XiangqiPanel.CELL_WIDTH, Image.SCALE_SMOOTH));
    }
    return  keyNameValueImage;
  }
  
  public static void main(String[] args) throws IOException {
    Map<String, Image> keyNameValueImage = createPieceImages();

    XiangqiEngine xiangqiEngine = new XiangqiEngine(XiangqiEngine.initialPieces());
    System.out.println(xiangqiEngine);   

    XiangqiFrame xiangqiFrame = new XiangqiFrame();
    XiangqiPanel xiangqiPanel = new XiangqiPanel(xiangqiEngine, keyNameValueImage);
    xiangqiFrame.getContentPane().add(xiangqiPanel, BorderLayout.CENTER);
    xiangqiFrame.setVisible(true);
  }
}

class XiangqiPanel extends JPanel implements MouseListener, MouseMotionListener {
  final static int ORIGIN_X = 83;
  final static int ORIGIN_Y = 83;
  final static int CELL_WIDTH = 67;
  final static int CELL_HEIGHT = 67;

  private Image movingPieceImage;
  private Point movingPieceScreenLocation;
  private Point logicalFrom;

  private XiangqiEngine xiangqiEngine;
  private Map<String, Image> keyNameValueImage;

  XiangqiPanel(XiangqiEngine xiangqiEngine, Map<String, Image> keyNameValueImage) {
    this.xiangqiEngine = xiangqiEngine;
    this.keyNameValueImage = keyNameValueImage;
    addMouseListener(this);
    addMouseMotionListener(this);
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
    XiangqiPiece pickedPiece = xiangqiEngine.pieceAt(p);
    if (pickedPiece != null) {
      logicalFrom = p;
      movingPieceImage = getPieceImage(pickedPiece.imgName); 
    }
  }

  public void mouseReleased(MouseEvent me) {
    if (logicalFrom == null) {
      return;
    }
    Point logicalTo = screenToLogical(me.getPoint());
    System.out.print("(" + logicalFrom.x + ", " + logicalFrom.y + ") -> (" + logicalTo.x + ", " + logicalTo.y + ") ");   

    boolean kingCaptured = false;
    if (xiangqiEngine.isValidMove(logicalFrom, logicalTo)) {
      System.out.println("valid move");   
      kingCaptured = xiangqiEngine.move(logicalFrom, logicalTo);
    } else {
      System.out.println("invalid move");   
    }

    if (kingCaptured) {
      JOptionPane.showMessageDialog(this, "Game Over");
      xiangqiEngine.resetGame();
    }

    logicalFrom = null;
    movingPieceImage = null;
    movingPieceScreenLocation = null;
    repaint();
    System.out.println(xiangqiEngine);   
  }

  // MouseMotionListener
  
  public void mouseDragged(MouseEvent me) {
    Point p = me.getPoint();
    movingPieceScreenLocation = new Point(p.x - CELL_WIDTH / 2, p.y - CELL_WIDTH / 2);
    repaint();
  }

  public void mouseMoved(MouseEvent me) {}
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.black);
    drawGrid(g);
    drawPieces(g);
    drawWhoseTurn(g);
    if (movingPieceImage != null) {
      g.drawImage(movingPieceImage, movingPieceScreenLocation.x, movingPieceScreenLocation.y, null);
    }
  }

  private void drawWhoseTurn(Graphics g) {
    if (xiangqiEngine.isRedTurn()) {
      g.setColor(Color.RED);
      g.fillRect(ORIGIN_X + XiangqiEngine.COLS * CELL_WIDTH, ORIGIN_Y, CELL_WIDTH, CELL_WIDTH);
    } else {
      g.setColor(Color.BLACK);
      g.fillRect(ORIGIN_X + XiangqiEngine.COLS * CELL_WIDTH, ORIGIN_Y + (XiangqiEngine.ROWS - 1) * CELL_HEIGHT - CELL_WIDTH, CELL_WIDTH, CELL_WIDTH);
    }
  }

  private Image getPieceImage(String imgName) {
    return keyNameValueImage.get(imgName);
  }

  private void drawPieces(Graphics g) {
    for(XiangqiPiece piece: xiangqiEngine.getPieces()) {
      if (logicalFrom != null && logicalFrom.x == piece.x && logicalFrom.y == piece.y) {
        continue;
      }
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
  BISHOP,
  KNIGHT,
  ROOK,
  KING
}

class XiangqiEngine {
  final static int ROWS = 10;
  final static int COLS = 9;
  private Set<XiangqiPiece> pieces;
  private boolean isRedTurn = true;
  
  XiangqiEngine(Set<XiangqiPiece> pieces) {
    this.pieces = pieces;
  }

  Set<XiangqiPiece> getPieces() {
    return pieces;
  }

  void resetGame() {
    pieces = initialPieces();
    isRedTurn = true;
  }

  boolean isRedTurn() {
    return isRedTurn;
  }
  
  boolean isValidMove(Point from, Point to) {
    if (isValid(from, to)) {
      HashSet<XiangqiPiece> tmpPieces = new HashSet<XiangqiPiece>(pieces);
      XiangqiEngine tmpEngine = new XiangqiEngine(tmpPieces);
      boolean tmpKingCaptured = tmpEngine.move(from, to);
      if (tmpKingCaptured) { 
        return true;
      } else {
        XiangqiPiece redKing = null;
        XiangqiPiece blackKing = null;
        for (XiangqiPiece p: tmpPieces) {
          if (p.rank == Rank.KING) {
            if (p.isRed) {
              redKing = p;
            } else {
              blackKing = p;
            }
          }
        }
        return redKing.x != blackKing.x || tmpEngine.numPiecesBetween(new Point(redKing.x, redKing.y), new Point(blackKing.x, blackKing.y)) > 0;
      }
    }
    return false;
  }

  private boolean isValid(Point from, Point to) {
    if (from == null || to == null || from == to || !insideBoard(to) || sameColor(from, to)) {
      return false;
    }

    XiangqiPiece movingPiece = pieceAt(from);
    if (movingPiece == null || isRedTurn != movingPiece.isRed) {
      return false;
    }
    switch (movingPiece.rank) {
      case GUARD:
        return isValidGuardMove(from, to, movingPiece.isRed);
      case KING: 
        return isValidKingMove(from, to, movingPiece.isRed); 
      case BISHOP:
        return isValidBishopMove(from, to, movingPiece.isRed);
      case KNIGHT:
        return isValidKnightMove(from, to);
      case ROOK:
        return isValidRookMove(from, to);
      case CANNON:
        return isValidCannonMove(from, to);
      case PAWN:
        return isValidPawnMove(from, to, movingPiece.isRed);
    }

    return true;
  }

  boolean move(Point from, Point to) {
    XiangqiPiece movingPiece = pieceAt(from);
    XiangqiPiece targetPiece = pieceAt(to);
    pieces.remove(movingPiece);
    pieces.remove(targetPiece);
    addPiece(pieces, to.x, to.y, movingPiece.rank, movingPiece.isRed, movingPiece.imgName);
    isRedTurn = !isRedTurn;
    return targetPiece != null && targetPiece.rank == Rank.KING;
  }

  private boolean isValidPawnMove(Point from, Point to, boolean isRed) {
    boolean oneStepForward = isStraight(from, to) && (to.y - from.y) * (isRed ? 1 : -1) == 1;
    if (inSelfSide(from, isRed)) {
      return oneStepForward;
    } else {
      return isStraight(from, to) && Math.abs(from.x - to.x) == 1 || oneStepForward;
    }
  }

  private boolean isValidCannonMove(Point from, Point to) {
    if (pieceAt(to) == null) {
      return isValidRookMove(from, to);
    } else {
      return numPiecesBetween(from, to) == 1 && !sameColor(from, to);
    }
  }
    
  private boolean isValidRookMove(Point from, Point to) {
    return isStraight(from, to) && numPiecesBetween(from, to) == 0;
  }

  private boolean isValidKnightMove(Point from, Point to) {
    if (Math.abs(from.x - to.x) == 1 && Math.abs(from.y - to.y) == 2) {
      return pieceAt(from.x, (from.y + to.y)/2) == null;
    } else if (Math.abs(from.x - to.x) == 2 && Math.abs(from.y - to.y) == 1) {
      return pieceAt((from.x + to.x)/2, from.y) == null;
    }
    return false;
  }

  private boolean isValidBishopMove(Point from, Point to, boolean isRed) {
    if (!inSelfSide(to, isRed) || pieceAt((from.x + to.x)/2, (from.y + to.y)/2) != null) {
      return false;
    }

    return Math.abs(from.x - to.x) == 2 && Math.abs(from.y - to.y) == 2;
  }

  private boolean isValidKingMove(Point from, Point to, boolean isRed) {
    if (!insidePalace(to, isRed)) {
      return false;
    }

    XiangqiPiece opponentKing = null;
    for (XiangqiPiece p: pieces) {
      if (p.rank == Rank.KING && p.isRed != isRed) {
        opponentKing = p;
        break;
      }
    }
    if (to.x == opponentKing.x && numPiecesBetween(to, new Point(opponentKing.x, opponentKing.y)) == 0) {
      return false;
    }

    return isStraight(from, to) && (Math.abs(from.y - to.y) == 1 || Math.abs(from.x - to.x) == 1);
  }

  private boolean isValidGuardMove(Point from, Point to, boolean isRed) {
    if (!insidePalace(to, isRed)) {
      return false;
    }

    return Math.abs(from.x - to.x) == 1 && Math.abs(from.y - to.y) == 1;
  }

  private boolean insideBoard(Point location) {
    return location.x >= 0 && location.x <= 8 && location.y >= 0 && location.y <= 9;
  }

  private boolean inSelfSide(Point location, boolean isRed) {
    if (!insideBoard(location)) {
      return false;
    }

    if (isRed) {
      return location.y <= 4;
    } else {
      return location.y >= 5;
    }
  }

  private boolean sameColor(Point from, Point to) {
    XiangqiPiece fromP = pieceAt(from);
    XiangqiPiece toP = pieceAt(to);
    return fromP != null && toP != null && fromP.isRed == toP.isRed;
  }

  private boolean isStraight(Point from, Point to) {
    return from.x == to.x || from.y == to.y;
  }

  private boolean insidePalace(Point location, boolean isRed) {
    if (isRed) {
      return location.x >= 3 && location.x <= 5 && location.y >= 0 && location.y <= 2;
    } else {
      return location.x >= 3 && location.x <= 5 && location.y >= 7 && location.y <= 9;
    }
  }

  private int numPiecesBetween(Point from, Point to) {
    if (from.x != to.x && from.y != to.y || from.x == to.x && Math.abs(from.y - to.y) <= 1 || from.y == to.y && Math.abs(from.x - to.x) <= 1) {
      return 0;
    }
    int pieceCnt = 0;
    int smallerCoordinate;
    int largerCoordinate;
    if (from.x == to.x) {
      smallerCoordinate = Math.min(from.y, to.y);
      largerCoordinate = Math.max(from.y, to.y);
      for (int y = smallerCoordinate + 1; y < largerCoordinate; y++) {
        if (pieceAt(from.x, y) != null) {
          pieceCnt++;
        }
      }
    } else {
      smallerCoordinate = Math.min(from.x, to.x);
      largerCoordinate = Math.max(from.x, to.x);
      for (int x = smallerCoordinate + 1; x < largerCoordinate; x++) {
        if (pieceAt(x, from.y) != null) {
          pieceCnt++;
        }
      }
    }
    return pieceCnt;
  }

  static Set<XiangqiPiece> initialPieces() {
    Set<XiangqiPiece> pieces = new HashSet<XiangqiPiece>();
    for (int i = 0; i < 5; i++) {
      addPiece(pieces, 2 * i, 3, Rank.PAWN, true, "rz");
      addPiece(pieces, 2 * i, 6, Rank.PAWN, false, "bz");
    }

    for (int i = 0; i < 2; i++) {
      addPiece(pieces, 8 * i, 0, Rank.ROOK, true, "rj");
      addPiece(pieces, 8 * i, 9, Rank.ROOK, false, "bj");
      addPiece(pieces, 1 + 6 * i, 0, Rank.KNIGHT, true, "rm");
      addPiece(pieces, 1 + 6 * i, 9, Rank.KNIGHT, false, "bm");
      addPiece(pieces, 2 + 4 * i, 0, Rank.BISHOP, true, "rx");
      addPiece(pieces, 2 + 4 * i, 9, Rank.BISHOP, false, "bx");
      addPiece(pieces, 3 + 2 * i, 0, Rank.GUARD, true, "rs");
      addPiece(pieces, 3 + 2 * i, 9, Rank.GUARD, false, "bs");
      addPiece(pieces, 1 + 6 * i, 2, Rank.CANNON, true, "rp");
      addPiece(pieces, 1 + 6 * i, 7, Rank.CANNON, false, "bp");
    }
    
    addPiece(pieces, 4, 0, Rank.KING, true, "rb");
    addPiece(pieces, 4, 9, Rank.KING, false, "bb");
    return pieces;
  }

  private static void addPiece(Set<XiangqiPiece> pieces, int col, int row, Rank rank, boolean isRed, String imgName) {
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
            case BISHOP: brdStr += piece.isRed ? " B" : " b"; break;
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

  XiangqiPiece pieceAt(Point location) {
    return pieceAt(location.x, location.y);
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

  public String toString() {
    return rankString() + "(" + x + ", " + y + ")";
  }

  private String rankString() {
    String rkStr = "";
    switch (rank) {
      case PAWN: rkStr += isRed ? " P" : " p"; break;
      case CANNON: rkStr += isRed ? " C" : " c"; break;
      case GUARD: rkStr += isRed ? " G" : " g"; break;
      case BISHOP: rkStr += isRed ? " B" : " b"; break;
      case KNIGHT: rkStr += isRed ? " N" : " n"; break;
      case ROOK: rkStr += isRed ? " R" : " r"; break;
      case KING: rkStr += isRed ? " K" : " k"; break;
    }
    return rkStr;
    
  }
}
