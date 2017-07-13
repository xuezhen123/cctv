package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 区域管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	//属性驱动，用于接收页面上传的文件
	private File regionFile;

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
	@Autowired
	private RegionService service;
	
	//区域数据导入功能
	public String importRegion() throws Exception{
		List<Region> list = new ArrayList<Region>();
		//使用ＰＯＩ解析上传的Ｅｘｃｅｌ文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//读取表格文件的Sheet标签页
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		//遍历出每一行
		for (Row row : sheet) {
			int rowNum = row.getRowNum();
			if(rowNum == 0){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			
			province =	province.substring(0, province.length() - 1);
			city =	city.substring(0, city.length() - 1);
			district =	district.substring(0, district.length() - 1);
			//简码:BJBJSY
			String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
			String shortcode = StringUtils.join(headByString);
			//城市编码:beijing
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			list.add(region);
		}
		service.saveBatch(list);
		return NONE;
	}
	
	//分页查询
	public String pageQuery() throws Exception{
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentpage","pageSize","detachedCriteria","subareas"});
		return NONE;
	}
	
	//属性驱动，接收页面combobox输入的值
	private String q;
	
	public void setQ(String q) {
		this.q = q;
	}

	//查询所有的区域数据，返回json
	public String listajax(){
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)){
			//需要根据q进行模糊查询
			list = service.findByQ(q);
		}else{
			//查询所有区域数
			list = service.findAll();
		}
		this.java2Json(list, new String[]{"subareas"});
		return NONE;
	}
}
