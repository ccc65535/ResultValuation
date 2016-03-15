import java.io.*;

import javax.swing.JOptionPane;


public class FileDivide {

	private long startTime,endTime;
	private String inputFolder,outputFolder;
	
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public String getInputFolder() {
		return inputFolder;
	}
	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}
	public String getOutputFolder() {
		return outputFolder;
	}
	public void setOutputFolder(String outFolder) {
		this.outputFolder = outFolder;
	}

	public void divide() throws IOException{
		File inFolder=new File(inputFolder);				//get inputFolder by path
		
		if(inFolder.isDirectory()){
			
			String  []userFolderInPath=inFolder.list();			//get userFolders' path
			
			 for (int i = 0; i < userFolderInPath.length; i++) {	 
                 File userInFolder =new File(inputFolder+"/"+userFolderInPath[i]);	//get userFolder by path
                 
                 String userOutFolder=outputFolder+"/"+userInFolder.getName();
                 System.out.println(userOutFolder);
                 
                 
                 if(inFolder.isDirectory()){
                	 String []userDatePath=userInFolder.list();	//get userDates' path
                		 //makeDirs(userOutFolder);
 
                	 for (int j = 0;j < userDatePath.length; j++){
                		 File userDate =new File(userDatePath[j]);
                		 long date=Long.parseLong(userDate.getName());//.substring(0,userDate.getName().indexOf('.')));
                		 if(date>=startTime&&date<=endTime){
                			 String userDateinPath=inputFolder+"/"+userInFolder.getName()+"/"+userDate.getName();
                			 String userDateOutPath=userOutFolder+"/"+userDate.getName();
                			 copyFile(userDateinPath,userDateOutPath,true);
                		 }
                	 }
                 }
                 
                 
			 }
		}
	}
	
	 public static boolean copyFile(String srcFileName, String destFileName,  
	            boolean overlay) {  
		 	String MESSAGE;
	        File srcFile = new File(srcFileName);  
	  
	        // 判断源文件是否存在  
	        if (!srcFile.exists()) {  
	            MESSAGE = "源文件：" + srcFileName + "不存在！";  
	            JOptionPane.showMessageDialog(null, MESSAGE);  
	            return false;  
	        } else if (!srcFile.isFile()) {  
	            MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";  
	            JOptionPane.showMessageDialog(null, MESSAGE);  
	            return false;  
	        }  
	  
	        // 判断目标文件是否存在  
	        File destFile = new File(destFileName);  
	        if (destFile.exists()) {  
	            // 如果目标文件存在并允许覆盖  
	            if (overlay) {  
	                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
	                new File(destFileName).delete();  
	            }  
	        } else {  
	            // 如果目标文件所在目录不存在，则创建目录  
	            if (!destFile.getParentFile().exists()) {  
	                // 目标文件所在目录不存在  
	                if (!destFile.getParentFile().mkdirs()) {  
	                    // 复制文件失败：创建目标文件所在目录失败  
	                    return false;  
	                }  
	            }  
	        }  
	  
	        // 复制文件  
	        int byteread = 0; // 读取的字节数  
	        InputStream in = null;  
	        OutputStream out = null;  
	  
	        try {  
	            in = new FileInputStream(srcFile);  
	            out = new FileOutputStream(destFile);  
	            byte[] buffer = new byte[1024];  
	  
	            while ((byteread = in.read(buffer)) != -1) {  
	                out.write(buffer, 0, byteread);  
	            }  
	            return true;  
	        } catch (FileNotFoundException e) {  
	            return false;  
	        } catch (IOException e) {  
	            return false;  
	        } finally {  
	            try {  
	                if (out != null)  
	                    out.close();  
	                if (in != null)  
	                    in.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	
	 public static void copy(String path, String copyPath) throws IOException{
		 	FileReader file = new FileReader(path);
		 	FileWriter copyFile=new FileWriter(copyPath);
		 	 int read;  
	            //byte b[]=new byte[1024];  
	            read=file.read();  
	            while(read!=-1)  
	            {  
	            	copyFile.write(read);  
	                //read=fis.read(b);  
	                read=file.read();  
	            }  
	            file.close();  
	            copyFile.close();  
		 	
		 }
	 public static boolean makeDirs(String filePath) throws IOException {
	        String folderName = filePath;
	        if (folderName == null || folderName.isEmpty()) {
	            return false;
	        }

	        File folder = new File(folderName);
	        if(!folder.exists()){
	        	folder.mkdirs();
	        }
	        if(folder.exists()){
	        	folder.delete();
	        	folder.mkdirs();
	        }
	        
	        return (folder.exists() && folder.isDirectory()) ? true : false;
	    }
	
	public static void main(String args[]){
		
		FileDivide fd=new FileDivide();
		fd.setInputFolder("D:/个人保存/隐式评分模块文档/code/用户行为数据");
		fd.setOutputFolder("D:/个人保存/隐式评分模块文档/code/input1");
		fd.setStartTime(20141201);
		fd.setEndTime(20141215);
		
		FileDivide fd2=new FileDivide();
		fd2.setInputFolder("D:/个人保存/隐式评分模块文档/code/用户行为数据");
		fd2.setOutputFolder("D:/个人保存/隐式评分模块文档/code/input2");
		fd2.setStartTime(20141216);
		fd2.setEndTime(20141231);
		try {
			fd.divide();
			fd2.divide();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
