package com.midea.trade.rws.util;

import java.util.HashMap;
import java.util.Map;


public class DsConfDO {

	private Map<String, String> connectionProperties = new HashMap<String, String>();

	/**
	 * 写 次数限制
	 */
	private int writeRestrictTimes;

	/**
	 * 读 次数限制
	 */
	private int readRestrictTimes;

	/**
	 * 统计时间片 
	 */
	private int timeSliceInMillis;

	/**
	 * 线程技术count限制
	 */
	private int threadCountRestrict;

	/**
	 * 允许并发读的最大个数，0为不限制
	 */
	private int maxConcurrentReadRestrict;

	/**
	 * 允许并发写的最大个数，0为不限制
	 */
	private int maxConcurrentWriteRestrict;
	
	private volatile boolean isSingleInGroup;


	public int getWriteRestrictTimes() {
		return writeRestrictTimes;
	}

	public void setWriteRestrictTimes(int writeRestrictTimes) {
		this.writeRestrictTimes = writeRestrictTimes;
	}

	public int getReadRestrictTimes() {
		return readRestrictTimes;
	}

	public void setReadRestrictTimes(int readRestrictTimes) {
		this.readRestrictTimes = readRestrictTimes;
	}

	public int getThreadCountRestrict() {
		return threadCountRestrict;
	}

	public void setThreadCountRestrict(int threadCountRestrict) {
		this.threadCountRestrict = threadCountRestrict;
	}

	public int getTimeSliceInMillis() {
		return timeSliceInMillis;
	}

	public void setTimeSliceInMillis(int timeSliceInMillis) {
		this.timeSliceInMillis = timeSliceInMillis;
	}

	public int getMaxConcurrentReadRestrict() {
		return maxConcurrentReadRestrict;
	}

	public void setMaxConcurrentReadRestrict(int maxConcurrentReadRestrict) {
		this.maxConcurrentReadRestrict = maxConcurrentReadRestrict;
	}

	public int getMaxConcurrentWriteRestrict() {
		return maxConcurrentWriteRestrict;
	}

	public void setMaxConcurrentWriteRestrict(int maxConcurrentWriteRestrict) {
		this.maxConcurrentWriteRestrict = maxConcurrentWriteRestrict;
	}

	public DsConfDO clone() {
		DsConfDO dsConfDO = null;
		try {
			dsConfDO = (DsConfDO) super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return dsConfDO;
	}

}
