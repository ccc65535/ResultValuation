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
	  
	        // �ж�Դ�ļ��Ƿ����  
	        if (!srcFile.exists()) {  
	            MESSAGE = "Դ�ļ���" + srcFileName + "�����ڣ�";  
	            JOptionPane.showMessageDialog(null, MESSAGE);  
	            return false;  
	        } else if (!srcFile.isFile()) {  
	            MESSAGE = "�����ļ�ʧ�ܣ�Դ�ļ���" + srcFileName + "����һ���ļ���";  
	            JOptionPane.showMessageDialog(null, MESSAGE);  
	            return false;  
	        }  
	  
	        // �ж�Ŀ���ļ��Ƿ����  
	        File destFile = new File(destFileName);  
	        if (destFile.exists()) {  
	            // ���Ŀ���ļ����ڲ�������  
	            if (overlay) {  
	                // ɾ���Ѿ����ڵ�Ŀ���ļ�������Ŀ���ļ���Ŀ¼���ǵ����ļ�  
	                new File(destFileName).delete();  
	            }  
	        } else {  
	            // ���Ŀ���ļ�����Ŀ¼�����ڣ��򴴽�Ŀ¼  
	            if (!destFile.getParentFile().exists()) {  
	                // Ŀ���ļ�����Ŀ¼������  
	                if (!destFile.getParentFile().mkdirs()) {  
	                    // �����ļ�ʧ�ܣ�����Ŀ���ļ�����Ŀ¼ʧ��  
	                    return false;  
	                }  
	            }  
	        }  
	  
	        // �����ļ�  
	        int byteread = 0; // ��ȡ���ֽ���  
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
		fd.setInputFolder("D:/���˱���/��ʽ����ģ���ĵ�/code/�û���Ϊ����");
		fd.setOutputFolder("D:/���˱���/��ʽ����ģ���ĵ�/code/input1");
		fd.setStartTime(20141201);
		fd.setEndTime(20141215);
		
		FileDivide fd2=new FileDivide();
		fd2.setInputFolder("D:/���˱���/��ʽ����ģ���ĵ�/code/�û���Ϊ����");
		fd2.setOutputFolder("D:/���˱���/��ʽ����ģ���ĵ�/code/input2");
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
