package ca.ualberta.todolistapp;

import java.io.Serializable;

public class TodoItem implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108287748641899647L;

	private String item;
	private String display;
	private int is_checked;
	private int is_archived;
	
	public TodoItem(String str)
	{
		this.item = str;
		this.display = this.item;
		this.is_checked = 0;
		this.is_archived = 0;
	}

	public int checked()
	{
		return this.is_checked;
	}
	
	public int archived()
	{
		return this.is_archived;
	}
	
	public void set_checked(int i)
	{
		this.is_checked = i;
		update_display();
	}
	
	public void set_archived(int i)
	{
		this.is_archived = i;
		update_display();
	}
	
	public String toString()
	{
		return this.display;
	}
	
	private void update_display()
	{
		this.display = this.item;
		if (is_checked == 1)
		{
			this.display += "\nCHECKED";
			if (is_archived == 1)
			{
				this.display += " and ARCHIVED";
			}
		}
		else
		{
			if (is_archived == 1)
			{
				this.display += "\nARCHIVED";
			
			}
		}
	}
}
