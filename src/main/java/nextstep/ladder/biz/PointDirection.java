package nextstep.ladder.biz;

public class PointDirection {

  private final int index;
  private final Direction direction;

  private PointDirection(int index, Direction direction) {
    this.index = index;
    this.direction = direction;
  }

  public int move() {
    if (direction.isRight()) {
      return index + 1;
    }

    if (direction.isLeft()) {
      return index - 1;
    }

    return this.index;
  }

  public PointDirection next() {
    return new PointDirection(index + 1, direction.next());
  }

  public PointDirection next(boolean nextRight) {
    return new PointDirection(index + 1, direction.next(nextRight));
  }

  public static PointDirection first(boolean right) {
    return new PointDirection(0, Direction.first(right));
  }
}
