package org.firstinspires.ftc.teamcode.zLibraries.Files.Testing;

import org.firstinspires.ftc.teamcode.zLibraries.Files.FileUtils.LogWriter;

import java.util.ArrayList;
import java.util.List;

public class TestWriter implements LogWriter {

    private List<String> lines = new ArrayList<>();
    private boolean stopCalled;

    public TestWriter(){
        stopCalled = false;
    }

    public void writeLine(String line){
        lines.add(line);
    }

    public List<String> getLines(){
        return lines;
    }

    public void stop(){
        stopCalled = true;
    }

    public boolean isStopCalled(){
        return stopCalled;
    }

    @Override
    public boolean isWriting() {
        return false;
    }
}
