package nextstep.ladder.ui;

import nextstep.ladder.Players;
import nextstep.ladder.Prizes;
import nextstep.ladder.biz.Ladder2;
import nextstep.ladder.biz.LadderLine;
import nextstep.ladder.biz.PointDirection;

import java.util.List;
import java.util.stream.Collectors;

public class LadderRenderer2 implements Renderer {

  private static final String AIR = "        ";
  private static final String BRIDGE = "--------";
  private static final String PIER = "|";

  private Players players;
  private Ladder2 ladder;
  private Prizes prizes;

  public LadderRenderer2(Players players, Ladder2 ladder, Prizes prizes) {
    this.players = players;
    this.ladder = ladder;
    this.prizes = prizes;
  }

  @Override
  public void render() {
    renderPlayers(players);
    renderLadder(ladder);
    renderPrizes(prizes);
  }

  public void renderLadder(Ladder2 ladder) {
    for (LadderLine line : ladder.getLines()) {
      renderLadder(line);
    }
  }

  private void renderLadder(LadderLine line) {
    System.out.printf("%4s%s\n", "", pointToBridge(line.getPoints()));
  }

  private String pointToBridge(List<PointDirection> points) {
    return points.stream()
            .map(this::toBridge)
            .collect(Collectors.joining(PIER, PIER, ""));
  }

  private String toBridge(PointDirection point) {
    return point.hasPoint() ? BRIDGE : AIR;
  }

  private void renderPlayers(Players playerNames) {
    System.out.println(playerNames.nameAsList().stream()
            .map(LadderRenderer2::center)
            .collect(Collectors.joining("", "\n", "")));
  }

  private void renderPrizes(Prizes prizes) {
    System.out.println(
            prizes.nameToList().stream()
                    .map(LadderRenderer2::center)
                    .collect(Collectors.joining()));

  }

  static String center(String text) {
    int emptyCount = BRIDGE.length() + PIER.length() - text.length();
    int lpad = emptyCount / 2;
    int rpad = emptyCount - lpad;

    return String.format("%s%s%s", padding(lpad), text, padding(rpad));
  }

  static String padding(int length) {
    return String.format("%" + length + "s", "");
  }

}
