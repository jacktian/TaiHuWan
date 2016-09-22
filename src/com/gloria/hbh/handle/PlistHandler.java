package com.gloria.hbh.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/** 
 * .plist配置文件的解析器 
 * 支持array 
 * <plist version="1.0"> 
 *   <array> 
 *     <dict> 
 *       ... 
 *     </dict> 
 *     ... 
 *   </array> 
 * </plist version="1.0"> 
 *  
 * 支持Map 
 * <plist version="1.0"> 
 * <dict> 
 *   <id>key</id> 
 *   <array> 
 *     <dict> 
 *       ... 
 *     </dict> 
 *     ... 
 *   </array> 
 *   ... 
 * </dict>   
 * </plist version="1.0"> 
 *  
 * @author chen_weihua 
 * 
 */
@SuppressWarnings("rawtypes")
public class PlistHandler extends DefaultHandler { 
	
	private LinkedList<Object> list = new LinkedList<Object>();

	private boolean isRootElement = false;
	private boolean keyElementBegin = false;
	private String key;
	private boolean valueElementBegin = false;
	//根对�?
    private Object root;  
	
    public Map getMapResult() {  
        return (Map)root;  
    }  
    
    public List getArrayResult() {  
        return (List)root;  
    }  
	
	@SuppressWarnings("unchecked")
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("plist".equals(localName)) {
			isRootElement = true;
		}
		if ("dict".equals(localName)) {
			if (isRootElement) {
				list.addFirst(new HashMap()); 
				isRootElement = !isRootElement;
			} else {
				ArrayList parent = (ArrayList)list.get(0);
				list.addFirst(new HashMap());
				parent.add(list.get(0));
			}
		}
		if ("key".equals(localName)) {keyElementBegin = true;}
		if ("true".equals(localName)) {
			HashMap parent = (HashMap)list.get(0);
			parent.put(key, true);
		}
		if ("false".equals(localName)) {
			HashMap parent = (HashMap)list.get(0);
			parent.put(key, false);
		}
		if ("array".equals(localName)) {
			if (isRootElement) {
				ArrayList obj = new ArrayList();
				list.addFirst(obj);
				isRootElement = !isRootElement;
			} else {
				HashMap parent = (HashMap)list.get(0);
				ArrayList obj = new ArrayList();
				list.addFirst(obj);
				parent.put(key, obj);
			}
		}
		if ("string".equals(localName)) {
			valueElementBegin = true;
		}
	}

	@SuppressWarnings("unchecked")  
    @Override  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
        if (length > 0) {  
            if (keyElementBegin) {  
                key = new String(ch, start, length);  
                //Log.d("AR native", "key:" + key);  
            }  
            if (valueElementBegin) {  
                  
                if (HashMap.class.equals(list.get(0).getClass())) {  
                    HashMap parent = (HashMap)list.get(0);  
                    String value = new String(ch, start, length);  
                    parent.put(key, value);  
                } else if (ArrayList.class.equals(list.get(0).getClass())) {  
                    ArrayList parent = (ArrayList)list.get(0);  
                    String value = new String(ch, start, length);  
                    parent.add(value);  
                }  
                //Log.d("AR native", "value:" + value);  
            }  
        }  
    }  
  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        if ("plist".equals(localName)) {  
            ;  
        }  
        if ("key".equals(localName)) {  
            keyElementBegin = false;  
        }  
        if ("string".equals(localName)) {  
            valueElementBegin = false;  
        }  
        if ("array".equals(localName)) {  
            root = list.removeFirst();  
        }  
        if ("dict".equals(localName)) {  
            root = list.removeFirst();  
        }  
    }  
}