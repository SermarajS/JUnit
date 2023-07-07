package com.project.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.utility.InputData;
import com.project.utility.JUnitGeneratorV2;
import com.project.utility.JUnitGeneratorV3;
import com.project.utility.JavaClassNamesInClasspath;
import com.project.utility.UserDefinedClassFinder;
import com.project.utility.Utility;

@RestController
public class TestCaseController {
	
	@Autowired
	JUnitGeneratorV2 jUnitGenerator;
	@Autowired
	JUnitGeneratorV3 jUnitGeneratorV3;
	@Autowired
	JavaClassNamesInClasspath javaClassNamesInClasspath;
	@Autowired
	UserDefinedClassFinder userDefinedClassFinder;
	@Autowired
	Utility utility;
	
	@GetMapping("/generateTestCase_backup")
	public String generateTestCase_backup() {
		String packageName = "com.project.utility";
		List<Class<?>> classList = userDefinedClassFinder.getClassesInPackage(packageName);
		for(Class cls : classList){
			
			System.out.println("className: "+cls.getName()+" Package Name "+cls.getPackageName());
			jUnitGenerator.createTestcase(cls.getName()); 
		}
		
		return "TestCaseGenerated Successfully!!";
	}
	
	@PostMapping("/generateTestCase")
	public String generateTestCase(@RequestBody InputData input) throws IOException {
		//String packageName = "com.project.utility";
		//List<Class<?>> classList = userDefinedClassFinder.getClassesInPackage(packageName);
		String srcDir = "C:\\Project\\zip\\";
		String fileName = "citibank-hackaton-junitutiltiy.zip";
		String destDir = "C:\\Project\\zip\\";
		
		fileName = input.getFileName();
		srcDir = input.getSrcFolder();		
		destDir = input.getDestinationFolder();
		
		System.out.println("srcDir "+srcDir);
		System.out.println("fileName "+fileName);
		System.out.println("destDir "+destDir);
		
		boolean extractStatus = utility.extractZip(srcDir, fileName, destDir);
		System.out.println(extractStatus);
		HashMap<String, String> hMap = new HashMap<String, String>();
		if(extractStatus) {
			//List<String> javaFileList = utility.scanJavaFiles(destDir);
			HashMap<String, String> javaFileList = utility.scanJavaFiles(hMap, destDir+File.separator+fileName.replaceAll(".zip", ""));
			//for(String cls : javaFileList){
				
				//System.out.println("className: "+cls);
				//jUnitGenerator.createTestcase(cls);
			//}
			/*
			 * Set<String> keySet = javaFileList.keySet(); for(String fileDetail : keySet) {
			 * System.out.println(fileDetail+" :: "+javaFileList.get(fileDetail)); }
			 */
			
			jUnitGeneratorV3.generateTestCase(javaFileList);
			
			return "TestCaseGenerated Successfully!!";
		}else {
			return "Error occured while extracting zip";
		}
	}


}
