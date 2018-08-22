package cn.manytag.manytagUtil.web.jspDev;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 自定义标签，用于在jsp模板中重写指定的占位内容
 * 
 * 基本原理： 将overwrite标签内容部分添加到ServletRequest的attribute属性中
 * 在后续block标签中再通过属性名读取出来，将其渲染到最终的页面上即可
 * 
 * @author 逆风之羽 http://www.cnblogs.com/gewei/p/4596927.html
 */
public class OverwriteTag extends BodyTagSupport {

	private static final long serialVersionUID = 5901780136314677968L;
	// 模块名的前缀
	public static final String PREFIX = "JspTemplateBlockName_";
	// 模块名
	private String name;

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
	}

	@Override
	public int doEndTag() throws JspException {
		ServletRequest request = pageContext.getRequest();
		// 标签内容
		BodyContent bodyContent = getBodyContent();
		request.setAttribute(PREFIX + name, bodyContent == null ? "" : bodyContent.getString().trim());
		return super.doEndTag();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}