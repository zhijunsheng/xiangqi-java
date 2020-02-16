import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

class CChessPanel extends JPanel {
  static int cSpace = 60;
  int orgX = -50000; 
  int orgY = -50000; 
 
  public void paintComponent(Graphics g) {
    orgX = (getWidth() - cSpace * 8) / 2;
    orgY = (getHeight() - cSpace * 9) / 2;
    System.out.println("" + orgX + " " +  getWidth() + " getHeight() = " + getHeight());

    drawStars(g);
    drawBoard(g);
    drawPieces(g);
  }

  private void drawBoard(Graphics g) {
    for (int i = 0; i < 9; i++) {
      g.drawLine(orgX + i * cSpace, orgY, orgX + i * cSpace, orgY + cSpace * 4); 
      g.drawLine(orgX + i * cSpace, orgY + cSpace * 5, orgX + i * cSpace, orgY + cSpace * 9);
    }
    g.drawLine(orgX, orgY + cSpace * 4, orgX, orgY + cSpace * 5);
    g.drawLine(orgX + cSpace * 8, orgY + cSpace * 4, orgX + cSpace * 8, orgY + cSpace * 5);
    for (int j = 0; j < 10;j++) {
      g.drawLine(orgX, orgY + j * cSpace, orgX + cSpace * 8, orgY + j * cSpace);
    }
    g.drawLine(orgX + cSpace * 3, orgY, orgX + cSpace * 5, orgY + cSpace * 2);
    g.drawLine(orgX + cSpace * 5, orgY, orgX + cSpace * 3, orgY + cSpace * 2);
    g.drawLine(orgX + cSpace * 3, orgY + cSpace * 7, orgX + cSpace * 5, orgY + cSpace * 9);
    g.drawLine(orgX + cSpace * 5, orgY + cSpace * 7, orgX + cSpace * 3, orgY + cSpace * 9);
  }

  private void drawStars(Graphics g) {
    drawLstar(g, 0, 3);
    drawStar(g, 1, 2);
    drawStar(g, 2, 3);
    drawStar(g, 4, 3);
    drawStar(g, 6, 3);
    drawStar(g, 7, 2);
    drawRstar(g, 8, 3 );
    drawLstar(g, 0, 6);
    drawStar(g, 1, 7);
    drawStar(g, 2, 6);
    drawStar(g, 4, 6);
    drawStar(g, 6, 6);
    drawStar(g, 7, 7);
    drawRstar(g, 8, 6); 
  }

  private void drawStar(Graphics g, int col, int row) {
    drawLstar(g, col, row);
    drawRstar(g, col, row);
  }

  private void drawLstar(Graphics g, int col, int row) {
    int sLength = cSpace / 4;
    int refX = cSpace / 15;
    int refY = cSpace / 15;
 
    g.drawLine(orgX + cSpace * col + refX, orgY + cSpace * row + refY, orgX + cSpace * col + refX + sLength, orgY + cSpace * row + refY);
    g.drawLine(orgX + cSpace * col + refX, orgY + cSpace * row + sLength + refY, orgX + cSpace * col + refX, orgY + cSpace * row + refY);
    g.drawLine(orgX + cSpace * col + refX, orgY + cSpace * row - sLength - refY, orgX + cSpace * col + refX, orgY + cSpace * row - refY);
    g.drawLine(orgX + cSpace * col + refX, orgY + cSpace * row - refY, orgX + cSpace * col + sLength + refX, orgY + cSpace * row - refY);
  } 

  private void drawRstar(Graphics g, int col, int row) {
    int sLength = cSpace / 4;
    int refX = cSpace / 15;
    int refY = cSpace / 15;

    g.drawLine(orgX + cSpace * col - refX, orgY + cSpace * row - sLength - refY, orgX + cSpace * col - refX, orgY + cSpace * row - refY);
    g.drawLine(orgX + cSpace * col - refX, orgY + cSpace * row - refY, orgX + cSpace * col - refX - sLength, orgY + cSpace * row - refY);
    g.drawLine(orgX + cSpace * col - refX, orgY + cSpace * row + refY, orgX + cSpace * col - refX - sLength, orgY + cSpace * row + refY);
    g.drawLine(orgX + cSpace * col - refX, orgY + cSpace * row + sLength + refY, orgX + cSpace * col - refX, orgY + cSpace * row + refY);
  }

  private void drawPieces(Graphics g) {
    for(int i = 0; i < 2; i++) {
      drawPiece(g, 8 * i, 0, CChess.pieceImg2);
      drawPiece(g, 8 * i, 9, CChess.pieceImg9);
      drawPiece(g, 6 * i + 1, 0, CChess.pieceImg3);
      drawPiece(g, 6 * i + 1, 9, CChess.pieceImg10);
      drawPiece(g, 4 * i + 2, 0, CChess.pieceImg6);
      drawPiece(g, 4 * i + 2, 9, CChess.pieceImg13);
      drawPiece(g, 2 * i + 3, 0, CChess.pieceImg5);
      drawPiece(g, 2 * i + 3, 9, CChess.pieceImg12);
      drawPiece(g, 6 * i + 1, 2, CChess.pieceImg4);
      drawPiece(g, 6 * i + 1, 7, CChess.pieceImg11);
    }
    drawPiece(g, 4, 0, CChess.pieceImg);
    drawPiece(g, 4, 9, CChess.pieceImg8);

    for(int i = 0; i < 5; i++) {
      drawPiece(g, 2 * i, 3, CChess.pieceImg7);
      drawPiece(g, 2 * i, 6, CChess.pieceImg14);
    }
  }

