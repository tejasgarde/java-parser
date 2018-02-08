package com.parser.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.parser.util.FileUtils;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		
		main.processParsing();

	}
	
	
	private void processParsing(){
		List<String> fileList = FileUtils.getFilesList();
		
		for(String filesName : fileList){
			try {
				String fileString = checkImports(filesName);
				if(fileString != null){
					Files.write(new File(filesName).toPath(), fileString.getBytes());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	private String checkImports(String fileName) throws ParseException, IOException{
		
		CompilationUnit cu = JavaParser.parse(new File(fileName));
		
		List<ImportDeclaration> imports = cu.getImports();
		
		List<ImportDeclaration> newImports = new ArrayList<ImportDeclaration>();
		
		boolean hasLogger = false;
		
		if (imports != null) {

			Iterator<ImportDeclaration> it = imports.iterator();

			while (it.hasNext()) {
				ImportDeclaration id = it.next();
				if("org.apache.log4j.Logger".equalsIgnoreCase(id.getName().toString())){
					hasLogger = true;
					break;
				}
			}
			
			if(!hasLogger){
				imports.add(new ImportDeclaration(new NameExpr("org.apache.log4j.Logger"), false, false));
				cu.setImports(imports);
				
				addLoggerStatement(cu);
				
				return cu.toString();
			}
			
			
				
		}
		
		return null;
		
		
	}
	
	
	private void addLoggerStatement(CompilationUnit cu){
		

		TypeDeclaration td = cu.getTypes().get(0);
		
		String className = td.getName();
		
		String loggerSatement = "private static Logger logger = Logger.getLogger("+className+".class)";
		
		List<Node> childNodes = td.getChildrenNodes();
		
		FieldDeclaration fd = new FieldDeclaration(
		                            ModifierSet.PRIVATE, 
		                            new ClassOrInterfaceType("Logger"), 
		                            new VariableDeclarator(
		                                new VariableDeclaratorId("logger =  Logger.getLogger("+className+".class)"
		                                		)));
		
		td.getMembers().add(0, fd);
		
		
		
		
		
		
		
	}

}
