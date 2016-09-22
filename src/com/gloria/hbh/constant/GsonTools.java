package com.gloria.hbh.constant; 
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import com.gloria.hbh.data.app.activitybean;
import com.gloria.hbh.data.app.informationbean;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class GsonTools
{
    public GsonTools()
    {
    }
    
    public static List<activitybean> newsinforad(String jsonData) throws Exception
    {
        ArrayList<activitybean> wxczhomelist = new ArrayList<activitybean>();
        activitybean item;
        JsonReader reader = new JsonReader(new StringReader(jsonData));
        try
        {
            reader.beginArray(); 
            while (reader.hasNext())
            {
                reader.beginObject();
                item = new activitybean(); 
                while (reader.hasNext())
                {
                    String tagName = reader.nextName();
                    if (tagName.equals("content"))
                    {
                        item.setContent(reader.nextString());
                    }
                    else if (tagName.equals("img"))
                    {
                        item.setImg(reader.nextString());
                    } 
                    else if (tagName.equals("time"))
                    {
                        item.setTime(reader.nextString());
                    } 
                    else if (tagName.equals("title"))
                    {
                        item.setTitle(reader.nextString());
                    } 
                    else
                    {
                        reader.skipValue();
                    }
                }
                wxczhomelist.add(item);
                reader.endObject();
            }
            reader.endArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return wxczhomelist;
    }
    
    
    
    public static List<informationbean> newsinfor(String jsonData) throws Exception
    {
        ArrayList<informationbean> wxczhomelist = new ArrayList<informationbean>();
        informationbean item;
        JsonReader reader = new JsonReader(new StringReader(jsonData));
        try
        {
            reader.beginArray(); 
            while (reader.hasNext())
            {
                reader.beginObject();
                item = new informationbean(); 
                while (reader.hasNext())
                {
                    String tagName = reader.nextName();
                    if (tagName.equals("content"))
                    {
                        item.setContent(reader.nextString());
                    }
                    else if (tagName.equals("img"))
                    {
                        item.setImg(reader.nextString());
                    } 
                    else if (tagName.equals("time"))
                    {
                        item.setTime(reader.nextString());
                    } 
                    else if (tagName.equals("title"))
                    {
                        item.setTitle(reader.nextString());
                    } 
                    else
                    {
                        reader.skipValue();
                    }
                }
                wxczhomelist.add(item);
                reader.endObject();
            }
            reader.endArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return wxczhomelist;
    }
    
    
}
 