package converter.v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader 
{
	public static byte[] getBytesFromFile(File file)
	{
		int offset = 0;
		int byteQuantity = 0;
		byte[] bytes = null;
		try(FileInputStream fileInputStream = new FileInputStream(file))
		{
			bytes = new byte[(int) file.length()];

			while((byteQuantity = fileInputStream.read(bytes, offset,  bytes.length - offset)) > 0)
			{
				offset += byteQuantity;	
			}
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return bytes;
	}
}
