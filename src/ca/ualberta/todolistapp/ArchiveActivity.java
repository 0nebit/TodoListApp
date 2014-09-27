package ca.ualberta.todolistapp;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/* This activity allows the user to view, check/uncheck, delete, and unarchive
 * archived TODO items.
 * The menu options can take the user back Main Menu.
 */
public class ArchiveActivity extends Activity
{
	private ArrayList<TodoItem> curr_list;
	private ArrayList<TodoItem> arch_list;
	
	private Bundle bundle;
	private ListView list_view;
	private ArrayAdapter<TodoItem> adapter;
	
	// for getting the indices of the selected items from the ListView
	private SparseBooleanArray selected;
	private ArrayList<Integer> positions; // stores the selected indices
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archive);
        
        list_view = (ListView) findViewById(R.id.display_archive);
    }

    @Override
    public void onStart()
    {
    	super.onStart();
    	
    	// retrieve data from previous activity
    	Intent intent = getIntent();
    	bundle = intent.getExtras();
    	
		curr_list = (ArrayList<TodoItem>) bundle.getSerializable("curr_list");
		arch_list = (ArrayList<TodoItem>) bundle.getSerializable("arch_list");
		
		positions = new ArrayList<Integer>();
				

		// adapts to arch_list which stores all archived items
		// which this activity displays and adds to
		// uses built-in layout simple_list_item_multiple_choice
        adapter = new ArrayAdapter<TodoItem>(this,
                android.R.layout.simple_list_item_multiple_choice, arch_list);
        
        list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // allows for multiple selections of items
        list_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.archive_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_arch_main)
        {
        	// pass data to next activity
        	bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(ArchiveActivity.this, MainActivity.class);
        	intent.putExtras(bundle);
        	
    		startActivity(intent);
        }
        else if (id == R.id.menu_arch_check)
        {
        	int total = 0;
        	update_positions(); // get selected indices
        	for (int pos : positions)
        	{
				TodoItem temp = arch_list.get(pos);
				if (temp.checked() == 0) // only check unchecked items
				{
					temp.set_checked(1);
					arch_list.set(pos, temp);
					total += 1; // update number of items handled, for Toast display
				}
			}
				
			Context context = getApplicationContext();
        	String msg = " archived TODOs checked.";

        	Toast toast = Toast.makeText(context, "(NEW) "+Integer.valueOf(total).toString()+msg, Toast.LENGTH_SHORT);
        	toast.show();
        	
        	adapter.notifyDataSetChanged();
        }
        else if (id == R.id.menu_arch_uncheck)
        {
        	int total = 0;
        	update_positions();
        	for (int pos : positions)
        	{
				TodoItem temp = arch_list.get(pos);
				if (temp.checked() == 1) // only unchecks checked items
				{
					temp.set_checked(0);
					arch_list.set(pos, temp);
					total += 1;
				}
			}
				
			Context context = getApplicationContext();
        	String msg = " archived TODOs unchecked.";

        	Toast toast = Toast.makeText(context, "(NEW) "+Integer.valueOf(total).toString()+msg, Toast.LENGTH_SHORT);
        	toast.show();
        	
        	adapter.notifyDataSetChanged();
        }
        else if (id == R.id.menu_arch_delete)
        {
        	int total = 0;
        	update_positions();
        	for (int pos : positions)
        	{
        		// need to set to null because saved index numbers relies on original positions of items
				arch_list.set(pos, null);
				total += 1;
			}
        	
        	// remove items (null)
        	while (arch_list.contains(null))
        	{
        		arch_list.remove(null);
        	}
        	
			Context context = getApplicationContext();
        	String msg = " archived TODOs deleted.";

        	Toast toast = Toast.makeText(context, Integer.valueOf(total).toString()+msg, Toast.LENGTH_SHORT);
        	toast.show();
        	
        	adapter.notifyDataSetChanged();
        }
        else if (id == R.id.menu_arch_unarchive)
        {
        	int total = 0;
        	update_positions();
           	for (int pos : positions)
        	{
				TodoItem temp = arch_list.get(pos);
				if (temp.archived() == 1)
				{
					temp.set_archived(0);
					curr_list.add(temp);
					// need to set to null because saved index numbers relies on original positions of items
					arch_list.set(pos, null);
					total += 1;
				}
			}
           	
           	// remove items (null)
        	while (arch_list.contains(null))
        	{
        		arch_list.remove(null);
        	}
        	
			Context context = getApplicationContext();
        	String msg = " TODOs unarchived.";

        	Toast toast = Toast.makeText(context, Integer.valueOf(total).toString()+msg, Toast.LENGTH_SHORT);
        	toast.show();
        	
        	adapter.notifyDataSetChanged();
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    }
    
    private void update_positions()
    {
    	positions.clear();
		selected = list_view.getCheckedItemPositions();
		int pos = 0;
		for (int i = 0; i < selected.size(); i++)
		{
			if (selected.valueAt(i) == true)
			{
				pos = selected.keyAt(i);
				positions.add(pos);
			}
		}
    }
}
