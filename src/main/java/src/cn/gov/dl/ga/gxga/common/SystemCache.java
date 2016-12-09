package cn.gov.dl.ga.gxga.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.gov.dl.ga.gxga.core.Constant;

/**
 * 系统缓存，用于初始化时加载
 * 
 * @author Nan Lei
 * 
 */
public final class SystemCache {
	private Logger logger = LoggerFactory.getLogger(SystemCache.class);

	private JdbcTemplate jt;
	private ResourceBundle res;

	private static SystemCache systemCache = new SystemCache();

	private HashSet<String> ipSet = new HashSet<String>();
	private String mode;
	private HashMap<String, Object> constantMap = new HashMap<String, Object>();
	private HashMap<String, Object> cacheMap = new HashMap<String, Object>();

	public HashSet<String> getIpSet() {
		return ipSet;
	}

	public String getMode() {
		return mode;
	}

	public HashMap<String, Object> getConstantMap() {
		return constantMap;
	}

	public HashMap<String, Object> getCacheMap() {
		return cacheMap;
	}

	public static SystemCache getInstants() {
		if (systemCache == null) {
			systemCache = new SystemCache();
		}
		return systemCache;
	}

	public void init(ServletContext sc) {
		jt = (JdbcTemplate) WebApplicationContextUtils.getWebApplicationContext(sc).getBean("jdbcTemplate");

		res = ResourceBundle.getBundle("conf");

		loadIP();
		loadMode();
		loadPermission();
		loadConstant();

		logger.info("{}", "SystemCache Init Successfully!");
	}

	public void refresh(String constantName) {
		refreshConstant(constantName);
	}

	private void loadIP() {
		logger.info("{}", "Loading IP...");
		// Load from conf.properties
		String[] sysIP = res.getString("sysIP").trim().split(",");
		String[] segIP_1 = res.getString("segIP_1").trim().split(",");
		String[] segIP_2 = res.getString("segIP_2").trim().split(",");
		String[] segIP_3 = res.getString("segIP_3").trim().split(",");
		String[] segIP_4 = res.getString("segIP_4").trim().split(",");

		for (String ip : sysIP) {
			ipSet.add(ip);
		}

		for (String seg_ip_1 : segIP_1) {
			ipSet.add(seg_ip_1);
		}

		for (String seg_ip_2 : segIP_2) {
			ipSet.add(seg_ip_2);
		}

		for (String seg_ip_3 : segIP_3) {
			ipSet.add(seg_ip_3);
		}

		for (String seg_ip_4 : segIP_4) {
			ipSet.add(seg_ip_4);
		}

		// Load from DB
		List<String> userIP = jt.queryForList(SQL.SQL_LOAD_IP, String.class);

		for (String ip : userIP) {
			ipSet.add(ip);

		}

		// Load external IP
		List<String> externalIP = jt.queryForList(SQL.SQL_LOAD_EXTERNAL_IP, String.class);

		for (String ip : externalIP) {
			ipSet.add(ip);
		}
	}

	public void refreshIP() {
		logger.info("{}", "Refreshing IP Set...");

		// Load external IP
		List<String> externalIP = jt.queryForList(SQL.SQL_LOAD_EXTERNAL_IP, String.class);

		for (String ip : externalIP) {
			ipSet.add(ip);
		}
	}

	private void loadMode() {
		mode = res.getString("mode").trim();
	}

	public void loadPermission() {
		logger.info("{}", "Loading Permission...");

		HashMap<String, String> sessionPermission = new HashMap<String, String>();

		List<Map<String, Object>> permissionList = jt.queryForList(SQL.SQL_LOAD_PERMISSION);

		for (int i = 0; i < permissionList.size(); i++) {
			Map<String, Object> permission = permissionList.get(i);
			String permissionUrl = (String) permission.get("permissionUrl");
			String roleId = String.valueOf(permission.get("roleId"));

			if (sessionPermission.get(permissionUrl) == null) {
				sessionPermission.put(permissionUrl, "#" + roleId + "#");
			} else {
				String roleKey = (String) sessionPermission.get(permissionUrl);

				if (!roleKey.contains("#" + roleId + "#")) {
					roleKey = roleKey + roleId + "#";
				}

				sessionPermission.remove(permissionUrl);
				sessionPermission.put(permissionUrl, roleKey);
			}
		}

		cacheMap.remove(Constant.CACHE_PERMISSION);
		cacheMap.put(Constant.CACHE_PERMISSION, sessionPermission);
	}