  private void drawPiece(Graphics g, int col, int row, Image pieceImg) {
    g.drawImage(pieceImg, orgX + cSpace * col - cSpace / 2, orgY + cSpace * row - cSpace / 2, null);
  } 
}

class CChess {
  static Image pieceImg;
  static Image pieceImg2;
  static Image pieceImg3;
  static Image pieceImg4;
  static Image pieceImg5;
  static Image pieceImg6;
  static Image pieceImg7;
  static Image pieceImg8;
  static Image pieceImg9;
  static Image pieceImg10;
  static Image pieceImg11;
  static Image pieceImg12;
  static Image pieceImg13;
  static Image pieceImg14;

  CChess(){ // constructor of class CChess
    JFrame f = new JFrame("CChess Board");  
    f.setSize(600,622);
    f.setLocation(30,40);
    CChessPanel brainDead = new CChessPanel();
    f.add(brainDead);
    f.setVisible(true);


  }

  public static void main (String[] args) throws IOException {
    File imgFile1 = new File("img/bb.png");
    File imgFile2 = new File("img/bj.png");
    File imgFile3 = new File("img/bm.png");
    File imgFile4 = new File("img/bp.png");
    File imgFile5 = new File("img/bs.png");
    File imgFile6 = new File("img/bx.png");
    File imgFile7 = new File("img/bz.png");
    File imgFile8 = new File("img/rb.png");
    File imgFile9 = new File("img/rj.png");
    File imgFile10 = new File("img/rm.png");
    File imgFile11 = new File("img/rp.png");
    File imgFile12 = new File("img/rs.png");
    File imgFile13 = new File("img/rx.png");
    File imgFile14 = new File("img/rz.png");
    pieceImg = ImageIO.read(imgFile1);
    pieceImg2 = ImageIO.read(imgFile2);
    pieceImg3 = ImageIO.read(imgFile3);
    pieceImg4 = ImageIO.read(imgFile4);
    pieceImg5 = ImageIO.read(imgFile5);
    pieceImg6 = ImageIO.read(imgFile6);
    pieceImg7 = ImageIO.read(imgFile7);
    pieceImg8 = ImageIO.read(imgFile8);
    pieceImg9 = ImageIO.read(imgFile9);
    pieceImg10 = ImageIO.read(imgFile10);
    pieceImg11 = ImageIO.read(imgFile11);
    pieceImg12 = ImageIO.read(imgFile12);
    pieceImg13 = ImageIO.read(imgFile13);
    pieceImg14 = ImageIO.read(imgFile14);
    pieceImg = pieceImg.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg2 = pieceImg2.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg3 = pieceImg3.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg4 = pieceImg4.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg5 = pieceImg5.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg6 = pieceImg6.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg7 = pieceImg7.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg8 = pieceImg8.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg9 = pieceImg9.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg10 = pieceImg10.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg11 = pieceImg11.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg12 = pieceImg12.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg13 = pieceImg13.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    pieceImg14 = pieceImg14.getScaledInstance(CChessPanel.cSpace, CChessPanel.cSpace, Image.SCALE_SMOOTH);
    
    new CChess();
    CChessBoard brd = new CChessBoard();
    System.out.println(brd);
    
  //  CPiece redKing = new CPiece();
/*
    Student studentA = new Student("John", 12);
    System.out.println("Name: " + studentA.name + " Age: " + studentA.age);

    int[] ints = new int[]{4, 3, 6, 0};
    // System.out.println(ints[0]);
    // System.out.println(ints[1]);
    for (int i = 0; i < ints.length; i++) {
      System.out.println(ints[i]);
    }

    Student[] team = new Student[] {
      new Student("John", 12),
      new Student("Michael", 142),
      new Student("Susan", 52),
    };
    for (int i = 0; i < team.length; i++) {
      System.out.println("Name: " + team[i].name + "  " +  " Age: " + team[i].age);
    }
*/
  }
}

class CChessBoard {
  CPiece[] pieces = new CPiece[32];

  public String toString() {
    String brdStr = "";
    brdStr = brdStr + "  ";
    for (int k = 0; k < 9; k++) {
      brdStr = brdStr + " ";
      brdStr = brdStr + k; 
    }
    for (int j = 0; j < 10; j++) {
      brdStr = brdStr + " ";
      brdStr = brdStr + "\n" + " " + j;
      for (int i = 0; i < 9; i++) {
        brdStr = brdStr + " .";
      }
    }
    return brdStr;
  }
}

class Student {
  String name;
  int age;

  // constructor
  Student(String theName, int theAge) {
    name = theName;
    age = theAge;
  }
  
}

class CPiece {
  String name;
  int xCoordinate;
  int yCoordinate;

  CPiece(String theName, int theXCoordinate, int theYCoordinate) {
    name = theName;
    xCoordinate = theXCoordinate;
    yCoordinate = theYCoordinate;
  }

  public String toString() {
    String brdStr = "";
    brdStr = brdStr + "  ";
    for (int k = 0; k < 9; k++) {
      brdStr = brdStr + " ";
      brdStr = brdStr + k;
    }
    for (int j = 0; j < 10; j++) {
      brdStr = brdStr + " ";
      brdStr = brdStr + "\n" + " " + j;
      for (int i = 0; i < 9; i++) {
        brdStr = brdStr + " .";
      }
    }
    return brdStr;
  }
}
// try to design a class for our pieces
