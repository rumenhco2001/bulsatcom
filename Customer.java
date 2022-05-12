import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Customer extends JDialog{
    private JTextField tfName;
    private JTextField tfEgn;
    private JTextField tfAddress;
    private JTextField tfTelephone;
    private JPanel customersPanel;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField tfEgnSearch;
    private JButton searchByEgnButton;
    private JTextField tfIdSearch;
    private JButton searchByIdButton;
    private JButton button1;
    private JButton editCustomerSContractButton;
    private JButton reportButton;
    private static JFrame customerFrame;

    public static void main(String[] args) {
    }

    public Customer(){
        Connect();

        customerFrame = new JFrame("Customers");
        customerFrame.setContentPane(customersPanel);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerFrame.pack();
        customerFrame.setVisible(true);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, egn, address, telephone;

                name = tfName.getText();
                egn = tfEgn.getText();
                address = tfAddress.getText();
                telephone = tfTelephone.getText();

                try {
                    pst = con.prepareStatement("INSERT INTO customer(name,egn, address, telephone) VALUES(?, ?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, egn);
                    pst.setString(3, address);
                    pst.setString(4, telephone);

                    pst.executeUpdate();

                    tfName.setText("");
                    tfEgn.setText("");
                    tfAddress.setText("");
                    tfTelephone.setText("");
                    tfName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                if(name.isEmpty() || egn.isEmpty() || address.isEmpty() || telephone.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null,"This customer is added successfully!");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, egn, address, telephone, customerId;

                name = tfName.getText();
                egn = tfEgn.getText();
                address = tfAddress.getText();
                telephone = tfTelephone.getText();
                customerId = tfIdSearch.getText();

                try {

                    pst = con.prepareStatement("UPDATE customer SET name = ?, egn = ?, address = ?, telephone = ? WHERE id = ?");
                    pst.setString(1, name);
                    pst.setString(2, egn);
                    pst.setString(3, address);
                    pst.setString(4, telephone);
                    pst.setString(5, customerId);

                    pst.executeUpdate();

                    tfName.setText("");
                    tfEgn.setText("");
                    tfAddress.setText("");
                    tfTelephone.setText("");
                    tfIdSearch.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
                if(name.isEmpty() || egn.isEmpty() || address.isEmpty() || telephone.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null, "The contract`s information is updated!");
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerFrame.dispose();
                new HomePage();
            }
        });
        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String custId = tfIdSearch.getText();
                    pst = con.prepareStatement("SELECT name, egn, address, telephone, egn FROM customer WHERE id = ?");
                    pst.setString(1, custId);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String egn = rs.getString(2);
                        String address = rs.getString(3);
                        String telephone = rs.getString(4);
                        String egnSearch = rs.getString(5);

                        tfName.setText(name);
                        tfEgn.setText(egn);
                        tfAddress.setText(address);
                        tfTelephone.setText(telephone);
                        tfEgnSearch.setText(egnSearch);
                        if(name.isEmpty() || egn.isEmpty() || address.isEmpty() || telephone.isEmpty()){
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
                        tfEgn.setText("");
                        tfAddress.setText("");
                        tfTelephone.setText("");
                        JOptionPane.showMessageDialog(null,"This ID does not exists!");
                    }
                }

                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        searchByEgnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String egnSearch = tfEgnSearch.getText();
                    pst = con.prepareStatement("SELECT name, egn, address, telephone, id FROM customer WHERE egn = ?");
                    pst.setString(1, egnSearch);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String egn = rs.getString(2);
                        String address = rs.getString(3);
                        String telephone = rs.getString(4);
                        String id = rs.getString(5);

                        tfName.setText(name);
                        tfEgn.setText(egn);
                        tfAddress.setText(address);
                        tfTelephone.setText(telephone);
                        tfIdSearch.setText(id);
                        if(name.isEmpty() || egn.isEmpty() || address.isEmpty() || telephone.isEmpty()){
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
                        tfEgn.setText("");
                        tfAddress.setText("");
                        tfTelephone.setText("");
                        JOptionPane.showMessageDialog(null,"This EGN does not exists!");
                    }
                }

                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid;
                bid = tfIdSearch.getText();

                try {
                    pst = con.prepareStatement("DELETE FROM customer WHERE id = ?");
                    pst.setString(1, bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "This customer is deleted successfully!");

                    tfName.setText("");
                    tfEgn.setText("");
                    tfAddress.setText("");
                    tfTelephone.setText("");
                    tfName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        editCustomerSContractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerFrame.dispose();
                new ContractCustomer();
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableCustomers();
            }
        });
    }

    Connection con;
    PreparedStatement pst;

    public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bulsatcom", "root","RumenVr01");
            System.out.println("Successful connection to the database! - customers");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
