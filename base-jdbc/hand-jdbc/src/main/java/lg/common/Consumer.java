package lg.common;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T1, T2> {

	void accept(T1 t1, T2 t2);

	default Consumer<T1, T2> andThen(Consumer<? super T1, ? super T2> after) {
		Objects.requireNonNull(after);
		return (T1 t1, T2 t2) -> {
			accept(t1, t2);
			after.accept(t1, t2);
		};
	}
}
