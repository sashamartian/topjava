package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDateTime, Integer> caloriesDayList = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();

        // составляем список дата - общее за дату
        for (UserMeal userMeal : mealList) {
            if (caloriesDayList.containsKey(userMeal.getDateTime().truncatedTo(ChronoUnit.DAYS))) {
                caloriesDayList.put(userMeal.getDateTime().truncatedTo(ChronoUnit.DAYS),
                        caloriesDayList.get(userMeal.getDateTime().truncatedTo(ChronoUnit.DAYS)) + userMeal.getCalories());
            } else {
                caloriesDayList.put(userMeal.getDateTime().truncatedTo(ChronoUnit.DAYS), userMeal.getCalories());
            }
        }

        // отфильтровываем данные
        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesDayList.get(userMeal.getDateTime().truncatedTo(ChronoUnit.DAYS)) > caloriesPerDay));
            }
        }

        return userMealWithExceedList;
    }
}
