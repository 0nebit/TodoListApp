package ca.ualberta.todolistapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileHandler
{
	private static String file_name0 = "curr_items.txt";

	public FileHandler() {
	}
	
	public static ArrayList<TodoItem> load_curr_items()
	{
		ArrayList<TodoItem> curr_items = new ArrayList<TodoItem>();
		
		try
		{
			FileInputStream fis = new FileInputStream(file_name0);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
		
			String line;
		
			while ((line = br.readLine()) != null)
			{
				curr_items.add(new TodoItem(line));
			}
			
			fis.close();
			isr.close();
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return curr_items;
	}
	
	public void save_curr_items(ArrayList<TodoItem> list0)
	{
		FileOutputStream fos;
		try
		{
			fos = new FileOutputStream(file_name0);
			//bos = new BufferedOutputStream(fos);
			
			for (TodoItem item : list0)
			{
				//fos.write(item.get_item().getBytes());
			}

			fos.close();
			//bos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
