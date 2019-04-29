import javax.swing.JFrame;

class XiangqiFrame extends JFrame {

  XiangqiFrame() {
    setTitle("Xiang Qi");
    setSize(150, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public static void main(String[] args) {
    XiangqiFrame xiangqiFrame = new XiangqiFrame();
    xiangqiFrame.setVisible(true);
  }
}
