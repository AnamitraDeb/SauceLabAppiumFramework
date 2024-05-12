package com.saucelabs.utilities;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataLoader {
	
	public static List<HashMap<String, String>> readJsonFiles(String jsonFileName)
	{
		String jsonFilePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "com" + 
	                          File.separator + "saucelabs" + File.separator + "testdata" + File.separator + jsonFileName;

		String jsonContent;
		try {
			jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
			ObjectMapper mapper = new ObjectMapper();
			List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){
			});
			return data;			
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to read JsonFile");
		}

	}
	
	public static Object[][] getMappedData(List<HashMap<String, String>> dataList)
	{
		Object data[][] = new Object[dataList.size()][1];
		for (int i = 0; i<dataList.size(); i++)
		{
			for (int j=0; j<1; j++)
			{
				data[i][j]= dataList.get(i);
			}
		}
		return data;
		
	}
}
