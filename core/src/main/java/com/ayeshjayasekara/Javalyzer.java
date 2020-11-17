package com.ayeshjayasekara;

import com.ayeshjayasekara.processor.FileProcessor;

import java.io.FileNotFoundException;

public class Javalyzer{

    public static void main(String[] args) throws FileNotFoundException {
        FileProcessor processor = new FileProcessor(FileProcessor.FILE_PATH);
        processor.process();
    }
}
