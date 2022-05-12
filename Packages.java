import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Packages extends JDialog {
    private JTextField tfName;
    private JPanel packagesPanel;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField tfId;
    private JButton searchButton;
    private JButton returnToChannelsButton;
    private JButton homeButton;
    private JButton reportButton;
    private JTextField tfMinPrice;
    private static JFrame packagesFrame;


    public static void main(String[] args) {

    }

    public Packages(){
        Connect();
        packagesFrame = new JFrame("Packages");
        packagesFrame.setContentPane(packagesPanel);
        packagesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        packagesFrame.pack();
        packagesFrame.setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, price;

                name = tfName.getText();
                price = tfMinPrice.getText();
                try{
                    pst = con.prepareStatement("INSERT INTO packages (name, minimum_price) VALUES(?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.executeUpdate();
                    tfName.setText("");
                    tfMinPrice.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                if (name.isEmpty() || price.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null,"This package is created successfully!");
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String id = tfId.getText();
                    pst = con.prepareStatement("SELECT name, minimum_price FROM packages WHERE id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        tfName.setText(name);
                        if(name.isEmpty() ){
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else
                    {
                        tfName.setText("");
                        JOptionPane.showMessageDialog(null,"This ID does not exists!");
                    }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, id, price;
                name = tfName.getText();
                id = tfId.getText();
                price = tfMinPrice.getText();

                try {

                    pst = con.prepareStatement("UPDATE packages SET name = ?, minimum_price =? WHERE id = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, id);

                    pst.executeUpdate();

                    tfName.setText("");
                    tfId.setText("");
                    tfMinPrice.setText("");

                    tfName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
                if(name.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null, "The package`s information is updated!");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, bid;
                name = tfName.getText();
                bid = tfId.getText();
                try {
                    pst = con.prepareStatement("DELETE FROM packages WHERE id = ?");
                    pst.setString(1, bid);

                    pst.executeUpdate();
                    if(name.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "This contract is deleted successfully!");
                    }

                    tfName.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
        returnToChannelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                packagesFrame.dispose();
                new Channels();
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                packagesFrame.dispose();
                new HomePage();
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TablePackages();
            }
        });
    }
    Connection con;
    PreparedStatement pst;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bulsatcom", "root", "RumenVr01");
            System.out.println("Successful connection to the database! - packages");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}