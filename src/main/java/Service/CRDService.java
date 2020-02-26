package Service;

import Models.Master;
import Util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CRDService {

    private static Gson gson = new Gson();
    public static long TTL = 1014000L;

    public static void writeDataToFile(String key, String jsonData, String filePath, Map<String, String> keyValueStorage, List<Master> masterCsvData) {
        keyValueStorage = readCsv(key, masterCsvData);
        keyValueStorage.put(key, jsonData);
        String convertedData = gson.toJson(keyValueStorage);
        try {
            FileUtils.writeStringToFile(new File(filePath), convertedData);
            long timeStamp = System.currentTimeMillis() + TTL;
            writeCsv(key, filePath, timeStamp, true, masterCsvData);
        } catch (IOException e) {
            System.out.println("Error!! while writing into file");
        }
    }

    public static Map<String, String> readCsv(String key, List<Master> masterCsvData) {
        Map<String, String> keyValueData = new HashMap<>();
        if (masterCsvData.size() == 0) {
            masterCsvData = CommonUtil.loadMasterFile();
        }
        masterCsvData.stream().filter(master -> master.getKey().equalsIgnoreCase(key)).collect(Collectors.toList()).forEach(master -> {
            try {
                String data = new BufferedReader(new FileReader(master.getFileLocation())).readLine();
                ObjectMapper oMapper = new ObjectMapper();
                Map temp = oMapper.readValue(data, Map.class);
                if (temp.size() > 0)
                    keyValueData.putAll(temp);

            } catch (FileNotFoundException e) {
                System.out.println("Specified file is missing");
            } catch (IOException e) {
                System.out.println("Error reading file contents");
            }
        });
        return keyValueData;
    }

    public static void writeCsv(String key, String filePath, long timeStamp, boolean append, List<Master> masterCsvData) {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("DataStore.csv", append);

            fileWriter.append("\n");
            fileWriter.append(key);
            fileWriter.append(",");
            fileWriter.append(filePath);
            fileWriter.append(",");
            fileWriter.append(String.valueOf(timeStamp));

            Master master = new Master();
            master.setFileLocation(filePath);
            master.setKey(key);
            master.setTimestamp(timeStamp);
            masterCsvData.add(master);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                assert fileWriter != null;
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteData(String key, Map<String, String> keyValueStorage, List<Master> masterCsvData) {
        keyValueStorage = CRDService.readCsv(key, masterCsvData);
        keyValueStorage.remove(key);
        int temp = masterCsvData.size();
        List<Master> masterData = masterCsvData.stream().filter(master -> !master.getKey().equalsIgnoreCase(key)).collect(Collectors.toList());
        if (masterData.size() == 0) {
            new File("DataStore.csv").delete();
        } else
            masterData.forEach(master -> {
                CRDService.writeCsv(master.getKey(), master.getFileLocation(), master.getTimestamp(), false, masterCsvData);
            });
        masterCsvData.stream().filter(master -> master.getKey().equalsIgnoreCase(key)).collect(Collectors.toList()).forEach(master -> {
            new File(master.getFileLocation()).delete();
            masterCsvData.remove(master);
        });
        if(temp == masterCsvData.size()) {
            System.out.println("Deletion Unsuccessful!");
        } else
            System.out.println("Deleted Successfully");
    }
}
