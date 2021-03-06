package com.game.part.tmpl.type;

import com.game.part.tmpl.XSSFRowReadStream;

/**
 * Excel Short 字段
 * 
 * @author hjj2019
 * @since 2015/2/23
 * 
 */
public class XlsxShort extends BasicTypeCol<Short> {
	/**
	 * 类默认构造器
	 * 
	 */
	public XlsxShort() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param nullable
	 * 
	 */
	public XlsxShort(boolean nullable) {
		super(nullable);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param nullable
	 * @param defaultVal
	 * 
	 */
	public XlsxShort(boolean nullable, short defaultVal) {
		super(nullable, defaultVal);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param defaultVal
	 * 
	 */
	public XlsxShort(short defaultVal) {
		super(defaultVal);
	}

	@Override
	protected void readImpl(XSSFRowReadStream stream) {
		if (stream != null) {
			super.setObjVal(stream.readShort());
		}
	}

	/**
	 * objVal 不能为空, 但如果真为空值, 则自动创建
	 * 
	 * @param objVal
	 * @return
	 * 
	 */
	public static XlsxShort ifNullThenCreate(XlsxShort objVal) {
		if (objVal == null) {
			// 创建对象
			objVal = new XlsxShort();
		}

		return objVal;
	}
}
