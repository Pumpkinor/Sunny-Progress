package tech.pumpkinor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 控制台进度条的实现
 * @author pumpkinor
 * @version 1.0
 */
public class ConsoleProgressBar implements ProgressCallback{
	private double onePiece = 1.00;
	private double maxBarValue = 100.00;
	private int progressBarLength = 100;
	private String beforeFilling = "_";
	private String afterFilling = "#";
    private int type = 0;

	private void init(){
		this.onePiece = divDouble(maxBarValue,progressBarLength);
	}
	
	public ConsoleProgressBar(){
		this.init();
	}
	
	public ConsoleProgressBar(double maxBarValue){
		this.maxBarValue = maxBarValue;
		this.init();
	}
	
	public ConsoleProgressBar(double maxBarValue, int type){
		this.maxBarValue = maxBarValue;
		this.type = type;
		this.init();
	}
	
	public ConsoleProgressBar(double maxBarValue, int progressBarLength, int type){
		this.maxBarValue = maxBarValue;
		this.progressBarLength = progressBarLength;
		this.type = type;
		this.init();
	}
	
	public ConsoleProgressBar(double maxBarValue, int progressBarLength, int type, String beforeFilling, String afterFilling){
		this.maxBarValue = maxBarValue;
		this.progressBarLength = progressBarLength;
		this.type = type;
		this.beforeFilling = beforeFilling;
		this.afterFilling = afterFilling;
		this.init();
	}

	public void draw(double current){
		draw(current, this.type);
	}

	public void draw(double current, int type){
		int fillNum = divInteger(current, onePiece);
		System.out.print('\r');
        StringBuilder text = new StringBuilder("[");
        text.append(String.valueOf(afterFilling).repeat(Math.max(0, fillNum)));
        text.append(String.valueOf(beforeFilling).repeat(Math.max(0, progressBarLength - fillNum)));
		text.append("] ");
        text = switch (type) {
            case 1 -> new StringBuilder(text.toString().concat("[" + divPercent(current, maxBarValue) + "%]"));
            case 2 -> new StringBuilder(text.toString().concat(divPercent(current, maxBarValue) + "%"));
            default -> new StringBuilder(text.toString().concat("[" + current + "/" + maxBarValue + "]"));
        };
		System.out.print(text);
		if (fillNum == progressBarLength){
			System.out.println();
		}
	}

	private double divDouble(double a, double b){
		BigDecimal ba = new BigDecimal(a);
		BigDecimal bb = new BigDecimal(b);
		return ba.divide(bb, 4, RoundingMode.HALF_DOWN).doubleValue();
	}
	
	private double divPercent(double a, double b){
		BigDecimal ba = new BigDecimal(a);
		BigDecimal bb = new BigDecimal(b);
		return ba.divide(bb, 4, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100)).doubleValue();
	}
	
	private int divInteger(double a, double b){
		BigDecimal ba = new BigDecimal(a);
		BigDecimal bb = new BigDecimal(b);
		return ba.divide(bb, 2, RoundingMode.HALF_DOWN).intValue();
	}
	
	@Override
	public void onProgress(final double progress) {
			this.draw(progress);
	}
}
