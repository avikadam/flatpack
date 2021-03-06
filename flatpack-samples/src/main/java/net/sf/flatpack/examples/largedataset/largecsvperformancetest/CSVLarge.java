package net.sf.flatpack.examples.largedataset.largecsvperformancetest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.flatpack.DataError;
import net.sf.flatpack.DataSet;
import net.sf.flatpack.brparse.BuffReaderDelimParser;
import net.sf.flatpack.brparse.BuffReaderParseFactory;

/*
 * Created on Dec 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author zepernick
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CSVLarge {
    private static final Logger LOG = LoggerFactory.getLogger(CSVLarge.class);

    public static void main(final String[] args) {
        try {
            final Map settings = readSettings();
            final String data = (String) settings.get("csvFile");
            call(data);
        } catch (final Exception e) {
            LOG.error("issue", e);
        }
    }

    public static String getDefaultDataFile() {
        return "LargeSampleCSV.csv";
    }

    public static void call(final String data) throws Exception {
        try (BuffReaderDelimParser pzparse = (BuffReaderDelimParser) BuffReaderParseFactory.getInstance().newDelimitedParser(new FileReader(data),
                ',', '"')) {

            // delimited by a comma
            // text qualified by double quotes
            // ignore first record

            final DataSet ds = pzparse.parse();
            final long timeStarted = System.currentTimeMillis();
            int totalCount = 0;
            int tmpCount = 0;
            while (ds.next()) {
                totalCount++;
                tmpCount++;
                if (tmpCount >= 2500) {
                    System.out.println("Read " + totalCount + " Records...");
                    tmpCount = 0;
                }
            }
            final long timeFinished = System.currentTimeMillis();

            String timeMessage = "";

            if (timeFinished - timeStarted < 1000) {
                timeMessage = timeFinished - timeStarted + " Milleseconds...";
            } else {
                timeMessage = (timeFinished - timeStarted) / 1000 + " Seconds...";
            }

            System.out.println("");
            System.out.println("********FILE PARSED IN: " + timeMessage + " ******");

            if (ds.getErrors() != null && !ds.getErrors().isEmpty()) {
                System.out.println("FOUND ERRORS IN FILE....");
                for (int i = 0; i < ds.getErrors().size(); i++) {
                    final DataError de = ds.getErrors().get(i);
                    System.out.println("Error: " + de.getErrorDesc() + " Line: " + de.getLineNo());
                }
            }
        } catch (final Exception ex) {
            LOG.error("Issue", ex);
        }
    }

    private static Map readSettings() throws Exception {
        final Map result = new HashMap();

        try (FileReader fr = new FileReader("settings.properties"); BufferedReader br = new BufferedReader(fr)) {

            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0 || line.startsWith("#") || line.indexOf("=") == -1) {
                    continue;
                }

                result.put(line.substring(0, line.indexOf("=")), line.substring(line.indexOf("=") + 1));
            }
        }

        return result;
    }
}
