package by.belstu.lab_45;

import java.util.Comparator;

/**
 * Created by Владислав on 25.10.2016.
 */
public class Person {
    private String name;
    private String surname;
    private String date;
    private int rating;
    private String organisation;
    private int orgSalary;
    private int duration;

    public Person(String name, String surname, String date)
    {
        setName (name);
        setSurname (surname);
        setDate (date);
    }

    public Person(String name, String surname, String date, String organisation, int orgSalary)
    {
        setName (name);
        setSurname (surname);
        setDate (date);
        setOrganisation (organisation);
        setOrgSalary (orgSalary);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public int getOrgSalary() {
        return orgSalary;
    }

    public void setOrgSalary(int orgSalary) {
        this.orgSalary = orgSalary;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
