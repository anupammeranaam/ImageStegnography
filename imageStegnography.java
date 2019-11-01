import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class imageStegnography
{
	String strToBit(String s)
	{
		byte[] bytes = s.getBytes();
		String binary = new String();
		for (byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				binary += ((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
		}
		return binary;
	}

	public static void main(String[] args)throws IOException
	{
		BufferedImage img = null;
		File f = null;

		String message = "hello";
		imageStegnography iobj = new imageStegnography();

		String binary = iobj.strToBit(message);
		char code[] = binary.toCharArray();

		try
		{
			f = new File("D:/movies/test.bmp");
			img = ImageIO.read(f);
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
		}

		int width = img.getWidth();
		int height = img.getHeight();
		int xy = 0;

		for(int y = 0 ; y < height ; y++)
		{
			for(int x = 0 ; x < width ; x++)
			{
				int p = img.getRGB(x, y);
				int a = (p>>24) & 0xff; 
				int r = (p>>16) & 0xff; 
				int g = (p>>8) & 0xff; 
				int b = (p) & 0xff; 

				String astr = new String(Integer.toBinaryString(a));
				String rstr = new String(Integer.toBinaryString(r));
				String gstr = new String(Integer.toBinaryString(g));
				String bstr = new String(Integer.toBinaryString(b));
				if(xy!=code.length-4)
				{
					String first = String.valueOf(code[xy++]);
					String second = String.valueOf(code[xy++]);
					String third = String.valueOf(code[xy++]);
					String last = String.valueOf(code[xy++]);
					
					String astr1,rstr1,gstr1,bstr1;

					if(!astr.endsWith(first))
					{
						astr1 = astr.copyValueOf(astr.toCharArray(), 0, astr.length()-1);
						astr1 += first;
					}
					else
						astr1 = astr;

					if(!rstr.endsWith(second))
					{
						rstr1 = rstr.copyValueOf(rstr.toCharArray(), 0, rstr.length()-1);
						rstr1 += second;
					}
					else
						rstr1 = rstr;

					if(!gstr.endsWith(third))
					{
						gstr1 = gstr.copyValueOf(gstr.toCharArray(), 0, gstr.length()-1);
						gstr1 += third;
					}
					else
						gstr1 = gstr;

					if(!bstr.endsWith(last))
					{
						bstr1 = bstr.copyValueOf(bstr.toCharArray(), 0, bstr.length()-1);
						bstr1 += last;
					}
					else
						bstr1 = bstr;
				
					a = Integer.parseInt(astr1, 2);
					r = Integer.parseInt(rstr1, 2);
					g = Integer.parseInt(gstr1, 2);
					b = Integer.parseInt(bstr1, 2);
				}
				
				p = (a<<24)|(r<<16)|(g<<8)|(b);
				img.setRGB(x, y, p);
			}
		}

		try
		{ 
			 f = new File("D:/movies/test.bmp"); 
			 ImageIO.write(img, "bmp", f); 
		} 
		catch(Exception e) 
		{ 
			System.out.println(e); 
		} 
	}

}
