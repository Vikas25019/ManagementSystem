package pojo;

import java.util.LinkedHashMap;

public class Client extends Person {
    private String address;
    private LinkedHashMap<String,String> map = new LinkedHashMap<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LinkedHashMap<String,String> clientData() {
        map.put("clientId",getId());
        map.put("name",getName());
        map.put("address",getAddress());
        return map;
    }

}
