import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ContractCustomer extends JDialog {
    private JPanel custContrPanel;
    private JTextField tfPackagePrice;
    private JTextField tfFirstDate;
    private JTextField tfLastDate;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField tfContrNumber;
    private JButton searchButton;
    private JButton searchPackageSIDButton;
    private JTextField tfPackName;
    private JTextField tfPackId;
    private JButton searchCustomerSIDButton;
    private JTextField tfCustEgn;
    private JTextField tfCustId;
    private JButton seePackagesButton;
    private JButton button1;
    private JButton returnToCustomersButton;
    private JButton reportButton;
    private static JFrame contractCustomerFrame;


    public static void main(String[] args) {

    }

    public ContractCustomer(){
        Connect();
        contractCustomerFrame = new JFrame("Customer`s contract");
        contractCustomerFrame.setContentPane(custContrPanel);
        contractCustomerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contractCustomerFrame.pack();
        contractCustomerFrame.setVisible(true);

        searchPackageSIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String providerName = tfPackName.getText();
                    pst = con.prepareStatement("SELECT id FROM packages WHERE name = ?");
                    pst.setString(1, providerName);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String packId = rs.getString(1);
                        tfPackId.setText(packId);

                        if (packId.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        tfPackId.setText("");
                        JOptionPane.showMessageDialog(null, "This package`s name does not exists!");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customer_id, package_id, price, firstDate, lastDate;
                customer_id = tfCustId.getText();
                package_id = tfPackId.getText();
                price = tfPackagePrice.getText();
                firstDate = tfFirstDate.getText();
                lastDate = tfLastDate.getText();

                try{
                    pst = con.prepareStatement("INSERT INTO contract_customer(customer_id, package_id, price, firstDate, lastDate) VALUES(?,?,?,?,?)");
                    pst.setString(1, customer_id);
                    pst.setString(2, package_id);
                    pst.setString(3, price);
                    pst.setString(4, firstDate);
                    pst.setString(5, lastDate);

                    pst.executeUpdate();
                    tfCustId.setText("");
                    tfPackId.setText("");
                    tfPackagePrice.setText("");
                    tfFirstDate.setText("");
                    tfLastDate.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                if (customer_id.isEmpty() || package_id.isEmpty() || price.isEmpty() || firstDate.isEmpty() || lastDate.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null,"This contract is created successfully!");
                }
            }
        });
        searchCustomerSIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String customerEgn = tfCustEgn.getText();
                    pst = con.prepareStatement("SELECT id FROM customer WHERE egn = ?");
                    pst.setString(1, customerEgn);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String custId = rs.getString(1);
                        tfCustId.setText(custId);

                        if(custId.isEmpty()){
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else
                    {
                        tfCustId.setText("");
                        JOptionPane.showMessageDialog(null,"This customer`s EGN does not exists!");
                    }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String contrId = tfContrNumber.getText();
                    pst = con.prepareStatement("SELECT customer_id, package_id, price, firstDate, lastDate FROM contract_customer WHERE id = ?");
                    pst.setString(1, contrId);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String customer_id = rs.getString(1);
                        String package_id = rs.getString(2);
                        String price = rs.getString(3);
                        String firstDate = rs.getString(4);
                        String lastDate = rs.getString(5);

                        tfCustId.setText(customer_id);
                        tfPackId.setText(package_id);
                        tfPackagePrice.setText(price);
                        tfFirstDate.setText(firstDate);
                        tfLastDate.setText(lastDate);

                        if(customer_id.isEmpty() || price.isEmpty() || package_id.isEmpty() || firstDate.isEmpty() || lastDate.isEmpty()){
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else
                    {
                        tfCustId.setText("");
                        tfContrNumber.setText("");
                        tfPackId.setText("");
                        tfPackagePrice.setText("");
                        tfFirstDate.setText("");
                        tfLastDate.setText("");

                        JOptionPane.showMessageDialog(null,"This contract`s name does not exists!");
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
                String customer_id, package_id, price, firstDate, lastDate, contrId;
                customer_id = tfCustId.getText();
                package_id = tfPackId.getText();
                price = tfPackagePrice.getText();
                firstDate = tfFirstDate.getText();
                lastDate = tfLastDate.getText();
                contrId = tfContrNumber.getText();

                try{
                    pst = con.prepareStatement("UPDATE contract_customer SET customer_id = ?, package_id = ?, price = ?, firstDate = ?, lastDate = ? WHERE id = ?");
                    pst.setString(1, customer_id);
                    pst.setString(2, package_id);
                    pst.setString(3, price);
                    pst.setString(4, firstDate);
                    pst.setString(5, lastDate);
                    pst.setString(6, contrId);


                    pst.executeUpdate();
                    tfCustId.setText("");
                    tfPackId.setText("");
                    tfPackagePrice.setText("");
                    tfFirstDate.setText("");
                    tfLastDate.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                if (customer_id.isEmpty() || package_id.isEmpty() ||  price.isEmpty() || firstDate.isEmpty() || lastDate.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null,"This contract is updated successfully!");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String customer_id, package_id, price, firstDate, lastDate, contrId;
                    customer_id = tfCustId.getText();
                    package_id = tfPackId.getText();
                    price = tfPackagePrice.getText();
                    firstDate = tfFirstDate.getText();
                    lastDate = tfLastDate.getText();
                    contrId = tfContrNumber.getText();

                    try{
                        pst = con.prepareStatement("DELETE from contract_customer WHERE id = ?");
                        pst.setString(1, contrId);


                        pst.executeUpdate();
                        tfCustId.setText("");
                        tfPackId.setText("");
                        tfPackagePrice.setText("");
                        tfFirstDate.setText("");
                        tfLastDate.setText("");
                    }
                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }
                    if (customer_id.isEmpty() || package_id.isEmpty() || price.isEmpty() || firstDate.isEmpty() || lastDate.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"This contract is deleted successfully!");
                    }
            }
        });
        seePackagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableChanPack();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contractCustomerFrame.dispose();
                new HomePage();
            }
        });
        returnToCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contractCustomerFrame.dispose();
                new Customer();
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

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bulsatcom", "root", "RumenVr01");
            System.out.println("Successful connection to the database! - customer`s contract");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}