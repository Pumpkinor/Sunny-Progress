package tech.pumpkinor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello world");
        List<Integer> list = IntStream.rangeClosed(0, 79).boxed().collect(Collectors.toList());
        ProgressBar.foreach(list, (i) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        Thread.sleep(1000);
        
        ProgressBar.fori(100, () -> {
            try {
                System.out.println("**********");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }}