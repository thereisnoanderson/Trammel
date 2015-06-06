package info.spotcomms.jhostsblock;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Created using IntelliJ IDEA
 * User: Tad
 * Date: 5/13/15
 * Time; 12:24 PM
 */
public class Utils {

    private String getOS() {
        try {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if (os.contains("linux")) {
                return "Linux";
            }
            if (os.startsWith("mac")) {
                return "Mac";
            }
            if (os.startsWith("win")) {
                return "Windows";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public File getConfigDir() {
        File configDir = new File("");
        switch (getOS()) {
            case "Linux":
                configDir = new File("/etc/jhostsblock/");
                break;
            case "Mac":
                configDir = new File(
                    System.getProperty("user.home") + "/Library/Application Support/JHostsBlock/");
                break;
            case "Windows":
                configDir = new File(System.getenv("AppData") + "/JHostsBlock/");
                break;
        }
        return configDir;
    }

    public File getHostsFile() {
        File hostsFile = new File("");
        switch (getOS()) {
            case "Linux":
                hostsFile = new File("/etc/", "hosts");
                break;
            case "Mac":
                hostsFile = new File("/private/etc/", "hosts");
                break;
            case "Windows":
                hostsFile = new File("C:/System32/drivers/etc/", "hosts");
                break;
        }
        return hostsFile;
    }

    //Credit: http://stackoverflow.com/a/4895572
    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public String identifyFileType(
        String url) {//Attempt to identify the file type based off the URL
        String extension = ".txt";
        if (url.contains("zip")) {
            extension = ".zip";
        } else if (url.contains("gz")) {
            extension = ".gz";
        } else if (url.contains(".hosts")) {
            extension = ".txt";
        }
        return extension;
    }

    public ArrayList<String> readFileIntoArray(
        File in) {//Read a file and put each line into an array
        ArrayList<String> out = new ArrayList<String>();
        try {
            Scanner fileIn = new Scanner(in);
            while (fileIn.hasNext()) {
                String line = fileIn.nextLine();
                if (!line.contains("#"))
                    out.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public ArrayList<String> readHostsFileIntoArray(
        File in) {//Read a compressed and put each line into an array
        ArrayList<String> out = new ArrayList<String>();
        try {
            Scanner fileIn = null;
            if (identifyFileType(in.toString()).contains(".txt")) {//Plain text
                fileIn = new Scanner(in);
            }
            if (identifyFileType(in.toString()).contains(".zip")) {//Decompress ZIP
                //Credit: http://stackoverflow.com/a/14656534 and http://stackoverflow.com/a/18974782
                ZipFile compressedList = new ZipFile(in);
                ArrayList compressedFiles = (ArrayList) compressedList.getFileHeaders();
                for (Object file : compressedFiles) {
                    FileHeader newFile = (FileHeader) file;
                    if (newFile.getFileName().equalsIgnoreCase("hosts") || newFile.getFileName()
                        .startsWith("hosts")) {
                        fileIn = new Scanner(compressedList.getInputStream(newFile));
                    }
                }
            }
            if (identifyFileType(in.toString()).contains(".gz")) {//Decompress GunZip
                fileIn = new Scanner(new GZIPInputStream(new FileInputStream(in)));
            }
            while (fileIn.hasNext()) {
                String line = fileIn.nextLine();
                if (!line.startsWith("#")) {
                    if (!line.trim().equals("")) {
                        if (line.startsWith("0.0.0.0")) {
                            line = "127.0.0.1" + line.substring(7, line.length());
                        }
                        if (!line.startsWith("127.0.0.1") || !line.contains("127.0.0.1")) {
                            line = "127.0.0.1 " + line;
                        }
                        if (line.contains("\t")) {
                            line.replaceAll("\t", " ");
                        }
                        if (line.contains("\t ")) {
                            line.replaceAll("\t ", "");
                        }
                        out.add(line.trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public void downloadFile(String url, Path out) {//Downloads a file from a website
        try {
            Files.copy(new URL(url).openStream(), out, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Credit: http://stackoverflow.com/a/5741080
    public void removeDuplicates(ArrayList<String> l) {//Remove duplicates from an array
        Set<Object> s = new TreeSet<Object>(new Comparator<Object>() {
            @Override
            public int compare(Object o1,
                Object o2) {//Compare the two object according to your requirements
                return 0;
            }
        });
        s.addAll(l);
        List<Object> res = Arrays.asList(s.toArray());
    }

}
