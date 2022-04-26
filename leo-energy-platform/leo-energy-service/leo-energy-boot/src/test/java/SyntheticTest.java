import java.lang.reflect.Field;

/**
 * @Author:gonglong
 * @Date:2022/4/24 16:49
 */
public class SyntheticTest {
    public static void main(String[] args) {
        Field[] fields = SyntheticTest.NestedClass.class
                .getDeclaredFields();
        for (Field f : fields) {
            System.out.println("Field: " + f.getName() + ", isSynthetic: " +
                    f.isSynthetic());
        }
    }
    class NestedClass{}
}
