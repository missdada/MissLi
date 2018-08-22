package cn.manytag.manytagUtil.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import cn.manytag.manytagUtil.Security;
import cn.manytag.manytagUtil.util.StringUtil;

/**
 * 读取property文件，将其中的加密信息解密
 *
 */
public class ManyTagPropertySecurity extends PropertyPlaceholderConfigurer {
	public static final Logger log = Logger.getLogger(ManyTagPropertySecurity.class);

	/**
	 * 加密标志
	 */
	public static final String MARK = "\nsecurity,";
	public static final int MARK_LEN = MARK.length();

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		log.debug("==================" + propertyName + ":" + propertyValue);
		//有加密项则进行解密
		if (propertyValue.startsWith(MARK)) {
			int index = StringUtil.indexOfNum(propertyValue, ",", 2);
			if (index == -1) {
				index = propertyValue.length();
			} else {
				index++;
			}
			//密文
			String value = propertyValue.substring(index, propertyValue.length());
			//密钥
			String password = propertyValue.substring(MARK_LEN, index - 1);

			String str = Security.xdDecrypted(value, password);
			return str;
		}
		return propertyValue;
	}

}