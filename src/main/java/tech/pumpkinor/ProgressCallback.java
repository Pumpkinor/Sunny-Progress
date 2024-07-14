package tech.pumpkinor;

public interface ProgressCallback {
    String PREFIX = "\033";
    String RED = PREFIX + "[31m";
    
    String GREEN = PREFIX + "[32m";
    String YELLOW = PREFIX + "[33m";
    String BLUE = PREFIX + "[34m";
    String PURPLE = PREFIX + "[35m";
    String CYAN = PREFIX + "[36m";
    String WHITE = PREFIX + "[37m";
    String NORMAL = PREFIX + "[0m";
    
    void onProgress(double progress);
}