import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PackageChannel {
    private JTextField tfPackId;
    private JTextField tfChanId;
    private JTextField tfChanName;
    private JPanel packChanPanel;
    private JButton saveButton;
    private JButton deleteButton;
    private JTextField tfPackName;
    private JButton searchButtonPackage;
    private JButton searchButtonChannel;
    private JButton button1;
    private JButton returnToChannelsButton;
    private JButton reportPackagesButton;
    private JButton reportChannelsButton;
    private JButton reportButton;
    private static JFrame packChanFrame;

    public static void main(String[] args) {

    }

    public PackageChannel(){
        Connect();

        packChanFrame = new JFrame("Add channels to packages");
        packChanFrame.setContentPane(packChanPanel);
        packChanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        packChanFrame.pack();
        packChanFrame.setVisible(true);

        searchButtonPackage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String packageName = tfPackName.getText();
                    pst = con.prepareStatement("SELECT id FROM packages WHERE name = ?");
                    pst.setString(1, packageName);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String packId = rs.getString(1);

                        tfPackId.setText(packId);

                        if(packageName.isEmpty()){
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else
                    {
                        tfPackName.setText("");
                        JOptionPane.showMessageDialog(null,"This package`s name does not exists!");
                    }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        searchButtonChannel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String channelName = tfChanName.getText();
                    pst = con.prepareStatement("SELECT id FROM channels WHERE name = ?");
                    pst.setString(1, channelName);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String chanId = rs.getString(1);

                        tfChanId.setText(chanId);

                        if(channelName.isEmpty()){
                            JOptionPane.showMessageDialog(null,
                                    "Please, enter all data!",
                                    "Try again",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else
                    {
                        tfPackName.setText("");
                        JOptionPane.showMessageDialog(null,"This channel`s name does not exists!");
                    }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String packId, chanId;
            packId = tfPackId.getText();
            chanId = tfChanId.getText();
                try{
                    pst = con.prepareStatement("INSERT INTO package_channel(package_id, channel_id) VALUES(?,?)");
                    pst.setString(1, packId);
                    pst.setString(2,chanId);

                    pst.executeUpdate();

                    tfPackId.setText("");
                    tfChanId.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
                if (packId.isEmpty() || chanId.isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Please, enter all data!",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null,"This channel is added to the package successfully!");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String packId, chanId;
                packId = tfPackId.getText();
                chanId = tfChanId.getText();
                try {
                    pst = con.prepareStatement("DELETE FROM package_channel WHERE package_id = ? and channel_id = ?");
                    pst.setString(1, packId);
                    pst.setString(2, chanId);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "This connection is deleted successfully!");

                    tfPackId.setText("");
                    tfChanId.setText("");
                    tfChanId.requestFocus();

                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                packChanFrame.dispose();
                new HomePage();
            }
        });
        returnToChannelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                packChanFrame.dispose();
                new Channels();
            }
        });
        reportPackagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TablePackages();
            }
        });
        reportChannelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableChannels();
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableChanPack();
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
            System.out.println("Successful connection to the database! - channel`s package");
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