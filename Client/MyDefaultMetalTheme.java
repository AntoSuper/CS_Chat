
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import java.awt.Color;

class MyDefaultMetalTheme extends DefaultMetalTheme {
  public ColorUIResource getWindowTitleInactiveBackground() {
    return new ColorUIResource(new Color(173,216,230));
  }

  public ColorUIResource getWindowTitleBackground() {
    return new ColorUIResource(new Color(173,216,230));
  }

  public ColorUIResource getPrimaryControlHighlight() {
    return new ColorUIResource(new Color(173,216,230));
  }

  public ColorUIResource getPrimaryControl() {
    return new ColorUIResource(new Color(173,216,230));
  }

  public ColorUIResource getControlHighlight() {
    return new ColorUIResource(new Color(173,216,230));
  }
  
  public ColorUIResource getControl() {
    return new ColorUIResource(new Color(173,216,230));
  }
}