package com.gloria.hbh.data.forum;

import java.util.ArrayList;

import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.Methods;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/**
 * ����Ԫ�� 
 * @author gejian
 *
 */
public class PostBasic {
	
	String id=""; // �����ղ�id
	
	String tid=""; // ����id
	String fid=""; // 
	String subject=""; // ���ӱ���
	String author=""; // ��������
	String authorid=""; // ��������
	String postdate=""; // ����ʱ�� 
	String type="";  //��������
	String url="";  //����
	int  replies=0;   //������ �� �ظ���
	int  hits=0;	//������ �� �����
	String ifupload =""; //�Ƿ���ͼƬ�򸽼�
	String lastpost=""; // ������ʱ�� 
	String pid = "";  //¥��ظ���id
	String blockquote = ""; // ��������
	
	public String getBlockquote() {
		return blockquote;
	}

	public void setBlockquote(String blockquote) {
		this.blockquote = blockquote;
	}

	String content = ""; // ��������
	int lou = 0; // ¥
	String micon = ""; // ͷ��
	ArrayList<ImageInfo> attachments = null; // ����ͼƬ�б�
	ArrayList<String> imgLists = null; // ����ͼƬ�б�
	
	public ArrayList<String> getImgLists() {
		if(imgLists == null){
			imgLists = new ArrayList<String>();
			for(ImageInfo imgInfo:attachments){
				imgLists.add(imgInfo.getUrl());
			}
		}
		return imgLists;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLou() {
		return lou;
	}

	public void setLou(int lou) {
		this.lou = lou;
	}

	public String getMicon() {
		return micon;
	}

	public void setMicon(String micon) {
		this.micon = micon;
	}

	public ArrayList<ImageInfo> getAttachments() {
		if(attachments == null){
			attachments = new ArrayList<ImageInfo>();
		}
		return attachments;
	}

	public String getAuthorId() {
		return authorid;
	}

	public void setAuthorId(String authorid) {
		this.authorid = authorid;
	}

	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid){
		this.tid = tid;
	}

	public String getFid() {
		return fid;
	}
	
	public void setFid(String fid){
		this.fid = fid;
	}

	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public void setType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public void setUrl(String url){
		if(url != null && !url.contains("http://")){
			url = BaseConfig.url + url;
		}
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public void setIfupload(String ifupload){
		this.ifupload = ifupload;
	}

	public String getIfupload() {
		return ifupload;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public static  PostBasic getFromJsonObject(JsonObject jsonObject){
		PostBasic postBasic=new PostBasic();
		String content = "";
		String newcontentString = "";
		ArrayList<String> contentUrlList = null;
		try {
			postBasic.setAuthor(JsonMethed.getJsonString(jsonObject.get("author")));
			postBasic.setAuthorId(JsonMethed.getJsonString(jsonObject.get("authorid")));
			
			content = JsonMethed.getJsonString(jsonObject.get("content"));
			newcontentString = Methods.setNewContent(content);    //�滻����
			contentUrlList = Methods.getContentPicUrlList(content);  //��ȡcontent�е�ͼƬ
			
			postBasic.setContent(newcontentString);
			postBasic.setId(JsonMethed.getJsonString(jsonObject.get("id")));
			postBasic.setFid(JsonMethed.getJsonString(jsonObject.get("fid")));
			postBasic.setHits(JsonMethed.getJsonInt(jsonObject.get("hits")));
			postBasic.setIfupload(JsonMethed.getJsonString(jsonObject.get("ifupload")));
			postBasic.setLastPost(JsonMethed.getJsonString(jsonObject.get("lastpost")));
			postBasic.setLou(JsonMethed.getJsonInt(jsonObject.get("lou")));
			postBasic.setMicon(JsonMethed.getJsonString(jsonObject.get("micon")));
			postBasic.setPostdate(JsonMethed.getJsonString(jsonObject.get("postdate")));
			postBasic.setReplies(JsonMethed.getJsonInt(jsonObject.get("replies")));
			postBasic.setSubject(JsonMethed.getJsonString(jsonObject.get("subject")));
			postBasic.setTid(JsonMethed.getJsonString(jsonObject.get("tid")));
			postBasic.setType(JsonMethed.getJsonString(jsonObject.get("type")));
			postBasic.setUrl(JsonMethed.getJsonString(jsonObject.get("url")));
			postBasic.setPid(JsonMethed.getJsonString(jsonObject.get("pid")));
			postBasic.setBlockquote(JsonMethed.getJsonString(jsonObject.get("blockquote")));
			
			//����ͼƬ
			JsonArray jsonArray_attachments = JsonMethed.getJsonArray(jsonObject.get("attachments"));
			if(jsonArray_attachments != null){
				for(int i = 0; i < jsonArray_attachments.size(); i++){
					JsonObject jsonObject_attachments = JsonMethed.getJsonObject(jsonArray_attachments.get(i));
					ImageInfo imageInfo = new ImageInfo();
					imageInfo.setUrl(JsonMethed.getJsonString(jsonObject_attachments.get("url")));
					imageInfo.setMiniurl(JsonMethed.getJsonString(jsonObject_attachments.get("miniUrl")));
					postBasic.getAttachments().add(imageInfo);
				}
			}
			//���Content�е�ͼƬ
			for(int m=0;m < contentUrlList.size();m++){
				if(!postBasic.getAttachments().contains(contentUrlList.get(m))){
					ImageInfo imageInfo = new ImageInfo();
					imageInfo.setUrl(contentUrlList.get(m));
					imageInfo.setMiniurl(contentUrlList.get(m));
					postBasic.getAttachments().add(imageInfo);
				}
			}
		}catch (JsonParseException e) {
		} catch (Exception e) {
		}
		return postBasic;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getLastPost() {
		return lastpost;
	}

	public void setLastPost(String lastpost) {
		this.lastpost = lastpost;
	}

	public static PostBasic getPostBasicFromJsonObject(JsonObject jsonObject) {
		PostBasic postBasic =new PostBasic();
		try {
			if(JsonMethed.getJsonString(jsonObject.get("author")) == null){
				postBasic.setAuthor(JsonMethed.getJsonString(jsonObject.get("username")));
			}else{
				postBasic.setAuthor(JsonMethed.getJsonString(jsonObject.get("author")));
			}
			postBasic.setAuthorId(JsonMethed.getJsonString(jsonObject.get("authorid")));
			postBasic.setId(JsonMethed.getJsonString(jsonObject.get("id")));
			postBasic.setFid(JsonMethed.getJsonString(jsonObject.get("fid")));
			postBasic.setHits(JsonMethed.getJsonInt(jsonObject.get("hits")));
			postBasic.setIfupload(JsonMethed.getJsonString(jsonObject.get("ifupload")));
			postBasic.setLastPost(JsonMethed.getJsonString(jsonObject.get("lastpost")));
			postBasic.setPostdate(JsonMethed.getJsonString(jsonObject.get("postdate")));
			postBasic.setReplies(JsonMethed.getJsonInt(jsonObject.get("replies")));
			postBasic.setSubject(JsonMethed.getJsonString(jsonObject.get("subject")));
			postBasic.setTid(JsonMethed.getJsonString(jsonObject.get("tid")));
			postBasic.setType(JsonMethed.getJsonString(jsonObject.get("type")));
			postBasic.setUrl(JsonMethed.getJsonString(jsonObject.get("url")));
		}catch (JsonParseException e) {
		} catch (Exception e) {
		}
		return postBasic;
	}
}

