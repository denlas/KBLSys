package com.HiveSys.dashboard.view.search;
import org.apache.solr.common.SolrDocumentList;

import com.HiveSys.core.SolrConnection;
import com.HiveSys.dashboard.layout.SearchLayout;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;


public class SearchView extends SearchLayout implements View{
	public static final String NAME = "search";
	
	public SearchView()
	{
		this.tfSearch.setImmediate(true);
		
		// handle enter key shortcut
		ShortcutListener shortcut = new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target){
				SubmitQuery();
			}
		};
		
		this.tfSearch.addShortcutListener(shortcut);
	}
	
	public void SubmitQuery()
	{
		Notification.show(this.tfSearch.getValue(), Notification.Type.HUMANIZED_MESSAGE);
		
		// query the solr
		SolrDocumentList doclist = SolrConnection.getDefault().query(tfSearch.getValue());
		
		System.out.println(doclist.size());
		for (int i=0; i<doclist.size();i++)
		{
			this.vertlayout.addComponent(new Label(doclist.get(i).getFieldValue("resourcename").toString()));
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
