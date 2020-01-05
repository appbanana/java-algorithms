package com.jqc.greedy.knapsack;

import javax.swing.plaf.IconUIResource;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author appbanana
 * @date 2020/1/5
 */
public class Knapsack {

    public static void main(String[] args) {
//        1.按价值主导; 2.按重量主导; 3.按价值密度主导

//        select("按价值主导", (Article a1, Article a2) -> a2.value - a1.value);
//        select("按重量主导", (Article a1, Article a2) -> a1.weight - a2.weight);
        select("按价值密度主导", (Article a1, Article a2) -> Double.compare(a2.valueDensity, a1.valueDensity));
    }
    static void select(String title, Comparator<Article> cmp) {
        Article[] articles = new Article[]{
                new Article(35, 10), new Article(30, 40),
                new Article(60, 30), new Article(50, 50),
                new Article(40, 35), new Article(10, 40),
                new Article(25, 30)
        };
        Arrays.sort(articles, cmp);

        int capacity = 150, weight = 0, value = 0;
        LinkedList<Article> selectedArticles = new LinkedList<>();
        for (int i = 0; i < articles.length && weight <= capacity; i++) {
            Article article = articles[i];
            int newWeight = weight + article.weight;
            if (newWeight <= capacity) {
                weight = newWeight;
                value += article.value;
                selectedArticles.add(article);
            }
        }
        System.out.println("【" + title + "】");
        System.out.println("总价值：" + value);
        System.out.println("总重量：" + weight);
        for (int i = 0; i < selectedArticles.size(); i++) {
            System.out.println(selectedArticles.get(i));
        }
        System.out.println("-----------------------------");

    }
}
