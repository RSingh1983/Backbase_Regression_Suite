package org.testing.framework.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileValidator {

    static Logger logger = LoggerFactory.getLogger(FileValidator.class.getName());

    /**
     * A private constructor so that the use of this class is static only
     */
    private FileValidator(){}

    /**
     * This will assert whether the passed two files are the same
     *
     * @param file1 First file to check against as a file
     * @param file2 Second file to check against as a file
     * @throws Exception
     */
    public static void isTheFileContentsTheSame(File file1, File file2) throws Exception {

        // Assert whether its been found
        assertTrue(FileHandler.compareFiles(file1, file2));
    }
}
