import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        System.out.println(countOfMinors(persons));
        System.out.println("--------------------");
        System.out.println(getСonscripts(persons));
        System.out.println("--------------------");
        System.out.println(getSortWorkable(persons));
    }

    private static long countOfMinors (Collection<Person> persons) {
        Stream stream = persons.stream();
        return stream.filter(o -> ((Person)o).getAge() < 18).count();
    }

    private static List<String> getСonscripts (Collection<Person> persons) {
        Stream stream = persons.stream();
        return (List<String>) stream.filter(o -> ((Person)o).getAge() >= 18 && ((Person)o).getAge() < 27)
                .map(o -> ((Person)o).getFamily()).collect(Collectors.toList());
    }

    private static List<String> getSortWorkable (Collection<Person> persons) {
        Stream stream = persons.stream();
        return (List<String>) stream.filter(new Predicate() {
            @Override
            public boolean test(Object o) {
                Person p = (Person) o;
                return p.getEducation() == Education.HIGHER &&
                        (p.getSex() == Sex.MAN && p.getAge() >= 18 && p.getAge() < 65 ||
                        p.getSex() == Sex.WOMEN && p.getAge() >= 18 && p.getAge() < 60);
            }
        }).map(o -> ((Person)o).getFamily()).sorted().collect(Collectors.toList());
    }
}
