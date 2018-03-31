package com.a.myapplication;

import java.io.File;
import java.io.FileFilter;

public class VideoFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory() || isVideoFile(pathname.getAbsolutePath());
    }


    public boolean isVideoFile(String path) {

        boolean isVideo = false;
        String suffix;
        try {
            suffix = path.substring(path.lastIndexOf(".") + 1);
        } catch (IndexOutOfBoundsException e) {
            suffix = "";
        }

        if (suffix.equalsIgnoreCase("mp4") || suffix.equalsIgnoreCase("avi") ||
                suffix.equalsIgnoreCase("wmv") || suffix.equalsIgnoreCase("mpeg") ||
                suffix.equalsIgnoreCase("mov") || suffix.equalsIgnoreCase("vob") ||
                suffix.equalsIgnoreCase("mkv") || suffix.equalsIgnoreCase("flv") ||
                suffix.equalsIgnoreCase("3gp") || suffix.equalsIgnoreCase("rm") ||
                suffix.equalsIgnoreCase("rmvb") || suffix.equalsIgnoreCase("asf") ||
                suffix.equalsIgnoreCase("dat") || suffix.equalsIgnoreCase("mpg")) {
            isVideo = true;
        }

        return isVideo;
    }
}
