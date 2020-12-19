/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2020/12/19 1:53
 *  Description:
 **************************************************************************** */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BaseFrameScreen extends JFrame implements Runnable {

    private int w;

    private int h;

    private int x;

    private int y;

    private BufferedImage image;

    private Robot robot;

    private Rectangle rt;

    private Graphics g;

    public BaseFrameScreen() {
        init();
        this.setBounds(x, y, w, h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Thread thread = new Thread(this);
        thread.start();
    }

    private void init() {
        try {
            int tookW = (int) Toolkit.getDefaultToolkit().getScreenSize()
                    .getWidth();
            int tookH = (int) Toolkit.getDefaultToolkit().getScreenSize()
                    .getHeight();
            x = 150;
            y = 100;
            w = tookW - 300;
            h = tookH - 200;
            robot = new Robot();
            rt = new Rectangle(0, 0, tookW, tookH);
            image = new BufferedImage(tookW, tookH,
                    BufferedImage.TYPE_3BYTE_BGR);
            g = image.getGraphics();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制方法
     */
    public void paint(Graphics gr) {
        BufferedImage img = robot.createScreenCapture(rt);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        gr.drawImage(image, 0, 0, this);
    }

    public static void main(String[] args) {
        new BaseFrameScreen();
    }

    public void run() {
        try {
            while (true) {
                repaint();
                Thread.sleep(20);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
