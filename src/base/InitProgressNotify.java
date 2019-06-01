package base;

/**
 * 数据初始化进度反馈 
 */
@FunctionalInterface
public interface InitProgressNotify {
	void notify(int progress);
}
