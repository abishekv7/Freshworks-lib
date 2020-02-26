package KeyValueCRD;

import Models.DataVo;

import java.util.Scanner;

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
