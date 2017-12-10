package control;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import service.HQService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

/**
 * 实现在tomcat启动时执行某段代码
 */
public class InitListener implements ServletContextListener {

    public static ComboPooledDataSource dataSource = null;

    @Override
    public void contextDestroyed(ServletContextEvent context) {

    }

    /**
     * 自动加载的函数
     * @param context
     */
    @Override
    public void contextInitialized(ServletContextEvent context) {
        // 上下文初始化执行
        System.out.println("Auto start...");
        //需要实现的功能
//        HQService hqService = new HQService();
//        dataSource = new ComboPooledDataSource("CashMachine");
        System.out.println("Auto end.");
    }
}
