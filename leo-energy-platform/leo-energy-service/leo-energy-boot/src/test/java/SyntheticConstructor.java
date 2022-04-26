import java.lang.reflect.Constructor;

/**
 * @Author:gonglong
 * @Date:2022/4/24 17:08
 */
public class SyntheticConstructor {
    private NestedClass nestedClass = new NestedClass();

    class NestedClass {
        private NestedClass() {}
    }
    public static void main(String[] args) {
        Constructor<?>[] constructors = SyntheticConstructor.NestedClass
                .class.getDeclaredConstructors();

        for (Constructor<?> c : constructors) {
            System.out.println("Constructor: " + c.getName() +
                    ", isSynthetic: " + c.isSynthetic());
        }
    }
}
