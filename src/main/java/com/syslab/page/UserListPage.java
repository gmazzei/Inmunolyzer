package com.syslab.page;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.entity.User;
import com.syslab.service.UserService;

public class UserListPage extends BasePage {
	
	@SpringBean
	private UserService userService;
		
	public UserListPage() {
		
		final WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);
		
		IModel<List<User>> model = new LoadableDetachableModel<List<User>>() {

			@Override
			protected List<User> load() {
				return userService.getAll();
			}
		};
		
		ListView<User> listView = new ListView<User>("listView", model) {

			@Override
			protected void populateItem(ListItem<User> item) {
				final User user = item.getModelObject();
				
				Label username = new Label("username", user.getUsername());
				item.add(username);
				
				AjaxLink<Void> showButton = new AjaxLink<Void>("showButton") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters params = new PageParameters();
						params.add("entityId", user.getId());
						setResponsePage(ShowUserPage.class, params);
					}
				};
				
				AjaxLink<Void> editButton = new AjaxLink<Void>("editButton") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters params = new PageParameters();
						params.add("entityId", user.getId());
						setResponsePage(CreateUserPage.class, params);
					}
				};
				
				AjaxLink<Void> deleteButton = new AjaxLink<Void>("deleteButton") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						userService.delete(user);
						target.add(container);
					}
				};
				
				item.add(showButton);
				item.add(editButton);
				item.add(deleteButton);
			}
		};
		
		container.add(listView);
		
	}
	
}
