package com.HiveSys.dashboard.layout;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { … }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class UploadLayout extends AbsoluteLayout {
	protected com.vaadin.ui.TextField title;
	protected com.vaadin.ui.TextField author;
	protected com.vaadin.ui.TextField fileType;
	protected com.vaadin.ui.DateField dateCreated;
	protected com.vaadin.ui.DateField dateModified;
	protected com.vaadin.ui.DateField dateUploaded;
	protected com.vaadin.ui.Upload boxUpload;

	public UploadLayout() {
		Design.read(this);
	}
}
