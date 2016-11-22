package kr.co.shineware.ds.trie;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TrieDictionaryTest {
	private static ExecutorService service;
	private static final int THREAD_NUM = 4;
	private static final int DATA_NUM = 100000;

	private static String[] KEYS;
	private static Integer[] VALS;

	@BeforeClass
	public static void setup() {
		KEYS = new String[DATA_NUM];
		VALS = new Integer[DATA_NUM];

		for (int i = 0; i < KEYS.length; i++) {
			KEYS[i] = "key" + i;
			VALS[i] = i;
		}
		service = Executors.newFixedThreadPool(THREAD_NUM);
	}

	@AfterClass
	public static void teardown() {
		if (service != null) {
			service.shutdown();
		}
	}

	@Test
	public void testSequentialGet() {
		final TrieDictionary<Integer> dic = new TrieDictionary<>();
		for (int i = 0; i < KEYS.length; i++) {
			dic.put(KEYS[i], VALS[i]);
		}

		for (int i = 0; i < KEYS.length; i++) {
			final Integer result = dic.get(KEYS[i]);
			assertEquals(VALS[i], result);
		}
	}

	@Test
	public void testConcurrentGet() throws ExecutionException, InterruptedException {
		final TrieDictionary<Integer> dic = new TrieDictionary<>();
		for (int i = 0; i < KEYS.length; i++) {
			dic.put(KEYS[i], VALS[i]);
		}

		final List<Future<Integer>> futures = new ArrayList<>();

		for (int i = 0; i < KEYS.length; i++) {
			final int index = i;

			futures.add(service.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					return dic.get(KEYS[index]);
				}
			}));
		}

		for (int i = 0; i < futures.size(); i++) {
			assertEquals(VALS[i], futures.get(i).get());
		}
	}
}