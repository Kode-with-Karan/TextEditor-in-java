import java.awt.*;
import java.awt.Font.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.Files;

class GUI {

    public static String selectedText = "";
    public static String savedFilePath = "";
    public static String savedFolderPath = "";

    public static void main(String arg[]) {

        JFrame jframe = new JFrame("Text Editor");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(800, 800);

        JPanel panel = new JPanel(new BorderLayout());

        // Menu bar
        JMenuBar jmenubar = new JMenuBar();

        // menu items
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu about_us = new JMenu("About Us");
        JMenu creater_name = new JMenu("Powered By Karan");

        jmenubar.add(file);
        jmenubar.add(edit);
        jmenubar.add(about_us);
        jmenubar.add(BorderLayout.WEST, creater_name);

        // menu subitems

        // file menu items
        JMenuItem openf = new JMenuItem("Open file");
        JMenuItem openfl = new JMenuItem("Open folder");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem save_as = new JMenuItem("Save As");
        JMenuItem close = new JMenuItem("Close");

        file.add(openf);
        file.add(openfl);
        file.add(save);
        file.add(save_as);
        file.add(close);

        // edit menu items
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");

        edit.add(undo);
        edit.add(redo);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);

        // edit menu items
        JMenuItem about = new JMenuItem("About");

        about_us.add(about);

        // Create a DefaultListModel to hold folder names
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Open Folder");

        // Create a JList with the DefaultListModel
        JList<String> list = new JList<>(listModel);

        // Create a JScrollPane to hold the JList
        JScrollPane scrollPanelist = new JScrollPane(list);

