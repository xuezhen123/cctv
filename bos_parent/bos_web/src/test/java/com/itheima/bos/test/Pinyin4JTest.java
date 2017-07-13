package com.itheima.bos.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.itheima.bos.utils.PinYin4jUtils;

public class Pinyin4JTest {
	//测试使用工具类生成简码和城市编码
	@Test
	public void test1(){
		//QY010	北京市	北京市	顺义区	110113		
		String province = "山东省";
		String city = "济南市";
		String district = "顺义区";
		
		province =	province.substring(0, province.length() - 1);
		city =	city.substring(0, city.length() - 1);
		district =	district.substring(0, district.length() - 1);
		//简码:BJBJSY
		String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
		String shortcode = StringUtils.join(headByString);
		System.out.println(shortcode);
		//城市编码:beijing
		String citycode = PinYin4jUtils.hanziToPinyin(city, "");
		System.out.println(citycode);
	}
}
