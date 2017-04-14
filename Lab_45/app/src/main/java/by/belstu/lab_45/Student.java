package by.belstu.lab_45;

/**
 * Created by Владислав on 25.10.2016.
 */
public class Student extends Person implements IAction{
    public Student(String name, String surname, String date) {
        super (name, surname, date);
    }
    @Override
    public void Show()
    {
        System.out.printf("%2s %12s %10s", getName (), getSurname (), getDate ());
        System.out.println();
    }
}
