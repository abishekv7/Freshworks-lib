package com.freshworks.app;

import com.freshworks.exception.FileSizeException;
import com.freshworks.models.Master;
import com.freshworks.service.CRDService;
import com.freshworks.util.CommonUtil;
import org.json.JSONException;
import org.json.JSONObject;

import com.freshworks.keyValueCRD.CRDInterface;
import com.freshworks.models.DataVo;

import java.util.List;
import java.util.Map;

public class FileDataStore implements CRDInterface {

    private static Map<String, String> keyValueStorage;
    private static List<Master> masterCsvData = CommonUtil.loadMasterFile();

    @Override
    public void createData(DataVo dataVo) {
        try {
            JSONObject jsonData = new JSONObject(dataVo.getData());
            if (CommonUtil.checkDataSize(dataVo.getData()))
                CRDService.writeDataToFile(dataVo.getKey(), dataVo.getData(), dataVo.getFilePath(), keyValueStorage, masterCsvData);
            else
                throw new FileSizeException("Json object size should be less than 16KB");
        } catch (JSONException e) {
            System.out.println("Entered data is not a valid Json");
        } catch (FileSizeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(String key) {
        CRDService.deleteData(key, keyValueStorage, masterCsvData);
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
