package cn.gov.dl.ga.gxga.core;

/**
 * 系统常量配置
 * 
 * @author Nan Lei
 * 
 */
public interface Constant {
	public static final String ENCODING = "UTF-8";

	/* HTTP parameters from Android device */
	public static final String HTTP_PARAM_DATA = "DATA";
	public static final String HTTP_PARAM_USERNAME = "USERNAME";
	public static final String HTTP_PARAM_PASSWORD = "PASSWORD";
	public static final String HTTP_PARAM_IMEI = "IMEI";
	public static final String HTTP_PARAM_IMSI = "IMSI";

	/* 通用操作结果页面返回值 */
	public static final String EXECUTE_RESULT = "executeResult";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	public static final String COMMAND = "command";

	public static final String LOGIN_USER = "loginUser";

	public static final String DEFAULT = "default";

	/************************* 全局跳转开始 *****************************/
	public static final String SYSTEM_ERROR = "systemerror";
	/************************* 全局跳转结束 *****************************/

	/************************* 信息常量开始 *****************************/
	public static final String UNDEFINE_ERROR = "ERRS000";

	public static final String ERROR_PREFIX = "ERR";

	public static final String PROCEDURES_MESSAGE_PREFIX = "MSG";
	/************************* 信息常量结束 *****************************/

	public static final String DEFAULT_SESSIONID_FALG = "sid";

	/**
	 * 操作状态标识 status
	 */
	public static final String STATUS_FLAG = "status";

	/**
	 * 成功状态标识
	 */
	public static final String STATUS_SUCCESS = "success";

	/**
	 * 失败状态标识
	 */
	public static final String STATUS_FAILURE = "failure";

	/* 分页相关 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int MAX_PAGE_SIZE = 1000;
	public static final String NORMAL_MARK = "?";
	public static final String START_MARK = ":_START_INDEX_";
	public static final String END_MARK = ":_END_INDEX_";

	/* 缓存常量集 */
	public static final String CACHE_CONSTANT = "CONSTANT";
	public static final String CACHE_YN = "YN";
	public static final String CACHE_CATEGORY = "CATEGORY";
	public static final String CACHE_ROLE = "ROLE";

	public static final String CACHE_PERMISSION = "PERMISSION";

	public static final String CACHE_POSITION = "POSITION";
	public static final String CACHE_DEPARTMENT = "DEPARTMENT";

	/* 状态常量集 */
	public static final String STS_NEW = "NEW";// 新建/待处理
	public static final String STS_WAI = "WAI";// 处理中
	public static final String STS_RUN = "RUN";// 正常
	public static final String STS_FIN = "FIN";// 结束/办结
	public static final String STS_OVT = "OVT";// 超时

	/* 文章类型常量集 */
	public static final String ARTICLETYPE_LAW = "LAW";// 法律纵览
	public static final String ARTICLETYPE_INFORMATIONSECURITY = "INFORMATIONSECURITY";// 信息安全

	public static final String ARTICLETYPE_IMAGENEWS = "IMAGENEWS";// 图片新闻

	public static final String ARTICLETYPE_BRANCHFILE = "BRANCHFILE";// 分局文件
	public static final String ARTICLETYPE_SUPERIORFILE = "SUPERIORFILE";// 上级文件
	public static final String ARTICLETYPE_NOTICE = "NOTICE";// 通知通报
	public static final String ARTICLETYPE_WORKREPORT = "WORKREPORT";// 工作动态(工作简报)
	public static final String ARTICLETYPE_POLITICALREPORT = "POLITICALREPORT";// 政工简报
	public static final String ARTICLETYPE_POLITICALNOTICE = "POLITICALNOTICE";// 政工通知
	public static final String ARTICLETYPE_LEGAL = "LEGAL";// 公安法制
	public static final String ARTICLETYPE_DISCIPLINE = "DISCIPLINE";// 纪检监察
	public static final String ARTICLETYPE_POLICECASE = "POLICECASE";// 每日警情(警情研判)
	public static final String ARTICLETYPE_EXPERIENCE = "EXPERIENCE";// 经验交流
	public static final String ARTICLETYPE_POLICECULTURE = "POLICECULTURE";// 警营文化
	public static final String ARTICLETYPE_EVALUATION = "EVALUATION";// 督导考核(综合考评)
	public static final String ARTICLETYPE_TALENT = "TALENT";// 人才市场
	public static final String ARTICLETYPE_MONTHLYSTAR = "MONTHLYSTAR";// 每月之星

	public static final String ARTICLEBIZTYPE_NOR = "NOR";// 普通文章
	public static final String ARTICLEBIZTYPE_RED = "RED";// 红头文件

	/* EXTERNAL TRANSACTION类型常量集 */
	public static final String EXTERNALTRANSACTIONTYPE_MXW = "MXW";// 民心网
	public static final String EXTERNALTRANSACTIONTYPE_MYW = "MYW";// 民意网
	public static final String EXTERNALTRANSACTIONTYPE_PUBCOMM = "PUBCOMM";// 公众交流平台
	public static final String EXTERNALTRANSACTIONTYPE_OFFICALMAILBOX = "OFFICALMAILBOX";// 局长信箱

	/* WORD类型常量集 */
	public static final String DOCWORD_POLICECASE = "POLICECASE";

	/* 首页链接常量集 */
	public static final String LINKTYPE_QGK = "QGK";// 全国库
	public static final String LINKTYPE_STK = "STK";// 省厅库
	public static final String LINKTYPE_SJK = "SJK";// 市局库
	public static final String LINKTYPE_FJK = "FJK";// 分局库
	public static final String LINKTYPE_QGDH = "QGDH";// 全国导航
	public static final String LINKTYPE_SNDH = "SNDH";// 省内导航
	public static final String LINKTYPE_SJDH = "SJDH";// 市局导航
	public static final String LINKTYPE_SJZSBMDH = "SJZSBMDH";// 市局直属部门导航
	public static final String LINKTYPE_QXFJDH = "QXFJDH";// 区县分局导航
}
