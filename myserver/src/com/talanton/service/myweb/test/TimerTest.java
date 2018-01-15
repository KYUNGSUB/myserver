package com.talanton.service.myweb.test;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	private Timer timer = null;
	private TimerTask task;

	public void registerJob(long delay) {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Timer time-out");
				printCurrentTime();
				incrementCount();
				if(getCount() < 5) {
					registerJob(calculateBackoffTime());
				}
				else {
					stopJob();
				}
			}
		};
		
		timer.schedule(task, delay);
	}
	
	public void stopJob() {
		timer = null;
		System.out.println("stop job");
	}
	
	private int count = 0;
	
	public void incrementCount() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	int low = 1;
	int high = 1000;
	Random r = new Random();
	
	public long calculateBackoffTime() {
		return ((long) Math.round(Math.pow(2, count)) * 1000) 
        + (r.nextInt(high - low) + low);
	}
	
	public static void main(String[] args) {
		printCurrentTime();
		TimerTest tt = new TimerTest();
		tt.start();
	}

	private static void printCurrentTime() {
		System.out.println("" + new Date().toLocaleString());
	}

	private void start() {
		registerJob(1000L);
	}
}