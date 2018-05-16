package com.pingan.finance.xyd.entry.common;

import java.util.List;

public interface Transfer<Source, Target> {
	void convertExt(Source source, Target target);

	void reverseConvertExt(Target target, Source source);

	List<Target> convert(List<Source> sourceList);

	List<Source> reverseConvert(List<Target> targetList);

	Target convert(Source source);

	Source reverseConvert(Target target);
}
