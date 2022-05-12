import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TablePackages extends JDialog{
    private JTable table1;
    private JPanel tablePackPan;
    private JButton displayButton;
    private JButton resetButton;
    private JButton button1;
    private static JFrame tablePackFrame;

    public static void main(String[] args) {

    }

    public TablePackages(){
        Connect();
        tablePackFrame = new JFrame("Report packages and their channels");
        tablePackFrame.setContentPane(tablePackPan);
        tablePackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablePackFrame.pack();
        tablePackFrame.setVisible(true);


        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("SELECT id, name, minimum_price FROM packages;");
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i=0; i<cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                    }
                    String id, packName, price;
                    while (rs.next()){
                        id = rs.getString(1);
                        packName = rs.getString(2);
                        price = rs.getString(3);

                        String[] row = {id, packName, price};
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
                tablePackFrame.dispose();
            }
        });
    }


    Connection con;
    PreparedStatement pst;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bulsatcom", "root", "RumenVr01");
            System.out.println("Successful connection to the database! - Report packages and their channels");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
