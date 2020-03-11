package sailpoint.rapidapponboarding.reports;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


import sailpoint.api.SailPointContext;
import sailpoint.object.Application;
import sailpoint.object.Attributes;
import sailpoint.object.Custom;
import sailpoint.object.Filter;
import sailpoint.object.LiveReport;
import sailpoint.object.ManagedAttribute;
import sailpoint.object.QueryOptions;
import sailpoint.object.Sort;
import sailpoint.reporting.datasource.JavaDataSource;
import sailpoint.task.Monitor;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Util;
import sailpoint.object.*;
import sailpoint.object.Identity;
/**
 * Policy Violation Feature Report
 * @author rohit.gupta
 *
 */
public class ManagerReport implements JavaDataSource {
	private QueryOptions baseQueryOptions;
	private SailPointContext context;
	private Integer startRow;
	private Integer pageSize;
	private Monitor monitor;
	private String managerId;
	private List<String> managerList;
	private List<Map<String, Object>> objectList = new ArrayList();

	private Iterator<Map<String, Object>> objects;
	private Map<String, Object> object;
	private String separator = "|";


	//private static final Log sodMatrixLogger = LogFactory.getLog(AppHealthCheckReport.class);
	@Override
	public void initialize(SailPointContext context, LiveReport report,
			Attributes<String, Object> arguments, String groupBy,
			List<Sort> sort) throws GeneralException {
		this.context = context;
		baseQueryOptions = new QueryOptions();
			// Check if the use all apps checkbox was selected
			/*if (arguments.getBoolean("selectAllApplications")) {
				System.out.println("I am in select all applications");
				this.applicationList = arguments.getList("applications");
				//System.out.println("I am in specific  applications");

				//this.applicationList = PolicyViolationsRuleLibrary.getPVAppList(context);
			} else {
				// If not, then use the applications that were manually input
				this.applicationList = arguments.getList("applications");
				System.out.println("I am in specific  applications");
			}*/
		    this.managerList = arguments.getList("manager");
		    for (String managerName : this.managerList) {
		    	managerId=managerName;
		    	System.out.println(managerName);
		    }
			prepare();
		    /*
		    Map<String, Object> itemMap = null;
		    List managerList=new ArrayList();
			List<Identity> identityAll = new ArrayList<Identity>();
			identityAll = context.getObjects(Identity.class);
			for (Identity iden : identityAll){
				  
				  String identityName = iden.getName();
				   if(identityName !=null){
				          Identity identity=context.getObject(Identity.class,identityName);
				          Identity managerName=identity.getManager();
				          if(managerName!=null){
				            String manager=managerName.getName();
				            if (manager.equals(managerId)){
				               managerList.add(identityName);
				            }
				                }
				   }
				  }
			// Check if this application only supports one
			// entitlement per account
				itemMap = new HashMap<String, Object>();
				itemMap.put("Identity", managerList.get(0));
				itemMap.put("Manager", "Rupak Group");
				itemMap.put("Status", "Rupak Entitlement");
				itemMap.put("Application","This application only permits 1 entitlement per account");
				objectList.add(itemMap);
				itemMap = new HashMap<String, Object>();
				itemMap.put("Identity", "AWS ");
				itemMap.put("Manager", "Rupak Group AWS");
				itemMap.put("Status", "Rupak Entitlement AWS");
				itemMap.put("Application","This application only permits 1 entitlement per account");
				objectList.add(itemMap);
				
				
				
				objects = objectList.iterator();
			
		    
		    */
		    
		    
		    

	}

