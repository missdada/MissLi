package cn.manytag.manytagUtil.checkcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import cn.manytag.manytagUtil.util.Random;

public class Checkcode {

	private String code;
	private BufferedImage image;

	public Checkcode() {
		this(70, 30, 4);
	}

	/**
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param len 字符个数
	 */
	public Checkcode(int width, int height, int len) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 背景色的范围
		int colorBoundB = 60;
		// 前景色的范围
		int colorBoundP = 190;
		Graphics g = image.getGraphics();
		// 随机对象
		Random rand = new Random();
		boolean colorType = rand.nextBoolean();
		// 设置笔的颜色，值越大颜色约淡
		g.setColor(new Color(randColorValue(colorBoundB, colorType), randColorValue(colorBoundB, colorType), randColorValue(
				colorBoundB, colorType)));
		// 背景画个矩形，（填充背景色）
		g.fillRect(0, 0, width, height);

		// 简化随机字符，避免容易混淆输入错误的值 没有 数字（零 0）字母（大写 I）（小写l）(大小写 o O)
		char[] codes = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		// char[] codes = new char[] { '0', 'I', 'l', 'o', 'O'};

		// 字符颜色取值范围，和背景相反
		colorType = !colorType;
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < len; i++) {
			char codec = codes[rand.nextInt(codes.length)];
			strBuilder.append(codec);
			// 设置字体(字体,风格,大小)(风格有3种，0 普通，1 加粗，2 倾斜)
			g.setFont(new Font(null, rand.nextInt(3), 22 + rand.nextInt(4)));
			g.setColor(new Color(randColorValue(colorBoundP, colorType), randColorValue(colorBoundP, colorType), randColorValue(
					colorBoundP, colorType)));
			// 画字符 字符 X Y 坐标
			g.drawString(String.valueOf(codec), 1 + i * 16, 19 + rand.nextInt(8));
		}
		code = strBuilder.toString();

		// 加干扰物 5个线条
		for (int i = 0; i < 5; i++) {
			g.setColor(new Color(randColorValue(colorBoundP, colorType), randColorValue(colorBoundP, colorType), randColorValue(
					colorBoundP, colorType)));
			g.drawLine(rand.nextInt(width), rand.nextInt(height), rand.nextInt(width), rand.nextInt(height));
		}
	}

	private int randColorValue(int colorValue, boolean min) {
		Random rand = new Random();
		if (min) {
			return rand.nextInt(colorValue);
		} else {
			return rand.nextInt(255 - colorValue, 255);
		}
	}

	@Override
	public String toString() {
		return "Checkcode [code=" + code + "]";
	}

	public String getCode() {
		return code;
	}

	public BufferedImage getImage() {
		return image;
	}
}
