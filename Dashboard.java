// import javax.swing.*;
// public class Dashboard {
//     public void DisplayDashboard(String[] singleCustomerData){
//         System.out.println("Hello," + singleCustomerData[2]);
//     }
// }

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame implements ActionListener {

    private JLabel welcomeLabel;
    private JLabel extraText;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton transferButton;
    private JButton balanceInquiryButton;
    private JButton logoutButton;
    static String singleCustomerData;

    public void DisplayDashboard(String singleCustomerData1) {
        this.setResizable(false);
        singleCustomerData = singleCustomerData1;
        
        // Create and configure welcome label
        welcomeLabel = new JLabel("Welcome, "+singleCustomerData.substring(22)+"!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        extraText = new JLabel("Which function would you like to perform?");
        extraText.setFont(new Font("Arial", Font.BOLD, 12));

        // Create and configure buttons
        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        withdrawButton.setBackground(Color.WHITE);
        depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        depositButton.setBackground(Color.WHITE);
        transferButton = new JButton("Transfer");
        transferButton.addActionListener(this);
        transferButton.setBackground(Color.WHITE);
        balanceInquiryButton = new JButton("Balance Inquiry");
        balanceInquiryButton.addActionListener(this);
        balanceInquiryButton.setBackground(Color.WHITE);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setBackground(Color.WHITE);
        ImageIcon logoutIcon = new ImageIcon("logout.png");
        ImageIcon resizedIcon = new ImageIcon(logoutIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        logoutButton.setIcon(resizedIcon);
        Dimension buttonDimension = new Dimension(50, 30);

        // Set button size
        withdrawButton.setPreferredSize(buttonDimension);
        depositButton.setPreferredSize(buttonDimension);
        transferButton.setPreferredSize(buttonDimension);
        balanceInquiryButton.setPreferredSize(buttonDimension);
        logoutButton.setPreferredSize(buttonDimension);

        // Create and configure panel
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(Color.CYAN);
        panel.setBorder(BorderFactory.createMatteBorder(50, 50, 50, 50, Color.CYAN));
        panel.add(welcomeLabel);
        panel.add(extraText);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(transferButton);
        panel.add(balanceInquiryButton);
        panel.add(logoutButton);

        // Add panel to the frame
        getContentPane().add(panel, BorderLayout.CENTER);

        // Set frame size and visibility
        setSize(750, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Handle button clicks
        if (source == withdrawButton) {
            // Open withdraw panel
            new WithdrawPanel();
        } else if (source == depositButton) {
            // Open deposit panel
            new DepositPanel();
        } else if (source == transferButton) {
            // Open transfer panel
            new TransferPanel();
        } else if (source == balanceInquiryButton) {
            // Display balance information
            new BalanceInquiryPanel();
        } else if (source == logoutButton) {
            // Close user dashboard and return to login screen
            dispose();
            Login login = new Login();
            login.LoginPage();
        }
    }
    // public String oneDArray(){
    //     String singleCustomerData2 = singleCustomerData;
    //     return singleCustomerData2;
    //   }
}