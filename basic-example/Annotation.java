import java.lang.reflect.Method;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Annotation {
    @SuppressWarnings("cast")
    // 加了SuppressWarnings，还是有警告，不知道是因为该警告在范围之外，还是因为 SuppressWarnings的@Retention(RetentionPolicy.SOURCE)
    public static void main(String[] args) {
        try {
            Method method = Annotation.class.getMethod("doSomething", null);
            if(method.isAnnotationPresent(MyAnnotation.class))//如果doSomething方法上存在注解@MyTarget，则为true  
            {
                System.out.println(method.getAnnotation(MyAnnotation.class)); // 打印 @MyAnnotation()
            }
        } catch (NoSuchMethodException e) {

        }
    }

    @MyAnnotation
    public void doSomething() {
        System.out.println("hello world");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {

}
