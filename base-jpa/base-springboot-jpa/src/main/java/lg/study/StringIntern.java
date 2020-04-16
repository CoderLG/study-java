package lg.study;

/**
 * author: LG
 * date: 2020-01-07 10:35
 * desc:
 * 对string 上锁的时候 锁不住
 * 调用intern
 */
public class StringIntern {
    public static void main(String[] args) {

        String new1 = new String("x");
        String new2 = new String("x");
        String intern1 = new String("x").intern();
        System.out.println(new1==new2);
        System.out.println(new2==intern1);
        System.out.println(new1==intern1);
        String intern2 = new String("x").intern();
        System.out.println(intern2==intern1);
        String local = "x";
        System.out.println(intern2==local);

    }
}
