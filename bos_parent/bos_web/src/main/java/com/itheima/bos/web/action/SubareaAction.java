package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 分区管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	@Autowired
	private SubareaService service;
	
	//添加分区
	public String add(){
		service.save(getModel());
		return LIST;
	}
	
	//分页查询
	public String pageQuery(){
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//动态添加页面提交的过滤条件
		String addresskey = getModel().getAddresskey();//地址关键字
		if(StringUtils.isNotBlank(addresskey)){
			//添加一个过滤条件，根据地址关键字进行模糊查询
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		Region region = getModel().getRegion();
		if(region != null){
			//通过hibernate提供的别名方式进行连接查询join
			dc.createAlias("region", "r");
			
			String province = region.getProvince();
			if(StringUtils.isNotBlank(province)){
				//添加一个过滤条件，根据省份进行模糊查询
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			
			String city = region.getCity();
			if(StringUtils.isNotBlank(city)){
				//添加一个过滤条件，根据省份进行模糊查询
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			
			String district = region.getDistrict();
			if(StringUtils.isNotBlank(district)){
				//添加一个过滤条件，根据省份进行模糊查询
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		
		//dc.add(criterion);//添加过滤条件
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentpage","pageSize","detachedCriteria","decidedzone","subareas"});
		return NONE;
	}
	
	//分区数据导出功能
	public String exportSubarea() throws IOException{
		//1、查询数据库，获取所有分区数据
		List<Subarea> list = service.findAll();
		
		//2、使用POI技术将查询出的分区数据写到Excel表格文件中
		HSSFWorkbook workbook = new HSSFWorkbook();//在内存中创建一个表格文件
		HSSFSheet sheet = workbook.createSheet("分区数据");//在文件中创建一个标签页
		HSSFRow row = sheet.createRow(0);//创建标题行
		row.createCell(0).setCellValue("分区编号");//在当前行对象上创建第一个单元格
		row.createCell(1).setCellValue("分区关键字");
		row.createCell(2).setCellValue("分区地址信息");
		row.createCell(3).setCellValue("省市区");
		
		for(Subarea s : list){
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(s.getId());//在当前行对象上创建第一个单元格
			dataRow.createCell(1).setCellValue(s.getAddresskey());
			dataRow.createCell(2).setCellValue(s.getPosition());
			dataRow.createCell(3).setCellValue(s.getRegion().getName());
		}
		
		//3、通过输出流将文件下载到客户端（一个流、两个头）
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		
		//设置服务端响应文件类型
		ServletActionContext.getResponse().setContentType("application/vnd.ms-excel");
		
		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");//客户端使用的浏览器类型
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		
		//设置以附件形式下载
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename="+filename);
		workbook.write(out);
		return NONE;
	}
	
	//查询所有未分配到定区的分区，返回json
	public String listajax(){
		List<Subarea> list = service.findSubareaNotAssociation();
		this.java2Json(list, new String[]{"decidedzone","region"});
		return NONE;
	}
	
	//根据定区id查询分区
	public String findSubareasByDecidedzoneId(){
		String did = getModel().getDecidedzone().getId();
		List<Subarea> list = service.findSubareasByDecidedzoneId(did);
		this.java2Json(list, new String[]{"decidedzone","subareas"});
		return NONE;
	}
}
