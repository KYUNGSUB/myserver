package com.talanton.service.myweb.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jivesoftware.smack.XMPPException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.talanton.service.myweb.music.model.Information;
import com.talanton.service.myweb.music.model.MainCategory;
import com.talanton.service.myweb.music.model.SubCategory;
import com.talanton.service.myweb.xmpp.CcsClient;

/**
 * Application Lifecycle Listener implementation class ApplicationListener
 *
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

	List<MainCategory> mList;
    /**
     * Default constructor. 
     */
    public ApplicationListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {
    	CommonParameter cp = CommonParameter.getInstance();
    	String count_per_page = sce.getServletContext().getInitParameter("count_per_page");
    	cp.put("count_per_page", count_per_page);
    	String os_name = System.getProperty("os.name");
    	if(os_name.contains("Window")) {
    		String upload_path = sce.getServletContext().getInitParameter("window_upload_path");
    		cp.put("os.name", "Windows");
    		cp.put("upload_path", upload_path);
    	}
    	else {
    		String upload_path = sce.getServletContext().getInitParameter("linux_upload_path");
    		cp.put("os.name", "Linux");
    		cp.put("upload_path", upload_path);
    	}
//    	System.out.println("OS type : " + os_name);
    	String senderId = sce.getServletContext().getInitParameter("fcm_sender_id");
    	String serverKey = sce.getServletContext().getInitParameter("fcm_server_key");
    	cp.put("fcm_server_key", serverKey);
    	cp.put("fcm_sender_id", senderId);
    	
    	initialMainCategoryTable(sce.getServletContext().getRealPath("/"));
    	CcsClient ccsClient;
    	if(os_name.contains("Window")) {
    		ccsClient = CcsClient.prepareClient(senderId, serverKey, true);
    	}
    	else {
    		ccsClient = CcsClient.prepareClient(senderId, serverKey, false);
    	}
    	
        try {
			ccsClient.connect();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
    }

	private void initialMainCategoryTable(String path) {
		File inputFile = new File(path + File.separator + "raw/logical_list.xml");
//		System.out.println("path : " + inputFile.getAbsolutePath().toString());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        mList = new ArrayList<MainCategory>();
        
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName("main-category");
//	        System.out.println("----------------------------");
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
//	            System.out.println("Current Element :" + nNode.getNodeName() + ", n = " + temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	Element eElement = (Element) nNode;
	            	MainCategory mc = new MainCategory();
	            	mc.setCodeId(Integer.valueOf(eElement.getElementsByTagName("code").item(0).getTextContent()));
	            	mc.setKname(eElement.getElementsByTagName("ko-subject").item(0).getTextContent());
	            	mc.setEname(eElement.getElementsByTagName("en-subject").item(0).getTextContent());
	            	mc.setSubCategory(parseSubCategoryInfo(eElement));
	            	mc.setMcount(mc.getSubCategory().size());
	            	mList.add(mc);
//	            	System.out.println("pid :" + mc.getCodeId());
	            }
	        }
//	        System.out.println("size of array : " + mList.size());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<SubCategory> parseSubCategoryInfo(Element eElement) {
		NodeList nList = eElement.getElementsByTagName("sub-category");
		List<SubCategory> sList = new ArrayList<SubCategory>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
//            System.out.println("Current Element :" + nNode.getNodeName() + ", n = " + temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            	Element sElement = (Element) nNode;
            	SubCategory sc = new SubCategory();
            	sc.setCodeId(Integer.valueOf(eElement.getElementsByTagName("code").item(0).getTextContent()));
            	sc.setKname(eElement.getElementsByTagName("ko-subject").item(0).getTextContent());
            	sc.setEname(eElement.getElementsByTagName("en-subject").item(0).getTextContent());
            	sc.setiList(parseLogicalListInfo(sElement));
            	sc.setMcount(sc.getiList().size());
            	sList.add(sc);
            }
        }
		return sList;
	}

	private List<Information> parseLogicalListInfo(Element sElement) {
		NodeList nList = sElement.getElementsByTagName("information");
		List<Information> iList = new ArrayList<Information>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
//            System.out.println("Current Element :" + nNode.getNodeName() + ", n = " + temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            	Element iElement = (Element) nNode;
            	Information logical = new Information();
            	logical.setNo(iElement.getElementsByTagName("no").item(0).getTextContent());
            	logical.setPid(Long.valueOf(iElement.getElementsByTagName("pid").item(0).getTextContent()));
            	iList.add(logical);
            }
        }
		return iList;
	}
	
}