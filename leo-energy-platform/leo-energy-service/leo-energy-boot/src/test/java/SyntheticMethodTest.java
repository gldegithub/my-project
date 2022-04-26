import java.lang.reflect.Method;

/**
 * @Author:gonglong
 * @Date:2022/4/24 16:52
 */
public class SyntheticMethodTest {
    class NestedClass {
        private String nestedField;
    }

    public String getNestedField() {
        return new NestedClass().nestedField;
    }

    public void setNestedField(String nestedField) {
        new NestedClass().nestedField = nestedField;
    }

    public static void main(String[] args) {
        Method[] methods = SyntheticMethodTest.NestedClass.class
                .getDeclaredMethods();
        for (Method m : methods) {
            System.out.println("Method: " + m.getName() + ", isSynthetic: " +
                    m.isSynthetic());
        }
    }
}
