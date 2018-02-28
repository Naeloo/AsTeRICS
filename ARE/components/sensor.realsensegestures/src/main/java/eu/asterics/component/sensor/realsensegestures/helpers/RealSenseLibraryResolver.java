package eu.asterics.component.sensor.realsensegestures.helpers;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by leonhard on 2/28/18.
 */
public class RealSenseLibraryResolver {
    public enum OsType {
        WINDOWS, LINUX, BOTH
    }

    class LibraryEntry {
        OsType os;
        String libraryName;
        String windowsAppend = "";
        String windowsPrepend = "lib";

        public LibraryEntry(String libraryName, OsType os) {
            this.os = os; this.libraryName = libraryName;
        }
        public LibraryEntry(String libraryName, OsType os, String windowsAppend) {
            this.os = os; this.libraryName = libraryName; this.windowsAppend = windowsAppend;
        }
        public LibraryEntry(String libraryName, OsType os, String windowsAppend, String windowsPrepend) {
            this.os = os; this.libraryName = libraryName; this.windowsAppend = windowsAppend;
            this.windowsPrepend = windowsPrepend;
        }

        public String getWindowsLibraryName() {
            return this.windowsPrepend + libraryName + this.windowsAppend;
        }
        public String getLinuxLibraryName() {
            return libraryName;
        }
    }

    private ArrayList<LibraryEntry> libraries;
    private OsType localOS;

    public RealSenseLibraryResolver(){
        libraries = new ArrayList<>();
        String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (osName.contains("win")) {
            localOS = OsType.WINDOWS;
        } else if (osName.contains("nux")) {
            localOS = OsType.LINUX;
        }else {
            System.out.println("Unknown or unsupported OS " + osName);
        }

    }
    public void addLibrary(String libraryName, OsType os) {
        libraries.add(new LibraryEntry(libraryName, os));
    }
    public void addLibrary(String libraryName, OsType os, String windowsAppend) {
        libraries.add(new LibraryEntry(libraryName, os, windowsAppend));
    }
    public void addLibrary(String libraryName, OsType os, String windowsAppend, String windowsPrepend) {
        libraries.add(new LibraryEntry(libraryName, os, windowsAppend, windowsPrepend));
    }

    public void loadAllLibraries() {
        for(LibraryEntry le : libraries){
            if(localOS == OsType.LINUX && (localOS == le.os || OsType.BOTH == le.os)){
                System.loadLibrary(le.getLinuxLibraryName());
            }
            if(localOS == OsType.WINDOWS && (localOS == le.os || OsType.BOTH == le.os)){
                System.loadLibrary(le.getWindowsLibraryName());
            }
        }
    }
}
