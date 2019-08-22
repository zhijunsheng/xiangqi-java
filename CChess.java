class CChess {
  public static void main(String[] args) {
    CChessBoard brd = new CChessBoard();
    System.out.println(brd);
  }
}

class CChessBoard {
  public String toString() {
    String brdStr = "";
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 9; col++) {
        brdStr += ". ";
      }
      brdStr += "\n";
    }
    return brdStr;
  } 
}
