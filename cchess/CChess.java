import java.util.Set;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.event.MouseMotionListener;

class CChess {
  static Map<String, Image> keyNameValueImage = new HashMap<>();

  CChess() {
    CChessBoard brd = new CChessBoard();
    System.out.println(brd);

    JFrame f = new JFrame("Chinese Chess");
    f.setSize(800, 800);
    f.setLocation(50, 50);
    f.add(new CChessPanel(brd));
    f.setVisible(true);
  }

  public static void main(String[] args) throws IOException {
    Set<String> imgNames = new HashSet<>(Arrays.asList(
          "bj", "bm", "bx", "bs", "bb", "bp", "bz",
          "rj", "rm", "rx", "rs", "rb", "rp", "rz"));
    for (String imgName : imgNames) {
      File imgFile = new File("../img/" + imgName + ".png");
      keyNameValueImage.put(imgName, ImageIO.read(imgFile).getScaledInstance(CChessPanel.side, CChessPanel.side, Image.SCALE_SMOOTH)); 
    }
    new CChess();
  }
}

class CChessPanel extends JPanel implements MouseListener, MouseMotionListener {
  static int orgX = 83, orgY = 83, side = 67;

  private CChessBoard brd;
  private Point fromColRow;
  private Point movingPieceXY;
  private Image movingPieceImage;

