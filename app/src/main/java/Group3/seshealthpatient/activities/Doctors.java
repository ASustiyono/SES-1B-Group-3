package group3.seshealthpatient.activities;

public class Doctors {

    public String firstName;
    public String lastName;
    public String gender;
    public String age;
    public String occupation;
    public String clinicName;
    public String clinicNumber;
    public String clinicEmail;

    public Doctors() {

    }

    public Doctors(String firstName,
                   String lastName,
                   String gender,
                   String age,
                   String occupation,
                   String clinicName,
                   String clinicNumber,
                   String clinicEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.clinicName = clinicName;
        this.clinicNumber = clinicNumber;
        this.clinicEmail = clinicEmail;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public String getClinicEmail() {
        return clinicEmail;
    }

    public void setClinicEmail(String clinicEmail) {
        this.clinicEmail = clinicEmail;
    }
}
