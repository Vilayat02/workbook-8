package com.pluralsight;

import com.pluralsight.data.DataManager;
import com.pluralsight.model.Actor;
import com.pluralsight.model.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://127.0.0.1:3306/sakila";
        String user = args[0];
        String password = args[1];

        DataManager dataManager = new DataManager(url, user, password);

        /*String movieInfo = "SELECT a.actor_id AS ActorID, a.first_name AS FirstName, a.last_name AS LastName, f.film_id AS FilmID,f.title AS FilmTitle \n" +
                "FROM film AS f\n" +
                "JOIN film_actor AS fa\n" +
                "ON f.film_id = fa.film_id\n" +
                "JOIN actor AS a\n" +
                "ON fa.actor_id = a.actor_id\n" +
                "WHERE a.last_name LIKE ?";*/

        boolean run = true;
        while (run) {
            System.out.println("\n1-Search films by surname\n2-Search by actor ID\n0-Exit");
            int search = sc.nextInt();
            sc.nextLine();
            switch (search) {
                case 1:
                    System.out.print("Enter the surname of your favorite actor: ");
                    String surname = sc.nextLine();
                    List<Actor> actors = dataManager.searchActorsByLastName(surname);
                    if (actors.isEmpty()) {
                        System.out.println("No actors found with surname `" + surname + "`.");
                    } else {

                        System.out.printf("%-10s %-15s %-15s %-10s %-20s\n",
                                "ActorID", "FirstName", "LastName", "FilmID", "FilmTitle");
                        System.out.println("------------------------------------------------------------------------");

                        for (Actor actor : actors) {
                            List<Film> films = dataManager.getFilmsByActorId(actor.getActorId());
                            for (Film film : films) {
                                System.out.printf("%-10d %-15s %-15s %-10d %-20s\n",
                                        actor.getActorId(),
                                        actor.getFirstName(),
                                        actor.getLastName(),
                                        film.getFilmId(),
                                        film.getTitle()
                                );
                            }
                        }
                    }

                    break;


                case 2:
                    System.out.print("Enter actor ID: ");
                    int actorId = sc.nextInt();
                    sc.nextLine();
                    List<Film> films = dataManager.getFilmsByActorId(actorId);
                    if (films.isEmpty()) {
                        System.out.println("No films found for this actor.");
                    } else {
                        System.out.printf("%-10s %-15s %-15s %-10s %-20s\n",
                                "ActorID", "FirstName", "LastName", "FilmID", "FilmTitle");
                        System.out.println("------------------------------------------------------------------------");
                        for (Film film : films) {
                            System.out.println(film);
                            System.out.println("--------------------------------------------------");
                        }
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    run = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

    }

}
