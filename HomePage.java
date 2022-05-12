import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {
    private JButton channelsButton;
    private JPanel homePanel;
    private JButton customerButton;
    private JButton providersButton;
    private JButton paymentsButton;
    private static JFrame homeFrame;

    public static void main(String[] args) {

    }

    public HomePage() {
        System.out.println("Welcome to HomePage");
        homeFrame = new JFrame("Home Page");
        homeFrame.setContentPane(homePanel);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.pack();
        homeFrame.setVisible(true);

        channelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.dispose();
                new Channels();
            }
        });
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.dispose();
                new Customer();
            }
        });
        providersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.dispose();
                new Providers();
            }
        });
        paymentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.dispose();
                new Payments();
            }
        });
    }
}