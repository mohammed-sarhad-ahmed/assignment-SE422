import java.util.concurrent.atomic.AtomicReference;

public class SharedCounter {
    private static final AtomicReference<FourThreadCountedValue> value =
            new AtomicReference<>(new FourThreadCountedValue(0));

    public static int getFourThreadCountedValue() {
        return value.get().counter;
    }

    public static void setFourThreadCountedValue() {
        while (true) {
            FourThreadCountedValue current = value.get();
            FourThreadCountedValue next = new FourThreadCountedValue(current.counter + 1);
            if (value.compareAndSet(current, next)) {
                break;
            }
        }
    }
}
