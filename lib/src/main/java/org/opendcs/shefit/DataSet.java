package org.opendcs.shefit;

import java.util.ArrayList;

public class DataSet {
    private ArrayList<ShefRecord> shefData = new ArrayList<>();


    public void addValue(ShefRecord value) {        
        shefData.add(value);
    }
    
    public ArrayList<ShefRecord> getData() {
        return shefData;
    }
}
