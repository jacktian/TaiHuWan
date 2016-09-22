package com.gloria.hbh.data.forum;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gloria.hbh.data.app.SubTabInfo.SubTabInfoTypeConstants;
import com.gloria.hbh.data.forum.BaseData;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.Methods;
import com.gloria.hbh.util.TextUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PostInfo extends BaseData{
	
//	public class PostActionTypeConstants {
//		public static final String POSTACTION_LIST = "list"; //栏目列表
//		public static final String POSTACTION_ADD = "favor"; //栏目添加
//		public static final String POSTACTION_DELETE = "del"; //栏目删除
//	}
	
	public class PostActionTypeConstants {
		public static final int POSTACTION_LIST = -1; //帖子列表
		public static final int POSTACTION_ADD = -2; //帖子添加
		public static final int POSTACTION_DELETE = -3; //帖子删除
	}

	private PostBasic postBasic;  //楼主
	private ArrayList<PostBasic> replyList; //回复列表
	
	public PostBasic getPostBasic() {
		if(postBasic == null){
			postBasic = new PostBasic();
		}
		return postBasic;
	}
	
	public ArrayList<PostBasic> getReplyList() {
		if(replyList == null){
			replyList = new ArrayList<PostBasic>(1);
		}
		return replyList;
	}
	
	public static  PostInfo getPostBasicFromJsonObject(JsonObject jsonObject){
		PostInfo postInfo =new PostInfo();
		postInfo.getPostBasic().setSubject(JsonMethed.getJsonString(jsonObject.get("subject")));		
		postInfo.getPostBasic().setFid(JsonMethed.getJsonString(jsonObject.get("fid")));
		postInfo.getPostBasic().setTid(JsonMethed.getJsonString(jsonObject.get("tid")));
		postInfo.getPostBasic().setAuthor(JsonMethed.getJsonString(jsonObject.get("author")));
		postInfo.getPostBasic().setAuthorId(JsonMethed.getJsonString(jsonObject.get("authorid")));
		postInfo.getPostBasic().setPostdate(JsonMethed.getJsonString(jsonObject.get("postdate")));
		postInfo.getPostBasic().setLastPost(JsonMethed.getJsonString(jsonObject.get("lastpost")));
		postInfo.getPostBasic().setHits(JsonMethed.getJsonInt(jsonObject.get("hits")));
		postInfo.getPostBasic().setReplies(JsonMethed.getJsonInt(jsonObject.get("replies")));
		return postInfo;
	}
	
	public static  PostInfo getPostBasicFromJsonObject(int type,JsonObject jsonObject){
		PostInfo postInfo =new PostInfo();
		try {
			if(type == SubTabInfoTypeConstants.SUBCATE_ACTIVITY){
				postInfo.getPostBasic().setSubject(JsonMethed.getJsonString(jsonObject.get("title")));
				postInfo.getPostBasic().setUrl(JsonMethed.getJsonString(jsonObject.get("url")));
				postInfo.getPostBasic().setTid(JsonMethed.getJsonString(jsonObject.get("tid")));
				postInfo.getPostBasic().setContent(JsonMethed.getJsonString(jsonObject.get("depict")));
				postInfo.getPostBasic().setMicon(JsonMethed.getJsonString(jsonObject.get("logo")));
				postInfo.getPostBasic().setAuthor(JsonMethed.getJsonString(jsonObject.get("address")));
				postInfo.getPostBasic().setPostdate(JsonMethed.getJsonString(jsonObject.get("stime")));
				postInfo.getPostBasic().setLastPost(JsonMethed.getJsonString(jsonObject.get("etime")));
				postInfo.getPostBasic().setType(JsonMethed.getJsonString(jsonObject.get("status")));
			}else{
				if(JsonMethed.getJsonString(jsonObject.get("author")) == null 
						|| JsonMethed.getJsonString(jsonObject.get("author")).equals("")){
					postInfo.getPostBasic().setAuthor(JsonMethed.getJsonString(jsonObject.get("username")));
				}else{
					postInfo.getPostBasic().setAuthor(JsonMethed.getJsonString(jsonObject.get("author")));
				}
				postInfo.getPostBasic().setAuthorId(JsonMethed.getJsonString(jsonObject.get("authorid")));
				postInfo.getPostBasic().setId(JsonMethed.getJsonString(jsonObject.get("id")));
				postInfo.getPostBasic().setFid(JsonMethed.getJsonString(jsonObject.get("fid")));
				postInfo.getPostBasic().setHits(JsonMethed.getJsonInt(jsonObject.get("hits")));
				postInfo.getPostBasic().setIfupload(JsonMethed.getJsonString(jsonObject.get("ifupload")));
				postInfo.getPostBasic().setLastPost(JsonMethed.getJsonString(jsonObject.get("lastpost")));
				postInfo.getPostBasic().setPostdate(JsonMethed.getJsonString(jsonObject.get("postdate")));
				postInfo.getPostBasic().setReplies(JsonMethed.getJsonInt(jsonObject.get("replies")));
				postInfo.getPostBasic().setSubject(JsonMethed.getJsonString(jsonObject.get("subject")));
				postInfo.getPostBasic().setTid(JsonMethed.getJsonString(jsonObject.get("tid")));
				postInfo.getPostBasic().setType(JsonMethed.getJsonString(jsonObject.get("type")));
				postInfo.getPostBasic().setUrl(JsonMethed.getJsonString(jsonObject.get("attachurl")));
			}
		}catch (JsonParseException e) {
		} catch (Exception e) {
		}
		return postInfo;
	}

	public static PostInfo getPostInfoFromJsonObject(JsonObject jsonObject) {
		PostInfo postInfo =new PostInfo();
		try {
			JsonArray jsonArray_page = JsonMethed.getJsonArray(jsonObject.get("page"));
			if(jsonArray_page != null){
				getPageFromJson(jsonArray_page);
			}
			JsonArray jsonArray_list = JsonMethed.getJsonArray(jsonObject.get("list"));
			if(jsonArray_list != null){
				for(int i = 0; i < jsonArray_list.size(); i++){
					JsonObject jsonObjec_list = JsonMethed.getJsonObject(jsonArray_list.get(i));
					postInfo.getReplyList().add(PostBasic.getFromJsonObject(jsonObjec_list));
				}
			}
		}catch (JsonParseException e) {
		}catch (Exception e) {
		}
		return postInfo;
	}

	public static PostInfo getPostInfoFromElement(Element url) {
		if(url == null){
			return null;
		}
		PostInfo postInfo =new PostInfo();
		Element loc = (Element)url.getElementsByTagName("loc").item(0);    
		postInfo.getPostBasic().setUrl(loc.getFirstChild().getNodeValue());
		
		NodeList data = url.getElementsByTagName("data");    
		//得到data
		
		Element display = (Element)data.item(0);  
//		Element website = (Element)display.getElementsByTagName("website").item(0);    
//		Element siteurl = (Element)display.getElementsByTagName("siteurl").item(0);
//		Element city = (Element)display.getElementsByTagName("city").item(0);    
//		Element type = (Element)display.getElementsByTagName("type").item(0);  
//		Element signid = (Element)display.getElementsByTagName("signid").item(0);  
		Element title = (Element)display.getElementsByTagName("title").item(0);  
		Element image = (Element)display.getElementsByTagName("image").item(0);  
		Element startTime = (Element)display.getElementsByTagName("startTime").item(0);  
		Element endTime = (Element)display.getElementsByTagName("endTime").item(0);  
		Element value = (Element)display.getElementsByTagName("value").item(0);  
		Element price = (Element)display.getElementsByTagName("price").item(0);  
//		Element rebate = (Element)display.getElementsByTagName("rebate").item(0);  
		Element bought = (Element)display.getElementsByTagName("bought").item(0);  
		//存储
		postInfo.getPostBasic().setSubject(title.getFirstChild().getNodeValue());
		postInfo.getPostBasic().setMicon(image.getFirstChild().getNodeValue());
		postInfo.getPostBasic().setPostdate(startTime.getFirstChild().getNodeValue());
		long etime = Long.parseLong(endTime.getFirstChild().getNodeValue())*1000;
		postInfo.getPostBasic().setLastPost("距离结束的时间还剩下:" +TextUtils.calTime(etime));
		postInfo.getPostBasic().setContent("原价:"+value.getFirstChild().getNodeValue()
												+"     现价:"+price.getFirstChild().getNodeValue());
		postInfo.getPostBasic().setAuthor("已购买:"+bought.getFirstChild().getNodeValue()+"件");
		return postInfo;
	}

}
