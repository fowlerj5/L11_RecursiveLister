import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class RecursiveListerFrame extends JFrame {
    JPanel mainPnl, titlePnl, displayPnl, cmdPnl;
    JLabel titleLbl, directoryLbl, dirPathLbl;
    JScrollPane scroller;
    JTextArea fileListTA;
    JButton quitBtn, directoryBtn;

    public RecursiveListerFrame() throws HeadlessException
    {
        setTitle("Recursive File Lister");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int scrnHeight = screenSize.height;
        int scrnWidth = screenSize.width;
        setSize(scrnWidth*3/4, scrnHeight*3/4);
        setLocation(scrnWidth/8, scrnHeight/8);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);

        createTitlePanel();
        createDisplayPanel();
        createCommandPanel();

        setVisible(true);
    }
    private void createCommandPanel()
    {
        cmdPnl = new JPanel();
        cmdPnl.setLayout(new GridLayout(1,2));

        quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Bold", Font.BOLD, 18));
        directoryBtn = new JButton("Choose Directory");
        directoryBtn.setFont(new Font("Bold", Font.BOLD, 18));

        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));
        directoryBtn.addActionListener((ActionEvent ae) ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                fileListTA.setText("");
                File dir = chooser.getSelectedFile();
                dirPathLbl.setText(dir.toPath().toString());
                listFiles(dir);
            }
        });

        cmdPnl.add(directoryBtn);
        cmdPnl.add(quitBtn);

        mainPnl.add(cmdPnl, BorderLayout.SOUTH);
    }
    private void createDisplayPanel()
    {
        displayPnl = new JPanel();
        displayPnl.setLayout(new GridLayout(1,2));
        displayPnl.setBorder(new EmptyBorder(50,50,50,50));

        directoryLbl = new JLabel("Directory: ");
        dirPathLbl = new JLabel("");

        fileListTA = new JTextArea(10,20);
        fileListTA.setBorder(new EmptyBorder(4,4,4,4));
        fileListTA.setEditable(false);
        scroller = new JScrollPane(fileListTA);

        JPanel directoryLblPnl = new JPanel();
        directoryLblPnl.setLayout(new BoxLayout(directoryLblPnl,BoxLayout.Y_AXIS));
        directoryLblPnl.add(directoryLbl);
        directoryLblPnl.add(new JLabel(" "));
        directoryLblPnl.add(dirPathLbl);

        displayPnl.add(directoryLblPnl);
        displayPnl.add(scroller);

        mainPnl.add(displayPnl, BorderLayout.CENTER);
    }
    private void createTitlePanel()
    {
        titlePnl = new JPanel();

        titleLbl = new JLabel("File Lister", JLabel.CENTER);
        titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
        titleLbl.setHorizontalTextPosition(JLabel.CENTER);
        titleLbl.setFont(new Font("Bold Italic", Font.BOLD | Font.ITALIC, 36));

        titlePnl.add(titleLbl);

        mainPnl.add(titlePnl, BorderLayout.NORTH);
    }
    private void listFiles(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if(file.isFile()) {
                fileListTA.append(file.getName() + "\n");
            }
            else {
                listFiles(file);
            }
        }
    }
}