package org.testing.framework.utils.file;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.zip.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.utils.date.DateUtility;

public class FileHandler {

    static Logger LOGGER = LoggerFactory.getLogger(FileHandler.class.getName());

    /**
     * A FileNameFilter implementation, it will filter a file list with the passed extension
     */
    public static class MyFileNameFilter implements FilenameFilter {

        private String ext;

        public MyFileNameFilter(String ext) {
            this.ext = ext.toLowerCase();
        }

        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }

    /**
     * A private constructor so that the use of this class is static only
     */
    private FileHandler() {}

    /**
     * This will open a file using the file path.
     *
     * @param fileName The file name to be opened
     * @return The contents of the file as a String
     * @throws IOException
     */
    public static String readTextFile(String fileName) throws IOException {

        // Read the text using a file reader
        return readTextFromReader(new FileReader(fileName));
    }

    /**
     * This will open a file using the class get resource. So files need to be in the class path.
     *
     * @param fileName The file name to be opened
     * @return The contents of the file as a String
     * @throws IOException
     */
    public static String readTextFileFromResource(String fileName) throws IOException {

        // Get a reader from a file in resource
        return readTextFromReader(new InputStreamReader(FileHandler.class.getResourceAsStream(fileName)));
    }

    /**
     * This will read text to a string from a reader.
     *
     * @param reader The reader to use to get the date from
     * @return The contents of the file as a String
     * @throws IOException
     */
    public static String readTextFromReader(Reader reader) throws IOException {

        // Get a reader from a file in resource
        BufferedReader br = new BufferedReader(reader);

        // Try and read the file into a builder and then return it as a string
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            return sb.toString();

        } finally {
            try {
                br.close();
            } catch (IOException e) {
                // Ignore issues during closing
            }
        }
    }

    /**
     * This will get a file using the class get resource. So files need to be in the class path.
     *
     * @param fileName The file name to be opened
     * @return The file as a File
     * @throws Exception
     */
    public static File getFileFromResource(String fileName) throws Exception {

        // Get the uri of the file in the resource folder then create a file from it
        return new File(FileHandler.class.getResource(fileName).toURI());
    }

    /**
     * This will write a text file from a given string
     *
     * @param fileName The file name as a String
     * @param text The text to be written to file
     */
    public static void writeTextFile(String fileName, String text) {

        FileWriter output = null;
        BufferedWriter writer = null;

        try {
            output = new FileWriter(fileName);
            writer = new BufferedWriter(output);
            writer.write(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (output != null) {
                try {
                    writer.close();
                    output.close();
                } catch (IOException e) {
                    // Ignore issues during closing
                }
            }
        }
    }

    /**
     * This will copy the src file to the dest file
     * @param srcFile The source file as a string location
     * @param destFile The destination file as a string location
     * @throws IOException
     */
    public static void copyFile(String srcFile, String destFile) throws IOException {
        FileUtils.copyFile(new File(srcFile), new File(destFile));
    }

    /**
     * This will copy the src file to the dest file
     * @param srcFile The source file as a File
     * @param destFile The destination file as a File
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        FileUtils.copyFile(srcFile, destFile);
    }

    /**
     * This will append a given string to a given file location
     *
     * @param text			Text as a string that is to be appended to a file.
     * @param fileLocation	File location as a string that is to be written too.
     */
    public static void appendTextToFile(String text, String fileLocation) {
        // Try the following io code
        try {
            // Create a file link from the string file location
            File file = new File(fileLocation);

            // If the file doesn't exist, then create it
            if(!file.exists()) {
                file.createNewFile();
            }

            // Create the writers with the file name to use and setting the file writer to true so that it appends not overwrites
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

            // Write the text to the file
            bufferWriter.write(text);
            bufferWriter.close();
        }
        // Catch the errors here
        catch(IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * This will compare the contents and metadata of two files and return a boolean on if they are the same
     *
     * @param fileLocation1 First file location to check against as a String
     * @param fileLocation2 Second file location to check against as a String
     * @return A boolean value to say whether the files are the same or not
     * @throws IOException
     */
    public static boolean compareFiles(String fileLocation1, String fileLocation2) throws IOException {
        return FileUtils.contentEquals(new File(fileLocation1), new File(fileLocation2));
    }

    /**
     * This will compare the contents and metadata of two files and return a boolean on if they are the same
     *
     * @param file1 First file to check against as a file
     * @param file2 Second file to check against as a file
     * @return A boolean value to say whether the files are the same or not
     * @throws IOException
     */
    public static boolean compareFiles(File file1, File file2) throws IOException {
        return FileUtils.contentEquals(file1, file2);
    }

    /**
     * Create a directory if it does not exist using a string name
     *
     * @param location	A file location as a String
     */
    public static void createDirIfNotExists(String location) {
        // Create a file from the name and call the file version
        createDirIfNotExists(new File(location));
    }

    /**
     * Create a directory if it does not exist using a file
     *
     * @param file	A file location as a File.
     */
    public static void createDirIfNotExists(File file) {
        // If it doesn't exist
        if(!file.exists()) {
            // Create it
            file.mkdirs();
        }
    }

    /**
     * This will create a temp directory
     *
     * @return Passes back the directory in a File
     * @throws Exception
     */
    public static File createTempDirectory() throws Exception {

        final File temp;

        temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

        if(!(temp.delete())) {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        if(!(temp.mkdir())) {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return (temp);
    }

    /**
     * This will delete all files from a given string location
     *
     * @param fileOrDirectoryLocation	File location as a string that is to be deleted.
     */
    public static void deleteAllFilesRecursively(String fileOrDirectoryLocation) {
        // Call delete with the file
        deleteAllFilesRecursively(new File(fileOrDirectoryLocation));
    }

    /**
     * This function will delete all files in the directory including other folders
     *
     * @param fileOrDirectory	File location as a File that is to be deleted.
     */
    public static void deleteAllFilesRecursively(File fileOrDirectory) {
        // If the File is a directory
        if (fileOrDirectory.isDirectory()) {
            // For each child call itself
            for (File child : fileOrDirectory.listFiles()) {
                deleteAllFilesRecursively(child);
            }
        }

        // Delete the file or directory
        fileOrDirectory.delete();
    }

    /**
     * This will return a file list based on the given file location and extension
     *
     * @param filelocation A file location as a String
     * @param extension	The file extension to be used for the list
     * @return An array of files from the passed location
     */
    public static File[] getFileList(String filelocation, String extension) {
        // Create a list for the file names
        File[] files;

        // If the file path is a directory - i.e. more than one file
        if(new File(filelocation).isDirectory()) {
            // Get a list of all the files
            files = new File(filelocation).listFiles(new MyFileNameFilter("." + extension));
        }
        // Else its just a single file
        else {
            // Set the array to 1 and the file to this
            files = new File[1];
            files[0] = new File(filelocation);
        }

        // Return the files in an array
        return files;
    }

    /**
     * Unzip a file to a directory
     * @param zipFile The file to unzip
     * @param outputFolder The folder to place the zipped file(s) in
     */
    public static void unZipFiles(String zipFile, String outputFolder) {

        byte[] buffer = new byte[1024];

        try{

            // Create output directory is not exists
            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.mkdir();
            }

            // Get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            // Get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                // Create all non existent folders
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

        } catch(IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * This will take a directory and zip all file with to a zip file with the specified name
     * @param directory
     * @param location
     */
    public static void zipFiles(String directory, String location) {
        // Initiate the output writers needed to save the files to a zip file
        FileOutputStream fileOut = null;
        ZipOutputStream zos = null;

        // Try and write to the zip file
        try {
            // Create the zip file
            fileOut = new FileOutputStream(location + ".zip");
            zos = new ZipOutputStream(new BufferedOutputStream(fileOut));

            // Get the directory
            File dir = new File(directory);

            zipDirToZipStream(zos, dir, "");
        }
        // Catcht the errors
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        // Finally close
        finally {
            try {
                // Close the zip file
                zos.close();
            } catch (IOException e) {
                LOGGER.error("Error closing zip stream");
            }
        }
    }

    /**
     *  This will take a zipstream and zip it to a dir
     * @param zos
     * @param dir
     * @param zipDir
     * @throws Exception
     */
    public static void zipDirToZipStream(ZipOutputStream zos, File dir, String zipDir) throws Exception {

        FileInputStream in = null;
        // Create a byte buffer
        byte[] buf = new byte[1024];

        // Get the list of file in the directory
        File[] fileList = dir.listFiles();

        // If there are file
        if(fileList.length > 0) {
            // For each file
            for (File file : fileList) {
                if(!file.isDirectory()) {
                    in = new FileInputStream(file);

                    // Create a zip entry using the file name
                    ZipEntry entry = new ZipEntry(zipDir + file.getName());

                    // Add it to the zip file
                    zos.putNextEntry(entry);

                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        // Write the buffer to the zipfile
                        zos.write(buf, 0, len);
                    }

                    // Close the zip entry
                    zos.closeEntry();
                }
                // Its a directory
                else {
                    // Create a zip entry using the file name + the a sepearator
                    ZipEntry entry = new ZipEntry(file.getName() + File.separator);

                    // Add it to the zip file
                    zos.putNextEntry(entry);

                    // Write this directory to the zip file
                    zipDirToZipStream(zos, file, file.getName() + File.separator);
                }
            }
        }
    }

    /**
     * Un-gzip a file to a directory
     * @param gzipFile The file to unzip
     * @param outputFolder The folder to place the unzipped file(s) in
     */
    public static void unGZipFiles(String gzipFile, String outputFolder, String fileName) {

        byte[] buffer = new byte[1024];
        try{

            // Create output directory is not exists
            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.mkdir();
            }
            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(gzipFile));

            File newFile = new File(outputFolder + File.separator + fileName);

            // Create all non existent folders
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = gzis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            gzis.close();

        } catch(IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * This will take a file and gzip the file with to a gzip file with the specified name
     * @param file
     * @param gzipFile
     */
    public static void gzipFiles(String file, String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String generateTimestampedFileName(String fileName) {
        StringBuilder builder = new StringBuilder();
        int seperatorIndex = fileName.indexOf(".");
        int fileSeperator = fileName.lastIndexOf(File.separator);
        builder.append(fileName.substring(fileSeperator+1, seperatorIndex));
        builder.append(DateUtility.getTimeStamp());
        builder.append(fileName.substring(seperatorIndex));
        return builder.toString();
    }
}

