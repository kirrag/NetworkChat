package ru.netology.share;

import java.time.LocalDateTime;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Logger {
    private static Logger INSTANCE = null;

	private	static File logFile;
	
    private BufferedWriter out;

    private Logger() { }

    public static Logger getInstance() {
        if (INSTANCE == null) {
            synchronized (Logger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Logger();
                }
            }
        }
        return INSTANCE;
    }
	
	public static void setLogFile(String dir, String file) {
		logFile = new File(dir, file);
	}

    public void log(String msg) {
        
		String message = LocalDateTime.now() + " - " + msg;

		try {
            out = new BufferedWriter(new FileWriter(logFile, true));
			if(logFile.exists()) {
            	out.write(message);
				out.newLine();
            	out.close();
			} else {
        		boolean created = logFile.createNewFile();
        		if(created) out.write(message);
            	out.close();
			}
	    } catch (IOException e) {
    	    System.out.println(e.getMessage());
	    }
    }	
}
