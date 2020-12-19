import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame implements ActionListener {

    JTextArea ta = new JTextArea();

    JButton bu = new JButton("cut");

    public Test() {
        this.setBounds(100, 100, 500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(ta);
        this.add(jsp, BorderLayout.CENTER);
        bu.addActionListener(this);
        this.add(bu, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Test();
    }

    public void actionPerformed(ActionEvent e) {
        String s = ta.getText();
        String[] str = s.split(" [0-9]+\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
        }
        ta.setText(sb.toString());
    }

}
