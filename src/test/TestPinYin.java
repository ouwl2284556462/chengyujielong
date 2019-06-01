package test;

import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * ²âÊÔÆ´Òô 
 */
public class TestPinYin {

	public static void main(String[] args) {
		String[] pinyinList = PinyinHelper.convertToPinyinArray('µÄ');
		
		for(String pinyin : pinyinList){
			System.out.println(pinyin);
		}
	}

}
