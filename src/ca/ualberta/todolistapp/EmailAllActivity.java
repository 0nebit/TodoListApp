package ca.ualberta.todolistapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/* This activity handles emailing all TODOs.
 * The user enters the recipient address and email subject. The items are viewable through a ListView.
 * The task is continued through a "send"
 * button and the built-in email client is used to handle the actual emailing.
 */
public class EmailAllActivity extends Activity
{
	private ArrayList<TodoItem> curr_list;
	private ArrayList<TodoItem> arch_list;
	private ArrayList<TodoItem> display_list;
	
	private Bundle bundle;

	private EditText editText0; // takes address
	private EditText editText1; // takes email subject
	private ListView list_view;
	private ArrayAdapter<TodoItem> adapter;
	private Button send_button;
	
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_all);    
        
        list_view = (ListView) findViewById(R.id.email_all_list);
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
		
		// dislay_list unique to this activity
		// stores a temporary list of all TODOs
		display_list = new ArrayList<TodoItem>();
		display_list.addAll(curr_list);
		display_list.addAll(arch_list);

		editText0 = (EditText) findViewById(R.id.edit_email_addr3);
		editText1 = (EditText) findViewById(R.id.edit_e_title3);

		send_button = (Button) findViewById(R.id.email_all_button_send0);
		
		// adapts to display_list which stores all TODO items
		// uses built-in layout simple_list_item_multiple_choice
        adapter = new ArrayAdapter<TodoItem>(this,
                android.R.layout.simple_list_item_1, display_list);
        list_view.setAdapter(adapter);
        
        send_button.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View arg0)
        	{
            	String addr = editText0.getText().toString();
            	String title = editText1.getText().toString();
            	
            	if (addr.equals("") && title.equals(""))
            	{
            		Context context = getApplicationContext();
            		String msg_EmptyInput = "Error: empty input.";

            		Toast toast = Toast.makeText(context, msg_EmptyInput, Toast.LENGTH_SHORT);
            		toast.show();
            	}
            	else if (addr.equals(""))
            	{
            		Context context = getApplicationContext();
            		String msg_EmptyAddr = "Error: empty recipient address.";

            		Toast toast = Toast.makeText(context, msg_EmptyAddr, Toast.LENGTH_SHORT);
            		toast.show();
            	}
            	else if (title.equals(""))
            	{
            		Context context = getApplicationContext();
            		String msg_EmptyTitle = "Error: empty title.";

            		Toast toast = Toast.makeText(context, msg_EmptyTitle, Toast.LENGTH_SHORT);
            		toast.show();            	}
            	else
            	{
            		// if both fields are filled
            		String content = new String();
            		
        			// format content of message
            		content += "All your TODO items:\n\n";
            			
            		for (TodoItem item : display_list)
            		{
            			content += item.get_display()+"\n";
            		}
            		
            		content += "\nEnd of message.";
            		// send email
                	
                	Intent email_intent = new Intent(Intent.ACTION_SEND);
            		
                	email_intent.setType("message/rfc822");
            		// passes address string to intent's extra email address data
                	email_intent.putExtra(Intent.EXTRA_EMAIL, new String[] {editText0.getText().toString()});
            		// passes subject name to intent's extra email subject data
                	email_intent.putExtra(Intent.EXTRA_SUBJECT, editText1.getText().toString());
            		// passes formatted content to intent's extra content area
                	email_intent.putExtra(Intent.EXTRA_TEXT, content);
                	
                	Toast toast = Toast.makeText(EmailAllActivity.this, "You have no email clients installed.", Toast.LENGTH_SHORT);
                	
            		// try starting intent to start email client
                	// raises exception if no email client is installed in host machine
                	try {
                	    startActivity(Intent.createChooser(email_intent, "Send mail..."));
                	} catch (android.content.ActivityNotFoundException ex) {
                	    toast.show();
                	}
                	
                	editText0.setText("");
               		editText1.setText("");
            	}
            }
        });
		
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.email_all_activity_menu, menu);
        return true;
    }

    /* two menu items, Main Menu and Cancel
     * Cancel takes the user back to the main emailing portal
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_email_all_main)
        {
        	// pass data to next activity
        	bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(EmailAllActivity.this, MainActivity.class);
        	intent.putExtras(bundle);
        	
    		startActivity(intent);
        }
        else if (id == R.id.menu_email_all_cancel)
        {
        	editText0.setText("");
        	editText1.setText("");
        	
        	bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(EmailAllActivity.this, EmailActivity.class);
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