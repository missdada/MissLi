package cn.manytag.manytagUtil.util.pagination;

public class RowRange
{

	public static final int NO_ROW_OFFSET = 0;
	public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
	
	public static final RowRange DEFAULT = new RowRange();
	
	private int offset;
	private int limit;
	
	public RowRange()
	{
		this.offset = NO_ROW_OFFSET;
		this.limit = NO_ROW_LIMIT;
	}
	
	public RowRange(int offset, int limit)
	{
		this.offset = offset;
		this.limit = limit;
	}
	
	public int getOffset()
	{
		return this.offset;
	}
	
	public int getLimit()
	{
		return this.limit;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("offset = ").append(offset).append(", ");
		sb.append("limit = ").append(limit);
		sb.append("}");
		return sb.toString();
	}
	
}
