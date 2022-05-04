import algorithm.SecretCombiner;
import algorithm.SecretSplitter;

import java.io.File;
import java.util.*;

public class Main {
    private static int REBUILD_COUNT = 4;
    public static void main(String[] args) {
        Menu menu = new Menu();
        int choice = 0;
        int rebuildCount = REBUILD_COUNT;
        Scanner scanner = new Scanner(System.in);
        while(choice != -1) {
            choice = menu.showMenu();
            switch (choice) {
                case (0):
                    System.out.println("Введите минимальное число людей для восстановления (не меньше 4) : ");
                    rebuildCount = scanner.nextInt();
                    SecretSplitter secretSplitter = new SecretSplitter(rebuildCount);
                    System.out.println("Введите имя файла : ");
                    String fileName = scanner.next();
                    secretSplitter.splitIntoPieces("/secret" + fileName);
                    System.out.println("Секрет разделен");
                    break;
                case (1):
                    System.out.println("Введите количесво файлов : ");
                    int count = scanner.nextInt();
                    List<File> files = new ArrayList<>();
                    System.out.println("Введите имена файлов: ");
                    for (int i = 0; i < count; i++) {
                        fileName = scanner.next();
                        File file = new File("parts/" + fileName);
                        files.add(file);
                    }
                    SecretCombiner secretCombiner = new SecretCombiner(count);
                    secretCombiner.combineParts(files);
                    System.out.println("Секрет восстановлен");
                    break;
                default:
                    choice = -1;
                    System.out.println("Выход");
                    break;


            }
        }

    }

    private static List<File> getRandomSecretParts(String folderPath, int partCount){
        File[] files = new File(folderPath).listFiles();
        Set<Integer> parts = new HashSet<>();
        List<File> partsList = new ArrayList<>();
        while(parts.size() != partCount){
            Random rand = new Random();
            parts.add(rand.nextInt(files.length));
        }
        for (Integer part : parts) {
            partsList.add(files[part]);
        }
        return partsList;

    }

}
