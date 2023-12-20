import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.awt.*;

public class WithdrawPanel extends JFrame {
    private JPanel panel;
    private JTextField amountField;
    private JButton enterButton;
    static int balance;
    public static String singleCustomerData;
    public int index;
    public int[] allBalance;
    public WithdrawPanel(){
        this.setResizable(false);

        amountField = new JTextField();
        enterButton = new JButton("Enter");

        panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(Color.CYAN);
        panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.CYAN));
        panel.add(amountField);
        panel.add(enterButton);

        // Initialize Login and singleCustomerData after creation
        try{
            FileHandler handle = new FileHandler();
            FileReader fr = new FileReader("index.txt");
            int firstChar = fr.read();
            fr.close();
            if (Character.isDigit(firstChar)) {
                index = Character.getNumericValue((char) firstChar);
            }
            // singleCustomerData = handle.readLine("customerDataFile.txt", index);
            allBalance = handle.readBalance();
            balance = handle.readSpecificBalance(index);
        }
        catch (Exception e) { 
            System.out.println("Error: " + e);
        }
        // Add action listener to enter button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get entered withdraw amount
                int withdrawAmount = Integer.parseInt(amountField.getText());
                if (balance < withdrawAmount) {
                    JOptionPane.showMessageDialog(panel,
                            "Insufficient funds!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                balance -= withdrawAmount;
                allBalance[index-1] = balance;
                try {
                    FileHandler file = new FileHandler();
                    file.updateBalance(allBalance);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                dispose();
                JOptionPane.showMessageDialog(panel,
                        "The amount has been withdrawn from your account",
                        "Error", JOptionPane.WARNING_MESSAGE);
                    return;
            }
        });

        // Add panel to frame and set visibility
        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(200, 200);
        setLocationRelativeTo(null);
        setVisible(true); // Set visibility after initialization
    }
}
