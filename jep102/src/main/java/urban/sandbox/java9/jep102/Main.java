package urban.sandbox.java9.jep102;

import java.io.IOException;
import java.lang.ProcessHandle.Info;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class Main {

    public static void main(final String[] args) throws IOException, InterruptedException {
        final ProcessHandle self = ProcessHandle.current();
        final long PID = self.pid();
        final Info procInfo = self.info();

        final Optional<String[]> procArgs = procInfo.arguments();
        final Optional<String> cmd = procInfo.commandLine();
        final Optional<Instant> startTime = procInfo.startInstant();
        final Optional<Duration> cpuUsage = procInfo.totalCpuDuration();

        System.out.println("PID = " + PID);
        System.out.println("Args = " + procArgs);
        System.out.println("CMD = " + cmd);
        System.out.println("Start Time = " + startTime);
        System.out.println("CPU Usage = " + cpuUsage);

        final Process geditProcess = new ProcessBuilder("gedit").start();
        System.out.println("gedit PID = " + geditProcess.pid());
        System.out.println("gedit isalive = " + geditProcess.isAlive());
        System.out.println("gedit pareint pid = " + geditProcess.toHandle().parent().get().pid());

        Thread.sleep(5000);

        self.children().forEach(child -> {
            System.out.println("killing child pid = " + child.pid());
            boolean destroyed = child.destroy();
            System.out.println("Destroyed? = " + destroyed);
        });

        System.out.println("gedit isalive = " + geditProcess.isAlive());
    }

}