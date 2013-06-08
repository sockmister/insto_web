package com.example.icreatesecretproject.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icreatesecretproject.R;
 
public class placesListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
 
	public placesListAdapter(Context context, String[] values) {
		super(context, R.layout.fragment_check_places_list, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.fragment_check_places_list, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
 
		// Change icon based on name
		String s = values[position];
 
		System.out.println(s);
 
/*		if (s.equals("WindowsMobile")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else if (s.equals("iOS")) {
			imageView.setImageResource(R.drawable.android_logo);
		} else if (s.equals("Blackberry")) {
			imageView.setImageResource(R.drawable.android_logo);
		} else {
			imageView.setImageResource(R.drawable.android_logo);
		}
 */
		return rowView;
	}
}