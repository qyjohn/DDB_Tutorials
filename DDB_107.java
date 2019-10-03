import java.util.*;
import com.amazonaws.regions.*;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

public class DDB_107
{
        public AmazonDynamoDBClient client;
	public String tableName = "java-demo";

	public void test(int type)
	{
		if (type == 1)
		{
	                client = new AmazonDynamoDBClient();
			client.configureRegion(Regions.AP_SOUTHEAST_2);
			for (int i=0; i<=10; i++)
			{
				put(client);
			}
		}
		else
		{
			for (int i=0; i<=10; i++)
			{
				client = new AmazonDynamoDBClient();
				client.configureRegion(Regions.AP_SOUTHEAST_2);
				put(client);
			}
		}
	}

	public void put(AmazonDynamoDBClient client)
	{
		try
		{
			Random r = new Random();
			String hash = UUID.randomUUID().toString();
			int sort = r.nextInt(1000000);
			String value = hash + "-" + sort;

			HashMap<String, AttributeValue> item = new HashMap<String, AttributeValue>();
			item.put("hash", new AttributeValue(hash));
			item.put("sort", new AttributeValue().withN(Integer.toString(sort)));
			item.put("val", new AttributeValue(value));
			PutItemRequest putItemRequest = new PutItemRequest().withTableName(tableName).withItem(item);

			long t0 = System.currentTimeMillis();
                        client.putItem(putItemRequest);
			long t1 = System.currentTimeMillis();
			long latency = t1 - t0;
			System.out.println("Latency: " + latency);
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

        public static void main(String[] args)
        {
		try
		{
			int type = Integer.parseInt(args[0]);
			DDB_107 test = new DDB_107();
			test.test(type);
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        }
}

