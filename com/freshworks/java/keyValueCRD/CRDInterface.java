package com.freshworks.java.keyValueCRD;

import com.freshworks.java.models.DataVo;

public interface CRDInterface {

    /*
     *To add data to a file based on key
     */
    public void createData(DataVo dataVo);

    /*
     *Fetching the record based on key
     */
    public void fetchData(String Key);

    /*
     *Deleting a record from file based on key
     */
    public void deleteData(String Key);
}
