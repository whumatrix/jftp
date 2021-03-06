/*
 * Copyright 2012 jMethods, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myjavaworld.jftp;

import java.util.Comparator;

/**
 * An implementation of <code>Comparator<code> interface used to
 * compare objects of
 * <code>LocalFile</code> using various criteria.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class LocalFileComparator implements Comparator {

	public static final int COMPARE_BY_NAME = 1;
	public static final int COMPARE_BY_TYPE = 2;
	public static final int COMPARE_BY_SIZE = 3;
	public static final int COMPARE_BY_DATE = 4;
	public static final int ASC_ORDER = 1;
	public static final int DESC_ORDER = 2;
	private int compareBy = 0;
	private int order = 0;

	public LocalFileComparator() {
		this(COMPARE_BY_NAME, ASC_ORDER);
	}

	public LocalFileComparator(int compareBy) {
		this(compareBy, ASC_ORDER);
	}

	public LocalFileComparator(int compareBy, int order) {
		setCompareBy(compareBy);
		setOrder(order);
	}

	public void setCompareBy(int compareBy) {
		this.compareBy = compareBy;
	}

	public int getCompareBy() {
		return compareBy;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public int compare(Object o1, Object o2) {
		LocalFile l1 = (LocalFile) o1;
		LocalFile l2 = (LocalFile) o2;
		if (compareBy == COMPARE_BY_NAME) {
			return compareByName(l1, l2);
		} else if (compareBy == COMPARE_BY_SIZE) {
			return compareBySize(l1, l2);
		} else if (compareBy == COMPARE_BY_DATE) {
			return compareByDate(l1, l2);
		} else {
			return compareByType(l1, l2);
		}
	}

	private int compareByName(LocalFile l1, LocalFile l2) {
		int retVal = 0;
		if (l1.isDrive() && !l2.isDrive()) {
			retVal = -1;
		} else if (!l1.isDrive() && l2.isDrive()) {
			retVal = 1;
		} else if (l1.isDirectory() && !l2.isDirectory()) {
			retVal = -1;
		} else if (!l1.isDirectory() && l2.isDirectory()) {
			retVal = 1;
		} else {
			String name1 = l1.isDrive() ? l1.getAbsolutePath() : l1
					.getDisplayName();
			String name2 = l2.isDrive() ? l2.getAbsolutePath() : l2
					.getDisplayName();
			retVal = name1.toUpperCase().compareTo(name2.toUpperCase());
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}

	private int compareBySize(LocalFile l1, LocalFile l2) {
		int retVal = 0;
		if (l1.isDirectory() && !l2.isDirectory()) {
			retVal = -1;
		} else if (!l1.isDirectory() && l2.isDirectory()) {
			retVal = 1;
		} else {
			if (l1.getSize() == l2.getSize()) {
				retVal = 0;
			} else {
				retVal = l1.getSize() < l2.getSize() ? -1 : 1;
			}
			if (retVal == 0) {
				String name1 = l1.isDrive() ? l1.getAbsolutePath() : l1
						.getDisplayName();
				String name2 = l2.isDrive() ? l2.getAbsolutePath() : l2
						.getDisplayName();
				retVal = name1.toUpperCase().compareTo(name2.toUpperCase());
			}
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}

	private int compareByDate(LocalFile l1, LocalFile l2) {
		int retVal = 0;
		if (l1.isDirectory() && !l2.isDirectory()) {
			retVal = -1;
		} else if (!l1.isDirectory() && l2.isDirectory()) {
			retVal = 1;
		} else {
			if (l1.getLastModified() == l2.getLastModified()) {
				retVal = 0;
			} else {
				retVal = l1.getLastModified() < l2.getLastModified() ? -1 : 1;
			}
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}

	private int compareByType(LocalFile l1, LocalFile l2) {
		int retVal = 0;
		if (l1.isDirectory() && !l2.isDirectory()) {
			retVal = -1;
		} else if (!l1.isDirectory() && l2.isDirectory()) {
			retVal = 1;
		} else {
			retVal = l1.getType().compareTo(l2.getType());
			if (retVal == 0) {
				String name1 = l1.isDrive() ? l1.getAbsolutePath() : l1
						.getDisplayName();
				String name2 = l2.isDrive() ? l2.getAbsolutePath() : l2
						.getDisplayName();
				retVal = name1.toUpperCase().compareTo(name2.toUpperCase());
			}
		}
		return order == ASC_ORDER ? retVal : -retVal;
	}
}