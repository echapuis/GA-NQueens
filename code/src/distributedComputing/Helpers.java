package distributedComputing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helpers {
	
	 public static void deleteDirectory(final File directory) throws IOException {
         if (!directory.exists()) {
             return;
         }
         Path p = directory.toPath();
         if (!Files.isSymbolicLink(p)) {
             cleanDirectory(directory);
         }
 
         if (!directory.delete()) {
             final String message =
                 "Unable to delete directory " + directory + ".";
             throw new IOException(message);
         }
     }
	 
	
	 
	 public static void cleanDirectory(final File directory) throws IOException {
		         if (!directory.exists()) {
		             final String message = directory + " does not exist";
		             throw new IllegalArgumentException(message);
		         }
		 
		         if (!directory.isDirectory()) {
		             final String message = directory + " is not a directory";
		             throw new IllegalArgumentException(message);
		         }
		 
		         final File[] files = directory.listFiles();
		         if (files == null) {  // null if security restricted
		             throw new IOException("Failed to list contents of " + directory);
		         }
		 
		         IOException exception = null;
		         for (final File file : files) {
		             try {
		                 forceDelete(file);
		             } catch (final IOException ioe) {
		                 exception = ioe;
		             }
		         }

		         if (null != exception) {
		             throw exception;
		         }
		     }
	 
	 public static void forceDelete(final File file) throws IOException {
		         if (file.isDirectory()) {
		             deleteDirectory(file);
		         } else {
		             final boolean filePresent = file.exists();
		             if (!file.delete()) {
		                 if (!filePresent){
		                     throw new FileNotFoundException("File does not exist: " + file);
		                 }
		                 final String message =
		                     "Unable to delete file: " + file;
		                 throw new IOException(message);
		             }
		         }
		     }
	 
	public static void createFolder( String folderPath){
		
		File dir = new File(folderPath);
		dir.mkdir();
		
	}
	
	public static boolean createFile(String filePath, Boolean replace)
    {	
		if (!replace){
    	try {
    		 
	      File file = new File(filePath);
	      
	      if (!file.createNewFile()){
	        //System.out.println("File: " + filePath + " exists already.");
	        return false;
	      }
	      
    	} catch (IOException e) {
    	  System.out.println("Error creating file: " + filePath);
	      e.printStackTrace();
	}
		}
		else {
			Path p = Paths.get(filePath);
			try {
				Files.deleteIfExists(p);
				File file = new File(filePath);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
		}
    	return true;
    }
	
	public static boolean fileOfType (String fileName, String type){
		int l = fileName.length();
		String t = type;
		if (l < t.length()) return false;
		for (int i=1; i < t.length()+1; i++){
			if (!(fileName.charAt(l-i) == t.charAt(t.length()-i))) return false;
		}
		return true;
	}
	
	public static boolean lineContains(String line, String word, boolean startsWith){
		if (word.length() > line.length()) return false;
		if (startsWith){
			if (line.substring(0,word.length()).equals(word)) return true;
		}
		else {
			for (int l = 0; l < line.length() - word.length()+1; l++){
				if (line.substring(l,l+word.length()).equals(word)) {
					if (l != 0) {
						if (!Character.isWhitespace(line.charAt(l-1))) return false;
					}
					return true;
				}
			}
		}
		return false;
	}
}
