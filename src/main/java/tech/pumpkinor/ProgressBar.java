package tech.pumpkinor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ProgressBar {
    
    public static <T> void foreach(Collection<T> collection, Consumer<T> consumer){
        int size = collection.size();
        final ProgressCallback callback = new ConsoleProgressBar(size,1);
        final AtomicReference<BigDecimal> progressRef = new AtomicReference<>(BigDecimal.ZERO);
        try {
            collection.forEach(e -> {
                consumer.accept(e);
                double progress = getProgress(size, progressRef);
                CompletableFuture.runAsync(() ->callback.onProgress(progress));
            });
            
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error processing collection: " + ex.getMessage());
        }
    }
    
    public static void fori(int loop, Runnable runnable){
        final ProgressCallback callback = new ConsoleProgressBar(loop,1);
        final AtomicReference<BigDecimal> progressRef = new AtomicReference<>(BigDecimal.ZERO);
        
        try {
            for (int i = 1; i <= loop; i++) {
                runnable.run();
                double progress = getProgress(loop, progressRef);
                CompletableFuture.runAsync(() ->callback.onProgress(progress));
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error processing collection: " + ex.getMessage());
        }
    }
    
    private static double getProgress(final int size, final AtomicReference<BigDecimal> progressRef) {
        BigDecimal collectionSize = new BigDecimal(size);
        BigDecimal increment = BigDecimal.ONE.divide(collectionSize, 10, RoundingMode.HALF_UP);
        return progressRef.updateAndGet(v ->
                new BigDecimal(v.toString()).add(increment).setScale(10, RoundingMode.HALF_UP)).multiply(collectionSize).doubleValue();
    }
}