/**
 * @Author:gonglong
 * @Date:2022/4/13 11:22
 */
public class BeanUtilTest {
    public static void main(String[] args) {
        try {
            Class<?> foo = Class.forName("Foo", true, BeanUtilTest.class.getClassLoader());
            System.out.println(foo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
