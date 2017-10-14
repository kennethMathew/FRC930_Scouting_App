import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Setup {
	
	static File aXML;
	static File aJava;
	
	static File tXML;
	static File tJava;
	
	static String aXMLDir = "C:/autontest/";
	static String aJavaDir = "C:/autontest/";
	
	static String tXMLDir = "C:/teleoptest/";
	static String tJavaDir = "C:/teleoptest/";
	
	static FileWriter aXMLWriter;
	static FileWriter aJavaWriter;

	static FileWriter tXMLWriter;
	static FileWriter tJavaWriter;
	
	public static void autonInit(){
		
		aXML = new File(aXMLDir + "Auton.xml");
		aJava = new File(aJavaDir + "Auton.java");
		
		try {
			aXMLWriter = new FileWriter(aXML);
			aJavaWriter = new FileWriter(aJava); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(aXML.exists()){
			try {
				RandomAccessFile clearAXML = new RandomAccessFile(aXML,"rw");
				try {
					clearAXML.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				aXML.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
	
	public static void teleopInit(){
		
		tXML = new File(tXMLDir + "Teleop.xml");
		tJava = new File(tJavaDir + "Teleop.java");
		
		
		try {
			tXMLWriter = new FileWriter(tXML);
			tJavaWriter = new FileWriter(tJava);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void closeWriters(){
		try {
			aXMLWriter.close();
			aJavaWriter.close();
			tXMLWriter.close();
			tJavaWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
