package com.pingan.finance.risk.info.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public abstract class AbstractTransfer<Source, Target> implements
		Transfer<Source, Target> {
	private Class<Source> sourceClazz;
	private Class<Target> targetClazz;

	public AbstractTransfer() {
		Type[] c = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments();
		sourceClazz = (Class) c[0];
		targetClazz = (Class) c[1];
	}

	@Override
	public void convertExt(Source source, Target target) {

	}

	@Override
	public void reverseConvertExt(Target target, Source source) {
	}

	@Override
	public List<Source> reverseConvert(List<Target> targetList) {
		if (CollectionUtils.isEmpty(targetList)) {
			return null;
		}
		List<Source> sourceList = new ArrayList<Source>(targetList.size());
		for (Target target : targetList) {
			Source source;
			try {
				source = (Source) sourceClazz.newInstance();

			} catch (IllegalAccessException illegalAccessException) {
				throw new RuntimeException();
			} catch (InstantiationException illegalAccessException) {
				throw new RuntimeException();
			}

			BeanUtils.copyProperties(target, source);
			reverseConvertExt(target, source);
			sourceList.add(source);
		}
		return sourceList;
	}

	@Override
	public List<Target> convert(List<Source> sourceList) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return null;
		}
		List<Target> targetList = new ArrayList<Target>(sourceList.size());
		for (Source source : sourceList) {
			Target target;
			try {
				target = (Target) targetClazz.newInstance();
			} catch (IllegalAccessException illegalAccessException) {
				throw new RuntimeException();
			} catch (InstantiationException illegalAccessException) {
				throw new RuntimeException();
			}
			BeanUtils.copyProperties(source, target);
			convertExt(source, target);
			targetList.add(target);
		}
		return targetList;

	}

	@Override
	public Target convert(Source source) {
		if (source == null) {
			return null;
		}
		Target target;
		try {
			target = (Target) targetClazz.newInstance();
		} catch (IllegalAccessException illegalAccessException) {
			throw new RuntimeException();
		} catch (InstantiationException illegalAccessException) {
			throw new RuntimeException();
		}
		BeanUtils.copyProperties(source, target);
		convertExt(source, target);
		return target;
	}

	@Override
	public Source reverseConvert(Target target) {
		if (target == null) {
			return null;
		}
		Source source;
		try {
			source = (Source) sourceClazz.newInstance();
		} catch (IllegalAccessException illegalAccessException) {
			throw new RuntimeException();
		} catch (InstantiationException illegalAccessException) {
			throw new RuntimeException();
		}
		BeanUtils.copyProperties(target, source);
		reverseConvertExt(target, source);
		return source;
	}
}
