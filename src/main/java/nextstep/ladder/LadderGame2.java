package nextstep.ladder;

import nextstep.ladder.biz.Chessmen;
import nextstep.ladder.biz.Ladder2;
import nextstep.ladder.biz.LadderGenerator;
import nextstep.ladder.ui.LadderRenderer2;
import nextstep.ladder.ui.ViewInput;
import nextstep.ladder.ui.ViewOutput;

import java.util.Scanner;
import java.util.stream.Collectors;

public class LadderGame2 {

  private final ViewInput viewInput;

  public LadderGame2() {
    Scanner scanner = new Scanner(System.in);
    viewInput = new ViewInput(scanner);
  }

  private Players responsePlayer() {
    return Players.mapToPlayers(viewInput.requestPlayerNames());
  }

  private int responseLadderHeight() {
    return viewInput.requestLadderHeight();
  }

  private Prizes responsePrizes(int countOfPrize) {
    return Prizes.mapToPrizes(viewInput.requestPrizes(countOfPrize));
  }

  private void announce(Players players, Ladder2 ladder, Prizes prizes) {
    Player player = responsePlayerOfWinners(players);
    if (players.isIndividual(player)) {
      announceIndividual(players, ladder, prizes, player);
    }

    if (players.isAllPlayers(player)) {
      announceAllPlayers(players, ladder, prizes);
    }
  }

  private void announceIndividual(Players players, Ladder2 ladder, Prizes prizes, Player player) {
    if (players.existPlayer(player)) {
      Chessmen chessmen = ladder.play(Chessmen.of(player));

      ViewInput.printIndividual(prizes.prizeOf(chessmen));
    }

    announce(players, ladder, prizes);
  }

  private void announceAllPlayers(Players players, Ladder2 ladder, Prizes prizes) {
    ViewInput.printFinal(players.chessmenAsList().stream()
            .map(ladder::play)
            .collect(Collectors.toList()), prizes);
  }

  private Player responsePlayerOfWinners(Players players) {
    return players.nameToPlayer(viewInput.requestNameOfWinners());
  }

  public static void main(String[] args) {

    // 입력
    LadderGame2 ladderGame = new LadderGame2();
    Players players = ladderGame.responsePlayer();
    int ladderHeight = ladderGame.responseLadderHeight();
    Prizes prizes = ladderGame.responsePrizes(players.size());

    // 사다리 데이터 생성
    Ladder2 ladder = LadderGenerator.generate2(players.size(), ladderHeight);

    // 출력
    ViewOutput.printLadder(new LadderRenderer2(players, ladder, prizes));

    // 발표
    ladderGame.announce(players, ladder, prizes);
  }
}
