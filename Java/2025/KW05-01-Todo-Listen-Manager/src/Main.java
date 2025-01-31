import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean end = true;

        ArrayList<ToDoList> list = new ArrayList<ToDoList>();

        Scanner myObj = new Scanner(System.in);
        while (end) {
            System.out.println("1. Add To-Do List\n2. Remove From-Do List\n3. Print All To-Do Lists\n4. Exit");
            int choice = myObj.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter Title: ");
                    myObj.nextLine();
                    String title = myObj.nextLine();
                    System.out.println("Enter priority(1 = High, 2 = Middle, 3 = Low): ");
                    int priority = myObj.nextInt();
                    list.add(new ToDoList(priority, title));
                    break;
                case 2:
                    if (list.isEmpty()) {
                        System.out.println("No tasks in the list!");
                    } else {
                        System.out.println("\nYour To-Do Lists:");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println("Index: " + " | " + (i) + list.get(i));
                        }
                        System.out.println();
                        System.out.println("What To do at what Index do you want to remove? ");
                        int index = myObj.nextInt();
                        list.remove(index);
                    }
                    break;
                case 3:
                    if (list.isEmpty()) {
                        System.out.println("No tasks in the list!");
                    } else {
                        System.out.println("\nYour To-Do Lists:");
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).Priority == 1){
                                System.out.println((i + 1) + ". " + list.get(i));
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).Priority == 2){
                                System.out.println((i + 1) + ". " + list.get(i));
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).Priority == 3){
                                System.out.println((i + 1) + ". " + list.get(i));
                            }
                        }

                        System.out.println();
                    }
                    break;
                    case 4:
                        end = false;
                        break;
            }
        }
    }
}