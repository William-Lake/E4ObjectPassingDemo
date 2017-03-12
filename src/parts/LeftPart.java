
package parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import object.SampleObject;

/**
 * LeftPart
 * <p>
 * Builds an MPart and it's associated UI. Since this Part contains a table,
 * there is an additional method {@link LeftPart#fillTable()} for filling it with {@code SampleObject}s.
 * <p>
 * Uses IEventBroker to send the {@code SampleObject} to the other parts.
 * Doing so requires adding a few things to the MANIFEST.MF:
 * <p>
 * <ol>
 * <li>{@code org.eclipse.osgi.services}</li>
 * <li>{@code org.eclipse.e4.core.services}</li>
 * <li>{@code org.eclipse.e4.core.di.extensions}</li>
 * </ol>
 * <p>
 * These can be added by:
 * <ol>
 * <li>Navigating to your project hierarchy, opening the 'META-INF' folder and opening the 'MANIFEST.MF'.</li>
 * <li>In the opened MANIFEST.MF, choose the 'Dependencies' tab at the bottom.</li>
 * <li>In the Dependencies tab, navigate to the 'Required Plug-ins' section and click 'Add'.</li>
 * </ol>
 * <p>
 * When {@code post}ing via the IEventBroker, use the following format for your 'topic' String:
 * <p>
 * <ul>
 * <li>"topic/name"</li>
 * <li>In the most basic form, any String you wish can be substituted for either 'topic' or 'name' but a String
 * on either side AND the '/' are REQUIRED.**</li>
 * <li>NOTE: The String built here will be used in the receiver to identify what to listen for.</li>
 * <li>**Of course there are always exceptions to rules, so if you'd like
 * to learn more follow the links provided at the bottom of this Javadoc.</li>
 * </ul>
 * <p>
 * @see <a href="http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fe4%2Fcore%2Fservices%2Fevents%2FIEventBroker.html"> Eclipse IEventBroker API</a>
 * @see <a href="https://wiki.eclipse.org/Eclipse4/RCP/Event_Model">Eclipse4/RCP/Event Model</a>
 * @see <a href="http://www.vogella.com/tutorials/Eclipse4EventSystem/article.html">Vogella tutorial: 'Eclipse 4 event system (EventAdmin) - Tutorial'</a>
 * @author William Lake
 *
 */
public class LeftPart
{
	@Inject
	IEventBroker broker;
	
	// ***CONTROLS***
	Table tblObjects;
	Button btnSend;

	// ***VARIABLES***
	private String[] tableColumns = { "Object ID", "Sample Data" };

	private static final int GENTOP = 5;
	private static final int GENLEFT = 5;
	
	private static final int NUMOBJECTS = 5;

	@Inject
	public LeftPart()
	{

	}
	
	/**
	 * Builds the UI for the LeftPart.
	 * 
	 * @param parent
	 * 			The part Composite to build the UI on.
	 */
	@PostConstruct
	public void postConstruct(Composite parent)
	{
		// ***INSTANTIATE CONTROLS***
		Label lblWarning = new Label(parent, SWT.NONE);
		tblObjects = new Table(parent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL);
		tblObjects.setHeaderVisible(true);
		tblObjects.setLinesVisible(true);
		btnSend = new Button(parent, SWT.PUSH);
		
		// ***SET TEXT***
		fillTable();
		btnSend.setText("Send");

		// ***LISTENERS***
		btnSend.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				lblWarning.setText("");
				
				if (tblObjects.getSelection().length > 0)
				{
					SampleObject selectedObject = (SampleObject) tblObjects.getSelection()[0].getData(tblObjects.getSelection()[0].getText());
					broker.post("LeftPartPost/SampleObject", selectedObject);
					
					tblObjects.deselectAll();
				} else 
				{
					lblWarning.setText("Choose an object in the table.");
				}
				parent.layout();
			}
		});
		
		// ***LAYOUT***
		FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		parent.setLayout(layout);

		FormData fd = new FormData();
		fd.top = new FormAttachment(0,GENTOP);
		fd.left = new FormAttachment(0,GENLEFT);
		lblWarning.setLayoutData(fd);
		
		fd = new FormData();
		fd.top = new FormAttachment(lblWarning, GENTOP);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(100, 0);
		fd.height = 70;
		tblObjects.setLayoutData(fd);
		
		fd = new FormData();
		fd.top = new FormAttachment(tblObjects, GENTOP);
		fd.right = new FormAttachment(100,-GENLEFT);
		btnSend.setLayoutData(fd);

	}
	
	/**
	 * Fills the table object with {@code SampleObject}s. 
	 * Builds the {@code TableColumn}s first, then creates the {@code SampleObject}s,
	 * and finally {@code pack()}s everything up.
	 * The {@code NUMOBJECTS} constant dictates how many {@code SampleObject}s to build.
	 */
	private void fillTable()
	{
		for (int i = 0; i < tableColumns.length; i++)
		{
			TableColumn tblCol = new TableColumn(tblObjects, SWT.NULL);
			tblCol.setText(tableColumns[i]);
		}

		for (int i = 0; i < NUMOBJECTS; i++)
		{
			SampleObject sampleObject = new SampleObject();

			TableItem tableItem = new TableItem(tblObjects, SWT.NULL);
			tableItem.setText(sampleObject.getObjectId());
			tableItem.setText(0, sampleObject.getObjectId());
			tableItem.setText(1, sampleObject.getSampleDataString());
			tableItem.setData(sampleObject.getObjectId(), sampleObject);
		}

		for (int i = 0; i < tableColumns.length; i++)
		{
			tblObjects.getColumn(i).pack();
		}
	}
}