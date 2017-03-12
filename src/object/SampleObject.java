package object;

import java.util.Random;

/**
 * SampleObject
 * <p>
 * A Sample Object with sample data to be used in this sample app.
 * 
 * @author William Lake
 *
 */
public class SampleObject
{
	private Random rn;

	private String objectId;
	private String sampleDataString;
	
	/**
	 * Instantiates what is required for this object, and fills the object
	 * with sample data.
	 */
	public SampleObject()
	{
		rn = new Random();

		objectId = "";
		sampleDataString = "";

		generateObjectId();
		generateSampleData();
	}
	
	/**
	 * Generates a sample id for the object.
	 */
	private void generateObjectId()
	{
		for (int i = 0; i < 4; i++)
		{
			objectId += String.valueOf(rn.nextInt(9) + 1);
		}

		objectId += String.valueOf((char) rn.nextInt(94) + 33);
	}
	
	/**
	 * Generates a sample piece of data (String) for the object.
	 */
	private void generateSampleData()
	{
		for (int i = 0; i < 10; i++)
		{
			sampleDataString += String.valueOf((char) (rn.nextInt(26) + 65));
		}
	}
	
	public String getObjectId()
	{
		return objectId;
	}

	public String getSampleDataString()
	{
		return sampleDataString;
	}

}
