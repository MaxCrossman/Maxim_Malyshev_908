import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Student s1 = new Student("Иванов Иван", 2, 20);
        Student s2 = new Student("Петров Петр", 1, 22);
        Student s3 = new Student("Исламов Ислам", 4, 18);
        Student s4 = new Student("Ярославова Ярослава", 3, 26);
        Student s5 = new Student("Евгеньева Евгения", 2, 20);

        Queue<Student> npq = new PriorityQueue<>(1,NameComparator);
        npq.add(s1);
        npq.add(s2);
        npq.add(s3);
        npq.add(s4);
        npq.add(s5);
        Queue<Student> gpq = new PriorityQueue<>(1,GradeComparator);
        gpq.add(s1);
        gpq.add(s2);
        gpq.add(s3);
        gpq.add(s4);
        gpq.add(s5);
        Queue<Student> apq = new PriorityQueue<>(1,AgeComparator);
        apq.add(s1);
        apq.add(s2);
        apq.add(s3);
        apq.add(s4);
        apq.add(s5);
        System.out.println(npq.toString());
        System.out.println(gpq.toString());
        System.out.println(apq.toString());
        System.out.println(npq.peek().toString());
    }

    public static Comparator<Student> NameComparator = new Comparator<Student>(){
        @Override
        public int compare(Student s1, Student s2) {
            String curnaam1 = s1.getName().toLowerCase();
            String curnaam2 = s2.getName().toLowerCase();
            for (int i = 0; i < curnaam1.length() || i < curnaam2.length();i++) {
                if (curnaam1.charAt(i) < curnaam2.charAt(i)) return 1;
                else if (curnaam1.charAt(i) > curnaam2.charAt(i)) return -1;
            }
            return 0;
        }
    };

    public static Comparator<Student> GradeComparator = new Comparator<Student>(){
        @Override
        public int compare(Student s1, Student s2) {
            if (s1.getGrade() > s2.getGrade()) return 1;
            else if (s1.getAge() < s2.getAge()) return -1;
            else return 0;
        }
    };

    public static Comparator<Student> AgeComparator = new Comparator<Student>(){
        @Override
        public int compare(Student s1, Student s2) {
            if (s1.getAge() > s2.getAge()) return 1;
            else if (s1.getAge() < s2.getAge()) return -1;
            else return 0;
        }
    };
}
