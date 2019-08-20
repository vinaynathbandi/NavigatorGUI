package testrail;

import java.io.FileInputStream;
import java.util.Properties;

public class Settings {
	private static Properties propertiesSetting = null;

	private static String settingTestrailAPI;

	private static String settingUsername;
	private static String settingPassword;

	private static String settingProjectId;

	private static String settingURL;

	private static String settingENV;

	private static String Nav_Username;
	private static String Nav_Password;
	private static String WGS_INDEX;
	private static String WGSNAME;
	private static String FAV_WGS;

	private static String DeleteBridgeName;
	private static String DeleteDurableName;

	private static String IPAddress;
	private static String WGS_HostName;
	private static String WGS_PortNo;
	private static String WGS_Password;
	private static String VerificationData;
	private static String WGSSearchInputData;
	private static String NodeName;
	private static String Node_Hostname;
	private static String Node_IPAddress;
	private static String Node_PortNumber;
	private static String Node_NewConnectionName;

	private static String ScreenshotPath;

	private static String QueueName;
	private static String DownloadPath;

	private static String LocalQueue;
	private static String QueueNameFromOptions;

	private static String EMS_QueueName;
	private static String SearchInputData;

	private static String Queuemanager;

	private static String DestinationTopicName;
	private static String AddSubscriptionName;
	private static String Dnode;
	private static String EMS_NodeName;
	private static String EMS_DestinationQueue;

	private static String ManagerName;
	private static String DefaultTransmissionQueue;
	private static String DeleteManagerName;

	private static String Node_hostnam;
	private static String Node_NameFromIcon;
	private static String HostNameFromIcon;
	private static String IPAddressFromIcon;
	private static String QueueManagerName;


	
	private static String Q_QueueName;
	private static String Q_SearchInputData;

	private static String Q_Node;
	private static String Q_Queuemanager;

	private static String DeleteRouteName;

	private static String Ser_DestinationManager;

	private static String DestinationManager;
	private static String DestinationQueue;
	private static String DestinationNodeName;
	private static String DestinationManagerName;
	private static String AddSubscriptionNameFromIcon;
	private static String DestinationIconTopicName;
	private static String TopicStringDataFromICon;
	private static String DWGSIcon;
	private static String NodeNameFromIcon;
	private static String DestinationManagerFromIcon;
	private static String DestinationQueueFromIcon;
	
	private static String M_QueueManagerName;
	private static String UploadFilepath;
	private static String DWGS;
	
	private static String EMS_WGS_INDEX;
	private static String EMS_WGSNAME;
	
	private static String SourceName;
	private static String TargetName;
	private static String SelectTopicName;
	
	private static String S_WGSName;
	
	private static String Manager1;
	private static String Manager2;
	
	public static String getManager1() {
		return Manager1;
	}


	public static void setManager1(String manager1) {
		Manager1 = manager1;
	}
	
	public static String getManager2() {
		return Manager2;
	}


	public static void setManager2(String manager2) {
		Manager2 = manager2;
	}


	public static String getS_WGSName() {
		return S_WGSName;
	}


	public static void setS_WGSName(String s_WGSName) {
		S_WGSName = s_WGSName;
	}


	public static String getSelectTopicName() {
		return SelectTopicName;
	}


	public static void setSelectTopicName(String selectTopicName) {
		SelectTopicName = selectTopicName;
	}


	public static String getTargetName() {
		return TargetName;
	}


	public static void setTargetName(String targetName) {
		TargetName = targetName;
	}


	public static String getSourceName() {
		return SourceName;
	}


	public static void setSourceName(String sourceName) {
		SourceName = sourceName;
	}


	public static String getEMS_WGSNAME() {
		return EMS_WGSNAME;
	}


	public static void setEMS_WGSNAME(String eMS_WGSNAME) {
		EMS_WGSNAME = eMS_WGSNAME;
	}


	public static String getEMS_WGS_INDEX() {
		return EMS_WGS_INDEX;
	}


	public static void setEMS_WGS_INDEX(String eMS_WGS_INDEX) {
		EMS_WGS_INDEX = eMS_WGS_INDEX;
	}


	public static String getDWGS() {
		return DWGS;
	}


	public static void setDWGS(String dWGS) {
		DWGS = dWGS;
	}


	public static String getM_QueueManagerName() {
		return M_QueueManagerName;
	}


	public static void setM_QueueManagerName(String m_QueueManagerName) {
		M_QueueManagerName = m_QueueManagerName;
	}


