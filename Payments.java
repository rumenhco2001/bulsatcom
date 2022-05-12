import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Payments extends JDialog {
    private JTable table1;
    private JButton displayDataButton;
    private JPanel paymentsPanel;
    private JScrollPane scrollPane;
    private JTextField tfEgn;
    private JButton resetButton;
    private JTextField tfBillId;
    private JButton payButton;
    private JButton homeButton;
    private JButton displayAllBillsButton;
    private static JFrame paymentsFrame;

    public static void main(String[] args) {

    }

    public Payments() {
        Connect();
        paymentsFrame = new JFrame("Payments");
        paymentsFrame.setContentPane(paymentsPanel);
        paymentsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentsFrame.pack();
        paymentsFrame.setVisible(true);

        displayDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String egn;
                    egn = tfEgn.getText();
                    if (egn.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    pst = con.prepareStatement("SELECT payments.id, customer.egn, payments.month, payments. year, packages.name, contract_customer.price, payments.dateOfPayment FROM payments\n" +
                            "JOIN customer ON payments.customer_id = customer.id\n" +
                            "JOIN contract_customer ON customer.id = contract_customer.id\n" +
                            "JOIN packages ON contract_customer.package_id = packages.id WHERE customer.egn = ?");
                    pst.setString(1, egn);
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }
                    String paymentsId, customerEgn, paymentsMonth, paymentsYear, packName, contractPrice, paymentsDateOfPayment;
                    while(rs.next()){
                        paymentsId = rs.getString(1);
                        customerEgn = rs.getString(2);
                        paymentsMonth = rs.getString(3);
                        paymentsYear = rs.getString(4);
                        packName = rs.getString(5);
                        contractPrice = rs.getString(6);
                        paymentsDateOfPayment = rs.getString(7);
                        String[] row = {paymentsId, customerEgn, paymentsMonth, paymentsYear, packName, contractPrice, paymentsDateOfPayment};
                        model.addRow(row);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(new DefaultTableModel());
            }
        });
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String billId;
                billId = tfBillId.getText();
                try {

                    pst = con.prepareStatement("update payments set dateOfPayment = now() where id = ?");
                    pst.setString(1, billId);

                    pst.executeUpdate();

                    tfBillId.setText("");
                    tfBillId.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
                if(billId.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null, "This bill is paid successfully!");
                }
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentsFrame.dispose();
                new HomePage();
            }
        });
        displayAllBillsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    pst = con.prepareStatement("SELECT payments.id, customer.egn, payments.month, payments. year, packages.name, contract_customer.price, payments.dateOfPayment FROM payments\n" +
                            "JOIN customer ON payments.customer_id = customer.id\n" +
                            "JOIN contract_customer ON customer.id = contract_customer.id\n" +
                            "JOIN packages ON contract_customer.package_id = packages.id;\n");

                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }

                    String paymentsId, customerEgn, paymentsMonth, paymentsYear, packName, contractPrice, paymentsDateOfPayment;
                    while(rs.next()){
                        paymentsId = rs.getString(1);
                        customerEgn = rs.getString(2);
                        paymentsMonth = rs.getString(3);
                        paymentsYear = rs.getString(4);
                        packName = rs.getString(5);
                        contractPrice = rs.getString(6);
                        paymentsDateOfPayment = rs.getString(7);
                        String[] row = {paymentsId, customerEgn, paymentsMonth, paymentsYear, packName, contractPrice, paymentsDateOfPayment};
                        model.addRow(row);
                    }

                } catch (SQLException ex) {
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
            System.out.println("Successful connection to the database! - payments");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}