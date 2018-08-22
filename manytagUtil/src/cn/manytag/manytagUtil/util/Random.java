package cn.manytag.manytagUtil.util;

public class Random extends java.util.Random {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8021242512066394492L;

	/**
	 * 返回 begin ~ end 的值，含头不含尾
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public int nextInt(int begin, int end) {
		return begin == end ? begin : begin + nextInt(end - begin);
	}
}