	public static  void read() throws Exception {
		if (propertiesSetting == null) {
			propertiesSetting = new Properties();
			propertiesSetting.load(new FileInputStream("File.properties"));

			settingTestrailAPI = propertiesSetting.getProperty("TESTRAILAPI");
			settingUsername = propertiesSetting.getProperty("USERNAME");
			settingPassword = propertiesSetting.getProperty("PASSWORD");
			settingProjectId = propertiesSetting.getProperty("PROJECTID");
			settingURL = propertiesSetting.getProperty("URL");
			settingENV = propertiesSetting.getProperty("ENV");
			
			Nav_Username= propertiesSetting.getProperty("Nav_Username");
			Nav_Password= propertiesSetting.getProperty("Nav_Password");
			WGS_INDEX= propertiesSetting.getProperty("WGS_INDEX");
			WGSNAME= propertiesSetting.getProperty("WGSNAME");
			FAV_WGS= propertiesSetting.getProperty("FAV_WGS");

			DeleteBridgeName= propertiesSetting.getProperty("DeleteBridgeName");
			DeleteDurableName= propertiesSetting.getProperty("DeleteDurableName");

			IPAddress= propertiesSetting.getProperty("IPAddress");
			WGS_HostName= propertiesSetting.getProperty("WGS_HostName");
			WGS_PortNo= propertiesSetting.getProperty("WGS_PortNo");
			WGS_Password= propertiesSetting.getProperty("WGS_Password");
			VerificationData= propertiesSetting.getProperty("VerificationData");
			WGSSearchInputData= propertiesSetting.getProperty("WGSSearchInputData");
			NodeName= propertiesSetting.getProperty("NodeName");
			Node_Hostname= propertiesSetting.getProperty("Node_Hostname");
			Node_IPAddress= propertiesSetting.getProperty("Node_IPAddress");
			Node_PortNumber= propertiesSetting.getProperty("Node_PortNumber");
			Node_NewConnectionName= propertiesSetting.getProperty("Node_NewConnectionName");

			ScreenshotPath= propertiesSetting.getProperty("ScreenshotPath");

			QueueName= propertiesSetting.getProperty("QueueName");
			DownloadPath= propertiesSetting.getProperty("DownloadPath");

			LocalQueue= propertiesSetting.getProperty("LocalQueue");
			QueueNameFromOptions= propertiesSetting.getProperty("QueueNameFromOptions");

			EMS_QueueName= propertiesSetting.getProperty("EMS_QueueName");
			SearchInputData= propertiesSetting.getProperty("SearchInputData");

			Queuemanager= propertiesSetting.getProperty("Queuemanager");

			DestinationTopicName= propertiesSetting.getProperty("DestinationTopicName");
			AddSubscriptionName= propertiesSetting.getProperty("AddSubscriptionName");
			Dnode= propertiesSetting.getProperty("Dnode");
			EMS_NodeName= propertiesSetting.getProperty("EMS_NodeName");
			EMS_DestinationQueue= propertiesSetting.getProperty("EMS_DestinationQueue");

			ManagerName= propertiesSetting.getProperty("ManagerName");
			DefaultTransmissionQueue= propertiesSetting.getProperty("DefaultTransmissionQueue");
			DeleteManagerName= propertiesSetting.getProperty("DeleteManagerName");

			Node_hostnam= propertiesSetting.getProperty("Node_hostnam");
			Node_NameFromIcon= propertiesSetting.getProperty("Node_NameFromIcon");
			HostNameFromIcon= propertiesSetting.getProperty("HostNameFromIcon");
			IPAddressFromIcon= propertiesSetting.getProperty("IPAddressFromIcon");
			QueueManagerName= propertiesSetting.getProperty("QueueManagerName");


				
			Q_QueueName= propertiesSetting.getProperty("Q_QueueName");
			Q_SearchInputData= propertiesSetting.getProperty("Q_SearchInputData");

			Q_Node= propertiesSetting.getProperty("Q_Node");
			Q_Queuemanager= propertiesSetting.getProperty("Q_Queuemanager");

			DeleteRouteName= propertiesSetting.getProperty("DeleteRouteName");

			Ser_DestinationManager= propertiesSetting.getProperty("Ser_DestinationManager");

			DestinationManager= propertiesSetting.getProperty("DestinationManager");
			DestinationQueue= propertiesSetting.getProperty("DestinationQueue");
			DestinationNodeName= propertiesSetting.getProperty("DestinationNodeName");
			DestinationManagerName= propertiesSetting.getProperty("DestinationManagerName");
			AddSubscriptionNameFromIcon= propertiesSetting.getProperty("AddSubscriptionNameFromIcon");
			DestinationIconTopicName= propertiesSetting.getProperty("DestinationIconTopicName");
			TopicStringDataFromICon= propertiesSetting.getProperty("TopicStringDataFromICon");
			DWGSIcon= propertiesSetting.getProperty("DWGSIcon");
			NodeNameFromIcon= propertiesSetting.getProperty("NodeNameFromIcon");
			DestinationManagerFromIcon= propertiesSetting.getProperty("DestinationManagerFromIcon");
			DestinationQueueFromIcon= propertiesSetting.getProperty("DestinationQueueFromIcon");
			M_QueueManagerName= propertiesSetting.getProperty("M_QueueManagerName");
			UploadFilepath = propertiesSetting.getProperty("UploadFilepath");
			DWGS = propertiesSetting.getProperty("DWGS");
			
			EMS_WGS_INDEX = propertiesSetting.getProperty("EMS_WGS_INDEX");
			EMS_WGSNAME = propertiesSetting.getProperty("EMS_WGSNAME");
			
			SourceName = propertiesSetting.getProperty("SourceName");
			TargetName = propertiesSetting.getProperty("TargetName");
			
			SelectTopicName = propertiesSetting.getProperty("SelectTopicName");
			
			S_WGSName=propertiesSetting.getProperty("EMS_WGSNAME");
			
			Manager1 = propertiesSetting.getProperty("Manager1");
			Manager2 = propertiesSetting.getProperty("Manager2");
			
			
		}
	}

	
	public static String getUploadFilepath() {
		return UploadFilepath;
	}


