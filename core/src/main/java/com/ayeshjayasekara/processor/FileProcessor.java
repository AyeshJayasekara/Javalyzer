package com.ayeshjayasekara.processor;

import com.ayeshjayasekara.model.RecordModel;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class FileProcessor {

    public static final String FILE_PATH = "/home/ayesh/Desktop/statistics.log";
    private final BufferedReader reader;

    public FileProcessor(String absoluteFilePath) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(absoluteFilePath));
    }

    private String readTime(String[] upTimeComponents){
        return upTimeComponents[0]
                .concat(":")
                .concat(upTimeComponents[1])
                .concat(":")
                .concat(upTimeComponents[2].substring(0,2))
                .replace(" ", "");
    }

    private String cpuLoadLast1Minute(String[] cpuComponentArray){
        return cpuComponentArray[0].replace(" ", "");
    }

    private String cpuLoadLast5Minute(String[] cpuComponentArray){
        return cpuComponentArray[1].replace(" ", "");
    }

    private String cpuLoadLast15Minute(String[] cpuComponentArray){
        return cpuComponentArray[2].replace(" ", "");
    }

    private HashMap<String, String> decodeUpTime(String line){
        String[] upTimeComponents = line.split(":");
        HashMap<String, String> componentsMap = new HashMap<String, String>();
        componentsMap.put("time", readTime(upTimeComponents));

        String[] cpuComponents = upTimeComponents[upTimeComponents.length - 1].split(",");

        componentsMap.put("1_minute", cpuLoadLast1Minute(cpuComponents));
        componentsMap.put("5_minute", cpuLoadLast5Minute(cpuComponents));
        componentsMap.put("15_minute", cpuLoadLast15Minute(cpuComponents));
        return componentsMap;
    }

    private String readLine() throws IOException {
        return reader.readLine();
    }

    public void closeReader() throws IOException {
        reader.close();
    }

    private HashMap<String, String> decodeMainMemory(String line){
        String memoryString = line.replaceAll(" +", ",");
        String[] memoryComponents = memoryString.split(",");
        HashMap<String, String> memoryComponentMap = new HashMap<String, String>();
        memoryComponentMap.put("available_memory", availableMemory(memoryComponents));
        memoryComponentMap.put("total_memory", totalMemory(memoryComponents));
        memoryComponentMap.put("used_memory", usedMemory(memoryComponents));
        memoryComponentMap.put("free_memory", freeMemory(memoryComponents));
        return memoryComponentMap;
    }

    private HashMap<String, String> decodeSwapMemory(String line){
        String memoryString = line.replaceAll(" +", ",");
        String[] memoryComponents = memoryString.split(",");
        HashMap<String, String> memoryComponentMap = new HashMap<String, String>();
        memoryComponentMap.put("total_swap_memory", totalSwapMemory(memoryComponents));
        memoryComponentMap.put("used_swap_memory", usedSwapMemory(memoryComponents));
        memoryComponentMap.put("free_swap_memory", freeSwapMemory(memoryComponents));
        return memoryComponentMap;
    }

    private String freeSwapMemory(String[] memoryComponents) {
        return memoryComponents[3];
    }

    private String usedSwapMemory(String[] memoryComponents) {
        return memoryComponents[2];
    }

    private String totalSwapMemory(String[] memoryComponents) {
        return memoryComponents[1];
    }


    private String freeMemory(String[] memoryComponents) {
        return memoryComponents[3];
    }

    private String usedMemory(String[] memoryComponents) {
        return memoryComponents[2];
    }

    private String totalMemory(String[] memoryComponents) {
        return memoryComponents[1];
    }

    private String availableMemory(String[] memoryComponents){
        return memoryComponents[memoryComponents.length -1 ];
    }

    public void process(){

        try {

            List<RecordModel> recordModelList = new ArrayList<>();

            while (reader.ready()){
                HashMap<String, String> timeMap = decodeUpTime(readLine());
                readLine();
                readLine();
                HashMap<String, String> memoryMap = decodeMainMemory(readLine());
                HashMap<String, String> swapMap = decodeSwapMemory(readLine());
                readLine();
                recordModelList.add(new RecordModel(memoryMap, swapMap, timeMap));
            }

            closeReader();


            Writer writer = new FileWriter("/home/ayesh/Desktop/yourfile.csv");
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(recordModelList);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }

    }


}
