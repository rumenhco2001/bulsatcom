import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LogIn extends JDialog{
    private JPanel logInPanel;
    private JTextField tfUsername;
    private JButton logInButton;
    private JPasswordField tfPass;
    private static JFrame logInFrame;

    public static void main(String[] args) {
        logInFrame = new JFrame("LogIn");
        logInFrame.setContentPane(new LogIn().logInPanel);
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.pack();
        logInFrame.setVisible(true);
    }

    public LogIn(){
        Connect();

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String user, pass;
                    user = tfUsername.getText();
                    pass = String.valueOf(tfPass.getPassword());

                    pst = con.prepareStatement("SELECT * FROM admins WHERE username = ? AND password = ?");
                    pst.setString(1, user);
                    pst.setString(2, pass);

                    ResultSet rs = pst.executeQuery();
                    if(user.isEmpty() || pass.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else if(rs.next()==true)
                    {
                        logInFrame.dispose();
                        new HomePage();

                    }
                    else
                    {
                        tfUsername.setText("");
                        tfPass.setText("");
                        JOptionPane.showMessageDialog(null,"The username or password is incorrect! Please try again!");
                    }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }


    Connection con;
    PreparedStatement pst;
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bulsatcom", "root", "RumenVr01");
            System.out.println("Successful connection to the database! - LogIn");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
