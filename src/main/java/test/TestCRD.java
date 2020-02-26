package test;

import Models.Master;
import Service.CRDService;
import Util.CommonUtil;
import org.junit.Test;

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
        CRDService.TTL = 5000;
        String key = "testKey";
        String data = "{\"testData\" : \"testValue\"}";
        String filePath = "testTestData.txt";
        CRDService.writeDataToFile(key, data, filePath, keyValueStorage, masterCsvData);
        Map<String, String> keyData = CRDService.readCsv(key, masterCsvData);
        assertEquals(data, keyData.get(key));
    }

    @Test
    public void testTTL() throws InterruptedException {
        String key = "testKey";
        Thread.sleep(5000);
        Map<String, String> keyData = CRDService.readCsv(key, masterCsvData);
        assertFalse(CommonUtil.checkTTL(key, masterCsvData).get());
    }

    @Test
    public void testDeleteData() throws InterruptedException {
        Thread.sleep(7000);
        String key = "testKey";
        int temp = masterCsvData.size();
        CRDService.deleteData(key, keyValueStorage, masterCsvData);
        assertNotSame(temp, masterCsvData.size());
    }

}
