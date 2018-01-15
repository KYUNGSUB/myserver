package com.talanton.service.myweb.test;

import java.util.Date;
import java.util.Random;

public class ExponentialBackoff {

	public static void main(String[] args) throws Exception {
		int low = 1;
		int high = 1000;
		Random r = new Random();

		// Attempt to execute our main action, retrying up to 4 times
		// if an exception is thrown
		for (int n = 0; n <= 4; n++) {
		    try {

			    // The main action you want to execute goes here
			    // If this does not come in the form of a return
		        // statement (i.e., code continues below the loop)
			    // then you must insert a break statement after the
		        // action is complete.
			    doSomething();

		    } catch (Exception e) {

		        // If we've exhausted our retries, throw the exception
		        if (n == 4) {
		            throw e;
		        }
			
		        // Wait an indeterminate amount of time (range determined by n)
		        try {
		        	int period = ((int) Math.round(Math.pow(2, n)) * 1000) 
			                + (r.nextInt(high - low) + low);
		        	System.out.println("sleep time : " + period);
		            Thread.sleep(period);
		        } catch (InterruptedException ignored) {
		            // Ignoring interruptions in the Thread sleep so that
		            // retries continue
		        }
		    }
		}
	}

	private static void doSomething() throws Exception {
		System.out.println("doSomething() : " + new Date().toLocaleString());
		throw new Exception("exception happen");
	}

}
