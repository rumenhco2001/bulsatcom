import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Providers extends JDialog {
    private JTextField tfName;
    private JButton updateButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JTextField tfId;
    private JButton searchButton;
    private JPanel providersPanel;
    private JButton homeButton;
    private JButton editAProviderSButton;
    private JButton searchByNameButton;
    private JTextField tfProvNameSearch;
    private JButton reportButton;
    private static JFrame providersFrame;

    public static void main(String[] args) {

    }

    public Providers(){
        Connect();

        providersFrame = new JFrame("Providers");
        providersFrame.setContentPane(providersPanel);
        providersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        providersFrame.pack();
        providersFrame.setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;

                name = tfName.getText();

                try {
                    pst = con.prepareStatement("INSERT INTO providers (name) VALUES(?)");
                    pst.setString(1, name);
                    pst.executeUpdate();
                    tfName.setText("");
                    tfId.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                if (name.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null,"This provider is added successfully!");
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String providerId = tfId.getText();
                    pst = con.prepareStatement("SELECT name FROM providers WHERE id = ?");
                    pst.setString(1, providerId);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        tfName.setText(name);

                        if(name.isEmpty()){
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
                        tfId.setText("");
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
                String name, providerId;
                name = tfName.getText();
                providerId = tfId.getText();
                try {

                    pst = con.prepareStatement("UPDATE providers SET name = ? WHERE id = ?");
                    pst.setString(1, name);
                    pst.setString(2, providerId);

                    pst.executeUpdate();

                    tfName.setText("");
                    tfProvNameSearch.setText("");
                    tfId.setText("");
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
                    JOptionPane.showMessageDialog(null, "The provider`s information is updated!");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid;
                bid = tfId.getText();
                try {
                    pst = con.prepareStatement("DELETE FROM providers  WHERE id = ?");
                    pst.setString(1, bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "This provider is deleted successfully!");

                    tfId.setText("");
                    tfName.setText("");
                    tfName.requestFocus();

                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                providersFrame.dispose();
                new HomePage();
            }
        });
        editAProviderSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                providersFrame.dispose();
                new ContractProvider();
            }
        });
        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String provName = tfProvNameSearch.getText();
                    pst = con.prepareStatement("SELECT id, name FROM providers WHERE name = ?");
                    pst.setString(1, provName);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(2);
                        tfName.setText(name);
                        String id = rs.getString(1);
                        tfId.setText(id);

                        if(name.isEmpty()){
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
                        tfId.setText("");
                        JOptionPane.showMessageDialog(null,"This provider`s name does not exists!");
                    }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableContractProviders();
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
            System.out.println("Successful connection to the database! - providers");
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
