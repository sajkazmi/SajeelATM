import java.awt.*; 
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;


class Login {
    // public void writeData(String creditNumber, String password, String customerName, double balance) {
    //     try {
    //         BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true));
    //         writer.write(creditNumber + "," + password + "," + customerName + "," + balance);
    //         writer.newLine();
    //         writer.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    // public List<String[]> readData() throws IOException {
    //     List<String[]> data = new ArrayList<>();
    //     try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             data.add(line.split(","));
    //         }
    //     }
    //     return data;
    // }
    public int index;
    static String singleCustomerData;
    static String[] customersData;
    public void LoginPage() {
        try{
            FileHandler handle = new FileHandler();
            handle.readFullFile();
            // customer data array
            customersData = handle.readFullFile();
        }
        catch(Exception e){
            System.out.println("Error reading file");
        }
        
        //Frame initiation
        JFrame frame = new JFrame("ATM Customer Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // all elements
        JLabel titleLabel = new JLabel("ATM Customer Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);
        JLabel creditNumberLabel = new JLabel("Credit Card Number:");
        JTextField creditNumberField = new JTextField(30);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(30);
        JButton loginButton = new JButton("Login");
        ImageIcon loginIcon = new ImageIcon("login-icon.png");
        ImageIcon resizedIcon = new ImageIcon(loginIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        loginButton.setIcon(resizedIcon);
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // elements addition in panel
        panel.setBackground(Color.CYAN);
        panel.setBorder(BorderFactory.createMatteBorder(50, 50, 50, 50, Color.CYAN));
        panel.add(creditNumberLabel);
        panel.add(creditNumberField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        frame.add(panel, BorderLayout.CENTER);

        // password checking logic
        loginButton.addActionListener(e -> {
            String creditNumber = creditNumberField.getText();
            String password = new String(passwordField.getPassword());
            if (creditNumber.length() != 16) {
            JOptionPane.showMessageDialog(frame,
                    "Invalid credit card number. Please enter a 16-digit number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            for(int i = 0; i<customersData.length; i++){
                if(customersData[i].contains(creditNumber)){
                    index = i+1;
                    // String[] singleCustomerData = oneDArray();
                    try{
                        FileHandler handle = new FileHandler();
                        singleCustomerData = handle.readLine("customerDataFile.txt", index);
                        File file = new File("index.txt");
                        FileWriter w = new FileWriter(file);
                        w.write(Integer.toString(index));
                        w.close();
                    }
                    catch(Exception ex){
                        System.out.println("Not found");
                    }
                    if (!singleCustomerData.contains(password)) {
                        JOptionPane.showMessageDialog(frame,
                                "Password does not match the given credit card's actual password. Please enter the correct password.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Dashboard dash = new Dashboard();
                    frame.setVisible(false);
                    dash.DisplayDashboard(singleCustomerData);
                    break;
                    //another program will start here
                }
                if(i== (customersData.length - 1)){
                    JOptionPane.showMessageDialog(frame,
                        "The entered credit card number is not registered\nPlease try again.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        });
        // frame setting
        frame.setSize(600, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
  }
  public int returnIndex(){
    System.out.println(index);
    return index;
    }
//   public String[][] twoDArray(){
//     String[] customersData[] = {
//             {"1111222233334444","1111","Dr. Tamimy","100000"},
//             {"1234123412341234","1234","Sajeel","30000"},
//             {"0000111122223333","0123","Miqdad","125000"},
//             {"1234567890123456","1234","Elon Musk","500"},
//             {"1234567891234567","1234","Nawaz Sharif","75000"}
//         };
//     return customersData;
//   }
//   public String[] oneDArray(){
//     String[] customersData[] = twoDArray();
//     String[] singleCustomerData = customersData[index];
//     return singleCustomerData;
//   }
}
// for reading data from csv
// try {
//     List<String[]> data = readData();
//     // Access specific data based on your needs
//     // For example, get the balance of customer with credit card number "1234567890123456":
//     double balance = Double.parseDouble(data.get(0)[3]);
// } catch (IOException e) {
//     e.printStackTrace();
// }

