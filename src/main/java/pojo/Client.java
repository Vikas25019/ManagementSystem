package pojo;

import java.util.HashMap;
import java.util.Map;

public class Client extends Person {
    private String address;
    private Map<String,String> map = new HashMap<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String,String> clientData() {
        map.put("clientId",getId());
        map.put("name",getName());
        map.put("address",getAddress());
        return map;
    }

}
