package ca.ualberta.todolistapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

/* This activity is the Main activity. It serves as the main portal to every
 * other activity. It contains a main drop down menu and a greeting message.
 */
public class MainActivity extends Activity
{
	private ArrayList<TodoItem> curr_list;
	private ArrayList<TodoItem> arch_list;
	
	private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // these two arrays stores all the necessary date
        // initializes when the app first gets created
    	curr_list = new ArrayList<TodoItem>();
    	arch_list = new ArrayList<TodoItem>();
    }

	@Override
	public void onStart()
	{
		super.onStart();
		
		Intent intent = getIntent();
		
		// if main activity gets called by another activity,
		// retrieve passed data
		if (intent.getExtras() != null)
		{
			bundle = intent.getExtras();
    	
			curr_list = (ArrayList<TodoItem>) bundle.getSerializable("curr_list");
			arch_list = (ArrayList<TodoItem>) bundle.getSerializable("arch_list");
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	// handles menu selections on the action bar
        int id = item.getItemId();
        if (id == R.id.menu_todos)
        {	
        	// pass data to next activity
        	bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(MainActivity.this, TodoActivity.class);
        	intent.putExtras(bundle);
        	
    		startActivity(intent);
        }
        else if (id == R.id.menu_archive)
        {	
        	// pass data to next activity
        	bundle = new Bundle();
        	
        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(MainActivity.this, ArchiveActivity.class);
        	intent.putExtras(bundle);
        	
    		startActivity(intent);
        }
        else if (id == R.id.menu_email)
        {	
        	// pass data to next activity
        	bundle = new Bundle();
        	
        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
        	Intent intent = new Intent(MainActivity.this, EmailActivity.class);
        	intent.putExtras(bundle);
        	
    		startActivity(intent);
        }
        else if (id == R.id.menu_summary)
        {	        	
        	// pass data to next activity
        	bundle = new Bundle();
        	
        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
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
}
