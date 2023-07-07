package com.project.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Component;
@Component
public class Utility {
	List<String> javaFileList = new ArrayList<String>();
	
	public static void main(String[] ag) {
		//extractZip("C:\\Project\\zip\\","citibank-hackaton-junitutiltiy.zip", "C:\\Project\\zip\\");
		//scanJavaFiles("C:\\Project\\zip\\");
	}
	
	public boolean extractZip(String srcDir,String zipFileName, String destDir) {
		String zipFilePath = srcDir + File.separator + zipFileName;
		boolean status = false;
		byte[] buffer = new byte[1024];
		File destFolder = new File(destDir);
		ZipInputStream inputStream = null;
		ZipEntry zipEntry = null;
		File file = null;
		try {
			if(!destFolder.exists()) {
				destFolder.mkdir();
			}
			inputStream = new ZipInputStream(new FileInputStream(zipFilePath));
			zipEntry = inputStream.getNextEntry();
			while(zipEntry != null) {
				String fileName = zipEntry.getName();
				file = new File(destDir + File.separator + fileName);
				if(zipEntry.isDirectory()) {
					file.mkdirs();
				}else {
					new File(file.getParent()).mkdirs();
					FileOutputStream outputStream = new FileOutputStream(file);
					int length;
					while((length = inputStream.read(buffer))>0) {
						outputStream.write(buffer, 0, length);
					}
					outputStream.close();
				}
				zipEntry = inputStream.getNextEntry();
			}
			inputStream.closeEntry();
			inputStream.close();
			status=true;
		}catch(Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	public HashMap<String,String> scanJavaFiles(HashMap<String, String> hMap, String folderName) throws IOException {
		File srcFile = new File(folderName);
		File[] fileList = srcFile.listFiles();
		for(File file:fileList) {
			if(file.isDirectory()) {
				//System.out.println(file.getAbsolutePath());
				scanJavaFiles(hMap,file.getAbsolutePath());
			}else if(file.getAbsolutePath().contains("\\src\\main\\java\\") && file.getName().endsWith(".java")){
				//System.out.println(file.getName()+" ::: "+file.getParent());
				
				String[] splitSrc = file.getAbsolutePath().split("\\\\src\\\\main\\\\java\\\\");
				String srcDir = splitSrc[0]+"\\src\\main\\java\\";
				for(String temp : splitSrc) {
					//System.out.println(temp);
					if(temp.endsWith(".java")) {
						//String[] fileName = temp.split("\\\\");
						//temp = fileName[fileName.length-1];
						//System.out.println(fileName[fileName.length-1]);
						//System.out.println(temp.replaceAll("\\\\", ".").replaceAll(".java", ""));
						//javaFileList.add(temp.replaceAll("\\\\", ".").replaceAll(".java", ""));
						hMap.put(temp.replaceAll("\\\\", ".").replaceAll(".java", ""), srcDir);
					}
				}
			}
		}
		//System.out.println(hMap.size());
		return hMap;
	}
	
}
