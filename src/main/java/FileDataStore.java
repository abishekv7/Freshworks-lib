import KeyValueCRD.CRDInterface;
import Models.Master;
import Util.CommonUtil;
import Service.*;
import Exception.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileDataStore implements CRDInterface {

    private static Map<String, String> keyValueStorage;
    private static List<Master> masterCsvData = CommonUtil.loadMasterFile();

    @Override
    public void createData() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Key for the data");
        String key = input.nextLine();
        if (key.length() > 32) {
            System.out.println("Key should not exceed 32 characters enter again");
            key = input.nextLine();
        }
        System.out.println("Enter the data");
        String data = input.nextLine();
        System.out.println("Enter the file location");
        String filePath = input.next();
        try {
            JSONObject jsonData = new JSONObject(data);
            if (CommonUtil.checkDataSize(data))
                CRDService.writeDataToFile(key, data, filePath, keyValueStorage, masterCsvData);
            else
                throw new FileSizeException("Json object size should be less than 16KB");
            input.close();
        } catch (JSONException e) {
            System.out.println("Entered data is not a valid Json");
        } catch (FileSizeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(String key) {
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

    @Override
    public void fetchData(String key) {
        keyValueStorage = CRDService.readCsv(key, masterCsvData);
        if(CommonUtil.checkTTL(key, masterCsvData).get()) {
            String value = keyValueStorage.get(key);
            if (null != value && value.length() != 0) {
                System.out.println(keyValueStorage.get(key));
            } else {
                System.out.println("No record matching the given key");
            }
        } else {
            System.out.println("Data expired!! create a new data");
            deleteData(key);
        }
    }


}
