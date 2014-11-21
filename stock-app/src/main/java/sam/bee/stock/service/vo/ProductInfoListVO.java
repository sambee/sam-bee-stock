// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst noinners nonlb 
// S//source File Name:   ProductInfoListVO.java

package sam.bee.stock.service.vo;

import sam.bee.stock.vo.ProductInfoVO;

public class ProductInfoListVO {

	/**
	 * 
	 */
	public int date;

	/**
	 * 
	 */
	public int time;

	/**
	 * 
	 */
	public ProductInfoVO productInfos[];

	public ProductInfoListVO() {
		productInfos = new ProductInfoVO[0];
	}

	/**
	 * 
	 */
	public String toString() {
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**" + getClass().getName() + "**" + sep);
		sb.append("date:" + date + sep);
		sb.append("time:" + time + sep);
		for (int i = 0; i < productInfos.length; i++)
			sb.append(productInfos[i].toString());

		sb.append(sep);
		return sb.toString();
	}
}
