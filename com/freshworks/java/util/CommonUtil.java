package com.freshworks.java.util;

import com.freshworks.java.models.Master;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CommonUtil {

    public static List<Master> loadMasterFile() {
        BufferedReader reader = null;
        List<Master> masterData = new ArrayList<>();
        String line;
        if (new File("DataStore.csv").exists()) {
            try {
                reader = new BufferedReader(new FileReader("DataStore.csv"));
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");
                    if (fields.length <= 2) {
                        continue;
                    }
                    Master master = new Master();
                    master.setKey(fields[0]);
                    master.setFileLocation(fields[1]);
                    master.setTimestamp(Long.parseLong(fields[2]));
                    masterData.add(master);
                }

            } catch (Exception ex) {
                System.out.println("Error!! reading Models.Master file");
            } finally {
                try {
                    assert reader != null;
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return masterData;
    }

    public static boolean checkDataSize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(baos.size());
        return baos.size() <= 16384;
    }

    public static AtomicBoolean checkTTL(String key, List<Master> masterCsvData) {
        AtomicBoolean dataNotExpired = new AtomicBoolean(true);
        masterCsvData.stream().filter(master -> master.getKey().equalsIgnoreCase(key)).collect(Collectors.toList()).forEach(ttl -> {
            if(ttl.getTimestamp() <= System.currentTimeMillis())
                dataNotExpired.set(false);
        });
        return dataNotExpired;
    }
}
