import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ContractProvider extends JDialog{
    private JTextField tfProviderId;
    private JPanel providerContractPanel;
    private JTextField tfActivated;
    private JTextField tfExpires;
    private JButton updateButton;
    private JTextField tfContractNumber;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton searchIdButton;
    private JTextField tfProviderName;
    private JButton button1;
    private JButton returnToProvidersButton;
    private JButton reportButton;
    private static JFrame contractProviderFrame;

    public static void main(String[] args) {

    }

    public ContractProvider(){
        Connect();

        contractProviderFrame = new JFrame("Provider`s contract");
        contractProviderFrame.setContentPane(providerContractPanel);
        contractProviderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contractProviderFrame.pack();
        contractProviderFrame.setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String providerId, activated, expires;

                providerId = tfProviderId.getText();
                activated = tfActivated.getText();
                expires = tfExpires.getText();

                try{
                    pst = con.prepareStatement("INSERT INTO contract_provider(provider_id, firstDate, lastDate) VALUES(?,?,?)");
                    pst.setString(1, providerId);
                    pst.setString(2, activated);
                    pst.setString(3, expires);

                    pst.executeUpdate();

                    tfProviderId.setText("");
                    tfActivated.setText("");
                    tfExpires.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                if (providerId.isEmpty() || activated.isEmpty() || expires.isEmpty()){
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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String contractId = tfContractNumber.getText();
                    pst = con.prepareStatement("SELECT provider_id, firstDate, lastDate from contract_provider WHERE id = ?");
                    pst.setString(1, contractId);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String providerId = rs.getString(1);
                        String activated = rs.getString(2);
                        String expires = rs.getString(3);

                        tfProviderId.setText(providerId);
                        tfActivated.setText(activated);
                        tfExpires.setText(expires);
                        if(providerId.isEmpty() || activated.isEmpty() || expires.isEmpty()){
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else
                    {
                        tfProviderId.setText("");
                        tfActivated.setText("");
                        tfExpires.setText("");
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
                String providerId, activated, expires, contractId;
                providerId = tfProviderId.getText();
                activated = tfActivated.getText();
                expires = tfExpires.getText();
                contractId = tfContractNumber.getText();

                try {

                    pst = con.prepareStatement("UPDATE contract_provider SET provider_id = ?, firstDate = ?, lastDate = ? WHERE id = ?");
                    pst.setString(1, providerId);
                    pst.setString(2, activated);
                    pst.setString(3, expires);
                    pst.setString(4, contractId);

                    pst.executeUpdate();

                    tfProviderId.setText("");
                    tfActivated.setText("");
                    tfExpires.setText("");
                    tfProviderId.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
                if(providerId.isEmpty() || activated.isEmpty() || expires.isEmpty()){
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
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String providerId, activated, expires, contrId;
                providerId = tfProviderId.getText();
                activated = tfActivated.getText();
                expires = tfExpires.getText();
                contrId = tfContractNumber.getText();
                try {
                    pst = con.prepareStatement("DELETE FROM contract_provider WHERE id = ?");
                    pst.setString(1, contrId);

                    pst.executeUpdate();
                    if(providerId.isEmpty() || activated.isEmpty() || expires.isEmpty()){
                        JOptionPane.showMessageDialog(null,
                                "Please, enter all data!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "This contract is deleted successfully!");
                    }

                    tfProviderId.setText("");
                    tfActivated.setText("");
                    tfExpires.setText("");
                    tfProviderId.requestFocus();

                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
        searchIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String providerName = tfProviderName.getText();
                    pst = con.prepareStatement("SELECT id FROM providers WHERE name = ?");
                    pst.setString(1, providerName);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String provId = rs.getString(1);
                        tfProviderId.setText(provId);

                        if(provId.isEmpty()){
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else
                    {
                        tfProviderId.setText("");
                        JOptionPane.showMessageDialog(null,"This provider`s name does not exists!");
                    }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contractProviderFrame.dispose();
                new HomePage();
            }
        });
        returnToProvidersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contractProviderFrame.dispose();
                new Providers();
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
            System.out.println("Successful connection to the database! - provider`s contract");
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
