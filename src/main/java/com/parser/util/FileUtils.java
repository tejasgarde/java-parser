package com.parser.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {
	
	
	
	public static List<String> getFilesList(){
		
		return Arrays.asList("/home/tejas/Documents/development/IDE/java/eclipse-maven/maven-ws/parser-test/src/main/java/com/parser/files/Car.java",
				"/home/tejas/Documents/development/IDE/java/eclipse-maven/maven-ws/parser-test/src/main/java/com/parser/files/Tesla.java");
		
	}
	
	
	public static List<String> getExternalFileList(){
		
		List<String> fileList = new ArrayList<>();
		
		String filePath = "/home/tejas/Documents/development/IDE/java/eclipse-maven/maven-ws/parser-test/src/main/resources/filelist.txt";
		
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

			stream.forEach(file -> fileList.add(file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileList;
		
	}
	
	public static void main(String[] args) {
		for(String file : getExternalFileList()){
			System.out.println(file);
		}
	}

}