	/**
	 * This is the method which prepares data for the report.
	 * @throws GeneralException 
	 *
	 */
	private void prepare() throws GeneralException {

				Map<String, Object> itemMap = null;
				
				List <String>managerList=new ArrayList<String>();
				List<Identity> identityAll = new ArrayList<Identity>();
				identityAll = context.getObjects(Identity.class);
				for (Identity iden : identityAll){
					  
					  String identityName = iden.getName();
					   if(identityName !=null){
					          Identity identity=context.getObject(Identity.class,identityName);
					          Identity managerName=identity.getManager();
					          if(managerName!=null){
					            String manager=managerName.getName();
					            if (manager.equals(managerId)){
					               managerList.add(identityName);
					            }
					                }
					   }
					  }
				
				
				for (String identityUnderManager:managerList) {
				
					
					   Identity identity=context.getObject(Identity.class,identityUnderManager);
					   boolean inactive = false;
					   String allAppName=null;
						String status ="Active";   
					  inactive =(boolean)identity.getAttribute("inactive");
					   if (inactive==true) {
						   status = "Inactive";
						   
					   }
					  List <Link>links = identity.getLinks();  
					    List appList=new ArrayList();
					  for (Link link: links) {  
					    Application application = link.getApplication();  
					    String appName =application.getName();
					    appList.add(appName);
					    //allAppName=allAppName+appName+";";
					     //bool = link.getAttribute("inactive");
					  //  System.out.println(application.getName());  
					  }
					  if (appList!=null) {
					  allAppName = String.join(",", appList);
					  }
					    itemMap = new HashMap<String, Object>();
						itemMap.put("Identity",identityUnderManager );
						itemMap.put("Manager", managerId);
						itemMap.put("Status", status);
						itemMap.put("Application",allAppName);
						objectList.add(itemMap);
					    
					    
					  }
				
				objects = objectList.iterator();  
					    
					    
					
					
					
					
				
				
				
				
				/*
				

						// Check if this application only supports one
						// entitlement per account
							itemMap = new HashMap<String, Object>();
							itemMap.put("Identity", managerList.get(0));
							itemMap.put("Manager", "Rupak Group");
							itemMap.put("Status", "Rupak Entitlement");
							itemMap.put("Application","This application only permits 1 entitlement per account");
							objectList.add(itemMap);
							itemMap = new HashMap<String, Object>();
							itemMap.put("Identity", "AWS ");
							itemMap.put("Manager", "Rupak Group AWS");
							itemMap.put("Status", "Rupak Entitlement AWS");
							itemMap.put("Application","This application only permits 1 entitlement per account");
							objectList.add(itemMap);
							
							
							
							objects = objectList.iterator();
			
*/
	}
	

	public QueryOptions getBaseQueryOptions() {
		return baseQueryOptions;
	}

	@Override
	public String getBaseHql() {
		return null;
	}

	@Override
	public Object getFieldValue(String fieldName) throws GeneralException {
		if (fieldName.equals("Identity")) {
			return this.object.get("Identity");
		} else if (fieldName.equals("Manager")) {
			return this.object.get("Manager");
		} else if (fieldName.equals("Status")) {
			return this.object.get("Status");
		} else if (fieldName.equals("Application")) {
			return this.object.get("Application");
		} else {
			throw new GeneralException("Unknown column '" + fieldName + "'");
		}
	}

	@Override
	public int getSizeEstimate() throws GeneralException {

		if (this.managerList != null) {
			return this.managerList.size();
		} else {
			return 0;
		}
	}

	@Override
	public void close() {

	}

	@Override
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		String fieldName = jrField.getName();

		try {
			return getFieldValue(fieldName);
		} catch (GeneralException e) {
			throw new JRException(e);
		}
	}

	@Override
	public boolean next() throws JRException {
		boolean hasMore = false;

		if (this.objects != null) {
			hasMore = this.objects.hasNext();

			if (hasMore) {
				this.object = this.objects.next();
			} else {
				this.object = null;
			}

		}
		return hasMore;
	}

	@Override
	public void setLimit(int startRow, int pageSize) {
		this.startRow = startRow;
		this.pageSize = pageSize;

	}

	public static Comparator sodKeyComparator = new Comparator() {

		public int compare(Object key1, Object key2) {
			int key1Val = 0;
			int key2Val = 0;

			try {
				key1Val = Integer.parseInt(key1.toString());
				key2Val = Integer.parseInt(key2.toString());
			} catch (NumberFormatException nfe) {
				//LogEnablement.isLogErrorEnabled(sodMatrixLogger,"Cannot convert values to int: " + key1 + ", "+ key2);
				return 0;
			}

			return Integer.compare(key1Val, key2Val);
		}

	};
}
