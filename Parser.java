import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.util.*;

public class Parser {
    public static void main(String[] args) {
        int page = 0;
        boolean finish = false;
        Set<Task> mas = new HashSet<>();
        while (!finish) {
            page++;
            try {
                Document document = Jsoup.connect("https://codeforces.com/problemset/page/" + page).get();
                Element table = document.select("tbody").get(0);
                for (int i = 1; i < table.children().size(); i++) {
                    Element task = table.child(i);
                    String data1 = task.child(0).text();
                    String data2 = task.child(1).child(0).text();
                    int data3;
                    int data4;
                    try {
                        data3 = Integer.parseInt(task.child(3).text());
                    } catch (Throwable e) {
                        data3 = 0;
                    }
                    try {
                        data4 = Integer.parseInt(task.child(4).text().substring(1));
                    } catch (Throwable e) {
                        data4 = 0;
                    }
                    Task xyu = new Task(data1, data2, data3, data4);
                    if (mas.contains(xyu)) {
                        finish = true;
                    } else {
                        mas.add(xyu);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                break;
            }
        }
        Comparator<Task> comparator = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (o1.number > o2.number) {
                    return -1;
                } else if (o1.number == o2.number) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        Task[] ans = mas.toArray(new Task[0]);
        Arrays.sort(ans, comparator);
        for (int i = 0; i < ans.length; i++) {
            System.out.println((i + 1) + "     " + ans[i]);
        }
    }

    public static class Task {
        private final String index;
        private final String name;
        private final int points;
        private final int number;

        public Task(String index, String name, int points, int number) {
            this.index = index;
            this.name = name;
            this.points = points;
            this.number = number;
        }

        @Override
        public String toString() {
            return index + ": " + name + " " + points + "( " + number + " )";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return points == task.points && number == task.number && Objects.equals(index, task.index) && Objects.equals(name, task.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, name, points, number);
        }
    }
}
