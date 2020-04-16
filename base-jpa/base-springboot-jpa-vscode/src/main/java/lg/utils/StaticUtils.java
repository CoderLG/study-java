package lg.utils;

import lg.dvo.StaticVo;

import java.util.Properties;

/**
 * 1.静态变量 静态代码块 静态方法被执行的顺序
 * 静态变量 > 静态代码块 > 静态方法（）
 *
 * 2.final 修饰的成员只能初始化一次，不能被修改
 *
 * 3.根据lg.utils.StaticUtilTest#getProperty()
 * 得出静态变量是单例的
 */
public class StaticUtils {

	private static Properties prop = new Properties();

	private static StaticVo staticVo = new StaticVo();

    static {
        System.out.println("initStaticBlock running");
    }



    public  Properties getPropertyObj() {
        return prop;
    }

	/**
	 * 取属性值
	 *
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
	    System.out.println("staticMethon running");
		return prop.getProperty(key);
	}

	/**
	 * 设置属性值
	 *
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		prop.setProperty(key, value);
	}
}