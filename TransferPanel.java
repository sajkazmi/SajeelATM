import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.awt.*;

public class TransferPanel extends JFrame {

    private JPanel panel;
    private JTextField accountField, amountField;
    private JButton transferButton;
    public static int balance;
    public int secondBalance;
    public static String singleCustomerData;
    public int index;
    static String[] customersData;
    public int[] allBalance;
    public int secondIndex;
    public boolean validity;

    public TransferPanel() {
        this.setResizable(false);
        accountField = new JTextField();
        amountField = new JTextField();
        transferButton = new JButton("Transfer!");

        panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(Color.CYAN);
        panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.CYAN));
        panel.add(new JLabel("Account Number:"));
        panel.add(accountField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(transferButton);

        // Initialize Login, singleCustomerData and balance after creation
        try {
            FileHandler handle = new FileHandler();
            FileReader fr = new FileReader("index.txt");
            int firstChar = fr.read();
            fr.close();
            if (Character.isDigit(firstChar)) {
                index = Character.getNumericValue((char) firstChar);
            }
            singleCustomerData = handle.readLine("customerDataFile.txt", index);
            balance = handle.readSpecificBalance(index);
            allBalance = handle.readBalance();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        // Add action listener to transfer button
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountField.getText();
                int transferAmount = Integer.parseInt(amountField.getText());

                // Validate account number
                validateAccountNumber(accountNumber);
                if (!validity) {
                    JOptionPane.showMessageDialog(panel,
                            "The account number is inaccurate or invalid!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Check if sufficient funds available
                if (balance < transferAmount) {
                    JOptionPane.showMessageDialog(panel,
                            "Insufficient funds!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    FileHandler handle = new FileHandler();
                    secondBalance = handle.readSpecificBalance(secondIndex);
                } 
                catch (Exception ex) {
                    // TODO: handle exception
                    System.out.println(ex + "not found");
                }
                // Update balances
                balance -= transferAmount;
                secondBalance += transferAmount;
                allBalance[index - 1] = balance;
                allBalance[secondIndex - 1] = secondBalance;

                // TODO: Implement logic to update recipient's balance based on account number (using FileHandler methods)

                try {
                    FileHandler file = new FileHandler();
                    file.updateBalance(allBalance);
                    JOptionPane.showMessageDialog(panel,
                            "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                dispose();
            }
        });

        // Add panel to frame and set visibility
        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void validateAccountNumber(String creditNumber) {
        validity = false;
        // Implement validation logic for 16-digit card number (length, checksum, etc.)
        if(creditNumber.length() != 16)
            validity = false;
        else{
            try{
                FileHandler handle = new FileHandler();
                handle.readFullFile();
                // customer data array
                customersData = handle.readFullFile();
            }
            catch(Exception e){
                System.out.println("Error reading file");
            }
            for(int i = 0; i<customersData.length; i++){
                if(customersData[i].contains(creditNumber)){
                    validity = true;
                    secondIndex = i+1;
                    break;
                }
                if(i== (customersData.length - 1)){
                    validity = false;
                }
            }
        }
    }
}