  CChessPanel(CChessBoard brd) {
    this.brd = brd;
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  private Point xyToColRow(Point xy) {
    return new Point((xy.x - orgX + side/2)/side, (xy.y - orgY + side/2)/side);
  }

  public void mousePressed(MouseEvent me) {
    fromColRow = xyToColRow(me.getPoint());
    Piece movingPiece = brd.pieceAt(fromColRow.x, fromColRow.y);
    if (movingPiece != null) {
      movingPieceImage = CChess.keyNameValueImage.get(movingPiece.imgName);
    }
  }
  
  public void mouseReleased(MouseEvent me) {
    if (fromColRow == null) return;
    Point toColRow = xyToColRow(me.getPoint());
    if (brd.validMove(fromColRow.x, fromColRow.y, toColRow.x, toColRow.y)) {
      brd.movePiece(fromColRow.x, fromColRow.y, toColRow.x, toColRow.y);
      System.out.println(brd);
    }

    fromColRow = null;
    movingPieceXY = null;
    movingPieceImage = null;
    repaint();
  }

  public void mouseClicked(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseExited(MouseEvent me) {}

  public void mouseDragged(MouseEvent me) {
    Point mouseTip = me.getPoint();
    movingPieceXY = new Point(mouseTip.x - side/2, mouseTip.y - side/2);
    repaint();
  }

  public void mouseMoved(MouseEvent me) {}

  @Override
  public void paintComponent(Graphics g) {
    drawGrid(g);
    drawPieces(g);
    if (movingPieceImage != null) {
      g.drawImage(movingPieceImage, movingPieceXY.x, movingPieceXY.y, null);
    }
  }

  private void drawPieces(Graphics g) {
    for (Piece p : brd.getPieces()) {
      if (fromColRow != null && fromColRow.x == p.col && fromColRow.y == p.row) {
        continue; // skip moving piece since its image is drawn as movingPieceImage
      }
      Image img = CChess.keyNameValueImage.get(p.imgName);
      g.drawImage(img, orgX + side * p.col - side/2, orgY + side * p.row - side/2, this);
    }
  }

  private void drawGrid(Graphics g) {
    for (int i = 0; i < CChessBoard.cols; i++) {
      g.drawLine(orgX + i * side, orgY,
                 orgX + i * side, orgY + 4 * side);
      g.drawLine(orgX + i * side, orgY + 5 * side,
                 orgX + i * side, orgY + 9 * side);
    }
    for (int i = 0; i < CChessBoard.rows; i++) {
      g.drawLine(orgX,            orgY + i * side,
                 orgX + 8 * side, orgY + i * side);
    }
    for (int i = 0; i < 2; i++) {
      g.drawLine(orgX + 3 * side, orgY + i * 7 * side,
                 orgX + 5 * side, orgY + (2 + i * 7) * side);
      g.drawLine(orgX + 5 * side, orgY + i * 7 * side,
                 orgX + 3 * side, orgY + (2 + i * 7) * side);
      g.drawLine(orgX + 8 * i * side, orgY + 4 * side,
                 orgX + 8 * i * side, orgY + 5 * side);
    }
    for (int i = 0; i < 2; i++) {
      drawStarAt(g, 1 + i * 6, 2);
      drawStarAt(g, 1 + i * 6, 7);
      drawHalfStarAt(g, 0, 3, false);
      drawHalfStarAt(g, 0, 6, false);
      drawHalfStarAt(g, 8, 3, true);
      drawHalfStarAt(g, 8, 6, true);
    }
    for (int i = 0; i < 3; i++) {
      drawStarAt(g, 2 + i * 2, 3);
      drawStarAt(g, 2 + i * 2, 6);
    }
    int margin = side/15;
    g.drawRect(orgX - margin, 
               orgY - margin, 
               (CChessBoard.cols - 1)* side + 2 * margin, 
               (CChessBoard.rows - 1)* side + 2 * margin); 
  }

  private void drawStarAt(Graphics g, int col, int row) {
    drawHalfStarAt(g, col, row, true);
    drawHalfStarAt(g, col, row, false);
  }
    
  private void drawHalfStarAt(Graphics g, int col, int row, boolean left) {
    int gap = side / 9;
    int bar = side / 4;
    int hSign = left ? -1 : 1;
    int tipX = orgX + col * side + hSign * gap;
    for (int i = 0; i < 2; i++) {
      int vSign = -1 + i * 2;
      int tipY = orgY + row * side + vSign * gap;
      g.drawLine(tipX, tipY, tipX + hSign * bar , tipY); // horizontal
      g.drawLine(tipX, tipY, tipX, tipY + vSign * bar); // vertical
    }
  }
}

class CChessBoard {
  final static int rows = 10;
  final static int cols = 9;

  private Set<Piece> pieces = new HashSet<>();

  Set<Piece> getPieces() {
    return pieces;
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
    return 0; // neither straight nor diagonal
  }

  private boolean outOfPalace(int col, int row, boolean red) {
    if (red) {
      return col < 3 || col > 5 || row < 0 || row > 2;
    } else {
      return col < 3 || col > 5 || row < 7 || row > 9;
    }
  }

  private boolean selfSide(int row, boolean isRed) {
    return isRed ? row <= 4 : row >= 5;
  }

  private int numPiecesBetween(int fromCol, int fromRow,
                       int toCol, int toRow) {
    if (!isStraight(fromCol, fromRow, toCol, toRow)
     || steps(fromCol, fromRow, toCol, toRow) < 2) {
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

  private boolean selfKilling(int fromCol, int fromRow,
                      int toCol, int toRow, boolean isRed) {
    Piece target = pieceAt(toCol, toRow);
    return target != null && target.isRed == isRed;
  }

  private boolean isValidGuardMove(int fromCol, int fromRow, 
                           int toCol,   int toRow, boolean isRed) {
    if (outOfPalace(toCol, toRow, isRed)) { 
      return false; 
    }
    return isDiagonal(fromCol, fromRow, toCol, toRow) && 
           steps(fromCol, fromRow, toCol, toRow) == 1;
  }

  private boolean isValidKingMove(int fromCol, int fromRow,
                          int toCol, int toRow, boolean isRed) {
    if (outOfPalace(toCol, toRow, isRed)) {
      return false;
    }
    return isStraight(fromCol, fromRow, toCol, toRow) &&
                steps(fromCol, fromRow, toCol, toRow) == 1;
  }

  private boolean isValidKnightMove(int fromCol, int fromRow,
                            int toCol,   int toRow) {
    if (Math.abs(fromCol - toCol) == 1 && Math.abs(fromRow - toRow) == 2) {
      return pieceAt(fromCol, (fromRow + toRow)/2) == null;
    } else if (Math.abs(fromCol - toCol) == 2 && Math.abs(fromRow - toRow) == 1) {
      return pieceAt((fromCol + toCol)/2, fromRow) == null;
    }
    return false;
  }

  private boolean isValidBishopMove(int fromCol, int fromRow, 
                            int toCol,   int toRow, boolean isRed) {
    return selfSide(toRow, isRed) 
        && pieceAt((fromCol + toCol)/2, (fromRow + toRow)/2) == null
        && isDiagonal(fromCol, fromRow, toCol, toRow)
        && steps(fromCol, fromRow, toCol, toRow) == 2;
  }

  private boolean isValidRookMove(int fromCol, int fromRow,
                          int toCol,   int toRow) {
    return isStraight(fromCol, fromRow, toCol, toRow)
        && numPiecesBetween(fromCol, fromRow, toCol, toRow) == 0;
  }

  private boolean isValidCannonMove(int fromCol, int fromRow,
                            int toCol,   int toRow) {
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
    if (fromC == toC && fromR == toR // not moving
     || outOfBoard(toC, toR)) {
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
      pieces.add(new Piece(0 + i * 8, 0, true, Rank.ROOK, "rj"));
      pieces.add(new Piece(1 + i * 6, 0, true, Rank.KNIGHT, "rm"));
      pieces.add(new Piece(2 + i * 4, 0, true, Rank.BISHOP, "rx"));
      pieces.add(new Piece(3 + i * 2, 0, true, Rank.GUARD, "rs"));
      pieces.add(new Piece(1 + i * 6, 2, true, Rank.CANNON, "rp"));
      
      pieces.add(new Piece(0 + i * 8, 9, false, Rank.ROOK, "bj"));
      pieces.add(new Piece(1 + i * 6, 9, false, Rank.KNIGHT, "bm"));
      pieces.add(new Piece(2 + i * 4, 9, false, Rank.BISHOP, "bx"));
      pieces.add(new Piece(3 + i * 2, 9, false, Rank.GUARD, "bs"));
      pieces.add(new Piece(1 + i * 6, 7, false, Rank.CANNON, "bp"));
    }
    pieces.add(new Piece(4, 0, true, Rank.KING, "rb"));
    pieces.add(new Piece(4, 9, false, Rank.KING, "bb"));
    for (int i = 0; i < 5; i++) {
      pieces.add(new Piece(i * 2, 3, true, Rank.PAWN, "rz"));
      pieces.add(new Piece(i * 2, 6, false, Rank.PAWN, "bz"));
    }
  }

  void movePiece(int fromCol, int fromRow, int toCol, int toRow) {
    Piece movingP = pieceAt(fromCol, fromRow);
    Piece targetP = pieceAt(toCol, toRow);
    pieces.remove(movingP);
    pieces.remove(targetP);
    pieces.add(new Piece(toCol, toRow, movingP.isRed, movingP.rank, movingP.imgName));
  }

  Piece pieceAt(int col, int row) {
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
  String imgName;

  Piece(int col, int row, boolean isRed, Rank rank, String imgName) {
    this.col = col;
    this.row = row;
    this.isRed = isRed;
    this.rank = rank;
    this.imgName = imgName;
  }
}
