import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;



public class huff {

    private JTextField textField1;
    private JButton compressButton;
    private JButton decompressButton;
    private JLabel label1;
    private JPanel panel1;

    public huff() {
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "no text inserted");
                }
                else               {
                    JOptionPane.showMessageDialog(null, "compressed");
                    compress(textField1.getText());
                }
            }
        });
        decompressButton.addActionListener(new ActionListener() {
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
        HashMap<String,String>dic=new HashMap<String ,String>();
        HashMap<String,Double>freq=new HashMap<String, Double>();
        int n=s.length();
        for(int i=0;i<n;i++)
        {
            String d="";
            d+=s.charAt(i);
            if(freq.containsKey(d))
            {
                Double rep=freq.get(d)+1.0;
                freq.replace(d,rep);
            }
            else
            {
                freq.put(d,1.0);
            }
            //freq.replace(d, (freq.get(d) + 1));
        }
        for (Map.Entry<String, Double> entry : freq.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            freq.replace(key,(freq.get(key)/n));
            dic.put(key,"");
         //   System.out.println(key+" "+freq.get(key));
        }

        if(freq.size()==1)
        {
            for (Map.Entry<String, Double> entry : freq.entrySet()) {
                String key = entry.getKey();
                dic.replace(key,"0");
            }
        }

        while (freq.size()!=1)
        {
            String fir = null;
            double mi1=5.0;
            String second=null;
            double mi2=5.0;
            for (Map.Entry<String, Double> entry : freq.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                if(value<mi1)
                {
                    mi1=value;
                    fir=key;
                }
            }
            for (Map.Entry<String, Double> entry : freq.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                if(value<mi2&&!key.equals(fir))
                {
                    mi2=value;
                    second=key;
                }
            }
            for(int i=0;i<fir.length();i++)
            {
                String sub=fir.substring(i,i+1);
                String val=dic.get(sub);
                val="1"+val;
                dic.replace(sub,val);
            }
            for(int i=0;i<second.length();i++)
            {
                String sub=second.substring(i,i+1);
                String val=dic.get(sub);
                val="0"+val;
                dic.replace(sub,val);
            }
            String nw=second+fir;
            Double nwval=mi1+mi2;
            freq.remove(fir);
            freq.remove(second);
            freq.put(nw,nwval);
        }

        PrintWriter writer= null;
        try {
            writer = new PrintWriter("dic.txt");
        } catch (Exception e) {
        }

        for (Map.Entry<String, String> entry : dic.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
               writer.println(value+" "+key);
               System.out.println(value+" "+key);
        }
        writer.close();
        String out="";
        for(int i=0;i<n;i++)
        {
            String d="";
            d+=s.charAt(i);
            out+=dic.get(d);
        }
        try {
            writer = new PrintWriter("input.txt");
        } catch (Exception e) {
        }
        writer.println(out);
        writer.close();

    }

    public static String decompress()
    {
        String s;
        HashMap<String,String>dic=new HashMap<String ,String>();
        Scanner ref=null;
        try {
            ref=new Scanner(new File("input.txt"));
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        s=ref.nextLine();
     //   System.out.println(s);
        try {
            ref=new Scanner(new File("dic.txt"));
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        while(ref.hasNext())
        {
            String key=ref.next();
            String value=ref.nextLine();
            value=value.trim();
            dic.put(key,value);
          //  System.out.println(key+value);
        }
        String out="";
        String sofar="";
        for(int i=0;i<s.length();i++)
        {
            sofar+=s.charAt(i);
            if(dic.containsKey(sofar)) {
                out += dic.get(sofar);
                sofar = "";
            }
        }

        return  out;
    }

    public static void main(String[] args) {

        JFrame frame=new JFrame("huff");
        frame.setContentPane(new huff().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
