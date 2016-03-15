import java.io.*;
import java.util.*;


public class Evaluate {
	private String resultPath,examplePath;
	public List<Pref> result=new ArrayList<Pref>();
	public List<Pref> example=new ArrayList<Pref>();

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public String getExamplePath() {
		return examplePath;
	}

	public void setExamplePath(String examplePath) {
		this.examplePath = examplePath;
	}
	
	public void generateData() throws Exception{
		BufferedReader resultFile=new BufferedReader(new FileReader(resultPath));
		BufferedReader exampleFile=new BufferedReader(new FileReader(examplePath));
		String line;
		while((line=resultFile.readLine())!=null){
			Pref p=new Pref();
			p.uID=line.split(",")[0];
			p.iID=line.split(",")[1];
			p.score=line.split(",")[2];
			result.add(p);
		}
		resultFile.close();
		
		while((line=exampleFile.readLine())!=null){
			Pref p=new Pref();
			p.uID=line.split(",")[0];
			p.iID=line.split(",")[1];
			p.score=line.split(",")[2];
			example.add(p);
		}
		exampleFile.close();
	}
	
	
	public void eval(BufferedWriter out) throws Exception{
		int rn,en,correct=0;
		double precision,recall;
		rn=result.size();
		en=example.size();
		
		Pref p=new Pref();
		for(int i=0;i<rn;i++){
			p=result.get(i);
			if(contains(p,example)){
				correct++;
			}
		}
		precision=(double)correct/rn;
		recall=(double)correct/en;
		
		System.out.println("Precision:"+precision);
		System.out.println("Recall:"+recall);
		out.write("Precision:"+precision+" ,Recall:"+recall);
		out.newLine();
		
		
	}
	
	
	public void evalUser(List<Pref> userResult,List<Pref> userExample){

		if(userResult==null||userResult.size()==0){
			System.out.println("null");
		}
		else if(userExample==null||userExample.size()==0){
			long uID=Long.parseLong(userResult.get(0).uID);
			System.out.println(uID+"\tno example");
		}
		else{
			int rn,en,correct=0;
			long uID=Long.parseLong(userResult.get(0).uID);
			double precision,recall;
			rn=userResult.size();
			en=userExample.size();
			
			Pref p=new Pref();
			for(int i=0;i<rn;i++){
				p=userResult.get(i);
				if(contains(p,userExample)){
					correct++;
				}
			}
			precision=(double)correct/rn;
			recall=(double)correct/en;
			
			System.out.println(uID+"\tPrecision:"+precision+", Recall:"+recall);
		}
		
	}
	
	public void evalUser1(List<Pref> userResult,List<Pref> userExample,BufferedWriter out) throws Exception{

		
		if(userResult==null||userResult.size()==0){
			System.out.println("null");
		}
		else if(userExample==null||userExample.size()==0){
			long uID=Long.parseLong(userResult.get(0).uID);
			System.out.println(uID+"\tno example");
		}
		else{
			
			int rn,en,correct=0;
			long uID=Long.parseLong(userResult.get(0).uID);
			double precision,recall;
			List<Double> diffs=new ArrayList<Double>();
			double MSE=0;
			boolean contains;
			
			
			rn=userResult.size();
			en=userExample.size();
			
			Pref p1,p2;
			for(int i=0;i<userResult.size();i++){
				p1=userResult.get(i);
				contains=false;
				for(int j=0;j<userExample.size();j++){
					p2=userExample.get(j);
					if(p1.iID.equals(p2.iID)){
						contains=true;
						diffs.add(Double.parseDouble(p1.score)-Double.parseDouble(p2.score));
						userExample.remove(j);
						correct++;
						break;
					}
				}
				
				if(!contains){
					diffs.add(Double.parseDouble(p1.score));
				}
			}
			
			if(!userExample.isEmpty()){
				for(int j=0;j<userExample.size();j++){
					p2=userExample.get(j);
					diffs.add(Double.parseDouble(p2.score));
				}
					
			}
			
			if(!diffs.isEmpty()){
				for(int n=0;n<diffs.size();n++)
					MSE+=diffs.get(n)*diffs.get(n);
				MSE/=diffs.size();
			}
			
			precision=(double)correct/rn;
			recall=(double)correct/en;
			
			System.out.println(uID+"\tPrecision:"+precision+", Recall:"+recall+", MSE:"+MSE);
			out.write(uID+"\tPrecision:"+precision+", Recall:"+recall+", MSE:"+MSE);
			out.newLine();
		}
		
	}
	
	
	
	
	
	public static boolean contains(Pref record,List<Pref> list){
		if(list!=null){
			int n=list.size();
			Pref content;
			for(int i=0;i<n;i++){
				content=list.get(i);
				if(record.uID.equals(content.uID)&&record.iID.equals(content.iID)){
					return true;
				}
			}
			return false;
		}
		
		else{
			System.out.println("list is empty");
			return false;
		}
	}
	
	public static void main(String args[]){
		try {
			Evaluate val=new Evaluate();
			//val.resultPath="E:\\RATE\\formal1\\result-fm.txt";
			//val.examplePath="E:\\RATE\\formal1\\example.txt";	
			val.resultPath="E:\\RATE\\movielens\\result-icf.txt";
			val.examplePath="E:\\RATE\\movielens\\example.txt";	
			val.generateData();
			val.toString();
			
			BufferedWriter out=new BufferedWriter(new FileWriter("E:\\RATE\\movielens\\eval-icf.txt"));
			
			val.eval(out);
			
			List<Pref> userResult=new ArrayList<Pref>();
			List<Pref> userExample=new ArrayList<Pref>();
			long u,u1,u2;
			boolean flag=false;//两个list是否都存放相同uid的预测评分
			u=Long.parseLong(val.result.get(0).uID);
			for(int i=0,j=0;i<val.result.size()&&j<val.example.size();){
				
				u1=Long.parseLong(val.result.get(i).uID);
				u2=Long.parseLong(val.example.get(j).uID);
				if(u==u1||u==u2){
					if(u1==u2){
						userResult.add(val.result.get(i));
						userExample.add(val.example.get(j));
						i++;j++;
					}
					if(u1<u2){
						userResult.add(val.result.get(i));
						i++;
						//u1=Long.parseLong(val.result.get(i).uID);
					}
					if(u1>u2){
						userExample.add(val.example.get(j));
						j++;
						//u2=Long.parseLong(val.result.get(j).uID);
					}
					flag=true;
				}
				else{
					if(flag){
						val.evalUser1(userResult, userExample,out);
						userResult.clear();
						userExample.clear();
						//userResult=new ArrayList<Pref>();
						//userExample=new ArrayList<Pref>();
						flag=false;
					}
					u=u1<u2?u1:u2;
					
				}
					
			}
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}




class Pref{
	public String uID;
	public String iID;
	public String score;
}