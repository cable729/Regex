import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SimpleRegexTests {
    RegexNfa a = Regex.Regex("a");
    RegexNfa ab = Regex.Regex("ab");

    @Test
    public void aDoesNotAcceptB() {
        Assert.assertFalse(a.Accept("b"));
        Assert.assertFalse(a.Accept("bb"));
        Assert.assertFalse(a.Accept("bbe"));
        Assert.assertFalse(a.Accept("eb"));
        Assert.assertFalse(a.Accept("ebb"));
        Assert.assertFalse(a.Accept("ebeb"));
    }

    @Test
    public void aAccepts() {
        Assert.assertTrue(a.Accept("a"));
        Assert.assertTrue(a.Accept("ea"));
        Assert.assertTrue(a.Accept("eeeaeee"));
    }

    @Test
    public void abNotAccepts() {
        Assert.assertFalse(a.Accept("abb"));
        Assert.assertFalse(a.Accept("abab"));
        Assert.assertFalse(a.Accept("bab"));
        Assert.assertFalse(a.Accept("bb"));
        Assert.assertFalse(a.Accept("e"));
        Assert.assertFalse(a.Accept(""));
    }

    @Test
    public void abAccepts() {
        Assert.assertTrue(ab.Accept("ab"));
        Assert.assertTrue(ab.Accept("aeb"));
        Assert.assertTrue(ab.Accept("eab"));
        Assert.assertTrue(ab.Accept("eaebe"));
    }
}
