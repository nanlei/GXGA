package cn.gov.dl.ga.gxga.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqlHelper {
	public String buildWhereIn(String column, String item,
			List<Map<String, Object>> list) {
		if (list == null) {
			return "";
		}

		StringBuffer whereSql = new StringBuffer(512);

		Iterator<Map<String, Object>> init = list.iterator();

		int i = 0;

		whereSql.append(column + " in (");

		while (init.hasNext()) {

			if (i == 0) {
				whereSql.append(init.next().get(item));
			} else {
				whereSql.append("," + init.next().get(item));
			}
			i++;

		}

		whereSql.append(")");

		return whereSql.toString();
	}

	public String buildWhereNotIn(String column, Collection<Object> object) {
		if (object == null) {
			return "";
		}

		StringBuffer whereSql = new StringBuffer(512);

		Iterator<Object> initnot = object.iterator();

		int inot = 0;

		whereSql.append(column + " not in (");

		while (initnot.hasNext()) {

			if (inot == 0) {
				whereSql.append(initnot.next());
			} else {
				whereSql.append("," + initnot.next());
			}
			inot++;

		}

		whereSql.append(")");

		return whereSql.toString();
	}

	public String buildWhereIn(String column, Object[] object) {
		if (object == null || object.length == 0) {
			return "";
		}

		StringBuffer whereSql = new StringBuffer(512);

		whereSql.append(column + " in (");

		for (int i = 0; i < object.length; i++) {
			if (i == object.length - 1) {
				whereSql.append(object[i]);
			} else {
				whereSql.append(object[i] + ",");
			}
		}

		whereSql.append(")");

		return whereSql.toString();
	}

	public static void main(String[] args) {
		String[] roleIds = new String[] { "1", "2", "3", "4" };

		SqlHelper sqlHelper = new SqlHelper();
		System.out.println(sqlHelper.buildWhereIn("roleId", roleIds));
	}
}
