package common;

public enum PlayerColor {
  WHITE("weiß"), BLACK("schwarz"), NONE("keine");

  private final String color;

  PlayerColor(String color) {
    this.color = color;
  }

  public String getColorName() {
    return color;
  }
}
