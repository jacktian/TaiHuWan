package com.gloria.hbh.data.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.res.AssetManager;
import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.data.forum.ExhibitionInfo;
import com.gloria.hbh.handle.PListParser;
import com.google.gson.JsonParseException;

public class LeftSideData
{
    private static LeftSideData instance;
    private ArrayList<ExhibitionInfo> list;
    
    public ArrayList<ExhibitionInfo> getList()
    {
        if(list == null)
        {
            list=new ArrayList<ExhibitionInfo>(1);
        }
        return list;
    }
    public void setList(ArrayList<ExhibitionInfo> list)
    {
        this.list=list;
    }
    @SuppressWarnings("unchecked")
    public static LeftSideData getInstance()
    {
        if(null == instance)
        {
            instance=new LeftSideData();
            InputStream is=null;
            try
            {
                AssetManager assetManager=BaseApplication.getInstance().getApplicationContext().getAssets();
                is=assetManager.open("LeftSideData.plist");
                PListParser parser=new PListParser(is);
                getInfo((HashMap<String,Object>)parser.root);
            }
            catch(JsonParseException e)
            {
            }
            catch(Exception e)
            {
            }
        }
        return instance;
    }
    private static void getInfo(HashMap<String,Object> root)
    {
        getInstance().setList(ExhibitionInfo.getExhibitionInfo((ArrayList<Object>)root.get("Objects")));
    }
    public int getListCount()
    {
        int count=0;
        for(int i=0;i < getInstance().getList().size();i++)
        {
            ExhibitionInfo exhibitionInfo=getInstance().getList().get(i);
            for(int j=0;j < exhibitionInfo.getSublist().size();j++)
            {
                ExhibitionInfo subexhibitionInfo=exhibitionInfo.getSublist().get(j);
                if(subexhibitionInfo.getPoint() != null && !subexhibitionInfo.getPoint().equals(""))
                {
                    count++;
                }
            }
        }
        return count;
    }
    public ExhibitionInfo getExhibitionInfoById(int id)
    {
        int count=0;
        for(int i=0;i < getInstance().getList().size();i++)
        {
            ExhibitionInfo exhibitionInfo=getInstance().getList().get(i);
            for(int j=0;j < exhibitionInfo.getSublist().size();j++)
            {
                ExhibitionInfo subexhibitionInfo=exhibitionInfo.getSublist().get(j);
                if(subexhibitionInfo.getId() == id)
                {
                    return subexhibitionInfo;
                }
            }
        }
        return null;
    }
}
