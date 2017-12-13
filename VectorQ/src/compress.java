import java.util.ArrayList;

public class compress {
    static ArrayList< ArrayList<Integer> >arr=new ArrayList< >();
    static ArrayList< ArrayList<Integer> >codebook=new ArrayList< >();
    static Integer sz,leng,wid;
    static Integer levels;

    public static ArrayList<Integer> GetAvg(ArrayList< ArrayList<Integer> >a)
    {
        ArrayList<Integer>ret=new ArrayList<Integer>();
        for(int i=0;i<sz*sz;i++) {
            Integer sum = 0;
            for(int j=0;j<a.size();j++)
            {
                if(i<a.get(j).size())
                {
                    sum+=a.get(j).get(i);
                }
            }
            sum/=a.size();
            ret.add(sum);
        }
        return ret;
    }
    static int distance(ArrayList<Integer>a, ArrayList<Integer>b,Integer inc){
        int dist=0;
        for(int i=0;i<a.size();i++){
            dist+=Math.pow(a.get(i)-b.get(i)+inc,2);
        }
        return (int)Math.sqrt(dist);
    }
    static void solve(int level,ArrayList<ArrayList<Integer>>a)
    {
        if(level<=1||a.size()==0)
        {
            if(a.size()>0)codebook.add(GetAvg(a));
            return;
        }
        ArrayList<ArrayList<Integer>>left=new ArrayList<>();
        ArrayList<ArrayList<Integer>>right=new ArrayList<>();
        ArrayList<Integer>avg=GetAvg(a);
        for(int i=0;i<a.size();i++)
        {
            int e1=distance(a.get(i),avg,1);
            int e2=distance(a.get(i),avg,-1);
            if(e1>=e2)left.add(a.get(i));
            else right.add(a.get(i));
        }
        solve(level/2,left);
        solve(level/2,right);
    }
    public static void compress(int size,int codesize,String path) {

        int[][] img = ImageClass.readImage(path);
        sz=size;
        leng=img.length;
        wid=img[0].length;
        for (int i = 0; i < img.length; i += size)
        {
            for(int j=0;j<img[i].length;j+=size)
            {
                ArrayList<Integer>n=new ArrayList<>();
                for(int k=i;k<i+size;k++)
                {
                    for(int l=j;l<j+size;l++)
                    {
                        n.add(img[k][l]);
                    }
                }
                arr.add(n);
            }
        }
        solve(codesize,arr);
        for(int i=0;i<arr.size();i++)
        {
            int min= (int) (1e9+5);
            int indx= -1;
            for(int j=0;j<codebook.size();j++)
            {
                int d=distance(codebook.get(j),arr.get(i),0);
                if(d<min)
                {
                    min=d;
                    indx=j;
                }
            }
            arr.set(i,codebook.get(indx));
        }

        int cnt=0,Cntt=0;
        int[][] newMatrix=new int[img.length][img.length];
        for(int i=0;i<newMatrix.length;i+=sz){
            for(int j=0;j<newMatrix[i].length;j+=sz){
                for(int k=i;k<i+sz;k++){
                    for(int l=j;l<j+sz;l++){
                        if(l<newMatrix.length&&k<newMatrix.length&&cnt<arr.size()&&Cntt<arr.get(cnt).size())
                            newMatrix[k][l]=arr.get(cnt).get(Cntt++);
                    }
                }
                cnt++;
                if(cnt==leng)break;
                Cntt=0;
            }
        }

        ImageClass.writeImage(newMatrix,"C:\\Users\\Refaie\\Desktop\\compressed.jpg");


    }

    public static void main(String[] args) {
       // compress(10,32,"C:\\Users\\Refaie\\Desktop\\soraRef.jpg");
    }

}
