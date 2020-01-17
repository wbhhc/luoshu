package wbhhc.luoshu;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LuoShuTest {

    @Test
    public void doValied() {
        LuoShu luoShu =new LuoShu();
        luoShu.setDefault();
        boolean res = luoShu.doValied();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(luoShu.toString());
    }

    @Test
    public void doValied_customInput() {

        LuoShu luoShu =new LuoShu( 8,1,6,
                            3,5,7,
                            4,9,2);
        boolean res = luoShu.doValied();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(luoShu.toString());
    }

    @Test(expected = RuntimeException.class)
    public void autofill_throw_countenf() {
        LuoShu luoShu =new LuoShu( 0,0,0,
                            0,5,0,
                            0,9,0);
        boolean res = luoShu.autoFill();
    }

    @Test(expected = RuntimeException.class)
    public void autofill_throw_isSymmetry() {
        LuoShu luoShu =new LuoShu( 8,0,0,
                            0,5,0,
                            0,0,2);
        boolean res = luoShu.autoFill();
    }

    @Test
    public void autofill() {
        LuoShu luoShu =new LuoShu( 0,0,6,
                            3,5,0,
                            0,0,0);
        boolean res = luoShu.autoFill();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(luoShu.toString());
    }

    /**
     * 旋转角度顺时针90度
     */
    @Test
    public void autofill_rotating90() {
        LuoShu luoShu =new LuoShu( 0,3,0,
                            0,5,0,
                            0,0,6);
        boolean res = luoShu.autoFill();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(luoShu.toString());
    }

    /**
     * 旋转角度180度
     */
    @Test
    public void autofill_rotating180() {
        LuoShu luoShu =new LuoShu( 0,0,0,
                            0,5,3,
                            6,0,0);
        boolean res = luoShu.autoFill();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(luoShu.toString());
    }

    @Test
    public void rotate90() {
        LuoShu luoShu =new LuoShu();
        luoShu.setDefault();
        luoShu.rotate90();

        assertThat(4,is(luoShu.getRow1col3()));
        assertThat(9,is(luoShu.getRow2col3()));
        assertThat(2,is(luoShu.getRow3col3()));

        System.out.println(luoShu.toString());
    }
}