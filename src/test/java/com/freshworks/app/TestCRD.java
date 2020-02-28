package com.freshworks.app;

import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;

import com.freshworks.models.Master;
import com.freshworks.service.CRDService;
import com.freshworks.util.CommonUtil;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TestCRD {

    private static Map<String, String> keyValueStorage;
    private static List<Master> masterCsvData = CommonUtil.loadMasterFile();

    @Test
    public void testFileSize() {
        String str = "{\"map\":[{color: \"red\",value: \"#f00\"},{color: \"green\",value: \"#0f0\"},{color: \"blue\"," +
                "value: \"#00f\"},{color: \"cyan\",value: \"#0ff\"},{color: \"magenta\",value: \"#f0f\"},{color: " +
                "\"yellow\",value: \"#ff0\"},{color: \"black\",value: \"#000\"},{color: \"red\",value: " +
                "\"#f00\"},{color: \"green\",value: \"#0f0\"},{color: \"blue\",value: \"#00f\"}," +
                "{color: \"cyan\",value: \"#0ff\"},{color: \"magenta\",value: \"#f0f\"}," +
                "{color: \"yellow\",value: \"#ff0\"},{color: \"black\",value: \"#000\"}," +
                "{color: \"red\",value: \"#f00\"},{color: \"green\",value: \"#0f0\"}," +
                "{color: \"blue\",value: \"#00f\"},{color: \"cyan\",value: \"#0ff\"}" +
                ",{color: \"magenta\",value: \"#f0f\"},{color: \"yellow\",value: \"#ff0\"},{color: \"black\",value: \"#000\"}," +
                "{color: \"red\",value: \"#f00\"},{color: \"green\",value: \"#0f0\"},{color: \"blue\",value: \"#00f\"}," +
                "{color: \"cyan\",value: \"#0ff\"},{color: \"magenta\",value: \"#f0f\"},{color: \"yellow\",value: \"#ff0\"}," +
                "{color: \"black\",value: \"#000\"},{color: \"red\",value: \"#f00\"},{color: \"green\",value: \"#0f0\"}," +
                "{color: \"blue\",value: \"#00f\"},{color: \"cyan\",value: \"#0ff\"},{color: \"magenta\",value: \"#f0f\"}," +
                "{color: \"yellow\",value: \"#ff0\"},{color: \"black\",value: \"#000\"},{color: \"red\",value: \"#f00\"}," +
                "{color: \"green\",value: \"#0f0\"},{color: \"blue\",value: \"#00f\"},{color: \"cyan\",value: \"#0ff\"},{" +
                "color: \"magenta\",value: \"#f0f\"},{color: \"yellow\",value: \"#ff0\"},{color: \"black\",value: \"#000\"}," +
                "{color: \"red\",value: \"#f00\"},{color: \"green\",value: \"#0f0\"},{color: \"blue\",value: \"#00f\"}," +
                "{color: \"cyan\",value: \"#0ff\"},{color: \"magenta\",value: \"#f0f\"},{color: \"yellow\",value: \"#ff0\"}," +
                "{color: \"black\",value: \"#000\"},{color: \"red\",value: \"#f00\"},{color: \"green\",value: \"#0f0\"},{color: \"blue\",value: \"#00f\"},{color: \"cyan\",value: \"#0ff\"}," +
                "{color: \"magenta\",value: \"#f0f\"},{color: \"yellow\",value: \"#ff0\"},{color: \"black\",value: \"#000\"}]}";
        assertTrue(CommonUtil.checkDataSize(str));
    }

    @Test
    public void testWriteAndReadData() {
        CRDService crdService = new CRDService();
        crdService.setTTL(5000);
        String key = "testKey";
        String data = "{\"testData\" : \"testValue\"}";
        String filePath = "testTestData.txt";
        CRDService.writeDataToFile(key, data, filePath, keyValueStorage, masterCsvData);
        Map<String, String> keyData = CRDService.readCsv(key, masterCsvData);
        assertEquals(data, keyData.get(key));
    }

    @Ignore
    @Test
    public void testTTL() throws InterruptedException {
        String key = "testKey";
        Thread.sleep(10000);
        Map<String, String> keyData = CRDService.readCsv(key, masterCsvData);
        assertFalse(CommonUtil.checkTTL(key, masterCsvData).get());
    }

    @Ignore
    @Test
    public void testDeleteData() throws InterruptedException {
//        Thread.sleep(12000);
        String key = "testKey";
        Gson gson = new Gson();
        String convertedData = gson.toJson(keyValueStorage);
        CRDService.deleteData(key, keyValueStorage, masterCsvData);
        if(keyValueStorage.size() > 0) {
            assertFalse(keyValueStorage.containsKey(key));
        } else {
            assertTrue(true);
        }
    }

}
