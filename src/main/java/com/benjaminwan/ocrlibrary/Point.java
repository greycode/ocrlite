package com.benjaminwan.ocrlibrary;

import java.util.Objects;

public class Point {
  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public  int getX() {
    return this.x;
  }

  public  void setX(int var1) {
    this.x = var1;
  }

  public  int getY() {
    return this.y;
  }

  public  void setY(int var1) {
    this.y = var1;
  }

  public  int component1() {
    return this.x;
  }

  public  int component2() {
    return this.y;
  }

  public  Point copy(int x, int y) {
    return new Point(x, y);
  }

  public String toString() {
    return "Point(x=" + this.x + ", y=" + this.y + ')';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point point = (Point) o;
    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
