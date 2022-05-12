import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TableCustomers extends JDialog{
    private JTable table1;
    private JPanel tableCusromersPanel;
    private JButton displayButton;
    private JButton resetButton;
    private JTextField tfEgn;
    private JButton button1;
    private JButton displayAllButton;
    private static JFrame tableCustomersFrame;

    public static void main(String[] args) {

    }

    public TableCustomers(){
        Connect();
        tableCustomersFrame = new JFrame("Report of customers");
        tableCustomersFrame.setContentPane(tableCusromersPanel);
        tableCustomersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableCustomersFrame.pack();
        tableCustomersFrame.setVisible(true);
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String egn = tfEgn.getText();
                    //tfName.requestFocus();
                    if (egn.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    pst = con.prepareStatement("SELECT customer.id, customer.name, customer.egn, customer.address, customer.telephone, contract_customer.id, contract_customer.price, contract_customer.firstDate, contract_customer.lastDate FROM customer\n" +
                            "JOIN contract_customer ON customer.id = contract_customer.customer_id\n" +
                            "WHERE customer.egn = ?");
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
                    String custId, custName, custEgn, custAddress, custTelephone, contrId, contrPrice, contrFirstDate, contrLastDate;                    while (rs.next()){
                        custId = rs.getString(1);
                        custName = rs.getString(2);
                        custEgn = rs.getString(3);
                        custAddress = rs.getString(4);
                        custTelephone = rs.getString(5);
                        contrId = rs.getString(6);
                        contrPrice = rs.getString(7);
                        contrFirstDate = rs.getString(8);
                        contrLastDate = rs.getString(9);

                        String[] row = {custId, custName, custEgn, custAddress, custTelephone, contrId, contrPrice, contrFirstDate, contrLastDate  };
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
                tfEgn.setText("");
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableCustomersFrame.dispose();
            }
        });
        displayAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    pst = con.prepareStatement("SELECT customer.id, customer.name, customer.egn, customer.address, customer.telephone, contract_customer.id, contract_customer.price, packages.name, contract_customer.firstDate, contract_customer.lastDate FROM customer\n" +
                            "LEFT JOIN contract_customer ON customer.id = contract_customer.customer_id\n" +
                            "LEFT JOIN packages ON contract_customer.package_id = packages.id;\n");
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }
                    String custId, custName, custEgn, custAddress, custTelephone, contrId, contrPrice, packName, contrFirstDate, contrLastDate;

                    while (rs.next()){
                        custId = rs.getString(1);
                        custName = rs.getString(2);
                        custEgn = rs.getString(3);
                        custAddress = rs.getString(4);
                        custTelephone = rs.getString(5);
                        contrId = rs.getString(6);
                        contrPrice = rs.getString(7);
                        packName = rs.getString(8);
                        contrFirstDate = rs.getString(9);
                        contrLastDate = rs.getString(10);

                        String[] row = {custId, custName, custEgn, custAddress, custTelephone, contrId, contrPrice, packName, contrFirstDate, contrLastDate  };
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
            System.out.println("Successful connection to the database! - Report of customers");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
