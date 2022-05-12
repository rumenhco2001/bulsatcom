import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TableContractProviders extends JDialog{
    private JTable table1;
    private JTextField tfName;
    private JButton displayButton;
    private JButton resetButton;
    private JPanel tableProvContrPan;
    private JButton closeButton;
    private JButton displayAllContractsButton;
    private static JFrame frameContrProv;

    public static void main(String[] args) {

    }

    public TableContractProviders(){
        Connect();
        frameContrProv = new JFrame("Report for providers` contracts");
        frameContrProv.setContentPane(tableProvContrPan);
        frameContrProv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameContrProv.pack();
        frameContrProv.setVisible(true);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String provName = tfName.getText();
                    if (provName.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    pst = con.prepareStatement("SELECT contract_provider.id, providers.name, SUM(channels.price), contract_provider.firstDate, contract_provider.lastDate FROM contract_provider\n" +
                            "JOIN providers ON contract_provider.provider_id = providers.id\n" +
                            "LEFT JOIN channels ON channels.contract_provider = contract_provider.id\n" +
                            "WHERE providers.name = ? \n" +
                            "GROUP BY providers.name");
                    pst.setString(1, provName);
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }
                    String id, name, price, firstDate, lastDate;
                    while (rs.next()){
                        id = rs.getString(1);
                        name = rs.getString(2);
                        price = rs.getString(3);
                        firstDate = rs.getString(4);
                        lastDate = rs.getString(5);
                        String[] row = {id, name, price, firstDate, lastDate};
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
                tfName.setText("");
                ;
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameContrProv.dispose();
            }
        });
        displayAllContractsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("SELECT contract_provider.id, providers.name, SUM(channels.price), contract_provider.firstDate, contract_provider.lastDate FROM contract_provider\n" +
                            "RIGHT JOIN providers ON contract_provider.provider_id = providers.id\n" +
                            "LEFT JOIN channels ON channels.contract_provider = contract_provider.id\n" +
                            "GROUP BY providers.name");
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }
                    String id, name, price, firstDate, lastDate;
                    while (rs.next()){
                        id = rs.getString(1);
                        name = rs.getString(2);
                        price = rs.getString(3);
                        firstDate = rs.getString(4);
                        lastDate = rs.getString(5);
                        String[] row = {id, name, price, firstDate, lastDate};
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
            System.out.println("Successful connection to the database! - Report for providers` contracts");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}