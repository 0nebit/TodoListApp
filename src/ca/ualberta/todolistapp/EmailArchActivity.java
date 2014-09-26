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

public class EmailArchActivity extends Activity
{
	private ArrayList<TodoItem> curr_list;
	private ArrayList<TodoItem> arch_list;
	
	private Bundle bundle;

	private EditText editText0;
	private EditText editText1;
	private ListView list_view;
	private ArrayAdapter<TodoItem> adapter;
	private Button send_button;
	
	private SparseBooleanArray selected;
	private ArrayList<Integer> positions;
	
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_arch);    
        
        list_view = (ListView) findViewById(R.id.email_arch_list);
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
		
		editText0 = (EditText) findViewById(R.id.edit_email_addr2);
		editText1 = (EditText) findViewById(R.id.edit_e_title2);

		send_button = (Button) findViewById(R.id.email_arch_button_send0);
		
		positions = new ArrayList<Integer>();
        adapter = new ArrayAdapter<TodoItem>(this,
                android.R.layout.simple_list_item_multiple_choice, arch_list);
        list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
            		update_positions();
            		if (positions.size() == 0)
            		{
                		Context context = getApplicationContext();
                		String msg_EmptyItems = "Error: no item(s) selected.";

                		Toast toast = Toast.makeText(context, msg_EmptyItems, Toast.LENGTH_SHORT);
                		toast.show();
            		}
            		else
            		{
            			String content = new String();
            		
            			content += "Your archived TODO items:\n\n";
            			
            			for (int pos : positions)
            			{
            				content += arch_list.get(pos).get_display()+"\n";
            			}
            			
            			content += "\nEnd of message.";
            			// send email
                		
                		Intent email_intent = new Intent(Intent.ACTION_SEND);
            			
                		email_intent.setType("message/rfc822");
                		email_intent.putExtra(Intent.EXTRA_EMAIL, new String[] {editText0.getText().toString()});
                		email_intent.putExtra(Intent.EXTRA_SUBJECT, editText1.getText().toString());
                		email_intent.putExtra(Intent.EXTRA_TEXT, content);
                		
                		Toast toast = Toast.makeText(EmailArchActivity.this, "You have no email clients installed.", Toast.LENGTH_SHORT);
                		
                		try {
                		    startActivity(Intent.createChooser(email_intent, "Send mail..."));
                		} catch (android.content.ActivityNotFoundException ex) {
                		    toast.show();
                		}

                		editText0.setText("");
                		editText1.setText("");
            		}
            	}
            }
        });
		
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.email_arch_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_email_arch_main)
        {
        	// pass data to next activity
        	bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(EmailArchActivity.this, MainActivity.class);
        	intent.putExtras(bundle);
        	
    		startActivity(intent);
        }
        else if (id == R.id.menu_email_arch_cancel)
        {
        	editText0.setText("");
        	editText1.setText("");
        	
        	bundle = new Bundle();

        	bundle.putSerializable("curr_list", curr_list);
        	bundle.putSerializable("arch_list", arch_list);
        	
    		Intent intent = new Intent(EmailArchActivity.this, EmailActivity.class);
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