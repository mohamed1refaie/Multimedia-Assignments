package com.pack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import java.lang.*;
import javafx.util.Pair;
import java.util.Scanner;
import java.util.ArrayList;

public class LZ77CD {
    private JButton comp;
    private JPanel PanelMain;
    private JTextField textField1;
    private JButton decompressButton;
    private static  Scanner ref;
    private static  Scanner first;
    private static PrintWriter writer;

    public static void openfile()
    {
		/*try {
			out = new Formatter("input.txt");
		}
		catch(Exception e)
		{
			System.out.println("err");
		}*/
        try {
            ref=new Scanner(new File("input.txt"));
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        try {
            first=new Scanner (new File("first.txt"));
        }
        catch(Exception e)
        {
            System.out.println("Err");
        }

    }

    public static void compress()
    {
        try {
            writer = new PrintWriter("input.txt", "UTF-8");
        }
        catch(Exception e)
        {
            System.out.println("err");
        }

        ArrayList <Pair <Pair<Integer,Integer>,String> > tags =  new ArrayList <Pair <Pair<Integer,Integer>,String> > ();
        String input;
        input=first.nextLine();
        String temp="";
        char curr;
        Integer indx=-1;
        Integer lastindx=-1;
        Integer start=0;

        for(int i=0;i<input.length();i++)
        {
            curr=input.charAt(i);
            temp += curr;
            for(int j=0;j<=start-temp.length();j++)
            {
                String s=input.substring(j, j+temp.length());
                if(s.equals(temp))
                    indx=j;
            }
            if(indx==-1)
            {
                if(lastindx!=-1)
                {
                    String lol="";
                    lol += curr;
                    tags.add(new Pair < Pair<Integer,Integer> ,String > (new Pair<Integer,Integer>(start-lastindx,temp.length()-1),lol));
                }
                else
                {
                    String lol="";
                    lol += curr;
                    tags.add(new Pair < Pair<Integer,Integer> ,String > (new Pair<Integer,Integer>(0,0),lol));
                }
                lastindx=-1;
                indx=-1;
                temp="";
                start=i+1;
            }
            else
            {
                lastindx=indx;
                indx=-1;
            }
        }
        if(lastindx!=-1)
        {
            tags.add(new Pair < Pair<Integer,Integer> ,String > (new Pair<Integer,Integer>(start-lastindx,temp.length()),"NULL"));
        }
        for (Pair <Pair<Integer,Integer>,String> cntr : tags)
        {
            Integer first = cntr.getKey().getKey();
            Integer sec= cntr.getKey().getValue();
            String str=cntr.getValue();
            writer.println(first+ " "+ sec + " " + str );


        }
        writer.close();

    }

    public static void decompress ()
    {
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        ArrayList <Pair <Pair<Integer,Integer>,String> > tags =  new ArrayList <Pair <Pair<Integer,Integer>,String> > ();
        String output="";
        while(ref.hasNext())
        {
            Integer a;
            a=ref.nextInt();
            Integer b;
            b=ref.nextInt();
            String s;
            s=ref.nextLine();
            s=s.trim();
            String temp=output.substring(output.length()-a, (output.length()-a)+b);
            output+=temp;
            if(s.equals("NULL")==false)
            {
                output+=s;
            }

        }
        writer.println(output);
        writer.close();
    }

    public LZ77CD() {
        comp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"you must enter some text");
                }
                else
                {
                    try {
                        writer = new PrintWriter("first.txt", "UTF-8");
                    }
                    catch(Exception E)
                    {
                        System.out.println("err");
                    }
                    writer.println(textField1.getText());
                    JOptionPane.showMessageDialog(null,"compressed");
                    writer.close();
                    compress();
                }
            }
        });
        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"you must enter some text");
                }
                else
                {
                    decompress();
                    JOptionPane.showMessageDialog(null,"Decompressed");
                }
            }
        });
    }

    public static void main(String[] args) {
        openfile();
        JFrame frame = new JFrame ("LZ77");
        frame.setContentPane(new LZ77CD().PanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
