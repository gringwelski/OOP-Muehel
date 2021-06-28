package common;

public enum PlayerColor {
  WHITE("schwarz"), BLACK("weiß"), NONE("keine");

  private final String color;

  PlayerColor(String color) {
    this.color = color;
  }

  public String getColorName() {
    return color;
  }
}
