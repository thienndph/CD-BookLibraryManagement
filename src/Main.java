import Model.Book;
import Model.CD;
import Model.Item;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Exception.ItemAlreadyBorrowedException;
import Exception.ItemNotAvailableException;
import Exception.ItemNotFoundException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static LibraryManagement<Item> library = new LibraryManagement<>();
    private static Scanner scanner = new Scanner(System.in);

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {


        boolean exit = false;
        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1 Thêm một đối tượng mới vào thư viện");
            System.out.println("2 Xóa một đối tượng theo mã");
            System.out.println("3 Cập nhật thông tin của một đối tượng");
            System.out.println("4 Tìm kiếm đối tượng theo tiêu đề hoặc tác giả/nghệ sĩ");
            System.out.println("5 Mượn một đối tượng ");
            System.out.println("6 Trả đối tượng ");
            System.out.println("7 Ghi file ");
            System.out.println("8 Đọc file ");
            System.out.println("0 Thoát.\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    System.out.println("Thoát chương trình.");
                    return;
                case "1":
                    System.out.println("Bài 1 được chọn.");
                    Bai1();
                    break;
                case "2":
                    System.out.println("Bài 2 được chọn.");
                    library.displayItems();
                    Bai2();
                    break;
                case "3":
                    System.out.println("Bài 3 được chọn.");
                    Bai3();
                    break;
                case "4":
                    System.out.println("Bài 4 được chọn.");
                    Bai4();
                    break;
                case "5":
                    System.out.println("Bài 5 được chọn.");
                    Bai5();
                    break;

                case "6":
                    System.out.println("Bài 6 được chọn.");
                    Bai6();
                    break;
                case "7":
                    System.out.println("Bài 7 được chọn.");
                    Bai7();
                    break;
                case "8":
                    System.out.println("Bài 8 được chọn.");
                    Bai8();
                    break;
            }


        }
    }

    private static void Bai7() {
        // Khởi tạo Callable để ghi dữ liệu vào các tệp
        DataProcessor<Item> writer = new DataProcessor<>(library, "bookdata.txt", "cddata.txt", false);

        // Thực hiện ghi dữ liệu trong một luồng riêng biệt
        Future<String> writeResult = executor.submit(writer);

        // Hiển thị kết quả ghi dữ liệu
        try {
            System.out.println(writeResult.get());
            library.displayItems();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đóng ExecutorService sau khi hoàn thành
        executor.shutdown();
    }

    private static void Bai8() {
        // Khởi tạo Callable để ghi dữ liệu vào các tệp
        DataProcessor<Item> reader = new DataProcessor<>(library, "bookdata.txt", "cddata.txt", true);

        // Thực hiện đọc dữ liệu trong một luồng riêng biệt
        Future<String> readResult = executor.submit(reader);

        // Hiển thị thông tin các mục hiện có trong thư viện sau khi đọc dữ liệu
        try {
            System.out.println(readResult.get());
            System.out.println("\nDanh sách mục trong thư viện sau khi đọc dữ liệu:");
            library.displayItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Bai6() {
        library.displayItems();
        System.out.println("Nhập id sách hoặc đĩa bạn muốn trả ?");
        String returnItem = scanner.next();
        try {
            library.returnItem(returnItem);
            System.out.println("\nMục đã được trả thành công:");

            System.out.println("\nDanh sách mục trả khi mượn:");
            library.displayItems();
        } catch (ItemNotFoundException | ItemAlreadyBorrowedException | ItemNotAvailableException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void Bai5() {
        library.displayItems();
        try {
            System.out.println("Nhập id sách hoặc đĩa bạn muốn mượn ?");
            String IDborrowItem = scanner.next();
            library.borrowItem(IDborrowItem);
            System.out.println("\nMục đã được mượn thành công:");

            System.out.println("\nDanh sách mục sau khi mượn:");
            library.displayItems();
        } catch (ItemNotFoundException | ItemAlreadyBorrowedException | ItemNotAvailableException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void Bai4() {
        System.out.println("Bài 4 được chọn.");
        System.out.println("Nhập tên cần tìm kiếm ?");
        String find = scanner.next();

        List<Item> searchResults = library.searchItem(find);
        for (Item item : searchResults) {
            System.out.println(item);
        }
    }

    private static void Bai3() {
        library.displayItems();
        System.out.println("Nếu cần nhập Model.Book nhấn 1 \n Model.CD nhấn 2 ?");
        int needupdate = scanner.nextInt();
        System.out.println("Nhập mã cần cập nhập: ");
        String update = scanner.next();
        if (needupdate == 1) {
            try {
                System.out.println("Nhập thông tin sách");
                System.out.print("Tiêu đề sách: ");
                String bookTitle = scanner.nextLine();
                System.out.print("Nhà xuất bản: ");
                String bookPublisher = scanner.nextLine();
                System.out.print("Năm xuất bản: ");
                int bookYear = scanner.nextInt();
                scanner.nextLine();
                boolean status = true;
                System.out.print("Tác giả: ");
                String author = scanner.nextLine();
                Book book = new Book(update, bookTitle, bookPublisher, bookYear, status, author);
                library.updateItem(update, book);
                library.displayItems();
            } catch (ItemNotFoundException e) {
                System.err.println(e.getMessage());
            }
        } else if (needupdate == 2) {
            try {
                System.out.println("\nNhập thông tin đĩa Model.CD:");
                System.out.print("Mã đĩa Model.CD: ");
                String cdId = scanner.nextLine();
                System.out.print("Tiêu đề đĩa Model.CD: ");
                String cdTitle = scanner.nextLine();
                System.out.print("Nhà xuất bản: ");
                String cdPublisher = scanner.nextLine();
                System.out.print("Năm xuất bản: ");
                int cdYear = scanner.nextInt();
                scanner.nextLine();
                boolean status = true;
                System.out.print("Nghệ sĩ: ");
                String artist = scanner.nextLine();
                CD cd = new CD(update, cdTitle, cdPublisher, cdYear, status, artist);
                library.updateItem(update, cd);
                library.displayItems();
            } catch (ItemNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void Bai2() {
        try {
            System.out.println("Nhập mã cần xoá: \n");
            String remove = scanner.nextLine();
            library.removeItem(remove);
            System.out.println("\nDanh sách mục sau khi xóa:");
            library.displayItems();
        } catch (ItemNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void Bai1() {
        System.out.print("Muốn thêm book nhấn 1 \n Model.CD nhấn phím 2\n");
        int chonthem = scanner.nextInt();
        if (chonthem == 1) {
            System.out.println("Nhập thông tin sách:");
            System.out.print("Mã sách: ");
            String bookId = scanner.next();

            System.out.print("Tiêu đề sách: ");
            String bookTitle = scanner.next();

            System.out.print("Nhà xuất bản: ");
            String bookPublisher = scanner.next();

            System.out.print("Năm xuất bản: ");
            int bookYear = scanner.nextInt();

            boolean status = true;

            System.out.print("Tác giả: ");
            String author = scanner.next();

            Book book = new Book(bookId, bookTitle, bookPublisher, bookYear, status, author);
            library.addItem(book);
            library.displayItems();

        } else if (chonthem == 2) {
            scanner.nextLine();

            System.out.println("\nNhập thông tin đĩa CD:");
            System.out.print("Mã đĩa CD: ");
            String cdId = scanner.nextLine();

            System.out.print("Tiêu đề đĩa CD: ");
            String cdTitle = scanner.nextLine();

            System.out.print("Nhà xuất bản: ");
            String cdPublisher = scanner.nextLine();

            System.out.print("Năm xuất bản: ");
            int cdYear = Integer.parseInt(scanner.nextLine());

            boolean status = true;

            System.out.print("Nghệ sĩ: ");
            String artist = scanner.nextLine();

            CD cd = new CD(cdId, cdTitle, cdPublisher, cdYear, status, artist);
            library.addItem(cd);
            library.displayItems();
        }
    }
}