package jp.enpit.cloud.ritesavre.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class InitializeServlet extends GenericServlet {
	private static final long serialVersionUID = 3818927870090006L;

	/**
     * TEM のロード時に一度だけ呼び出される． 
     * TEMアプリケーションの初期設定を行いたい場合は，ここに追記していく．
     */
    public void init(ServletConfig config) {
        initialLogging();
    }

    private void initialLogging() {
        LogManager manager = LogManager.getLogManager();
        Logger logger = Logger.getLogger("jp.enpit.cloud.eventspiral");

        try (InputStream in = getClass().getResourceAsStream("/logging.properties")) {
            manager.readConfiguration(in);

            logger.info("DONE: logging initialization");
        } catch (IOException e) {
            logger.warning("I/O error in reading logging property");
        }
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        // do nothing
    }
}
