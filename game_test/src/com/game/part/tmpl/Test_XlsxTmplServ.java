package com.game.part.tmpl;

import java.util.List;

import org.junit.Test;

import com.game.bizModules.building.tmpl.BuildingTmpl;

/**
 * 模板服务测试
 * 
 * @author hjj2019
 * @since 2015/2/22
 *
 */
public class Test_XlsxTmplServ {
	@Test
	public void test() {
		XlsxTmplServ.OBJ._baseDir = "/data/temp_test/";
		XlsxTmplServ.OBJ.loadTmplData(BuildingTmpl.class);
		XlsxTmplServ.OBJ.packUp(BuildingTmpl.class);
		XlsxTmplServ.OBJ.validateAll();
		
		List<?> objList = XlsxTmplServ.OBJ.getObjList(BuildingTmpl.class);
		
		System.out.println(objList);
		System.out.println(BuildingTmpl._IDMap.get(2));
		System.out.println(BuildingTmpl._cityBuildingMap.values());
	}
}
