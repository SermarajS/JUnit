package com.project.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Component;
@Component
public class JUnitGeneratorV3 {
	
	private static void generateJUnitTestClass(String className, String classLoc) {
       // String testClassName = className + "Test";
        StringBuilder sb = new StringBuilder();

        sb.append("import org.junit.Test;\n");
        sb.append("import static org.junit.Assert.*;\n\n");
        sb.append("public class ").append(className.replace(".java", "")).append(" {\n\n");
        sb.append("\t@Test\n");
        sb.append("\tpublic void test() {\n");
        sb.append("\t\t// TODO: Write test method for ").append(className).append("\n");
        sb.append("\t}\n");
        sb.append("}\n");

        try {
            // Create the test class file in the same package as the original class
            //File testClassFile = new File(testClassName.replace(".", "/") + ".java");
        	File testClassFile = new File(classLoc+className);
            FileWriter writer = new FileWriter(testClassFile);
            writer.write(sb.toString());
            writer.close();

           // System.out.println("Generated JUnit test class: " + testClassFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to generate JUnit test class for: " + className);
            e.printStackTrace();
        }
    }
	
	public void generateTestCase(HashMap<String, String> hMap) {
		Set<String> keySet = hMap.keySet();
		String testClassName = "";
		String testClassLoc = "";
		try {
			for(String fileDetail : keySet) {
				//System.out.println(fileDetail+" :: "+hMap.get(fileDetail));
				testClassName = fileDetail + "Test.java";
				testClassLoc = hMap.get(fileDetail).replaceAll("\\\\src\\\\main\\\\java", "\\\\src\\\\test\\\\java");
				generateJUnitTestClass(testClassName, testClassLoc);
				System.out.println("TestCase created:: "+testClassLoc+testClassName);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
