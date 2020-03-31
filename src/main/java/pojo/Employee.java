package pojo;

import java.util.HashMap;
import java.util.Map;

public class Employee extends Person {
    private String customerId;
    private String department;
    private String email;
    private String dateOfBirth;
    private HashMap<String,String> map =new HashMap<>();

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Map<String,String> employeeData() {
        map.put("employeeId",getId());
        map.put("customerId",getCustomerId());
        map.put("department",getDepartment());
        map.put("email",getEmail());
        map.put("dateOfBirth",getDateOfBirth());
        return map;
    }
}
