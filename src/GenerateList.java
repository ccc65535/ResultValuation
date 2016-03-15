import java.io.*;
import java.util.*;


public class GenerateList {
	private String inPath;
	private String listPath;
	
	
	public String getInPath() {
		return inPath;
	}
	public void setInPath(String inPath) {
		this.inPath = inPath;
	}
	public String getListPath() {
		return listPath;
	}
	public void setListPath(String listPath) {
		this.listPath = listPath;
	}
	
	public void generateList() throws Exception{
		BufferedReader inFile=new BufferedReader(new FileReader(inPath));
		BufferedWriter listFile=new BufferedWriter(new FileWriter(listPath));
		List<Content> list=new ArrayList<Content>();
		String line;
		
		while((line=inFile.readLine())!=null){
			Content con=new Content();
			con.iID=line.split(",")[1].trim();
			con.name=line.split(",")[3].trim();
			if(!contains(con,list))
				list.add(con);
		}
		inFile.close();
		
		Collections.sort(list);
		for(int i=0;i<list.size();i++){
			listFile.write(list.get(i).iID+"\t"+list.get(i).name);
			listFile.newLine();
		}
		listFile.close();
		
		
	}
	
	public static boolean contains(Content con,List<Content> list){
		Content cc=new Content();
		for(int i=0;i<list.size();i++){
			cc=list.get(i);
			if(cc.iID.equals(con.iID)&&cc.name.equals(con.name))
				return true;
		}
		return false;
	}
	
	
	public static void main(String args[]){
		GenerateList gl=new GenerateList();
		gl.inPath="E:\\RATE\\formal1\\RateData.txt";
		gl.listPath="E:\\RATE\\formal1\\Ŀ¼.txt";
		try {
			gl.generateList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class Content implements Comparable{
	public String iID;
	public String name;
	@Override
	public int compareTo(Object obj) {
		Content c1=(Content)obj;
		if(Long.parseLong(iID)>Long.parseLong(c1.iID))
			return 1;
		else if(Long.parseLong(iID)<Long.parseLong(c1.iID))
			return -1;
		else
			return 0;
	}
	
	
}
