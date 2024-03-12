package library;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Import the File class`
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class Library_admin extends JFrame implements ActionListener {
    private JLabel label1, label2, label3, label4, label5, label6, label7;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton;
    private JPanel panel;
    private ArrayList<Book> books = new ArrayList<>();
    private String file_name;

    private void writeUpdateFileArray() {
        FileOperations.writeUpdateFileArray(books, file_name);
    }
    private void readFileUpdateArray() {
    	FileOperations.readFileAndUpdateArray(books, file_name);
    }

    public Library_admin(String filename) {
        file_name = filename;
        readFileUpdateArray();
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");
        label3 = new JLabel("Author");
        label4 = new JLabel("Publisher");
        label5 = new JLabel("Year of Publication");
        label6 = new JLabel("ISBN");
        label7 = new JLabel("Number of Copies");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(10);
        textField6 = new JTextField(20);
        textField7 = new JTextField(10);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        panel.add(textField5);
        panel.add(label6);
        panel.add(textField6);
        panel.add(label7);
        panel.add(textField7);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(exitButton);
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addButton) {
        handleAddButton();
    } else if (e.getSource() == viewButton) {
        handleViewButton();
    } else if (e.getSource() == editButton) {
        handleEditButton();
    } else if (e.getSource() == deleteButton) {
        handleDeleteButton();
    } else if (e.getSource() == clearButton) {
        handleClearButton();
    } else if (e.getSource() == exitButton) {
        handleExitButton();
    }
    }

     private void handleAddButton() {
        Book book = getBookFromFields();
        books.add(book);
        JOptionPane.showMessageDialog(this, "Book added successfully");
        clearFields();
    }
    
    private void handleViewButton() {
        showBooksTable();
    }

    private void handleEditButton() {
        String bookID = JOptionPane.showInputDialog(this, "Enter book ID to edit:");
        editBook(bookID);
    }


    private void handleDeleteButton() {
        String bookID = JOptionPane.showInputDialog(this, "Enter book ID to delete:");
        deleteBook(bookID);
    }

    private void handleClearButton() {
        clearFields();
    }

    private void handleExitButton() {
        writeUpdateFileArray();
        delayExecution(2000);
        System.exit(0);
    }

    private Book getBookFromFields() {
        Book book = new Book();
        book.setId(textField1.getText());
        book.setTitle(textField2.getText());
        book.setAuthor(textField3.getText());
        book.setPublisher(textField4.getText());
        book.setYearOfPublication(textField5.getText());
        book.setIsbn(textField6.getText());
        book.setNumberOfCopies(textField7.getText());
        return book;
    }

    private void showBooksTable() {
        String[] columns = {"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies"};
        Object[][] data = new Object[books.size()][7];
        for (int i = 0; i < books.size(); i++) {
            System.arraycopy(books.get(i), 0, data[i], 0, 7);
        }
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("View Books");
        frame.add(scrollPane);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    private void editBook(String bookID) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i)[0].equals(bookID)) {
                String[] book = getBookFromFields();
                book[0] = bookID;
                books.set(i, book);
                JOptionPane.showMessageDialog(this, "Book edited successfully");
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Book not found");
    }

    private void deleteBook(String bookID) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i)[0].equals(bookID)) {
                books.remove(i);
                JOptionPane.showMessageDialog(this, "Book deleted successfully");
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Book not found");
    }

    private void delayExecution(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            System.out.println("Sleep was interrupted");
        }
    }


    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
    }
}

class Book {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String yearOfPublication;
    private String isbn;
    private String numberOfCopies;

    // Constructors, getters, setters...

    // You can also add validation methods if needed
}

enum UserType {
    ADMIN,
    USER
}

class FileOperations {
    static void readFileAndUpdateArray(ArrayList<String[]> list, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String splitBy = ",";
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                if (isValidData(data)) {
                    list.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeUpdateFileArray(ArrayList<String[]> list, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (String[] data : list) {
                fw.write(String.join(",", data) + "\n");
            }
            System.out.println("Data successfully written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing data to the file.");
            e.printStackTrace();
        }
    }

    private static boolean isValidData(String[] data) {
        return data.length == 7 && Arrays.stream(data).noneMatch(String::isEmpty);
    }
}

class Library_user extends JFrame implements ActionListener {
    private JLabel label1, label2;
    private JTextField textField1, textField2;
    private JButton issueButton, viewButton, clearButton, exitButton;
    private JPanel panel;
    private ArrayList<String[]> books = new ArrayList<String[]>();
    private String file_name;

    private void writeUpdateFileArray() {
        FileOperations.writeUpdateFileArray(books, file_name);
    }
    private void readFileUpdateArray() {
    	FileOperations.readFileAndUpdateArray(books, file_name);
    }

    public Library_user(String filename) {
        file_name = filename;
        readFileUpdateArray();
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);

        issueButton = new JButton("Issue");
        viewButton = new JButton("View");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        issueButton.addActionListener(this);
        viewButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(issueButton);
        panel.add(viewButton);
        panel.add(clearButton);
        panel.add(exitButton);
        add(panel);
        setVisible(true);
    }

    private void issueBook(String[] book) {
        int copies = Integer.parseInt(book[6]);
        if (copies > 0) {
            copies--;
            book[6] = Integer.toString(copies);
            JOptionPane.showMessageDialog(this, "Book issued successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Book not available");
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            issueBook();
        } else if (e.getSource() == viewButton) {
            showBooksTable();
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == exitButton) {
            handleExitButton();
        }
    }

    private void issueBook() {
        String bookID = textField1.getText();
        Optional<String[]> optionalBook = findBookById(bookID);

        if (optionalBook.isPresent()) {
            issueBook(optionalBook.get());
            JOptionPane.showMessageDialog(this, "Book issued successfully");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Book not found");
        }
    }

    private void showBooksTable() {
        String[] columns = {"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies"};
        Object[][] data = new Object[books.size()][7];

        for (int i = 0; i < books.size(); i++) {
            System.arraycopy(books.get(i), 0, data[i], 0, 7);
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("View Books");
        frame.add(scrollPane);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    private void handleExitButton() {
        writeUpdateFileArray();
        delayExecution(2000);
        System.exit(0);
    }

    private Optional<String[]> findBookById(String bookID) {
        return books.stream()
                .filter(book -> book[0].equals(bookID))
                .findFirst();
    }


    private void clearFields() { // clear
        textField1.setText("");
        textField2.setText("");
    }
}

class Library_login extends JFrame implements ActionListener {
    private JLabel label1, label2;
    private JTextField username, password;
    private JButton adminlogin, userlogin, clearButton;
    private JPanel panel;
    private ArrayList<String[]> user = new ArrayList<String[]>();
    private String file_name_records; // records
    private String file_name_user_data; // user data

    private void readFileUpdateArray() {
        FileOperations.readFileAndUpdateArray(user, file_name_user_data);
    }

    public Library_login(String user_data, String records) {
        file_name_user_data = user_data;
        file_name_records = records;
        readFileUpdateArray();
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        label1 = new JLabel("Username");
        label2 = new JLabel("Password");

        username = new JTextField(20);
        password = new JTextField(20);

        adminlogin = new JButton("Admin Login");
        userlogin = new JButton("User Login");
        clearButton = new JButton("Clear");
        adminlogin.addActionListener(this);
        userlogin.addActionListener(this);
        clearButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(username);
        panel.add(label2);
        panel.add(password);
        panel.add(adminlogin);
        panel.add(userlogin);
        panel.add(clearButton);
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == adminlogin) {
        handleAdminLogin();
    } else if (e.getSource() == userlogin) {
        handleUserLogin();
    } else if (e.getSource() == clearButton) {
        clearCredentials();
    }
}

private void handleAdminLogin() {
    String enteredUsername = username.getText();
    String enteredPassword = password.getText();

    if ("admin".equals(enteredUsername) && "admin".equals(enteredPassword)) {
        new Library_admin(file_name_records);
        setVisible(false);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password");
    }
}

private void handleUserLogin() {
    String enteredUsername = username.getText();
    String enteredPassword = password.getText();

    Optional<String[]> userOptional = user.stream()
            .filter(userData -> enteredUsername.equals(userData[0]) && enteredPassword.equals(userData[1]))
            .findFirst();

    if (userOptional.isPresent()) {
        new Library_user(file_name_records);
        setVisible(false);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password");
    }
}

private void clearCredentials() {
    username.setText("");
    password.setText("");
}

}

public class library_management {
	private static final String USER_DATA_FILE = "user_data.csv";
    private static final String RECORDS_FILE = "book_records.csv";

    public static void main(String[] args) {
        File userDataFile = new File(USER_DATA_FILE);
        File recordsDataFile = new File(RECORDS_FILE);

        if (!recordsDataFile.exists()) {
            createRecordsDataFile(recordsDataFile);
        }

        if (userDataFile.exists()) {
            new Library_login(USER_DATA_FILE, RECORDS_FILE);
        } else {
            System.out.println("The user data file does not exist. Please create it first.");
        }
    }

    private static void createRecordsDataFile(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("The records data file has been created.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the records data file.");
            e.printStackTrace();
        }
    }
}