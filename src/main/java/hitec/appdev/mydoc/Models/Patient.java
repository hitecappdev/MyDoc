package hitec.appdev.mydoc.Models;

public class Patient {


    private String name;
    private String email;
    private String phone;
    private String password;
    private String city;
    private String gender;
    private String height;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isDiabetic() {
        return diabetic;
    }

    public void setDiabetic(boolean diabetic) {
        this.diabetic = diabetic;
    }

    public Patient(String name, String email, String phone, String gender, String height, String weight, boolean diabetic) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.diabetic = diabetic;
    }

    private String weight;
    private String age;
    private boolean diabetic = false ;

    public Patient(){

    }

    public Patient(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
