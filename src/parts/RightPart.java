
package parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import object.SampleObject;

/**
 * RightPart
 * <p>
 * Builds an MPart and it's associated UI. Primarily, this part receives an object and
 * displays it's data, which happens via the {@link RightPart#receiveEvent(SampleObject)} method.
 * <p>
 * Uses {@link RightPart#broker} IEventBroker to receive events.
 * <p>
 * @see <a href="http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fe4%2Fcore%2Fservices%2Fevents%2FIEventBroker.html"> Eclipse IEventBroker API</a>
 * @see <a href="https://wiki.eclipse.org/Eclipse4/RCP/Event_Model">Eclipse4/RCP/Event Model</a>
 * @see <a href="http://www.vogella.com/tutorials/Eclipse4EventSystem/article.html">Vogella tutorial: 'Eclipse 4 event system (EventAdmin) - Tutorial'</a>
 * @author William Lake
 *
 */
public class RightPart
{
	@Inject
	IEventBroker broker;
	
	// ***CONTROLS***
	Composite cmpContainer;
	Label lblObjectToString;
	Label lblObjectId;
	Label lblObjectSampleData;

	// ***VARIABLES***
	private static final int GENTOP = 5;
	
	private SampleObject sampleObject;

	@Inject
	public RightPart()
	{
		
	}
	
	/**
	 * Builds the UI for the RightPart.
	 * 
	 * @param parent
	 * 			The part Composite to build the UI on.
	 */
	@PostConstruct
	public void postConstruct(Composite parent)
	{
		// ***INSTANTIATE CONTROLS***
		cmpContainer = new Composite(parent, SWT.NONE);
		Label lblObjectData = new Label(parent, SWT.NONE);
		lblObjectToString = new Label(cmpContainer, SWT.NONE);
		lblObjectId = new Label(cmpContainer, SWT.NONE);
		lblObjectSampleData = new Label(cmpContainer, SWT.NONE);

		// ***SET TEXT***
		lblObjectData.setText("Object Data");

		// ***LAYOUT***
		FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		parent.setLayout(layout);
		cmpContainer.setLayout(layout);

		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(100, 0);
		fd.bottom = new FormAttachment(100, 0);
		cmpContainer.setLayoutData(fd);

		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.left = new FormAttachment(0, 0);
		lblObjectData.setLayoutData(fd);

		fd = new FormData();
		fd.top = new FormAttachment(lblObjectData, GENTOP);
		fd.left = new FormAttachment(0, 0);
		lblObjectToString.setLayoutData(fd);

		fd = new FormData();
		fd.top = new FormAttachment(lblObjectToString, GENTOP);
		fd.left = new FormAttachment(0, 0);
		lblObjectId.setLayoutData(fd);

		fd = new FormData();
		fd.top = new FormAttachment(lblObjectId, GENTOP);
		fd.left = new FormAttachment(0, 0);
		lblObjectSampleData.setLayoutData(fd);
		
	}
	
	/**
	 * Receives an event of type {@code SampleObject}, based on the {@code @UIEventTopic} topic string,
	 * saves it and sets it's data to the associated Labels in this part.
	 * 
	 * @param sampleObject
	 * 			The {@code SampleObject} to be used.
	 */
	@Inject
	@Optional
	public void receiveEvent(@UIEventTopic("LeftPartPost/SampleObject") SampleObject sampleObject)
	{
		this.sampleObject = sampleObject;
		
		lblObjectToString.setText(sampleObject.toString());
		lblObjectId.setText(sampleObject.getObjectId());
		lblObjectSampleData.setText(sampleObject.getSampleDataString());

		cmpContainer.layout();
	}
}