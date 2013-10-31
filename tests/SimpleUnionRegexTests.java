import org.junit.Assert;
import org.junit.Test;

public class SimpleUnionRegexTests {
    RegexNfa aUnionB = Regex.Regex("a|b");
    RegexNfa abUnionAaaaa = Regex.Regex("ab|aaaaa");

    @Test
    public void abUnionAaaaaFails() {
        Assert.assertFalse(abUnionAaaaa.Accept("e"));
        Assert.assertFalse(abUnionAaaaa.Accept("aa"));
        Assert.assertFalse(abUnionAaaaa.Accept("b"));
        Assert.assertFalse(abUnionAaaaa.Accept("ba"));
        Assert.assertFalse(abUnionAaaaa.Accept("aaaa"));
        Assert.assertFalse(abUnionAaaaa.Accept("aba"));
    }

    @Test
    public void setAbUnionAaaaaPasses() {
        Assert.assertTrue(abUnionAaaaa.Accept("ab"));
        Assert.assertTrue(abUnionAaaaa.Accept("aaaaa"));
    }

    @Test
    public void aUnionBFails() {
        Assert.assertFalse(aUnionB.Accept("e"));
        Assert.assertFalse(aUnionB.Accept("ab"));
        Assert.assertFalse(aUnionB.Accept("ba"));
        Assert.assertFalse(aUnionB.Accept("bb"));
        Assert.assertFalse(aUnionB.Accept("aaaba"));
    }

    @Test
    public void aUnionBPasses() {
        Assert.assertTrue(aUnionB.Accept("a"));
        Assert.assertTrue(aUnionB.Accept("b"));
    }
}
