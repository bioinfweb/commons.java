package info.webinsel.util.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



/**
 * This class provides general tools for reading and writing data.
 * @author Ben St&ouml;ver
 */
public class IOUtils {
	/**
	 * Returns either the path to the class file of the specified class without the package path or 
	 * the path top the archive file that contains it (e.g. JAR, ZIP, EXE).
	 * @param c - the class that is contained in the returned JAR-file
	 * @return the path to the class folder or archive
	 */
	public static String getClassLocation(Class c) {
		return c.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20", " ");
	}
	
	
	public static String getClassDir(Class c) {
		File file = new File(getClassLocation(c));
		if (file.isDirectory()) {
			return file.getAbsolutePath();
		}
		else {
			return file.getParent();
		}
	}
	
	
	/**
	 * Copies data from an <code>InputStream</code> to a <code>OutputStream</code>.
	 * @param in - the stream to read from
	 * @param out - the stream to write to
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException {
		copy(in, out, null);
	}
	
	
	public static void copy(InputStream in, OutputStream out, boolean close) throws IOException {
		copy(in, out, close, null);
	}
	
	
	public static void copy(InputStream in, OutputStream out, IOProgressListener progressListener) 
      throws IOException {
		
		copy(in, out, true, progressListener);
	}
	

	/**
	 * Copies data from an <code>InputStream</code> to a <code>OutputStream</code>.
	 * @param in - the stream to read from
	 * @param out - the stream to write to
	 * @param close - defines whether the streams shall be closed after the input stream ended.
	 * @param progressListener - the progress listener which is informed about the ongoing progress
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out, boolean close, IOProgressListener progressListener) 
	    throws IOException {
		
    try
    {
      long bytes = 0;
      if (progressListener != null) {
      	progressListener.ioStarts();
      }
      
      byte[] buffer = new byte[0xFFFF];
      for (int len; (len = in.read(buffer)) != -1; ) {
      	out.write(buffer, 0, len);
      	
        if (progressListener != null) {
        	bytes += len;
        	progressListener.ioProgress(bytes);
        }
      }
    }
    finally {
    	if (close) {
	      if (in != null) {
	      	in.close(); 
	      }
	      if (out != null) {
	      	out.close(); 
	      }
    	}

      if (progressListener != null) {
      	progressListener.ioFinished();
      }
    }
  }
	
	
	/**
	 * Copies a file.
	 * @param src - the source file
	 * @param dest - the destination file
	 * @param progressListener - the progress listener which is informed about the ongoing progress
	 * @throws IOException
	 */
	public static void copy(File src, File dest, IOProgressListener progressListener) 
	    throws IOException {
		
		copy(new FileInputStream(src), new FileOutputStream(dest), progressListener);
		//TODO Time etc. auch von src nach dest übertragen
	}
	
	
	/**
	 * Copies a file.
	 * @param src - the source file
	 * @param dest - the destination file
	 * @throws IOException
	 */
	public static void copy(File src, File dest) 
	    throws IOException {
		
		copy(src, dest, null);
	}
	
	
	/**
	 * Copies data from an <code>InputStream</code> to a <code>OutputStream</code>.
	 * @param path - the path to the resource
	 * @param out - the stream to write to
	 * @throws IOException
	 */
	public static void copyFromResource(String path, OutputStream out) throws IOException {
		copyFromResource(path, out, null);
  }
	
	
	/**
	 * Copies data from an <code>InputStream</code> to a <code>OutputStream</code>.
	 * @param path - the path to the resource
	 * @param out - the stream to write to
	 * @param progressListener - the progress listener which is informed about the ongoing progress
	 * @throws IOException
	 */
	public static void copyFromResource(String path, OutputStream out, IOProgressListener progressListener) 
	    throws IOException {
		
		copy(Object.class.getResource(path).openStream(), out, progressListener);
  }
	
	
	/**
	 * Extracts a file from an archive (e.g. ZIP or JAR).
	 * @param file - the source archive file
	 * @param path - the path of the file to extract inside the archive
	 * @param out - the output stream to write the contents of the file to
	 * @param progressListener - the progress listener which is informed about the ongoing progress
	 * @throws IOException
	 */
	public static void extractFile(ZipFile file, String path, OutputStream out, 
			IOProgressListener progressListener) throws IOException {
		
		copy(file.getInputStream(file.getEntry(path)), out, progressListener);
	}
	
	
	/**
	 * Extracts a file from an archive (e.g. ZIP or JAR).
	 * @param file - the source archive file
	 * @param path - the path of the file to extract inside the archive
	 * @param out - the output stream to write the contents of the file to
	 * @throws IOException
	 */
	public static void extractFile(ZipFile file, String path, OutputStream out) throws IOException {
		extractFile(file, path, out, null);
	}
	
	
	/**
	 * Extracts a file from an archive (e.g. ZIP or JAR).
	 * @param file - the source archive file
	 * @param path - the path of the file to extract inside the archive
	 * @param dest - the destination file
	 * @param progressListener - the progress listener which is informed about the ongoing progress
	 * @throws IOException
	 */
	public static void extractFile(ZipFile file, String path, File dest, 
			IOProgressListener progressListener) throws IOException {
		
		copy(file.getInputStream(file.getEntry(path)), new FileOutputStream(dest), progressListener);
		//TODO Time etc. auch von src nach dest übertragen
	}
	
	
	/**
	 * Extracts a file from an archive (e.g. ZIP or JAR).
	 * @param file - the source archive file
	 * @param path - the path of the file to extract inside the archive
	 * @param dest - the destination file
	 * @throws IOException
	 */
	public static void extractFile(ZipFile file, String path, File dest) throws IOException {
		copy(file.getInputStream(file.getEntry(path)), new FileOutputStream(dest), null);
	}
	
	
	/**
	 * Extracts the contents of a directory from an archive (e.g. ZIP or JAR).
	 * @param file - the source archive file
	 * @param sourcePath - the path of the source directory inside the archive (must not start with 
	 *        a "/")
	 * @param destPath - the path of the destination directory where the extracted files should be 
	 *        stored
	 * @param progressListener - the progress listener which is informed about the ongoing progress
	 * @throws IOException
	 */
	public static void extractDir(ZipFile file, String sourcePath, String destPath, 
			IOProgressListener progressListener) throws IOException {

		if (!destPath.endsWith(System.getProperty("file.separator"))) {
			destPath += System.getProperty("file.separator");
		}
		new File(destPath).mkdirs();
		
		Enumeration<? extends ZipEntry> enumeration = file.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry entry = enumeration.nextElement();
			if (entry.getName().startsWith(sourcePath) && !entry.isDirectory()) {
				File dest = new File(destPath + 
						entry.getName().substring(sourcePath.length(), entry.getName().length()));
				dest.createNewFile();
				
				if (progressListener != null) {
					progressListener.newFile(entry.getName(), dest.getAbsolutePath(), entry.getSize());
				}
				extractFile(file, entry.getName(), dest, progressListener);
			}
		}
	}
	
	
	/**
	 * Extracts the contents of a directory from an archive (e.g. ZIP or JAR).
	 * @param file - the source archive file
	 * @param sourcePath - the path of the source directory inside the archive (must not start with 
	 *        a "/")
	 * @param destPath - the path of the destination directory where the extracted files should be 
	 *        stored
	 * @throws IOException
	 */
	public static void extractDir(ZipFile file, String sourcePath, String destPath) 
	    throws IOException {
		
		extractDir(file, sourcePath, destPath, null);
	}
	
	
	/**
	 * Calculates the overall uncompressed size of all files inculded in the specified directory of
	 * an archive.
	 * @param file - the source archive file
	 * @param path - the path of the source directory inside the archive (must not start with 
	 *        a "/")
	 * @return the sum of all uncompressed file-sizes in bytes
	 */
	public static long dirSize(ZipFile file, String path) {
		long result = 0;
		Enumeration<? extends ZipEntry> enumeration = file.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry entry = enumeration.nextElement();
			if (entry.getName().startsWith(path) && !entry.isDirectory()) {
				result += entry.getSize();
			}
		}
		return result;
	}
}
