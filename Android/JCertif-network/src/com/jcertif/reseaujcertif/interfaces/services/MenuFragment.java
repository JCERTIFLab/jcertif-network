package com.jcertif.reseaujcertif.interfaces.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.interfaces.ihm.Tchats;
import com.jcertif.reseaujcertif.interfaces.ihm.Events;
import com.jcertif.reseaujcertif.interfaces.ihm.FindCars;
import com.jcertif.reseaujcertif.interfaces.ihm.Forums;
import com.jcertif.reseaujcertif.interfaces.ihm.Home;
import com.jcertif.reseaujcertif.interfaces.ihm.Network;
import com.jcertif.reseaujcertif.interfaces.ihm.NewsFragement;
import com.jcertif.reseaujcertif.interfaces.ihm.Photos;
import com.jcertif.reseaujcertif.interfaces.ihm.Principale;
import com.jcertif.reseaujcertif.interfaces.ihm.Videos;

public class MenuFragment extends ListFragment {

	private Fragment newContent;
	public String[] menuPrincipale = { "Acceuil", "Actualités", "Evènement", "Forum", "Tchat", "Rechercher un endroit", "Galerie photos", "Galerie vidéos", "Réseau JCertif"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new IconicAdapter());
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		newContent = null;
		Intent intent = null;
		switch (position) {
		case 0:
			intent = new Intent(getActivity(), Principale.class);
        	startActivityForResult(intent, 1000);
        	getActivity().overridePendingTransition(R.anim.incoming, R.anim.outgoing);
        	getActivity().finish();
			break;
		case 1:
			newContent = new NewsFragement();
			break;
		case 2:
			newContent = new Events();
			break;
		case 3:
			newContent = new Forums();
			break;
		case 4:
			newContent = new Tchats();
			break;
		case 5:
			intent = new Intent(getActivity(), FindCars.class);
        	startActivityForResult(intent, 1000);
        	getActivity().finish();
			break;
		case 6:
			newContent = new Photos();
			break;
		case 7:
			newContent = new Videos();
			break;
		case 8:
			intent = new Intent(getActivity(), Network.class);
        	startActivityForResult(intent, 1000);
        	getActivity().finish();
			break;
		}		
		
		if (newContent != null)
			switchFragment(newContent);
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof Home) {
			Home fca = (Home) getActivity();
			fca.getSupportActionBar().removeAllTabs();
			fca.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			fca.switchContent(fragment);
		}
	}
	
	@SuppressWarnings("rawtypes")
	class IconicAdapter extends ArrayAdapter {
		@SuppressWarnings("unchecked")
		IconicAdapter() {
			super(getActivity(), R.layout.single_item, menuPrincipale);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if (row==null) {                          
				LayoutInflater inflater=getActivity().getLayoutInflater();
				row=inflater.inflate(R.layout.single_item, parent, false);
			}

			TextView titre = (TextView) row.findViewById(R.id.title);
			titre.setText(menuPrincipale[position]);
			return (row);
		}
	}

}
