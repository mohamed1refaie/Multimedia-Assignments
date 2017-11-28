
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.io.*;
import java.lang.*;
import javafx.util.Pair;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void compress(String s)
    {
        ArrayList<Integer>vec=new ArrayList<Integer>();
        HashMap<String,Integer>dic=new HashMap<String, Integer>();
        for(Integer i=0;i<127;i++)
        {
            String SOS;
            SOS="";
            SOS+=(char)('0'+i);
            dic.put(SOS,i);
        }
        dic.put(" ",127);
        int limit=128;
        String cur="";
        String last="";
        for(int i=0;i<s.length();i++)
        {
            cur+= s.charAt(i);
            if(dic.containsKey(cur))
            {
                last=cur;
            }
            else
            {
                Integer x=dic.get(last);
                vec.add(x);
                dic.put(cur,limit);
                limit++;
                cur="";
                last="";
                i--;
            }
        }
        Integer x=dic.get(last);
        vec.add(x);
        PrintWriter writer= null;
        try {
            writer = new PrintWriter("input.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<vec.size();i++)
            writer.print(vec.get(i)+" ");
        writer.close();

    }

    public static void decompress()
    {
        ArrayList<Integer>vec=new ArrayList<Integer>();
        Scanner ref=null;
        try {
            ref=new Scanner(new File("input.txt"));
        }
        catch(Exception e)
        {
            System.out.println("err");
        }
        while(ref.hasNext())
        {
            vec.add(ref.nextInt());
        }
        HashMap<Integer,String>dic=new HashMap<Integer,String>();
        for(Integer i=0;i<127;i++)
        {
            String SOS;
            SOS="";
            SOS+=(char)('0'+i);
            dic.put(i,SOS);
           // System.out.println(i+" "+SOS);
        }
        dic.put(127," ");
        int limit=128;
        String out="";
        String cur="";
        String toad="";
        String last=dic.get(vec.get(0));
        out+=last;
        for(int i=1;i<vec.size();i++)
        {
          //  System.out.print(vec.get(i)+" ");
            Integer x=vec.get(i);
            String ot=dic.get(x);
         //   System.out.print(ot+" ");
            if(dic.containsKey(x)) {
                cur=ot;
            //    System.out.println(cur+" ");
                out+=cur;
                toad=last+cur.substring(0,1);
                dic.put(limit,toad);
                limit++;
                last=cur;
            }
            else
            {
             //   System.out.print("second");
                toad=last+last.substring(0,1);
             //   System.out.println(toad);
                dic.put(limit,toad);
                limit++;
                last=toad;
                out+=toad;
            }
        }
        PrintWriter writer= null;
        try {
            writer = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.println(out);
        writer.close();

    }

    public static void main(String[] args) {
        compress("abaababbaabaabaaaababbbbbbbb    kkkkk       lkjkj");
        decompress();
    }
}
