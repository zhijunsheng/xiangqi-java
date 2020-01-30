import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

class CChessPanel extends JPanel {
  int cSpace = 60;
  int orgX = -50000; 
  int orgY = -50000; 
 
  public void paintComponent(Graphics g) {
    orgX = (getWidth() - cSpace * 8) / 2;
    orgY = (getHeight() - cSpace * 9) / 2;
    System.out.println("" + orgX + " " +  getWidth() + " getHeight() = " + getHeight());
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

    drawStars(g);
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


  //   drawPieces();

}

class CChess {
  CChess(){
    JFrame f = new JFrame("CChess Board");  
    f.setSize(600,622);
    f.setLocation(30,40);
    CChessPanel brainDead = new CChessPanel();
    f.add(brainDead);
    f.setVisible(true);
  }

  public static void main (String[] args){
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
 /*
   CPieces[] pieces = new Piece[] {
      new Piece("RedKing", (5, 9)),
      new Piece("BlackKing", (5, 0)),
      new Piece("RedGuard1", (4, 9)),
      new Piece("RedGuard2", (6, 9)),
      new Piece("BlackGuard1", (4, 0)),
      new Piece("BlackGuard2", (6, 0)),
      new Piece("RedBishop1", (3, 9)),
      new Piece("RedBishop2", (7, 9)),
      new Piece("BlackBishop1", (3, 0)),
      new Piece("BlackBishop2", (7, 0))
      new Piece("RedKnight1", (2, 9)),
      new Piece("RedKnight2", (8, 9)),
      new Piece("BlackKnight1", (2, 0)),
      new Piece("BlackKnight2", (8, 0)),
      new Piece("RedRook1", (1, 9)),
      new Piece("RedRook2", (9, 9)),
      new Piece("BlackRook1", (1, 0)),
      new Piece("BlackRook2", (9, 0)),
      new Piece("RedCannon1", (2, 6)),
      new Piece("RedCannon2", (8, 6)),
      new Piece("BlackCannon1", (2, 2)),
      new Piece("BlackCannon2", (8, 2)),
    };
   
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

