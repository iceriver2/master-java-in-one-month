import static java.lang.System.out;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DateTime
{
  public static void main(String[] args) {
    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    String text = date.format(formatter);
    out.println(text);
    // LocalDate parsedDate = LocalDate.parse(text, formatter);

    BirthdayDairy bdd = new BirthdayDairy();
    bdd.addBirthday("jim", 21, 5, 1981);
    out.println(bdd.getAgeInYear("jim", 2019));
  }
}

/**
 * 来自书上的例子，用到的好几个对象，留下好好学习一番
 */
class BirthdayDairy {
  private Map<String, LocalDate> birthdays;

  public BirthdayDairy() {
    birthdays = new HashMap<>();
  }

  public LocalDate addBirthday(String name, int day, int month, int year) {
    LocalDate bd = LocalDate.of(year, month, day);
    birthdays.put(name, bd);
    return bd;
  }

  public LocalDate getBirthday(String name) {
    return birthdays.get(name);
  }

  public int getAgeInYear(String name, int year) {
    Period period = Period.between(birthdays.get(name), birthdays.get(name).withYear(year));
    return period.getYears();
  }

  public Set<String> getFriendsOfAgeIn(int age, int year) {
    return birthdays.keySet().stream()
          .filter(p -> getAgeInYear(p, year) == age)
          .collect(Collectors.toSet());
  }

  public int getDaysUntilBirthday(String name) {
    Period period = Period.between(LocalDate.now(), birthdays.get(name));
    return period.getDays();
  }

  public Set<String> getBirthdaysIn(Month month) {
    return birthdays.entrySet().stream()
        .filter(p -> p.getValue().getMonth() == month)
        .map(p -> p.getKey())
        .collect(Collectors.toSet());
  }

  public Set<String> getBirthdaysInNextMonth() {
    return getBirthdaysIn(LocalDate.now().getMonth());
  }

  public int getTotalAgeInYears() {
    return birthdays.keySet().stream()
        .mapToInt(p -> getAgeInYear(p, LocalDate.now().getYear()))
        .sum();
  }
}