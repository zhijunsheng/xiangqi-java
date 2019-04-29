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
    System.out.println("in XiangqiEngine()");
    pieces = new ArrayList<XiangqiPiece>();
    
  }

  public String toString() {
    String brdStr = "";
    for (int x = 0; x < ROWS; x++) {
      for (int y = 0; y < COLS; y++) {
        System.out.print(" .");
      }
      System.out.println();
    }
    return "board string";
  }

  private XiangqiPiece pieceAt(int x, int y) {
    return null;

  }
}

class XiangqiPiece {
  int x;
  int y;
  int rank;
  boolean isRed;
}
