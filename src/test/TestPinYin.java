package test;

import com.github.stuxuhai.jpinyin.PinyinHelper;

public class TestPinYin {

	public static void main(String[] args) {
		String[] pinyinList = PinyinHelper.convertToPinyinArray('��');
		
		for(String pinyin : pinyinList){
			System.out.println(pinyin);
		}
	}

}
