import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TableChannels extends JDialog{
    private JPanel tableChannelsPan;
    private JTable table1;
    private JButton displayButton;
    private JButton resetButton;
    private JTextField tfProvName;
    private JButton button1;
    private JButton displayAllChannelsButton;
    private static JFrame tableChannelsFrame;

    public static void main(String[] args) {

    }
    public TableChannels(){
        Connect();
        tableChannelsFrame = new JFrame("Report for the channels");
        tableChannelsFrame.setContentPane(tableChannelsPan);
        tableChannelsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableChannelsFrame.pack();
        tableChannelsFrame.setVisible(true);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String provName = tfProvName.getText();
                    if (provName.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    pst = con.prepareStatement("SELECT channels.id, channels.name, channels.contract_provider, channels.category, channels.price\n" +
                            "FROM channels\n" +
                            "JOIN contract_provider ON channels.contract_provider = contract_provider.id\n" +
                            "JOIN providers ON contract_provider.provider_id = providers.id\n" +
                            "WHERE providers.name = ?;");
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
                    String id, channelName, contrProv, category, price;
                    while (rs.next()){
                        id = rs.getString(1);
                        channelName = rs.getString(2);
                        contrProv = rs.getString(3);
                        category = rs.getString(4);
                        price = rs.getString(5);
                        String[] row = {id, channelName, contrProv, category, price};
                        model.addRow(row);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableChannelsFrame.dispose();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(new DefaultTableModel());
                tfProvName.setText("");
            }
        });
        displayAllChannelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("SELECT channels.id, channels.name, channels.contract_provider, channels.category, channels.price\n" +
                            "FROM channels\n" +
                            "JOIN contract_provider ON channels.contract_provider = contract_provider.id\n" +
                            "JOIN providers ON contract_provider.provider_id = providers.id;");
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }
                    String id, channelName, contrProv, category, price;
                    while (rs.next()){
                        id = rs.getString(1);
                        channelName = rs.getString(2);
                        contrProv = rs.getString(3);
                        category = rs.getString(4);
                        price = rs.getString(5);
                        String[] row = {id, channelName, contrProv, category, price};
                        model.addRow(row);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    };

    Connection con;
    PreparedStatement pst;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bulsatcom", "root", "RumenVr01");
            System.out.println("Successful connection to the database! - Report for the channels");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
