package com.talanton.service.myweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.talanton.service.myweb.command.CommandAction;
import com.talanton.service.myweb.command.NullAction;

/**
 * Servlet implementation class Controller
 */
@WebServlet(
		description = "안드로이드나 자바 클라이언트와 연동하는 Controller", 
		urlPatterns = { "*.app" }, 
		initParams = { 
				@WebInitParam(name = "propertyConfigApp", value = "commandMappingApp.properties", description = "명령어 핸들러 매핑 테이블")
		})
public class AppController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//명령어와 명령어 처리 클래스를 쌍으로 저장
	private Map<String, Object> commandMap = new HashMap<String, Object>(); 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    // 명령어와 처리클래스가 매핑되어 있는 properties 파일을 읽어서 HashMap객체인 commandMap에 저장
	public void init(ServletConfig config) throws ServletException {
		//initParams에서 propertyConfig의 값을 읽어옴
		String props = config.getInitParameter("propertyConfigApp");
		String realFolder = "/property"; //properties파일이 저장된 폴더
		//웹어플리케이션 루트 경로
		ServletContext context = config.getServletContext();
		//realFolder를 웹어플리케이션 시스템상의 절대경로로 변경
		String realPath = context.getRealPath(realFolder) + File.separator + props;
									    
		//명령어와 처리클래스의 매핑정보를 저장할 Properties객체 생성
		Properties pr = new Properties();
		FileInputStream f = null;
		try{
			//command.properties파일의 내용을 읽어옴
			f = new FileInputStream(realPath); 
			//command.properties의 내용을 Properties객체 pr에 저장
			pr.load(f);
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (f != null) try { f.close(); } catch(IOException ex) {}
		}
		
		//Set객체의 iterator()메소드를 사용해 Iterator객체를 얻어냄
		Iterator<?> keyIter = pr.keySet().iterator();
		//Iterator객체에 저장된 명령어와 처리클래스를 commandMap에 저장
		while( keyIter.hasNext() ) {
			String command = (String)keyIter.next();
			String className = pr.getProperty(command);
			try{
				Class<?> commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();
				commandMap.put(command, commandInstance);
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (InstantiationException e) {
				e.printStackTrace();
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestPro(request, response);//요청처리 메소드 호출
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestPro(request, response);//요청처리 메소드 호출
	}

	//웹브라우저의 요청을 분석하고, 해당 로직의 처리를 할 모델 실행 및 처리 결과를 뷰에 보냄
	private void requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		CommandAction com=null;
		try {
			String command = request.getRequestURI();
		    if(command.indexOf(request.getContextPath()) == 0) 
		    	command = command.substring(request.getContextPath().length());
		    com = (CommandAction)commandMap.get(command);
		    if(com == null) {
		    	com = new NullAction();
		    }
		    result = com.requestPro(request, response);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		if (result != null) {
        	response.setCharacterEncoding("utf-8");
        	response.setContentType("text/json");
            response.setHeader("Cache-control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");
	        PrintWriter out = response.getWriter();
	        out.print(result);
        }
	}
}