	private void loadConstant() {
		logger.info("{}", "Loading Constant...");

		List<String> constantTypes = jt.queryForList(SQL.SQL_LOAD_CONSTANT_TYPE, String.class);

		for (String constantType : constantTypes) {
			List<Map<String, Object>> constants = jt.queryForList(SQL.SQL_LOAD_CONSTANT_BY_TYPE, constantType);
			constantMap.put(constantType, constants);
		}

		List<Map<String, Object>> categories = jt.queryForList(SQL.SQL_LOAD_CATEGORY);
		List<Map<String, Object>> roles = jt.queryForList(SQL.SQL_LOAD_ROLE);
		List<Map<String, Object>> positions = jt.queryForList(SQL.SQL_LOAD_POSITION);
		List<Map<String, Object>> departments = jt.queryForList(SQL.SQL_LOAD_DEPARTMENT);
		constantMap.put(Constant.CACHE_CATEGORY, categories);
		constantMap.put(Constant.CACHE_ROLE, roles);
		constantMap.put(Constant.CACHE_POSITION, positions);
		constantMap.put(Constant.CACHE_DEPARTMENT, departments);
	}

	private void refreshConstant(String constantName) {
		if (Constant.CACHE_CONSTANT.equals(constantName)) {
			List<String> constantTypes = jt.queryForList(SQL.SQL_LOAD_CONSTANT_TYPE, String.class);

			for (String constantType : constantTypes) {
				List<Map<String, Object>> constants = jt.queryForList(SQL.SQL_LOAD_CONSTANT_BY_TYPE, constantType);
				constantMap.put(constantType, constants);
			}
		} else if (Constant.CACHE_CATEGORY.equals(constantName)) {
			List<Map<String, Object>> categories = jt.queryForList(SQL.SQL_LOAD_CATEGORY);
			constantMap.put(Constant.CACHE_CATEGORY, categories);
		} else if (Constant.CACHE_ROLE.equals(constantName)) {
			List<Map<String, Object>> categories = jt.queryForList(SQL.SQL_LOAD_ROLE);
			constantMap.put(Constant.CACHE_ROLE, categories);
		} else if (Constant.CACHE_POSITION.equals(constantName)) {
			List<Map<String, Object>> positions = jt.queryForList(SQL.SQL_LOAD_POSITION);
			constantMap.put(Constant.CACHE_POSITION, positions);
		} else if (Constant.CACHE_DEPARTMENT.equals(constantName)) {
			List<Map<String, Object>> departments = jt.queryForList(SQL.SQL_LOAD_DEPARTMENT);
			constantMap.put(Constant.CACHE_DEPARTMENT, departments);
		}
	}

	private class SQL {
		private static final String SQL_LOAD_IP = "select bindIP from sys_user";
		private static final String SQL_LOAD_EXTERNAL_IP = "select externalIP from sys_externalip where isLock='N'";
		private static final String SQL_LOAD_PERMISSION = "select sys_permission.permissionUrl, sys_role_permissiongroup.roleId from sys_permission,sys_permissiongroup_permission,sys_role_permissiongroup where sys_permission.permissionid=sys_permissiongroup_permission.permissionid and sys_permissiongroup_permission.permissiongroupid=sys_role_permissiongroup.permissiongroupid";

		private static final String SQL_LOAD_CONSTANT_TYPE = "select constantType from sys_constant group by constantType";
		private static final String SQL_LOAD_CONSTANT_BY_TYPE = "select constantValue as id, constantName as text from sys_constant where constantType=? order by constantOrder asc";

		private static final String SQL_LOAD_CATEGORY = "select categoryId as id, categoryName as text from sys_category order by categoryOrder asc";
		private static final String SQL_LOAD_ROLE = "select roleId as id, roleName as text from sys_role order by roleOrder asc";

		private static final String SQL_LOAD_POSITION = "select positionId as id, positionName as text from hr_position order by positionOrder asc";

		private static final String SQL_LOAD_DEPARTMENT = "select departmentId as id, departmentName as text from hr_department order by departmentOrder asc";
	}

}
