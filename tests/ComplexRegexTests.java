import org.junit.Assert;
import org.junit.Test;

public class ComplexRegexTests {
    RegexNfa aUnionABStar = Regex.Regex("a|(ab)*");
    RegexNfa complex = Regex.Regex("a|ab*a|b(aa)*b");

    @Test
    public void complexFails() {
        Assert.assertFalse(complex.Accept("e"));
        Assert.assertFalse(complex.Accept("ab"));
        Assert.assertFalse(complex.Accept("abb"));
        Assert.assertFalse(complex.Accept("abbbbab"));
        Assert.assertFalse(complex.Accept("aab"));
        Assert.assertFalse(complex.Accept("bab"));
        Assert.assertFalse(complex.Accept("baaab"));
        Assert.assertFalse(complex.Accept("baaba"));
    }

    @Test
    public void aUnionABStarFails() {
        Assert.assertFalse(aUnionABStar.Accept("b"));
        Assert.assertFalse(aUnionABStar.Accept("ba"));
        Assert.assertFalse(aUnionABStar.Accept("bab"));
        Assert.assertFalse(aUnionABStar.Accept("bb"));
        Assert.assertFalse(aUnionABStar.Accept("aba"));
    }

    @Test
    public void aUnionABStarPasses() {
        Assert.assertTrue(aUnionABStar.Accept("e"));
        Assert.assertTrue(aUnionABStar.Accept("a"));
        Assert.assertTrue(aUnionABStar.Accept("ab"));
        Assert.assertTrue(aUnionABStar.Accept("abababab"));
        Assert.assertTrue(aUnionABStar.Accept("abab"));
    }
}
