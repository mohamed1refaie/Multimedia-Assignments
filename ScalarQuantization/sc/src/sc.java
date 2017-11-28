
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class sc {
    private JPanel panel1;
    private JButton selectFileButton;
    private JButton decompressButton;
    private JButton compressButton;
    private JLabel label1;


    static ArrayList<Integer>arr=new ArrayList<>();
    static ArrayList<Integer>fin=new ArrayList<>();
    static int levels;
    static File file=null;
    public sc() {
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select a file");
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
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
                if(file==null) {
                    JOptionPane.showMessageDialog(null, "no file selected");
                }
                else {
                    JOptionPane.showMessageDialog(null, "compressed");
                    compress();
                }
            }
        });
        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(file==null) {
                    JOptionPane.showMessageDialog(null, "no file selected");
                }
                else               {
                    JOptionPane.showMessageDialog(null, "decompressed");
                    decompress();
                }
            }
        });
    }

    public static void solve(int level, int start, int end)
    {
        int indx=0;
        for(int i=0;i<arr.size();i++)
        {
            if(arr.get(i)>=start)
            {
                indx=i;
                break;
            }
        }
        int cnt=0;
        int sum=0;

        for(int i=indx;i<arr.size();i++) {
            if (arr.get(i) >= start && arr.get(i) <= end){
                cnt++;
                sum+=arr.get(i);
            }
            else break;
        }

        int median=sum/cnt;
        if(level==levels)
        {
            fin.add(median);
            return;
        }
        else
        {
            solve(level+1,start,median);
            solve(level+1,median+1,end);
        }
    }
    public static void compress() {
        ArrayList<Integer>original=new ArrayList<Integer>();
        Scanner ref=null;
        try {
            ref=new Scanner(file);
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        fin.clear();
        arr.clear();
        int nbits=ref.nextInt();
        int maxmium=0;
        levels= nbits;
        nbits=(int)Math.pow(2,nbits);
        while(ref.hasNext())
        {
            int x=ref.nextInt();
            if(x>maxmium)
                maxmium=x;
            arr.add(x);
            original.add(x);
        }
        Collections.sort(arr);
        solve(0,0,maxmium);
        int start=0;
        ArrayList<Integer>compressed=new ArrayList<Integer>();

        for(int i=0;i<fin.size()-1;i++)
        {
            int mid=(fin.get(i)+fin.get(i+1))/2;
            for(int j=0;j<original.size();j++)
            {
                if(original.get(j)>=start&&original.get(j)<mid)
                    original.set(j,i);
            }
            start=mid;
        }
        for(int j=0;j<original.size();j++)
        {
            if(original.get(j)>=start&&original.get(j)<=maxmium)
                original.set(j,nbits-1);
        }
        PrintWriter writer= null;
        try
        {
            writer = new PrintWriter("compressed.txt");
        }
        catch (Exception e)
        {
            System.out.println("Error");
        }
        for(int j=0;j<original.size();j++)
        {
            writer.print(original.get(j)+" ");
        }
        writer.close();
        try
        {
            writer = new PrintWriter("table.txt");
        }
        catch (Exception e)
        {
            System.out.println("Error");
        }
        for(int i=0;i<nbits;i++)
        {
            writer.print(fin.get(i)+" ");
        }
        writer.close();
    }

    public static void decompress()
    {
        ArrayList<Integer>decompressed=new ArrayList<Integer>();
        ArrayList<Integer>codes=new ArrayList<Integer>();
        Scanner ref=null;
        try {
            ref=new Scanner(new File("table.txt"));
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        while(ref.hasNext())
        {
            int x=ref.nextInt();
            codes.add(x);
        }
        try {
            ref=new Scanner(new File("compressed.txt"));
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        while(ref.hasNext())
        {
            int x=ref.nextInt();
            int y=codes.get(x);
            decompressed.add(y);
        }
        PrintWriter writer= null;
        try
        {
            writer = new PrintWriter("decompressed.txt");
        }
        catch (Exception e)
        {
            System.out.println("Error");
        }
        for(int i=0;i<decompressed.size();i++) {
            System.out.print(decompressed.get(i) + " ");
            writer.print(decompressed.get(i)+" ");
        }
        writer.close();
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("sc");
        frame.setContentPane(new sc().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
