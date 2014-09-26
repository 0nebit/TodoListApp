package ca.ualberta.todolistapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SummaryActivity extends Activity
{
	private ArrayList<TodoItem> curr_list;
	private ArrayList<TodoItem> arch_list;
	private ArrayList<Integer> info_list;
	
	private ArrayList<String> display_list;
	
	private ListView list_view;
	private ArrayAdapter<String> adapter;
	private Bundle bundle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        
        list_view = (ListView) findViewById(R.id.display_summary);
    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	
    	info_list = new ArrayList<Integer>();
    	for (int i = 0; i < 5; i++)
    	{
    		info_list.add(Integer.valueOf(0));
    	}    
    	
    	Intent intent = getIntent();
    	bundle = intent.getExtras();
    	
		curr_list = (ArrayList<TodoItem>) bundle.getSerializable("curr_list");
		arch_list = (ArrayList<TodoItem>) bundle.getSerializable("arch_list");
		
		update_info_list();
		
		display_list = new ArrayList<String>();
		
		display_list.add("Total number of TODO items checked:\n"+info_list.get(0).toString());
		display_list.add("Total number of TODO items left unchecked:\n"+info_list.get(1).toString());
		display_list.add("Total number of archived TODO items:\n"+info_list.get(2).toString());
		display_list.add("Total number of checked archived TODO items:\n"+info_list.get(3).toString());
		display_list.add("Total number of unchecked archived TODO items:\n"+info_list.get(4).toString());
		
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, display_list);
        list_view.setAdapter(adapter);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.summary_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_summary_summary)
        {	
        	// pass data to next activity
        	Bundle bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
        	intent.putExtras(bundle);
        	
    		startActivity(intent);
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    }
    
    private void update_info_list()
    {
    	// update not archived TODOs
    	int num_checked = 0;
    	int num_unchecked = 0;
    	
    	for (TodoItem todo : curr_list)
    	{
    		if (todo.checked() == 1)
    		{
    			num_checked += 1;
    		}
    		else
    		{
    			num_unchecked += 1;
    		}
    	}
    	
    	info_list.set(0, num_checked);
    	info_list.set(1, num_unchecked);
    	info_list.set(2, arch_list.size());
    	
    	// update archived TODOs
    	num_checked = 0;
    	num_unchecked = 0;
    	
    	for (TodoItem todo : arch_list)
    	{
    		if (todo.checked() == 1)
    		{
    			num_checked += 1;
    		}
    		else
    		{
    			num_unchecked += 1;
    		}
    	}
    	
    	info_list.set(3, num_checked);
    	info_list.set(4, num_unchecked);
    }
}