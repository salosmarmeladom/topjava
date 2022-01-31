package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

       // System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> usermealwithexcess = new ArrayList<UserMealWithExcess>();
        HashMap<LocalDate, Integer> map = new HashMap<LocalDate, Integer>();
        boolean excess;
        for (UserMeal meal : meals){
            if (!map.containsKey(meal.getDateTime().toLocalDate()))
            map.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            else {
                int value = map.get(meal.getDateTime().toLocalDate()) + meal.getCalories();
                map.replace(meal.getDateTime().toLocalDate(), value);
            }
        }   // creating new HashMap: key - date, value - calories per day
        for (UserMeal meal : meals){
            LocalDateTime localDateTime = LocalDateTime.of(meal.getDateTime().getYear(), meal.getDateTime().getMonth(), meal.getDateTime().getDayOfYear(), meal.getDateTime().getHour(), meal.getDateTime().getMinute());
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)){
                if (map.get(meal.getDateTime().toLocalDate()) > caloriesPerDay){
                    excess = true;
                } else excess = false;
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(localDateTime, meal.getDescription(), meal.getCalories(), excess);
                usermealwithexcess.add(userMealWithExcess);
            }
        }
        return usermealwithexcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
