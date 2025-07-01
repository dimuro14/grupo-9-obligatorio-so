import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import com.sun.management.OperatingSystemMXBean;
import java.util.*;
import java.time.Instant;

public class MonitorRecursos implements Runnable {
    private final OperatingSystemMXBean osBean;
    private final MemoryMXBean memBean;
    private final List<ResourceSample> samples = Collections.synchronizedList(new ArrayList<>());
    private final int intervaloMs;
    private volatile boolean running = true;

    public MonitorRecursos(int intervaloMs) {
        this.osBean = (OperatingSystemMXBean)
            ManagementFactory.getOperatingSystemMXBean();
        this.memBean = ManagementFactory.getMemoryMXBean();
        this.intervaloMs = intervaloMs;
    }

    @Override
    public void run() {
        try {
            while (running) {
                long ts = Instant.now().toEpochMilli();
                double sysCpu  = osBean.getSystemCpuLoad();   // 0.0–1.0
                double procCpu = osBean.getProcessCpuLoad();  // 0.0–1.0
                long heapUsed  = memBean.getHeapMemoryUsage().getUsed();
                long heapMax   = memBean.getHeapMemoryUsage().getMax();
                samples.add(new ResourceSample(ts, sysCpu, procCpu, heapUsed, heapMax));
                Thread.sleep(intervaloMs);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
    }

    /** Vuelca al log todas las muestras y un resumen estadístico. */
    public void logReport() {
        Logger.log("=== Informe de recursos del sistema ===");
        // 1) Volcar cada muestra cruda (opcional si son pocas)
        for (ResourceSample s : samples) {
            Logger.log(s.toString());
        }

        // 2) Calcular promedios y máximos
        double avgSysCpu = samples.stream().mapToDouble(ResourceSample::getSysCpu).average().orElse(0);
        double avgProcCpu = samples.stream().mapToDouble(ResourceSample::getProcCpu).average().orElse(0);
        long maxHeapUsed = samples.stream().mapToLong(ResourceSample::getHeapUsed).max().orElse(0);
        long avgHeapUsed = (long) samples.stream().mapToLong(ResourceSample::getHeapUsed).average().orElse(0);

        Logger.log(String.format("CPU Sistema  promedio: %.2f%%", 100 * avgSysCpu));
        Logger.log(String.format("CPU Proceso promedio: %.2f%%", 100 * avgProcCpu));
        Logger.log(String.format("Heap usado   promedio: %d bytes", avgHeapUsed));
        Logger.log(String.format("Heap usado   máximo : %d bytes", maxHeapUsed));
        Logger.log("========================================");
    }

    /** Clase interna para almacenar una medición puntual */
    private static class ResourceSample {
        private final long timestamp;
        private final double sysCpu, procCpu;
        private final long heapUsed, heapMax;

        ResourceSample(long ts, double sys, double proc, long used, long max) {
            this.timestamp = ts;
            this.sysCpu = sys;
            this.procCpu = proc;
            this.heapUsed = used;
            this.heapMax = max;
        }

        public double getSysCpu()  { return sysCpu; }
        public double getProcCpu() { return procCpu; }
        public long   getHeapUsed(){ return heapUsed; }

        @Override
        public String toString() {
            return String.format(
              "[%d] sysCpu=%.2f%%, procCpu=%.2f%%, heapUsed=%d/%d",
              timestamp, 100*sysCpu, 100*procCpu, heapUsed, heapMax
            );
        }
    }
}
