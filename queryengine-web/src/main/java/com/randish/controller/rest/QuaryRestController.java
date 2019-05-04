package com.randish.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuaryRestController {

	@GetMapping("/processdata")
	public List<String> processdata(@RequestParam(value = "dataId", required = false) String critaria) {
		System.out.println("critaria " + critaria);
		List<String> tableNames = new ArrayList<String>();
		tableNames.add("Rajesh");
		tableNames.add("Anthari");
		tableNames.add("Shiva");
		tableNames.add("Naresh");
		tableNames.add("vinod");
		tableNames.add("mahender");
		return tableNames;
	}

	@GetMapping("/tableData")
	public List<List<String>> tableData(@RequestParam(value = "dataId", required = false) String critaria,
			@RequestParam(value = "tName", required = false) String tableName) {
		System.out.println("tableName " + tableName);
		return prepareSampleData(critaria);
	}

	private List<List<String>> prepareSampleData(String critaria) {
		double random = Math.random() * 11;
		System.out.println("random " + random);
		System.out.println("Before " + new Date());
		try {
			Thread.sleep((int) random * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("After " + new Date());
		List<List<String>> tableNames = new ArrayList<>();
		tableNames.add(Arrays.asList("Name", "Gender", "Address"));
		if (random < 5) {
			return tableNames;
		}
		critaria += " ";
		tableNames.add(Arrays.asList(critaria + random, "Male", "KukatPally"));
		tableNames.add(Arrays.asList(critaria + random, "Male", critaria + " Hyd"));
		tableNames.add(Arrays.asList(critaria + random, "Male", "Pune"));
		return tableNames;
	}

}
