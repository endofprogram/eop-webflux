package org.eop.webflux;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Flux;

/**
 * @author lixinjie
 * @since 2019-02-24
 */
public class ReactorFluxTests {

	public static void main(String[] args) {
		displayCurrTime(1);
		displayCurrThreadId(1);
		//创建一个数据源（数据流）
		Flux.fromArray(new Integer[]{5, 4, 3, 2, 1})
			.delaySequence(Duration.ofSeconds(3))
			.map(n -> {
				displayCurrTime(6);
				displayCurrThreadId(6);
				displayValue(n);
				delaySeconds(1);
				return n + 1;
			})
			.filter(n -> {
				displayCurrTime(7);
				displayCurrThreadId(7);
				displayValue(n);
				delaySeconds(1);
				return n % 2 == 0;
			})
			.reduce(0, (sum, n) -> {
				displayCurrTime(8);
				displayCurrThreadId(8);
				displayValue(n);
				delaySeconds(1);
				return sum + n;
			})
			.subscribe(sum -> {
				displayCurrTime(9);
				displayCurrThreadId(9);
				displayValue(sum);
				delaySeconds(1);
				System.out.println(sum + " consumed, worker Thread over, exit.");
			});
		
		displayCurrTime(5);
		displayCurrThreadId(5);
		pause();
	}

	//显示当前时间
	static void displayCurrTime(int point) {
		System.out.println(point + " : " + LocalTime.now());
	}
	
	//显示当前线程Id
	static void displayCurrThreadId(int point) {
		System.out.println(point + " : " + Thread.currentThread().getId());
	}
	
	//显示当前的数值
	static void displayValue(int n) {
		System.out.println("input : " + n);
	}
	
	//延迟若干秒
	static void delaySeconds(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//主线程暂停
	static void pause() {
		try {
			System.out.println("main Thread over, paused.");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
