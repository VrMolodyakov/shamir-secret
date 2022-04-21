import java.util.Scanner;

public class Menu {
    private String[] menu = {"0 - Разбить секрет на части","1 - Собрать секрет из частей","2 - Выход"};
    public int showMenu(){
        Scanner consoleRider = new Scanner(System.in);
        int choice;
        printMenu();
        choice = consoleRider.nextInt();
        return choice;




    }

    private void printMenu(){
        for (String s : menu) {
            System.out.println(s);
        }
    }
}
