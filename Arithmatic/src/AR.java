import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutput;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AR {

    private JButton compressionButton;
    private JPanel panel1;
    private JButton decompressionButton;
    private JTextField textField1;
    private JLabel label1;

    public AR() {
        compressionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textField1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "no text inserted");
                }
                else {
                    JOptionPane.showMessageDialog(null, "compressed");
                    compress(textField1.getText());
                }
            }
        });
        decompressionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "no text inserted");
                }
                else               {
                    JOptionPane.showMessageDialog(null, "decompressed");
                    label1.setText(decompress());
                }

            }
        });
    }

    public static void compress(String s)
    {

        HashMap<String,Double> freq=new HashMap<String, Double>();
        int n=s.length();
        for(int i=0;i<n;i++)
        {
            String d="";
            d+=s.charAt(i);
            if(freq.containsKey(d))
            {
                Double replac=freq.get(d)+1.0;
                freq.replace(d,replac);
            }
            else
            {
                freq.put(d,1.0);
            }
            //freq.replace(d, (freq.get(d) + 1));
        }

        Double last=0.0;
        HashMap<String,Double> lowRange=new HashMap<String, Double>();

        HashMap<String,Double> highRange=new HashMap<String, Double>();

        for (Map.Entry<String, Double> entry : freq.entrySet())
        {
            String key = entry.getKey();
            Double value = entry.getValue();
            freq.replace(key,(freq.get(key)/n));
            lowRange.put(key,last);
            last+=value/n;
            highRange.put(key,last);
            //   System.out.println(key+" "+freq.get(key));
        }


        Double low=0.0;
        Double high=1.0;
        Double range=high-low;
        for(int i=0;i<n;i++)
        {
            String d="";
            d+=s.charAt(i);
            range=high-low;
            high=low+(range*highRange.get(d));
            low=low+(range*lowRange.get(d));
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
        writer.println(n);
        writer.println((high+low)/2.0);
        for (Map.Entry<String, Double> entry : lowRange.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            writer.println(key+" "+value+" "+highRange.get(key));
            //System.out.println(value+" "+key);
        }
        writer.close();
    }

    public static String decompress()
    {
        Scanner ref=null;
        try {
            ref=new Scanner(new File("compressed.txt"));
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        HashMap<String,Double> lowRange=new HashMap<String, Double>();
        HashMap<String,Double> highRange=new HashMap<String, Double>();
        int n=ref.nextInt();
        Double compressioncode=ref.nextDouble();
        while(ref.hasNext())
        {
            String key=ref.next();
            Double low=ref.nextDouble();
            Double high=ref.nextDouble();
            lowRange.put(key,low);
            highRange.put(key,high);
        }
      /*  System.out.println(n);
        System.out.println(compressioncode);
        for (Map.Entry<String, Double> entry : lowRange.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            System.out.println(key+" "+value+" "+highRange.get(key));
            //System.out.println(value+" "+key);
        }
        */
      String output="";
      Double low=0.0,high=1.0,range=high-low,code=compressioncode;
      for(int i=0;i<n;i++)
      {
          code=(compressioncode-low)/(high-low);
          range=high-low;
          for (Map.Entry<String, Double> entry : lowRange.entrySet()) {
              String key = entry.getKey();
              Double lower = entry.getValue();
              Double higher= highRange.get(key);
              if(code>=lower&&code<=higher)
              {
                  output+=key;
                  high=low+(range*higher);
                  low=low+(range*lower);
                  break;
              }
              //System.out.println(value+" "+key);
          }
      }
    //  System.out.println(output);
        return output;

    }



    public static void main(String[] args)
    {

        JFrame frame=new JFrame("AR");
        frame.setContentPane(new AR().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}


