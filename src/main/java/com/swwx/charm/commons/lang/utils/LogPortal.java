/**
 * 
 */
package com.swwx.charm.commons.lang.utils;


import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class LogPortal {

	private static Logger logger = LoggerFactory.getLogger(LogPortal.class);

	public static void debug(String msg, Object ... objs){

		logger.debug(appendClassName2Msg(msg), objs);
	}

	public static void info(String msg,Object ... objs){

		logger.info(appendClassName2Msg(msg), objs);
	}

	private static String appendClassName2Msg(String msg) {
//		StackTraceElement[] strackArr = Thread.currentThread().getStackTrace();
//
//		StackTraceElement lastStrack = strackArr[strackArr.length - 1];
//
//		return lastStrack.getClassName() + "." + lastStrack.getMethodName() + " - " + msg;

		return msg;
	}

	public static void error(String msg,Object ... objs){
		logger.error(appendClassName2Msg(msg), objs);
	}

	public static void error(String msg,Throwable t){
		logger.error(appendClassName2Msg(msg),t);
	}

	/**
	 * 输出异常信息,异常会在msg后换行显示
	 * @param msg
	 * @param t
	 * @param objs
	 */
	public static void error(String msg,Throwable t, Object... objs) {
		String str = null;
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw, true);
			t.printStackTrace(pw);
			str = sw.toString();
		}catch (Exception e){
			error("输出异常信息时出错",e);
		}finally {
			try {
				if( sw != null )
					sw.close();
				if( pw != null )
					pw.close();
			} catch (IOException e) {
				error("输出异常信息时关闭IO出错",e);
			}
		}
		str = str == null ? "" : "\n"+str;
		logger.error(appendClassName2Msg(msg)+str, objs);
	}

	public static void setLogModule(String logModule){
		MDC.put("logModule", logModule);
	}

	public static void setLogLevel(LogLevel logLevel){
		((ch.qos.logback.classic.Logger)logger).setLevel(logLevel.getLevel());
	}

	public static enum LogLevel{
		ALL(Level.ALL),

		DEBUG(Level.DEBUG),

		INFO(Level.INFO),

		ERROR(Level.ERROR);

		private Level level;

		private LogLevel(Level level){
			this.level = level;
		}

		public Level getLevel(){
			return this.level;
		}
	}
}