        // Text Area
        Font font1 = new Font("SansSerif", Font.PLAIN, 18);
        JTextArea jtextarea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(jtextarea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        jframe.getContentPane().add(BorderLayout.NORTH, jmenubar);
        // jframe.add(scrollPane, BorderLayout.CENTER);

        jtextarea.setFont(font1);

        openf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechoose = new JFileChooser();
                filechoose.setDialogTitle("Open File");

                int userSelection = filechoose.showSaveDialog(jtextarea);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = filechoose.getSelectedFile();
                    savedFilePath = fileToSave.getAbsolutePath();

                    if (savedFilePath != "") {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(savedFilePath));
                            StringBuilder sb = new StringBuilder();
                            String line = br.readLine();

                            while (line != null) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                                line = br.readLine();
                            }
                            String everything = sb.toString();
                            jtextarea.setText(everything);
                            br.close();
                        } catch (Exception error) {
                            // TODO: handle exception
                            System.out.println("ERROR: " + error);
                        }
                    }
                    System.out.println("Succesfully Opened: " + fileToSave.getAbsolutePath());
                }
            }
        });

        openfl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser filechoose = new JFileChooser();
                filechoose.setDialogTitle("Open Folder");
                filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelection = filechoose.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = filechoose.getSelectedFile();
                    savedFolderPath = fileToSave.getAbsolutePath();

                    if (savedFolderPath != "") {
                        try {
                            listModel.clear();
                            File directoryPath = new File(savedFolderPath);
                            String contents[] = directoryPath.list();
                            // listElems = directoryPath.list();

                            for (int i = 0; i < contents.length; i++) {
                                listModel.addElement(directoryPath.list()[i]);
                            }
                        } catch (Exception error) {
                            // TODO: handle exception
                            System.out.println("ERROR: " + error);
                        }
                    }
                    System.out.println("Succesfully Opened Folder: " + fileToSave.getAbsolutePath());
                }
            }

        });

        // Add mouse listener to the list
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Detect single-click
                    // Get the selected index
                    int index = list.locationToIndex(e.getPoint());
                    // Get the selected folder path
                    String selectedFolder = listModel.getElementAt(index);

                    System.out.println(selectedFolder);
                    savedFilePath = savedFolderPath + "\\" + selectedFolder;

                    // If click one Openfolder then open the Folder options
                    if (selectedFolder == "Open Folder") {

                        JFileChooser filechoose = new JFileChooser();
                        filechoose.setDialogTitle("Open Folder");
                        filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                        int userSelection = filechoose.showSaveDialog(null);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = filechoose.getSelectedFile();
                            savedFolderPath = fileToSave.getAbsolutePath();

                            if (savedFolderPath != "") {
                                try {
                                    listModel.clear();
                                    File directoryPath = new File(savedFolderPath);
                                    String contents[] = directoryPath.list();
                                    // listElems = directoryPath.list();

                                    for (int i = 0; i < contents.length; i++) {
                                        listModel.addElement(directoryPath.list()[i]);
                                    }
                                } catch (Exception error) {
                                    // TODO: handle exception
                                    System.out.println("ERROR: " + error);
                                }
                            }
                            System.out.println("Succesfully Opened Folder: " + fileToSave.getAbsolutePath());
                        }

                    
                    } 
                    //If Click on the file then open the file in text edditor
                    else {

                        try {
                            BufferedReader br = new BufferedReader(new FileReader(savedFilePath));
                            StringBuilder sb = new StringBuilder();
                            String line = br.readLine();

                            while (line != null) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                                line = br.readLine();
                            }
                            String everything = sb.toString();
                            jtextarea.setText(everything);
                            br.close();
                        } catch (Exception error) {
                            // TODO: handle exception
                            System.out.println("ERROR1: " + error);
                        }
                    }

                }
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (savedFilePath != "") {
                    try {
                        PrintWriter writer = new PrintWriter(savedFilePath, "UTF-8");
                        writer.println(jtextarea.getText());
                        writer.close();
                    } catch (Exception error) {
                        // TODO: handle exception
                        System.out.println("ERROR: " + error);
                    }
                } else {
                    JFileChooser filechoose = new JFileChooser();
                    filechoose.setDialogTitle("Save");

                    int userSelection = filechoose.showSaveDialog(jtextarea);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = filechoose.getSelectedFile();
                        savedFilePath = fileToSave.getAbsolutePath();
                        System.out.println(savedFilePath);

                        try {
                            PrintWriter writer = new PrintWriter(savedFilePath, "UTF-8");
                            writer.println(jtextarea.getText());
                            writer.close();
                        } catch (Exception error) {
                            // TODO: handle exception
                            System.out.println("ERROR: " + error);
                        }
                    }

                }

            }
        });

        // save.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // JFileChooser filechoose = new JFileChooser();
        // filechoose.setDialogTitle("Save");

        // int userSelection = filechoose.showSaveDialog(jtextarea);

        // if (userSelection == JFileChooser.APPROVE_OPTION) {
        // File fileToSave = filechoose.getSelectedFile();
        // System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        // System.out.println(savedFilePath);

        // if (savedFilePath != "") {
        // try {
        // PrintWriter writer = new PrintWriter(savedFilePath, "UTF-8");
        // writer.println(jtextarea.getText());
        // writer.close();
        // } catch (Exception error) {
        // // TODO: handle exception
        // System.out.println("ERROR: " + error);
        // }

        // } else {
        // try {
        // fileToSave.createNewFile();
        // savedFilePath = fileToSave.getAbsolutePath();
        // PrintWriter writer = new PrintWriter(savedFilePath, "UTF-8");
        // writer.println(jtextarea.getText());
        // writer.close();
        // } catch (Exception error) {
        // // TODO: handle exception
        // System.out.println("ERROR: " + error);
        // }
        // }
        // }
        // }
        // });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedText = jtextarea.getSelectedText();
                if (selectedText != null && !selectedText.isEmpty()) {
                    // Get system clipboard
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    // Create a StringSelection object to hold the selected text
                    StringSelection selection = new StringSelection(selectedText);
                    // Set the StringSelection object as the clipboard contents
                    clipboard.setContents(selection, null);
                }
            }
        });

        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get selected text from the text area
                selectedText = jtextarea.getSelectedText();
                if (selectedText != null && !selectedText.isEmpty()) {
                    // Get system clipboard
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    // Create a StringSelection object to hold the selected text
                    StringSelection selection = new StringSelection(selectedText);
                    // Set the StringSelection object as the clipboard contents
                    clipboard.setContents(selection, null);
                    // Remove the selected text from the text area
                    jtextarea.replaceSelection("");
                }
            }
        });

        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Get system clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                // Get the clipboard contents
                Transferable clipboardContent = clipboard.getContents(null);
                // Check if the clipboard contains text
                if (clipboardContent != null && clipboardContent.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        // Get the clipboard data as text
                        String clipboardText = (String) clipboardContent.getTransferData(DataFlavor.stringFlavor);
                        // Get the caret position in the text area
                        int caretPosition = jtextarea.getCaretPosition();
                        // Insert the clipboard text at the caret position
                        jtextarea.insert(clipboardText, caretPosition);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jtextarea.setText("**About Us**\n\n" +
                "Welcome to our text editing software, meticulously crafted by Karan, a passionate Java developer dedicated to simplifying your text editing experience. Our software is designed to provide you with a seamless and intuitive platform for all your writing needs.\n\n" +
                "**Our Vision**\n\n" +
                "At our core, we envision a world where text editing is not only efficient but also enjoyable. With a keen focus on user experience and functionality, we strive to empower our users with powerful tools that streamline their workflow.\n\n" +
                "**Features**\n\n" +
                "Our text editor boasts a comprehensive array of features tailored to enhance your productivity:\n\n" +
                "- **Open File**: Seamlessly open individual files with just a click, enabling quick access to your documents.\n" +
                "- **Open Folder**: Effortlessly navigate through directories and open entire folders, providing a holistic view of your projects.\n" +
                "- **Save**: Safeguard your work by saving your documents locally, ensuring no data is lost.\n" +
                "- **Save As**: Customize your saving preferences by specifying file names and locations, offering flexibility and control.\n" +
                "- **Cut, Copy, Paste**: Utilize familiar editing commands to manipulate text effortlessly, facilitating efficient content management.\n" +
                "- **Sidebar File Show**: Access a convenient sidebar showcasing your files, simplifying navigation and organization.\n\n" +
                "**Our Commitment**\n\n" +
                "Driven by a passion for excellence, we are committed to delivering a text editing solution that exceeds expectations. With a user-centric approach and continuous innovation, we strive to evolve alongside your needs, ensuring an unparalleled editing experience.\n\n" +
                "**Join Us**\n\n" +
                "Experience the difference with our text editing software and embark on a journey of seamless productivity. Whether you're a seasoned professional or a budding writer, our platform is designed to empower you every step of the way.\n\n" +
                "Thank you for choosing our software. Together, let's redefine the art of text editing.\n\n" +
                "Sincerely,\n\n" +
                "Karan and the Development Team");
            }
        });

        panel.add(scrollPanelist, BorderLayout.WEST); // Label on the left
        panel.add(scrollPane, BorderLayout.CENTER); // Text area on the right

        // Add panel to the frame
        jframe.add(panel);

        // Set the size of the label and text area
        Dimension labelSize = new Dimension(jframe.getWidth() / 5, jframe.getHeight()); // 20% of width
        Dimension textAreaSize = new Dimension(jframe.getWidth() * 4 / 5, jframe.getHeight()); // 80% of width
        scrollPanelist.setPreferredSize(labelSize);
        scrollPane.setPreferredSize(textAreaSize);

        jframe.setVisible(true);
    }
}