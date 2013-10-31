import org.junit.Assert;
import org.junit.Test;

public class SimpleStarRegexTests {
    RegexNfa aStar = Regex.Regex("a*");
    RegexNfa abStar = Regex.Regex("(ab)*");

    @Test
    public void abStarFails() {
        Assert.assertFalse(abStar.Accept("a"));
        Assert.assertFalse(abStar.Accept("b"));
        Assert.assertFalse(abStar.Accept("ba"));
        Assert.assertFalse(abStar.Accept("aba"));
        Assert.assertFalse(abStar.Accept("aaa"));
        Assert.assertFalse(abStar.Accept("bbb"));
        Assert.assertFalse(abStar.Accept("abababababa"));
    }

    @Test
    public void abStarPasses() {
        Assert.assertTrue(abStar.Accept(""));
        Assert.assertTrue(abStar.Accept("ab"));
        Assert.assertTrue(abStar.Accept("abababab"));
    }

    @Test
    public void aStarFails() {
        Assert.assertFalse(aStar.Accept("b"));
        Assert.assertFalse(aStar.Accept("bb"));
        Assert.assertFalse(aStar.Accept("bba"));
        Assert.assertFalse(aStar.Accept("babab"));
    }

    @Test
    public void aStarPasses() {
        Assert.assertTrue(aStar.Accept("e"));
        Assert.assertTrue(aStar.Accept(""));
        Assert.assertTrue(aStar.Accept("a"));
        Assert.assertTrue(aStar.Accept("aa"));
        Assert.assertTrue(aStar.Accept("aaaaaaaaaaa"));
    }
}
