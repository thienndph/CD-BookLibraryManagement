import Model.Book;
import Model.CD;
import Model.Item;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Exception.ItemAlreadyBorrowedException;
import Exception.ItemNotAvailableException;
import Exception.ItemNotFoundException;

class LibraryManagement<T extends Item> {
    private List<T> itemList;

    public LibraryManagement() {
        this.itemList = new ArrayList<>();
    }

    public void addItem(T item) {
        itemList.add(item);

    }


    public void removeItem(String id) throws ItemNotFoundException {
        T item = findItemById(id);
        if (item != null) {
            itemList.remove(item);
        } else {
            throw new ItemNotFoundException(id);
        }
    }


    public void updateItem(String id, T newItem) throws ItemNotFoundException {
        T item = findItemById(id);
        if (item != null) {
            int index = itemList.indexOf(item);
            itemList.set(index, newItem);
        } else {
            throw new ItemNotFoundException(id);
        }
    }


    public List<T> searchItem(String keyword) {
        List<T> result = new ArrayList<>();
        String lowerCaseKeyword = keyword.toLowerCase();
        for (T item : itemList) {
            if (item.getTitle().toLowerCase().contains(lowerCaseKeyword) ||
                    (item instanceof Book && ((Book) item).getAuthor().toLowerCase().contains(lowerCaseKeyword)) ||
                    (item instanceof CD && ((CD) item).getArtist().toLowerCase().contains(lowerCaseKeyword))) {
                result.add(item);
            }
        }
        return result;
    }

    public void borrowItem(String id) throws ItemNotFoundException, ItemNotAvailableException, ItemAlreadyBorrowedException {

        T item = findItemById(id);
        if (item != null) {
            if (item.isStatus()) {
                throw new ItemAlreadyBorrowedException(id);
            } else {
                item.setStatus(true);
            }
        } else {
            throw new ItemNotFoundException(id);
        }

    }


    public void returnItem(String id) throws ItemNotFoundException, ItemNotAvailableException, ItemAlreadyBorrowedException {
        T item = findItemById(id);
        if (item != null) {
            if (!item.isStatus()) {
                throw new ItemNotAvailableException(id);
            } else {
                item.setStatus(false);
            }
        } else {
            throw new ItemNotFoundException(id);
        }

    }

    public void displayItems() {
        for (T item : itemList) {
            System.out.println(item);
        }
    }

    private T findItemById(String id) {
        for (T item : itemList) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void readDataFromFile(String bookFilePath, String cdFilePath) throws IOException {
        try (BufferedReader bookReader = new BufferedReader(new FileReader(bookFilePath));
             BufferedReader cdReader = new BufferedReader(new FileReader(cdFilePath))) {

            String line;

            while ((line = bookReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    String title = parts[1];
                    String publisher = parts[2];
                    int year = Integer.parseInt(parts[3]);
                    boolean status = Boolean.parseBoolean(parts[4]);
                    String author = parts[5];
                    Book book = new Book(id, title, publisher, year, status, author);
                    addItem((T) book);
                }
            }

            while ((line = cdReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    String title = parts[1];
                    String publisher = parts[2];
                    int year = Integer.parseInt(parts[3]);
                    boolean status = Boolean.parseBoolean(parts[4]);
                    String artist = parts[5];
                    CD cd = new CD(id, title, publisher, year, status, artist);
                    addItem((T) cd);
                }
            }
        }
    }

    public void writeDataToFile(String bookFilePath, String cdFilePath) throws IOException {
        try (BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookFilePath));
             BufferedWriter cdWriter = new BufferedWriter(new FileWriter(cdFilePath))) {

            for (T item : itemList) {
                if (item instanceof Book) {
                    Book book = (Book) item;
                    bookWriter.write(String.join(",", book.getId(), book.getTitle(), book.getPublisher(),
                            String.valueOf(book.getYear()), String.valueOf(book.isStatus()), book.getAuthor()));
                    bookWriter.newLine();
                } else if (item instanceof CD) {
                    CD cd = (CD) item;
                    cdWriter.write(String.join(",", cd.getId(), cd.getTitle(), cd.getPublisher(),
                            String.valueOf(cd.getYear()), String.valueOf(cd.isStatus()), cd.getArtist()));
                    cdWriter.newLine();
                }
            }
        }
    }
}