	public static void setUploadFilepath(String uploadFilepath) {
		UploadFilepath = uploadFilepath;
	}


	public static Properties getPropertiesSetting() {
		return propertiesSetting;
	}

	public static void setPropertiesSetting(Properties propertiesSetting) {
		Settings.propertiesSetting = propertiesSetting;
	}

	public static String getNav_Username() {
		return Nav_Username;
	}

	public static void setNav_Username(String nav_Username) {
		Nav_Username = nav_Username;
	}

	public static String getNav_Password() {
		return Nav_Password;
	}

	public static void setNav_Password(String nav_Password) {
		Nav_Password = nav_Password;
	}

	public static String getWGS_INDEX() {
		return WGS_INDEX;
	}

	public static void setWGS_INDEX(String wGS_INDEX) {
		WGS_INDEX = wGS_INDEX;
	}

	public static String getWGSNAME() {
		return WGSNAME;
	}

	public static void setWGSNAME(String wGSNAME) {
		WGSNAME = wGSNAME;
	}

	public static String getFAV_WGS() {
		return FAV_WGS;
	}

	public static void setFAV_WGS(String fAV_WGS) {
		FAV_WGS = fAV_WGS;
	}

	public static String getDeleteBridgeName() {
		return DeleteBridgeName;
	}

	public static void setDeleteBridgeName(String deleteBridgeName) {
		DeleteBridgeName = deleteBridgeName;
	}

	public static String getDeleteDurableName() {
		return DeleteDurableName;
	}

	public static void setDeleteDurableName(String deleteDurableName) {
		DeleteDurableName = deleteDurableName;
	}

	public static String getIPAddress() {
		return IPAddress;
	}

