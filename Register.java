import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Register {
    public String pin;
    public String name;
    public String initialDeposit;
    public void RegisterPage() {
        JFrame frame = new JFrame("Register");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel initialDepositLabel = new JLabel("Initial Deposit amount:");
        JTextField initialDepositField = new JTextField();
        JLabel passwordLabel = new JLabel("4-Digit pin:");
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(initialDepositLabel);
        panel.add(initialDepositField);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameField.getText();
                pin = new String(passwordField.getPassword());
                initialDeposit = initialDepositField.getText();
            }
        });
        int lineCount = 0;
        try (Scanner scanner = new Scanner(new File("textFile.txt"))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] customerData = new String[lineCount+1];
        try {
            FileHandler handle = new FileHandler();
            customerData = handle.readFullFile();
        } catch (Exception e) {
            System.out.println("problem"+ e);
        }
        long lastCustomerCreditNumber = Long.parseLong(customerData[customerData.length-1].substring(0,15));
        long newlyRegisteredCreditNumber = lastCustomerCreditNumber + 1;
        String newlyRegisteredCustomerData = Long.toString(newlyRegisteredCreditNumber) + "," + pin + "," + name;
        customerData[lineCount] = newlyRegisteredCustomerData;
        try (PrintWriter writer = new PrintWriter(new File("customerDataFile.txt"))) {
            for (String element : customerData) {
                writer.println(element);
                System.out.println("Writing data back again");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter("customerBalanceFile.txt", true)) {
            writer.write(initialDeposit+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}
