package by.belstu.lab_45;

import java.util.ArrayList;

/**
 * Created by Владислав on 25.10.2016.
 */
public class Staff {
    private ArrayList<Person> studList;

    public Staff() {
        studList = new ArrayList<Person>();
    }

    public Staff(ArrayList<Person> studList) {
        this.studList = studList;
    }

    public boolean add(Person person){
        studList.add(person);
        return true;
    }
    public boolean remove(Person person){
        studList.remove(person);
        return true;
    }
    public ArrayList<Person> getStudList() {
        return studList;
    }

    public void setStudList(ArrayList<Person> studList) {
        this.studList = studList;
    }
}
