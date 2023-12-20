import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public String readLine(String fileName, int lineNumber) throws Exception {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        for (int i = 1; i < lineNumber; i++) {
            bufferedReader.readLine();
        }
        String line = bufferedReader.readLine();
        bufferedReader.close();
        fileReader.close();
        return line;
    }
    public String[] readFullFile() throws Exception {
        List<String> customerDataList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("customerDataFile.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] customerData = line.split(",");
                if (customerData.length != 3) {
                    // handle invalid line format (throw exception, log error, etc.)
                    throw new Exception("Invalid customer data format: " + line);
                }
                // store customer data in a list
                customerDataList.add(String.join("|", customerData)); // use a delimiter different from comma
            }
        }
        catch(Exception e){
            System.out.println("Error reading file: " + e.getMessage());
        }
        return customerDataList.toArray(new String[0]); // convert list to string array
    }
    public int[] readBalance() throws IOException {
        // Create an empty list to store balances
        List<Integer> balances = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("customerBalanceFile.txt"))) {
            String line;
            // Read each line and parse the balance as an integer
            while ((line = reader.readLine()) != null) {
                try {
                    balances.add(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    // Handle invalid lines (e.g., not integers)
                    System.err.println("Error parsing line: " + line);
                }
            }
        }
        // Convert the list to an int array
        int[] balanceArray = balances.stream().mapToInt(Integer::intValue).toArray();
        return balanceArray;
    }
    public int readSpecificBalance(int lineNumber) throws IOException {
        int balance = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("customerBalanceFile.txt"))) {
            // Skip lines until the desired line is reached
            for (int i = 1; i < lineNumber; i++) {
                reader.readLine();
            }
            // Read the desired line and parse it as an integer
            String line = reader.readLine();
            if (line != null) {
                try {
                    balance = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    // Handle invalid line format
                    System.err.println("Error parsing line: " + line);
                }
            }
        }
        return balance;
    }
    
    // public void updateBalance(int lineNumber, double newBalance, String singleCustomerData) throws IOException {
    //     RandomAccessFile file = new RandomAccessFile("customerDataFile.txt", "rw");
    //     long lineStart = getLineStart(file, lineNumber);
    //     file.seek(lineStart);
    //     String updatedLine;
    //     System.out.println(newBalance);
    //     if(lineNumber == 1){
    //         updatedLine = String.format("%s,%f",singleCustomerData.substring(0,singleCustomerData.lastIndexOf(",")),newBalance); // Format updated line with new balance
    //     }
    //     else{
    //         updatedLine = String.format("\n%s,%f",singleCustomerData.substring(0,singleCustomerData.lastIndexOf(",")),newBalance); // Format updated line with new balance
    //     }
    //     file.writeBytes(updatedLine);
    //     file.close();
    // }
    public void updateBalance(int[] balances) throws IOException{
        try (FileWriter writer = new FileWriter("customerBalanceFile.txt")) {
            for (int b = 0; b < balances.length; b++) {
                if(b == 0)
                    writer.write(Integer.toString(balances[b]));
                else
                    writer.write("\n" + Integer.toString(balances[b]));                
            }
        }
    }
    public long getLineStart(RandomAccessFile file, int lineNumber) throws IOException {
        long position = 0;
        for (int i = 1; i < lineNumber; i++) {
            position += file.readLine().length() + 1; // Account for newline character
        }
        return position;
    }
}