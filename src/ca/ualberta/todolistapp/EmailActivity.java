package ca.ualberta.todolistapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/* This activity is the main screen to emailing. It is a portal for which emailing tasks the
 * user wants to do. It the the gate to:
 * 		selecting and emailing unarchived TODO items
 * 		selecting and emailing archived TODO items
 * 		emailing all items
 * 
 * It contains three buttons for each and warns the user of empty content.
 */
public class EmailActivity extends Activity
{
	private ArrayList<TodoItem> curr_list;
	private ArrayList<TodoItem> arch_list;
	
	// button0 for unarchived TODOs
	// button1 for archived TODOs
	// button2 for all TODOs
	private Button button0, button1, button2;
	
	private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);    
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
		
        button0 = (Button) findViewById(R.id.email_button0);
        button1 = (Button) findViewById(R.id.email_button1);
        button2 = (Button) findViewById(R.id.email_button2);

        button0.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View arg0)
        	{
        		if (curr_list.size() == 0)
        		{
            		Toast toast = Toast.makeText(EmailActivity.this, "You have no unarchived TODO items.", Toast.LENGTH_SHORT);
            		toast.show();
        		}
        		else
        		{
        			// no empty lists, ok to go to emailing activity
        			bundle = new Bundle();
        			
        			bundle.putSerializable("curr_list", curr_list);
        			bundle.putSerializable("arch_list", arch_list);
        			
        			Intent intent = new Intent(EmailActivity.this, EmailTodoActivity.class);
            		intent.putExtras(bundle);
            		
            		startActivity(intent);
        		}
        	}
        });
        button1.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View arg0)
        	{
        		if (arch_list.size() == 0)
        		{
            		Toast toast = Toast.makeText(EmailActivity.this, "You have no archived TODO items.", Toast.LENGTH_SHORT);
            		toast.show();
        		}
        		else
        		{
        			// no empty lists, ok to go to emailing activity
        			bundle = new Bundle();
        			
        			bundle.putSerializable("curr_list", curr_list);
        			bundle.putSerializable("arch_list", arch_list);
        			
        			Intent intent = new Intent(EmailActivity.this, EmailArchActivity.class);
            		intent.putExtras(bundle);
            		
            		startActivity(intent);
        		}
        	}
        });
        button2.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View arg0)
        	{
        		if ((curr_list.size() == 0) && (arch_list.size() == 0))
        		{
            		Toast toast = Toast.makeText(EmailActivity.this, "You do not have a single TODO item stored.", Toast.LENGTH_SHORT);
            		toast.show();
        		}
        		else
        		{
        			// no empty lists, ok to go to emailing activity
        			bundle = new Bundle();
        			
        			bundle.putSerializable("curr_list", curr_list);
        			bundle.putSerializable("arch_list", arch_list);
        			
        			Intent intent = new Intent(EmailActivity.this, EmailAllActivity.class);
            		intent.putExtras(bundle);
            		
            		startActivity(intent);
        		}
        	}
        });
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.email_activity_menu, menu);
        return true;
    }

    /* only one menu item, Main Mene */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_email_main)
        {
        	// pass data to next activity
        	bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(EmailActivity.this, MainActivity.class);
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
