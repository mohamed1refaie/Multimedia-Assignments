import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class vec {
    private JPanel panel1;
    private JLabel label1;
    private JButton SelectImageButton;
    private JButton compressButton;
    private JTextField textField1;
    private JTextField textField2;
    static File file=null;
    public vec() {
        SelectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select a file");
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.showOpenDialog(panel1);
                if(fileChooser.getSelectedFile()==null)
                {
                    return;
                }
                label1.setText(fileChooser.getDescription(fileChooser.getSelectedFile()));
                file = fileChooser.getSelectedFile();
            }
        });
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(file==null||textField1.getText()==""||textField2.getText()=="")
                {
                    JOptionPane.showMessageDialog(null, "either no file selected or no size entered or no codebook size entered");
                }
                else
                {
                    Integer size=Integer.valueOf(textField1.getText());
                    Integer codesize=Integer.valueOf(textField2.getText());
                    compress.compress(size,codesize,file.getPath());
                    JOptionPane.showMessageDialog(null, "compressed");

                }
            }
        });


    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("vec");
        frame.setContentPane(new vec().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
