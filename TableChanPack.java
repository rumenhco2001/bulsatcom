import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TableChanPack extends JDialog{
    private JPanel tableChanPackPan;
    private JTable table1;
    private JButton displayButton;
    private JButton resetButton;
    private JButton button1;
    private static JFrame tableChanPackFrame;

    public static void main(String[] args) {

    }

    public TableChanPack() {
        Connect();
        tableChanPackFrame = new JFrame("Report channels and packages");
        tableChanPackFrame.setContentPane(tableChanPackPan);
        tableChanPackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableChanPackFrame.pack();
        tableChanPackFrame.setVisible(true);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("SELECT package_channel.package_id, packages.name, GROUP_CONCAT(channels.name SEPARATOR ', '), packages.minimum_price\n" +
                            "FROM package_channel\n" +
                            "JOIN channels ON package_channel.channel_id = channels.id\n" +
                            "JOIN packages ON packages.id = package_channel.package_id\n" +
                            "GROUP BY package_channel.package_id;\n");
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }
                    String id, packName, chanName, minPrice;
                    while (rs.next()){
                        id = rs.getString(1);
                        packName = rs.getString(2);
                        chanName = rs.getString(3);
                        minPrice = rs.getString(4);

                        String[] row = {id, packName, chanName, minPrice};
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
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableChanPackFrame.dispose();
            }
        });
    }

    Connection con;
    PreparedStatement pst;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bulsatcom", "root", "RumenVr01");
            System.out.println("Successful connection to the database! - Report channels and packages");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