	public static void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}

	public static String getWGS_HostName() {
		return WGS_HostName;
	}

	public static void setWGS_HostName(String wGS_HostName) {
		WGS_HostName = wGS_HostName;
	}

	public static String getWGS_PortNo() {
		return WGS_PortNo;
	}

	public static void setWGS_PortNo(String wGS_PortNo) {
		WGS_PortNo = wGS_PortNo;
	}

	public static String getWGS_Password() {
		return WGS_Password;
	}

	public static void setWGS_Password(String wGS_Password) {
		WGS_Password = wGS_Password;
	}

	public static String getVerificationData() {
		return VerificationData;
	}

	public static void setVerificationData(String verificationData) {
		VerificationData = verificationData;
	}

	public static String getWGSSearchInputData() {
		return WGSSearchInputData;
	}

	public static void setWGSSearchInputData(String wGSSearchInputData) {
		WGSSearchInputData = wGSSearchInputData;
	}

	public static String getNodeName() {
		return NodeName;
	}

	public static void setNodeName(String nodeName) {
		NodeName = nodeName;
	}

	public static String getNode_Hostname() {
		return Node_Hostname;
	}

	public static void setNode_Hostname(String node_Hostname) {
		Node_Hostname = node_Hostname;
	}

	public static String getNode_IPAddress() {
		return Node_IPAddress;
	}

	public static void setNode_IPAddress(String node_IPAddress) {
		Node_IPAddress = node_IPAddress;
	}

	public static String getNode_PortNumber() {
		return Node_PortNumber;
	}

	public static void setNode_PortNumber(String node_PortNumber) {
		Node_PortNumber = node_PortNumber;
	}

	public static String getNode_NewConnectionName() {
		return Node_NewConnectionName;
	}

	public static void setNode_NewConnectionName(String node_NewConnectionName) {
		Node_NewConnectionName = node_NewConnectionName;
	}

	public static String getScreenshotPath() {
		return ScreenshotPath;
	}

	public static void setScreenshotPath(String screenshotPath) {
		ScreenshotPath = screenshotPath;
	}

	public static String getQueueName() {
		return QueueName;
	}

	public static void setQueueName(String queueName) {
		QueueName = queueName;
	}

	public static String getDownloadPath() {
		return DownloadPath;
	}

	public static void setDownloadPath(String downloadPath) {
		DownloadPath = downloadPath;
	}

	public static String getLocalQueue() {
		return LocalQueue;
	}

	public static void setLocalQueue(String localQueue) {
		LocalQueue = localQueue;
	}

	public static String getQueueNameFromOptions() {
		return QueueNameFromOptions;
	}

	public static void setQueueNameFromOptions(String queueNameFromOptions) {
		QueueNameFromOptions = queueNameFromOptions;
	}

	public static String getEMS_QueueName() {
		return EMS_QueueName;
	}

	public static void setEMS_QueueName(String eMS_QueueName) {
		EMS_QueueName = eMS_QueueName;
	}

	public static String getSearchInputData() {
		return SearchInputData;
	}

	public static void setSearchInputData(String searchInputData) {
		SearchInputData = searchInputData;
	}

	public static String getQueuemanager() {
		return Queuemanager;
	}

	public static void setQueuemanager(String queuemanager) {
		Queuemanager = queuemanager;
	}

	public static String getDestinationTopicName() {
		return DestinationTopicName;
	}

	public static void setDestinationTopicName(String destinationTopicName) {
		DestinationTopicName = destinationTopicName;
	}

	public static String getAddSubscriptionName() {
		return AddSubscriptionName;
	}

	public static void setAddSubscriptionName(String addSubscriptionName) {
		AddSubscriptionName = addSubscriptionName;
	}

	public static String getDnode() {
		return Dnode;
	}

	public static void setDnode(String dnode) {
		Dnode = dnode;
	}

	public static String getEMS_NodeName() {
		return EMS_NodeName;
	}

	public static void setEMS_NodeName(String eMS_NodeName) {
		EMS_NodeName = eMS_NodeName;
	}

	public static String getEMS_DestinationQueue() {
		return EMS_DestinationQueue;
	}

	public static void setEMS_DestinationQueue(String eMS_DestinationQueue) {
		EMS_DestinationQueue = eMS_DestinationQueue;
	}

	public static String getManagerName() {
		return ManagerName;
	}

	public static void setManagerName(String managerName) {
		ManagerName = managerName;
	}

	public static String getDefaultTransmissionQueue() {
		return DefaultTransmissionQueue;
	}

	public static void setDefaultTransmissionQueue(String defaultTransmissionQueue) {
		DefaultTransmissionQueue = defaultTransmissionQueue;
	}

	public static String getDeleteManagerName() {
		return DeleteManagerName;
	}

	public static void setDeleteManagerName(String deleteManagerName) {
		DeleteManagerName = deleteManagerName;
	}

	public static String getNode_hostnam() {
		return Node_hostnam;
	}

	public static void setNode_hostnam(String node_hostnam) {
		Node_hostnam = node_hostnam;
	}

	public static String getNode_NameFromIcon() {
		return Node_NameFromIcon;
	}

	public static void setNode_NameFromIcon(String node_NameFromIcon) {
		Node_NameFromIcon = node_NameFromIcon;
	}

	public static String getHostNameFromIcon() {
		return HostNameFromIcon;
	}

	public static void setHostNameFromIcon(String hostNameFromIcon) {
		HostNameFromIcon = hostNameFromIcon;
	}

	public static String getIPAddressFromIcon() {
		return IPAddressFromIcon;
	}

	public static void setIPAddressFromIcon(String iPAddressFromIcon) {
		IPAddressFromIcon = iPAddressFromIcon;
	}

	public static String getQueueManagerName() {
		return QueueManagerName;
	}

	public static void setQueueManagerName(String queueManagerName) {
		QueueManagerName = queueManagerName;
	}

	public static String getQ_QueueName() {
		return Q_QueueName;
	}

	public static void setQ_QueueName(String q_QueueName) {
		Q_QueueName = q_QueueName;
	}

	public static String getQ_SearchInputData() {
		return Q_SearchInputData;
	}

	public static void setQ_SearchInputData(String q_SearchInputData) {
		Q_SearchInputData = q_SearchInputData;
	}

	public static String getQ_Node() {
		return Q_Node;
	}

	public static void setQ_Node(String q_Node) {
		Q_Node = q_Node;
	}

	public static String getQ_Queuemanager() {
		return Q_Queuemanager;
	}

	public static void setQ_Queuemanager(String q_Queuemanager) {
		Q_Queuemanager = q_Queuemanager;
	}

	public static String getDeleteRouteName() {
		return DeleteRouteName;
	}

	public static void setDeleteRouteName(String deleteRouteName) {
		DeleteRouteName = deleteRouteName;
	}

	public static String getSer_DestinationManager() {
		return Ser_DestinationManager;
	}

	public static void setSer_DestinationManager(String ser_DestinationManager) {
		Ser_DestinationManager = ser_DestinationManager;
	}

	public static String getDestinationManager() {
		return DestinationManager;
	}

	public static void setDestinationManager(String destinationManager) {
		DestinationManager = destinationManager;
	}

	public static String getDestinationQueue() {
		return DestinationQueue;
	}

	public static void setDestinationQueue(String destinationQueue) {
		DestinationQueue = destinationQueue;
	}

	public static String getDestinationNodeName() {
		return DestinationNodeName;
	}

	public static void setDestinationNodeName(String destinationNodeName) {
		DestinationNodeName = destinationNodeName;
	}

	public static String getDestinationManagerName() {
		return DestinationManagerName;
	}

	public static void setDestinationManagerName(String destinationManagerName) {
		DestinationManagerName = destinationManagerName;
	}

	public static String getAddSubscriptionNameFromIcon() {
		return AddSubscriptionNameFromIcon;
	}

	public static void setAddSubscriptionNameFromIcon(String addSubscriptionNameFromIcon) {
		AddSubscriptionNameFromIcon = addSubscriptionNameFromIcon;
	}

	public static String getDestinationIconTopicName() {
		return DestinationIconTopicName;
	}

	public static void setDestinationIconTopicName(String destinationIconTopicName) {
		DestinationIconTopicName = destinationIconTopicName;
	}

	public static String getTopicStringDataFromICon() {
		return TopicStringDataFromICon;
	}

	public static void setTopicStringDataFromICon(String topicStringDataFromICon) {
		TopicStringDataFromICon = topicStringDataFromICon;
	}

	public static String getDWGSIcon() {
		return DWGSIcon;
	}

	public static void setDWGSIcon(String dWGSIcon) {
		DWGSIcon = dWGSIcon;
	}

	public static String getNodeNameFromIcon() {
		return NodeNameFromIcon;
	}

	public static void setNodeNameFromIcon(String nodeNameFromIcon) {
		NodeNameFromIcon = nodeNameFromIcon;
	}

	public static String getDestinationManagerFromIcon() {
		return DestinationManagerFromIcon;
	}

	public static void setDestinationManagerFromIcon(String destinationManagerFromIcon) {
		DestinationManagerFromIcon = destinationManagerFromIcon;
	}

	public static String getDestinationQueueFromIcon() {
		return DestinationQueueFromIcon;
	}

	public static void setDestinationQueueFromIcon(String destinationQueueFromIcon) {
		DestinationQueueFromIcon = destinationQueueFromIcon;
	}



	
	public static String getSettingTestrailAPI() {
		return settingTestrailAPI;
	}

	public static void setSettingTestrailAPI(String settingTestrailAPI) {
		Settings.settingTestrailAPI = settingTestrailAPI;
	}

	public static String getSettingUsername() {
		return settingUsername;
	}

	public static void setSettingUsername(String settingUsername) {
		Settings.settingUsername = settingUsername;
	}

	public static String getSettingPassword() {
		return settingPassword;
	}

	public static void setSettingPassword(String settingPassword) {
		Settings.settingPassword = settingPassword;
	}

	public static String getSettingProjectId() {
		return settingProjectId;
	}

	public static void setSettingProjectId(String settingProjectId) {
		Settings.settingProjectId = settingProjectId;
	}

	public static String getSettingURL() {
		return settingURL;
	}

	public static void setSettingURL(String settingURL) {
		Settings.settingURL = settingURL;
	}

	public static String getSettingENV() {
		return settingENV;
	}

	public static void setSettingENV(String settingENV) {
		Settings.settingENV = settingENV;
	}

}
