package org.opendcs.shefit;

import java.util.ArrayList;
import java.util.HashMap;

public class DataSet {
    private HashMap<String,ArrayList<ShefRecord>> shefData = new HashMap<>();


    public void addValue(String station, ShefRecord value) {
        if (!shefData.containsKey(station)) {
            shefData.put(station,new ArrayList<>());
        }
        shefData.get(station).add(value);
    }
    
    public HashMap<String,ArrayList<ShefRecord>> getData() {
        return shefData;
    }
}
