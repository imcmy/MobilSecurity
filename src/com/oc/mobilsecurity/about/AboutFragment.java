package com.oc.mobilsecurity.about;

import com.oc.mobilsecurity.MainActivity;
import com.oc.mobilsecurity.R;
import com.oc.mobilsecurity.decorator.CustomButton;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AboutFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		MainActivity.FragmentNo = 4;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about, null);
		Button helpButton = (Button) view.findViewById(R.id.aboutHelp);
		// Button aboutButton = (Button) view.findViewById(R.id.aboutUs);
		// Button serviceButton = (Button) view.findViewById(R.id.aboutService);

		helpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity mainActivity = (MainActivity) getActivity();
				Intent intent = new Intent(mainActivity, AboutHelp.class);
				startActivityForResult(intent, 1);
			}
		});
		
		/*serviceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity mainActivity = (MainActivity) getActivity();
				Intent intent = new Intent(mainActivity, PersonInfoFile.class);
				startActivityForResult(intent, 3);
			}
		});*/
		
		CustomButton versionButton = (CustomButton) view.findViewById(R.id.aboutVersion);
		versionButton.setTitleText(getResources().getString(R.string.aboutVersion));
		versionButton.setContentText(getResources().getString(R.string.Version));
		return view;
	}
}
